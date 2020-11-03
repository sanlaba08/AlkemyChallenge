package com.alkemy.challenge.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonBackReference
    @Column(name = "id_operation")
    private Integer id;

    @Column(name = "concept")
    private String concept;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "operation_date")
    private Date operationDate;

    @Column(name = "operation_type")
    @Enumerated(EnumType.STRING)
    private Type operationType;


}
