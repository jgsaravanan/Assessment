package com.workforce.optimizer.assessment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ShiftGroupingId implements Serializable {

    @Column(name = "SHIFT_GROUP_CODE")
    private String shiftGroupCode;

    @ManyToOne
    @JoinColumn(name = "SHIFT_CODE")
    private Shift shift;

}
