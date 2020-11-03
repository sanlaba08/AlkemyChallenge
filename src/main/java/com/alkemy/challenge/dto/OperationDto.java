package com.alkemy.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {
    private Integer id;
    private String concept;
    private Long amount;
    private Date operationDate;

}
