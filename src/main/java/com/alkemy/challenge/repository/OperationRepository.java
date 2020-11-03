package com.alkemy.challenge.repository;

import com.alkemy.challenge.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    @Procedure(procedureName = "sp_create_input")
    Integer addInputOperation(@Param("pConcept") String concept,
                        @Param("pAmount") Long amount,
                        @Param("pOperation_date") Date operationDate) throws JpaSystemException;

    @Procedure(procedureName = "sp_create_output")
    Integer addOutputOperation(@Param("pConcept") String concept,
                              @Param("pAmount") Long amount,
                              @Param("pOperation_date") Date operationDate) throws JpaSystemException;

    @Procedure(procedureName = "sp_get_total")
    Long getTotal() throws JpaSystemException;

    @Query(value = "SELECT * FROM operation o order by o.operation_date desc limit 10", nativeQuery = true)
    List<Operation> getTopTenOperations();

    @Procedure(procedureName = "sp_delete_operation")
    void deleteOperation(@Param("pId_operation") Integer idOperation);

    @Transactional
    @Procedure(procedureName = "sp_modify_operation")
    void modifyOperation(@Param("pId_operation") Integer idOperation,
                           @Param("pConcept") String concept,
                           @Param("pAmount") Long amount,
                           @Param("pOperation_date") Date operationDate) throws JpaSystemException;
}
