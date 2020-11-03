package com.alkemy.challenge.restController;

import com.alkemy.challenge.controller.OperationController;
import com.alkemy.challenge.domain.Operation;
import com.alkemy.challenge.dto.OperationDto;
import com.alkemy.challenge.exceptions.OperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import javax.management.OperationsException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/operation/")
public class OperationRestController {
    private final OperationController operationController;

    @Autowired
    public OperationRestController(OperationController operationController) {
        this.operationController = operationController;
    }

    @GetMapping()
    public ResponseEntity<List<Operation>> getAllOperations() {
        List<Operation> operations = operationController.getTopTenOperations();
        if (operations.size() > 0) {
            return ResponseEntity.ok().body(operations);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("input/")
    public ResponseEntity addInputOperation(@RequestBody Operation operation) throws JpaSystemException, URISyntaxException, OperationException {
        try {
            Integer idOperation = operationController.addInputOperation(operation);
            return ResponseEntity.created(new URI("http://localhost:8080/operation/input/" + idOperation)).body(operation);
        } catch (JpaSystemException e) {
            throw new OperationException(e.getCause().getCause().getMessage());
        }
    }

    @PostMapping("output/")
    public ResponseEntity addOutputOperation(@RequestBody Operation operation) throws JpaSystemException, URISyntaxException, OperationException {
        try {
            Integer idOperation = operationController.addInputOperation(operation);
            return ResponseEntity.created(new URI("http://localhost:8080/operation/output/" + idOperation)).body(operation);
        } catch (JpaSystemException e) {
            throw new OperationException(e.getCause().getCause().getMessage());
        }
    }

    @GetMapping("total/")
    public ResponseEntity getTotal() {
        Long total = operationController.getTotal();
        if (total == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(total);
        }
    }

    @DeleteMapping("/{idOperation}")
    public ResponseEntity deleteOperation(@PathVariable Integer idOperation) throws OperationException {
        try {
            operationController.deleteOperation(idOperation);
            return ResponseEntity.ok().build();
        } catch (JpaSystemException e) {
            throw new OperationException(e.getCause().getCause().getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity modifyOperation(@RequestBody OperationDto operationDto) throws OperationException {
        try {
            operationController.modifyOperation(operationDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (JpaSystemException e) {
            throw new OperationException(e.getCause().getCause().getMessage());
        }
    }


}
