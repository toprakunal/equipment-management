package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.EquipmentDto;
import com.example.equipmentmanagement.exception.EquipmentNotFoundException;
import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.service.EquipmentService;
import com.example.equipmentmanagement.system.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class EquipmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EquipmentService equipmentService;

    List<Equipment> equipmentList;

    @BeforeEach
    void setUp() {

        this.equipmentList = new ArrayList<>();

        Equipment e1 = new Equipment();
        e1.setSerialNo("1-C0200-FA17-34240070007-001-56");
        e1.setEquipmentGroup("FA17");
        e1.setName("ชุดทดลองเรื่องสนามแม่เหล็กโลก");
        e1.setCost(28335.0);
        e1.setLocation("อาคาร 22 ห้อง 202");
        e1.setDate("31.01.2013");
        e1.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");
        this.equipmentList.add(e1);

        Equipment e2 = new Equipment();
        e2.setSerialNo("1-C0200-FA17-34240070007/002-56");
        e2.setEquipmentGroup("FA17");
        e2.setName("ชุดทดลองเรื่องการดูดกลืนรังสีแกมมา");
        e2.setCost(28355.0);
        e2.setLocation("อาคาร 22 ห้อง 206");
        e2.setDate("31.01.2013");
        e2.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");
        this.equipmentList.add(e2);

        Equipment e3 = new Equipment();
        e3.setSerialNo("1-C0200-FA17-34240070007/003-56");
        e3.setEquipmentGroup("FA17");
        e3.setName("ชุดทดลองเรื่องกฏของการชนบนรางลมไร้แรงเสียดทาน");
        e3.setCost(27318.0);
        e3.setLocation("อาคาร 22 ห้อง 201");
        e3.setDate("31.01.2013");
        e3.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");
        this.equipmentList.add(e3);

        Equipment e4 = new Equipment();
        e4.setSerialNo("1-C0200-FA17-34240070007/003-56");
        e4.setEquipmentGroup("FA17");
        e4.setName("ชุดทดลองเรื่องกฏของการชนบนรางลมไร้แรงเสียดทาน");
        e4.setCost(27318.0);
        e4.setLocation("อาคาร 22 ห้อง 201");
        e4.setDate("31.01.2013");
        e4.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");
        this.equipmentList.add(e4);






    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindEquipmentByIdSuccess() throws Exception {

        //Given
        given(this.equipmentService.findEquipmentById("1-C0200-FA17-34240070007-001-56"))
                .willReturn(this.equipmentList.get(0));

        //When
        this.mockMvc.perform(get("http://localhost:8080/api/v1/equipments/1-C0200-FA17-34240070007-001-56").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"));


    }


    @Test
    void testFindEquipmentByIdNotFound() throws Exception {

        //Given
        given(this.equipmentService.findEquipmentById("1-C0200-FA17-34240070007-001-56"))
                .willThrow(new EquipmentNotFoundException("1-C0200-FA17-34240070007-001-56"));

        //When
        this.mockMvc.perform(get("http://localhost:8080/api/v1/equipments/1-C0200-FA17-34240070007-001-56").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not found equipment with serial no: 1-C0200-FA17-34240070007-001-56"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindAllEquipmentSuccess() throws Exception {
        //Given
        given(this.equipmentService.findAllEquipment()).willReturn(this.equipmentList);

        //When
        this.mockMvc.perform(get("/api/v1/equipments").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.equipmentList.size())))
                .andExpect(jsonPath("$.data[0].serialNo").value("1-C0200-FA17-34240070007-001-56"))
                .andExpect(jsonPath("$.data[1].serialNo").value("1-C0200-FA17-34240070007/002-56"));
    }

    @Test
    void testAddEquipmentSuccess() throws Exception {
        //Fake input data
        EquipmentDto equipmentDto = new EquipmentDto("1-C0200-FA17-66400310001-001-56",
                "ตู้ดูดควันไอกรดสารเคมี",
                "FA17",
                27781.0,
                "31.01.20130",
                "อาคาร 6 ห้อง 202/1",
                "บริษัท นีโอ ไดแด็กติค จำกัด",
                null,
                null);

        String json =this.objectMapper.writeValueAsString(equipmentDto);
        // Fake returned data
        Equipment newEquipment = new Equipment();
        newEquipment.setSerialNo("1-C0200-FA17-66400310001-001-56");
        newEquipment.setEquipmentGroup("FA17");
        newEquipment.setName("ตู้ดูดควันไอกรดสารเคมี");
        newEquipment.setCost(27781.0);
        newEquipment.setLocation("อาคาร 6 ห้อง 202/1");
        newEquipment.setDate("31.01.2013");
        newEquipment.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");

        given(this.equipmentService.addEquipment(Mockito.any(Equipment.class))).willReturn(newEquipment);

        //When and Then
        this.mockMvc.perform(post("/api/v1/equipments").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.serialNo").value("1-C0200-FA17-66400310001-001-56"))
                .andExpect(jsonPath("$.data.name").value("ตู้ดูดควันไอกรดสารเคมี"));
    }


    @Test
    void testUpdateEquipmentSuccess() throws Exception {

        //Fake input data
        EquipmentDto equipmentDto = new EquipmentDto("1-C0200-FA17-66400310001-001-56",
                "ตู้ดูดควันไอกรดสารเคมี",
                "FA17",
                27781.0,
                "31.01.20130",
                "อาคาร 6 ห้อง 202/1",
                "บริษัท นีโอ ไดแด็กติค จำกัด",
                null,
                null);

        String json =this.objectMapper.writeValueAsString(equipmentDto);
        // Fake returned data
        Equipment updatedEquipment = new Equipment();
        updatedEquipment.setSerialNo("1-C0200-FA17-66400310001-001-56");
        updatedEquipment.setEquipmentGroup("FA17");
        updatedEquipment.setName("ตู้ดูดควันไอกรดสารเคมี");
        updatedEquipment.setCost(27781.0);
        updatedEquipment.setLocation("This is updated location.");
        updatedEquipment.setDate("31.01.2013");
        updatedEquipment.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");

        given(this.equipmentService.updateEquipment(eq("1-C0200-FA17-66400310001-001-56"),Mockito.any(Equipment.class))).willReturn(updatedEquipment);

        //When and Then
        this.mockMvc.perform(put("/api/v1/equipments/1-C0200-FA17-66400310001-001-56").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.serialNo").value("1-C0200-FA17-66400310001-001-56"))
                .andExpect(jsonPath("$.data.location").value("This is updated location."));


    }

    @Test
    void testUpdateEquipmentErrorWithNonExistedSerialNo() throws Exception {
        //Fake input data
        EquipmentDto equipmentDto = new EquipmentDto("1-C0200-FA17-66400310001-001-56",
                "ตู้ดูดควันไอกรดสารเคมี",
                "FA17",
                27781.0,
                "31.01.20130",
                "อาคาร 6 ห้อง 202/1",
                "บริษัท นีโอ ไดแด็กติค จำกัด",
                null,
                null);

        String json =this.objectMapper.writeValueAsString(equipmentDto);
        // Fake returned data


        given(this.equipmentService.updateEquipment(eq("1-C0200-FA17-66400310001-001-56"),Mockito.any(Equipment.class))).willThrow(new EquipmentNotFoundException("1-C0200-FA17-66400310001-001-56"));

        //When and Then
        this.mockMvc.perform(put("/api/v1/equipments/1-C0200-FA17-66400310001-001-56").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not found equipment with serial no: 1-C0200-FA17-66400310001-001-56"))
                .andExpect(jsonPath("$.data").isEmpty());


    }

    @Test
    void testDeleteEquipmentSuccess() throws Exception {
        doNothing().when(equipmentService).deleteEquipment("1-C0200-FA17-66400310001-001-56");

        this.mockMvc.perform(delete("/api/v1/equipments/1-C0200-FA17-66400310001-001-56").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void testDeleteEquipmentErrorWithNonExistedSerialNo() throws Exception {
        //Given
        doThrow(new EquipmentNotFoundException("1-C0200-FA17-66400310001-001-56")).when(equipmentService).deleteEquipment("1-C0200-FA17-66400310001-001-56");

        this.mockMvc.perform(delete("/api/v1/equipments/1-C0200-FA17-66400310001-001-56").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not found equipment with serial no: 1-C0200-FA17-66400310001-001-56"))
                .andExpect(jsonPath("$.data").isEmpty());

    }
}