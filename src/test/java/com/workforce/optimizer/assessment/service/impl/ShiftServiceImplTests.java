package com.workforce.optimizer.assessment.service.impl;

import com.workforce.optimizer.assessment.entity.ShiftGrouping;
import com.workforce.optimizer.assessment.service.ShiftService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ShiftServiceImplTests {

    @Autowired
    private ShiftService shiftService;

    @Test
    public void doesShiftBelongToGroupTest() {
        boolean doesShiftBelongToGroup = shiftService.doesShiftBelongToGroup("S1", "G1");
        Assert.isTrue(!doesShiftBelongToGroup);
    }

    @Test
    public void mapShiftGroupAndCodeTest() {
        Map<String, List<String>> groupCodeMap = new HashMap<String, List<String>>() {{
            put("G1", Arrays.asList("S1", "S2", "S3"));
            put("G2", Arrays.asList("S1", "S5", "S3"));
            put("G3", Arrays.asList("S4", "S2", "S6"));
        }};
        List<ShiftGrouping> shiftGroupingList = shiftService.mapShiftGroupAndCode(groupCodeMap);
        Assert.notEmpty(shiftGroupingList);
    }
}
