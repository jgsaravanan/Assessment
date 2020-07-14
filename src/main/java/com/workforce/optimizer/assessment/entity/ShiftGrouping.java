package com.workforce.optimizer.assessment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SHIFT_GROUPING", schema = "WFO")
@Getter
@Setter
public class ShiftGrouping implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "SHIFT_CODE", insertable = false, updatable = false)
    private ShiftGroup shiftCode;

    @ManyToOne
    @JoinColumn(name = "SHIFT_GROUP_CODE")
    private ShiftGroup shiftGroup;

    @ManyToOne
    @JoinColumn(name = "SHIFT_CODE")
    private Shift shift;

}
