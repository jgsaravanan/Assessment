package com.workforce.optimizer.assessment.controllers;

import com.workforce.optimizer.assessment.entity.ShiftGrouping;
import com.workforce.optimizer.assessment.service.ShiftService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ShiftController {

    private static final Logger LOGGER = LogManager.getLogger(ShiftController.class);

    @Autowired
    private ShiftService shiftService;

    @ApiOperation(value = "Map the Shift code with Group", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully mapped the shift code and group")
    }
    )
    @PostMapping("create-group-code")
    public ResponseEntity<List<ShiftGrouping>> mapShiftGroupAndCode(@RequestBody Map<String, List<String>> groupCodeMap) {
        LOGGER.info("---------------Entered into mapShiftGroupAndCode with input {}-------------------", groupCodeMap);
        try {
            List<ShiftGrouping> shiftGroupingList = shiftService.mapShiftGroupAndCode(groupCodeMap);
            return ResponseEntity.ok(shiftGroupingList);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Exception in mapShiftGroupAndCode and the error is {}", e.getMessage());
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @ApiOperation(value = "Does the Shift Belong to Group", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully validated the shift")
    }
    )
    @GetMapping("shift-belong-to-group")
    public ResponseEntity<Map<String, Object>> doesShiftBelongToGroup(@RequestParam String shiftCode, @RequestParam String shiftGroupCode) {
        LOGGER.info("---------------Entered into doesShiftBelongToGroup with shiftCode={} and shiftGroupCode={}-------------------", shiftCode, shiftGroupCode);
        try {
            Boolean isAvailable = shiftService.doesShiftBelongToGroup(shiftCode, shiftGroupCode);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("ShiftCode", shiftCode);
                put("ShiftGroupCode", shiftGroupCode);
                put("doesShiftBelongToGroup", isAvailable);
            }};
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Exception in doesShiftBelongToGroup and the error is {}", e.getMessage());
        }
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("ShiftCode", shiftCode);
            put("ShiftGroupCode", shiftGroupCode);
            put("doesShiftBelongToGroup", false);
        }});
    }

}
