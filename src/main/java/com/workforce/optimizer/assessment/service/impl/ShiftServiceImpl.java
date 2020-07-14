package com.workforce.optimizer.assessment.service.impl;

import com.workforce.optimizer.assessment.entity.Shift;
import com.workforce.optimizer.assessment.entity.ShiftGroup;
import com.workforce.optimizer.assessment.entity.ShiftGrouping;
import com.workforce.optimizer.assessment.repositories.ShiftGroupRepository;
import com.workforce.optimizer.assessment.repositories.ShiftGroupingRepository;
import com.workforce.optimizer.assessment.repositories.ShiftRepository;
import com.workforce.optimizer.assessment.service.ShiftService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShiftServiceImpl implements ShiftService {

    private static final Logger LOGGER = LogManager.getLogger(ShiftServiceImpl.class);

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private ShiftGroupRepository shiftGroupRepository;

    @Autowired
    private ShiftGroupingRepository shiftGroupingRepository;


    /**
     * Does the shift belong to Group
     * @param shiftCode
     * @param shiftGroupCode
     * @return
     */
    @Override
    public boolean doesShiftBelongToGroup(String shiftCode, String shiftGroupCode) {
        LOGGER.info("Called from Controller method doesShiftBelongToGroup");
        try {
            Optional<ShiftGrouping> shiftGrouping = shiftGroupingRepository.findShiftGrouping(shiftCode, shiftGroupCode);
            return shiftGrouping.isPresent();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Exception is doesShiftBelongToGroup {}", e.getMessage());
        }
        return false;
    }

    /**
     * Map the shift code to group
     * @param groupCodeMap
     * @return
     */
    @Override
    public List<ShiftGrouping> mapShiftGroupAndCode(Map<String, List<String>> groupCodeMap) {
        LOGGER.info("Called from controller method mapShiftGroupAndCode");
        try {
            Set<String> codes = groupCodeMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
            List<Shift> existingShifts = shiftRepository.findCodes(codes);
            List<String> existingShiftCodes = existingShifts.stream().map(Shift::getCode).collect(Collectors.toList());
            List<String> newShiftCodes = codes.stream().filter(code -> !existingShiftCodes.contains(code)).collect(Collectors.toList());
            List<Shift> shifts = newShiftCodes.stream().map(Shift::new).collect(Collectors.toList());
            List<Shift> savedShifts = shiftRepository.saveAll(shifts);

            LOGGER.info("SHIFT data persisted {}", savedShifts);

            savedShifts.addAll(existingShifts);

            Set<String> groups = groupCodeMap.keySet();
            List<ShiftGroup> existingShiftGroups = shiftGroupRepository.findCodes(groups);
            List<String> existingShiftGroupCodes = existingShiftGroups.stream().map(ShiftGroup::getCode).collect(Collectors.toList());
            List<String> newShiftGroupCodes = groups.stream().filter(code -> !existingShiftGroupCodes.contains(code)).collect(Collectors.toList());

            Set<String> groupsAndCodes = Stream.of(newShiftGroupCodes, newShiftCodes).flatMap(List::stream).collect(Collectors.toSet());
            List<ShiftGroup> shiftGroups = groupsAndCodes.stream().map(ShiftGroup::new).collect(Collectors.toList());
            List<ShiftGroup> savedShiftGroups = shiftGroupRepository.saveAll(shiftGroups);

            LOGGER.info("SHIFT_GROUP data persisted {}", savedShiftGroups);

            savedShiftGroups.addAll(existingShiftGroups);

            Function<String, ShiftGroup> getShiftGroup = code -> savedShiftGroups.stream().filter(shiftGroup -> shiftGroup.getCode().equals(code)).findAny().get();
            Function<String, Shift> getShift = code -> savedShifts.stream().filter(shift -> shift.getCode().equals(code)).findAny().get();

            // To use the below code , enable EAGER FETCH in ShiftGroup
            /*Map<String, List<String>> shiftGroupCodesMap1 = existingShiftCodes.isEmpty() ? Collections.emptyMap() : savedShiftGroups.stream()
                    .collect(Collectors.groupingBy(ShiftGroup::getCode,
                            Collectors.mapping(o -> o.getShiftGroupings().stream()
                                            .map(shiftGrouping -> shiftGrouping.getShift().getCode())
                                            .collect(Collectors.toList()),
                                    Collectors.collectingAndThen(Collectors.toList(), lists -> lists.stream().flatMap(Collection::stream).collect(Collectors.toList())))));*/

            Map<String, List<String>> shiftGroupCodesMap = shiftGroupingRepository.getShiftCodeGroupMap(groups);

            List<ShiftGrouping> shiftGroupingList = groupCodeMap.entrySet().stream().map(groupCodeEntry -> {
                List<String> g_codes = shiftGroupCodesMap.getOrDefault(groupCodeEntry.getKey(), Collections.emptyList());
                List<ShiftGrouping> shiftGroupings = Collections.emptyList();
                ShiftGroup shiftGroup = getShiftGroup.apply(groupCodeEntry.getKey());
                if (!g_codes.isEmpty()) {
                    shiftGroupings = groupCodeEntry.getValue().stream().filter(code -> !g_codes.contains(code)).map(code -> {
                        ShiftGrouping shiftGrouping = new ShiftGrouping();
                        Shift shift = getShift.apply(code);
                        shiftGrouping.setShift(shift);
                        shiftGrouping.setShiftGroup(shiftGroup);
                        return shiftGrouping;
                    }).collect(Collectors.toList());
                } else {
                    shiftGroupings = groupCodeEntry.getValue().stream().map(code -> {
                        ShiftGrouping shiftGrouping = new ShiftGrouping();
                        Shift shift = getShift.apply(code);
                        shiftGrouping.setShift(shift);
                        shiftGrouping.setShiftGroup(shiftGroup);
                        return shiftGrouping;
                    }).collect(Collectors.toList());
                }
                return shiftGroupings;
            }).flatMap(Collection::stream).collect(Collectors.toList());

            shiftGroupingList = shiftGroupingRepository.saveAll(shiftGroupingList);

            LOGGER.info("SHIFT_GROUPING data persisted {}", shiftGroupingList);
            return shiftGroupingList;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Exception in mapShiftGroupAndCode {}", e.getMessage());
        }
        return Collections.emptyList();
    }


}
