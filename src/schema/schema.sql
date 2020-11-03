#drop database budget;
create database budget;
use budget;

create table Operation(
	id_operation Integer auto_increment,
	concept varchar(50) not null,
    amount long,
    operation_date date not null,
    operation_type ENUM("Input", "Output"),
	
	constraint pk_id_operation primary key (id_operation)
);

/*------------------------------------------------------------------------------------*/
#Obtener el balance actual: Funciones.
DELIMITER $$
CREATE FUNCTION getCurrentBalance()
RETURNS LONG
DETERMINISTIC
BEGIN
	declare vTotalInput long default 0;
    declare vTotalOutput long default 0;
    declare vTotal long default 0;
    
    select ifnull(sum(amount),0) into vTotalInput from operation where operation_type = 'Input';
    select ifnull(sum(amount),0) into vTotalOutput from operation where operation_type = 'Output';
    set vTotal = vTotalInput - vTotalOutput;
    return vTotal;
END
$$;
DELIMITER $$

/*------------------------------------------------------------------------------------*/
#Stored Procedure que será llamado desde Spring JPA para que pueda ser mostrado en el front-end.
DELIMITER $$
CREATE PROCEDURE sp_get_total(out total long)
BEGIN 
	declare vTotal long default 0;
    set total = getCurrentBalance();
END;
$$
DELIMITER ;

/*------------------------------------------------------------------------------------*/
#Ingreso de operaciones: Stored Procedure
DELIMITER $$
CREATE PROCEDURE sp_create_input(pConcept varchar(50), pAmount long, pOperation_date datetime, out idConcept int)
BEGIN 

	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		signal sqlstate '45000' SET MESSAGE_TEXT = 'Incorrect operation input data', MYSQL_ERRNO = 136;
    END;    

	INSERT INTO Operation(concept, amount, operation_date, operation_type)      
	VALUES (pConcept, pAmount, pOperation_date, "Input");
    set idConcept = last_insert_id();
END;
$$
DELIMITER ;

call sp_create_input('Sueldo depositado', 50000, '2020-11-04', @id);
call sp_create_input('Dolares convertidos a Pesos a su favor', 10000, '2020-11-01', @id);
call sp_create_input('PC vendida', 10000, '2020-10-01', @id);
call sp_create_input('Documentos a cobrar', 3200, '2020-01-20', @id);
call sp_create_input('Documentos a cobrar', 4000, '2020-12-16', @id);

/*------------------------------------------------------------------------------------*/
#Egreso de operaciones: Stored Procedure
DELIMITER $$
CREATE PROCEDURE sp_create_output(pConcept varchar(50), pAmount long, pOperation_date datetime, out idConcept int)
BEGIN 
	declare vTotal long default 0;
    set vTotal = getCurrentBalance();
    if (vTotal >= pAmount) then
		INSERT INTO Operation(concept, amount, operation_date, operation_type)      
		VALUES (pConcept, pAmount, pOperation_date, "Output");
		set idConcept = last_insert_id();
	else
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'Incorrect operation output data', MYSQL_ERRNO = 6;
    end if;
END;
$$
DELIMITER ;

call sp_create_output('Deuda - Banco Provincia', 1000, now(), @id);

/*------------------------------------------------------------------------------------*/
#Eliminacion de operaciones: Stored Procedure - Eliminado fisico ya que lo suprime de la base de datos.
DELIMITER $$
CREATE PROCEDURE sp_delete_operation(pId_operation int)
BEGIN 
    declare vIdOperation int default 0;
	SELECT ifnull(o.id_operation,0) INTO vIdOperation FROM operation o WHERE o.id_operation = pId_operation;
	if (vIdOperation = 0) then
		SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'Inexistent operation', MYSQL_ERRNO = 15;
	else
		delete from operation where id_operation = vIdOperation;
	end if;
END;
$$
DELIMITER ;

/*------------------------------------------------------------------------------------*/
#Modificacion de operaciones: Stored Procedure - No modifica el tipo de operacion (Input u Output).
DELIMITER $$
CREATE PROCEDURE sp_modify_operation(pId_operation int, pConcept varchar(50), pAmount long, pOperation_date datetime)
BEGIN 
	declare vTotal long default 0;
	declare vIdOperation int default 0;
	set vTotal = getCurrentBalance();
	SELECT ifnull(o.id_operation,0) INTO vIdOperation FROM operation o WHERE o.id_operation = pId_operation;
	if (vIdOperation = 0 and vTotal >= pAmount) then
		SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'Inexistent operation', MYSQL_ERRNO = 15;
	else
		UPDATE operation SET concept = pConcept, amount = pAmount, operation_date = pOperation_date where id_operation = vIdOperation;
	end if;
END;
$$
DELIMITER ;

call sp_modify_operation(3, 'Pesos convertidos', 20000, now());

