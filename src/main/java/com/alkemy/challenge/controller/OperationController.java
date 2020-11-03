package com.alkemy.challenge.controller;

import com.alkemy.challenge.domain.Operation;
import com.alkemy.challenge.dto.OperationDto;
import com.alkemy.challenge.repository.OperationRepository;
import com.alkemy.challenge.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OperationController {
    private final OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    public Integer addInputOperation(Operation operation) throws JpaSystemException {
        return operationService.addInputOperation(operation);
    }

    public Integer addOutputOperation(Operation operation) throws JpaSystemException {
        return operationService.addOutputOperation(operation);
    }

    public Long getTotal() throws JpaSystemException {
        return operationService.getTotal();
    }

    public List<Operation> getTopTenOperations() {
        return operationService.getTopTenOperations();
    }

    public void deleteOperation(Integer idOperation) {
        operationService.deleteOperation(idOperation);
    }

    public void modifyOperation(OperationDto operationDto) {
        operationService.modifyOperation(operationDto);
    }
}
