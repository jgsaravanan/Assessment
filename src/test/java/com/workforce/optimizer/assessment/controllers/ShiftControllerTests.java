package com.workforce.optimizer.assessment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workforce.optimizer.assessment.service.ShiftService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShiftController.class)
public class ShiftControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShiftService shiftService;

    @Test
    public void doesShiftBelongToGroup() throws Exception {
        Map<String, List<String>> groupCodeMap = new HashMap<String, List<String>>() {{
            put("G1", Arrays.asList("S1"));
        }};
        String input = new ObjectMapper().writeValueAsString(groupCodeMap);
        when(shiftService.mapShiftGroupAndCode(new HashMap<>())).thenReturn(new ArrayList<>());
        this.mockMvc.perform(post("/create-group-code")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(input))
                .andExpect(status().isOk());
    }

    @Test
    public void mapShiftGroupAndCodeTest() throws Exception {
        when(shiftService.doesShiftBelongToGroup("S2", "G1")).thenReturn(false);
        this.mockMvc.perform(get("/shift-belong-to-group")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("shiftCode", "S2")
                .param("shiftGroupCode", "G2"))
                .andExpect(status().isOk());
    }
}
