package com.workforce.optimizer.assessment.repositories;

import com.workforce.optimizer.assessment.entity.ShiftGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ShiftGroupRepository extends JpaRepository<ShiftGroup, Integer> {
    @Query("select r from ShiftGroup r where r.code in :codes")
    List<ShiftGroup> findCodes(@Param("codes") Set<String> codes);
}
