package com.workforce.optimizer.assessment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "SHIFT_GROUP", schema = "WFO")
@NoArgsConstructor
@Getter
@Setter
public class ShiftGroup implements Serializable {

    @Column(name = "ID", columnDefinition = "integer auto_increment")
    @Generated(GenerationTime.INSERT)
    private int id;

    @Id
    @Column(name = "CODE")
    private String code;

    @JsonIgnore
    @OneToMany(mappedBy = "shiftGroup")//, fetch = FetchType.EAGER
    private List<ShiftGrouping> shiftGroupings;

    @JsonIgnore
    @OneToMany(mappedBy = "shiftCode")//, fetch = FetchType.EAGER
    private List<ShiftGrouping> shiftCodes;

    public ShiftGroup(String code) {
        this.code = code;
    }

}
