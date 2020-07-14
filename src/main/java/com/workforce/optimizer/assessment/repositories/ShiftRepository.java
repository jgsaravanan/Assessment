package com.workforce.optimizer.assessment.repositories;

import com.workforce.optimizer.assessment.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    @Query("select r from Shift r where r.code in :codes")
    List<Shift> findCodes(@Param("codes") Set<String> codes);
}
