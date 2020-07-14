package com.workforce.optimizer.assessment.repositories;

import com.workforce.optimizer.assessment.entity.ShiftGrouping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface ShiftGroupingRepository extends JpaRepository<ShiftGrouping, Integer> {
    @Query(nativeQuery = true, value = "SELECT * from WFO.SHIFT_GROUPING where SHIFT_CODE = :shiftCode and SHIFT_GROUP_CODE = :shiftGroupCode")
    Optional<ShiftGrouping> findShiftGrouping(@Param("shiftCode") String shiftCode, @Param("shiftGroupCode") String shiftGroupCode);

    @Query(nativeQuery = true, value = "SELECT SHIFT_GROUP_CODE, SHIFT_CODE from WFO.SHIFT_GROUPING where SHIFT_GROUP_CODE in :shiftGroupCodes")
    List<Object[]> getShiftCodeGroupList(@Param("shiftGroupCodes") Set<String> shiftGroupCodes);

    default Map<String, List<String>> getShiftCodeGroupMap(Set<String> shiftGroupCodes) {
        List<Object[]> shiftCodeGroupList = this.getShiftCodeGroupList(shiftGroupCodes);
        Map<String, List<String>> shiftCodeGroupMap = new HashMap<>();
        for (Object[] object : shiftCodeGroupList) {
            shiftCodeGroupMap.putIfAbsent((String) object[0], new ArrayList<>());
            List<String> codes = shiftCodeGroupMap.get(object[0].toString());
            codes.add(object[1].toString());
        }
        return shiftCodeGroupMap;
    }
}
