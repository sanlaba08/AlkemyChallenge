package com.alkemy.challenge.service;

import com.alkemy.challenge.domain.Operation;
import com.alkemy.challenge.dto.OperationDto;
import com.alkemy.challenge.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationService {
    private final OperationRepository operationRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public Integer addInputOperation(Operation operation) throws JpaSystemException {
        return operationRepository.addInputOperation(operation.getConcept(), operation.getAmount(), operation.getOperationDate());
    }

    public Integer addOutputOperation(Operation operation) throws JpaSystemException {
        return operationRepository.addOutputOperation(operation.getConcept(), operation.getAmount(), operation.getOperationDate());
    }

    public Long getTotal() throws JpaSystemException {
        return operationRepository.getTotal();
    }

    public List<Operation> getTopTenOperations() {
        return operationRepository.getTopTenOperations();
    }

    public void deleteOperation(Integer idOperation) throws JpaSystemException {
        operationRepository.deleteOperation(idOperation);
    }

    public void modifyOperation(OperationDto operationDto) throws JpaSystemException {
        operationRepository.modifyOperation(operationDto.getId(), operationDto.getConcept(), operationDto.getAmount(), operationDto.getOperationDate());
    }
}
