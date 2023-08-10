package com.example.equipmentmanagement.controller;


import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.system.StatusCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration test for Equipment API endpoints.")
@Tag("integration")
public class EquipmentControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    String baseUrl = "/api/v1";

    String token;

    @BeforeEach
    void setUp() throws Exception {
        ResultActions resultActions =mockMvc.perform(post(baseUrl+"/users/login").with(httpBasic("admin","123")));
        MvcResult mvcResult = resultActions.andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(contentAsString);
        token = "Bearer " + jsonObject.getJSONObject("data").getString("token");


    }

    @Test
    void testFindAllArtifactsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"/equipments").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(1)));
    }

    @Test
    void testAddEquipmentSuccess() throws Exception {
        Equipment equipment = new Equipment();
        equipment.setName("ชุดทดลองเรื่องสนามแม่เหล็กโลก");
        equipment.setSerialNo("1-C0200-FA17-34240070007-001-56");
        equipment.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");
        equipment.setEquipmentGroup("FA17");
        equipment.setLocation("อาคาร 22 ห้อง 202");
        equipment.setCost(28335.0);
        equipment.setDate("31.01.2013");

        String json = objectMapper.writeValueAsString(equipment);

        this.mockMvc.perform(post(baseUrl+"/equipments").contentType(MediaType.APPLICATION_JSON).header("Authorization",token).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.serialNo").value("1-C0200-FA17-34240070007-001-56"))
                .andExpect(jsonPath("$.data.name").value("ชุดทดลองเรื่องสนามแม่เหล็กโลก"));

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"/equipments").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(1)));
    }

    }

