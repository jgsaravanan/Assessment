package com.workforce.optimizer.assessment.service;

import com.workforce.optimizer.assessment.entity.ShiftGrouping;

import java.util.List;
import java.util.Map;

public interface ShiftService {
    boolean doesShiftBelongToGroup(String shiftCode, String shiftGroupCode);
    List<ShiftGrouping> mapShiftGroupAndCode(Map<String, List<String>> groupCodeMap);
}
