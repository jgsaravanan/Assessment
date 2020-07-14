package com.workforce.optimizer.assessment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "SHIFT", schema = "WFO")
@NoArgsConstructor
@Getter
@Setter
public class Shift implements Serializable {

    @Column(name = "ID", columnDefinition = "integer auto_increment")
    @Generated(GenerationTime.INSERT)
    private int id;

    @Id
    @Column(name = "CODE")
    private String code;

    @OneToMany
    @JoinColumn(name = "SHIFT_CODE")
    private List<ShiftGrouping> shiftGroupings;

    public Shift(String code) {
        this.code = code;
    }

}
