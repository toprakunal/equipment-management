package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.exception.EquipmentNotFoundException;
import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.repository.EquipmentRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @Mock
    EquipmentRepository equipmentRepository;

    @InjectMocks
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
    void testFindByIdEquipmentSuccess() {

        //Given. Inputs and targets.

        Equipment mockEquipment = new Equipment();

        mockEquipment.setSerialNo("1-C0200-FA17-34240070007/001-56");
        mockEquipment.setName("ชุดทดลองเรื่องสนามแม่เหล็กโลก");
        mockEquipment.setEquipmentGroup("FA17");
        mockEquipment.setCost(28335.0);
        mockEquipment.setLocation("อาคาร 22 ห้อง 202");
        mockEquipment.setDate("31.01.2013");
        mockEquipment.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");

        given(equipmentRepository.findById("1-C0200-FA17-34240070007/001-56")).willReturn(Optional.of(mockEquipment));

        //When
        Equipment returnedEquipment = equipmentService.findEquipmentById("1-C0200-FA17-34240070007/001-56");

        //Then
        assertThat(returnedEquipment.getSerialNo()).isEqualTo(mockEquipment.getSerialNo());
        assertThat(returnedEquipment.getName()).isEqualTo(mockEquipment.getName());
        assertThat(returnedEquipment.getEquipmentGroup()).isEqualTo(mockEquipment.getEquipmentGroup());
        assertThat(returnedEquipment.getCost()).isEqualTo(mockEquipment.getCost());
        assertThat(returnedEquipment.getDate()).isEqualTo(mockEquipment.getDate());
        assertThat(returnedEquipment.getLocation()).isEqualTo(mockEquipment.getLocation());
        assertThat(returnedEquipment.getCompany()).isEqualTo(mockEquipment.getCompany());
        verify(equipmentRepository,times(1)).findById("1-C0200-FA17-34240070007/001-56");

    }

    @Test
    void testFindByIdEquipmentNotFound(){
        //given
        given(equipmentRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(()->{
            Equipment returnedEquipment = equipmentService.findEquipmentById("1-C0200-FA17-34240070007/001-56");
        });

        assertThat(thrown)
                .isInstanceOf(EquipmentNotFoundException.class)
                .hasMessage("Could not found equipment with serial no: 1-C0200-FA17-34240070007/001-56");
        verify(equipmentRepository,times(1)).findById("1-C0200-FA17-34240070007/001-56");
    }


    @Test
    void testFindAllSuccess(){
        //Given
        given(equipmentRepository.findAll()).willReturn(this.equipmentList);
        //When
        List<Equipment> actualEquipments = equipmentService.findAllEquipment();

        //Then
        assertThat(actualEquipments.size()).isEqualTo(equipmentList.size());
        verify(equipmentRepository,times(1)).findAll();
    }

    @Test
    void testAddEquipmentSuccess(){
        //Given

        Equipment newEquipment = new Equipment();
        newEquipment.setSerialNo("1-C0200-FA17-66400310001-001-56");
        newEquipment.setEquipmentGroup("FA17");
        newEquipment.setName("ตู้ดูดควันไอกรดสารเคมี");
        newEquipment.setCost(27781.0);
        newEquipment.setLocation("อาคาร 6 ห้อง 202/1");
        newEquipment.setDate("31.01.2013");
        newEquipment.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");


        given(equipmentRepository.save(newEquipment)).willReturn(newEquipment);

        Equipment savedEquipment = equipmentService.addEquipment(newEquipment);

        //Then
        assertThat(savedEquipment.getSerialNo()).isEqualTo("1-C0200-FA17-66400310001-001-56");
        assertThat(savedEquipment.getName()).isEqualTo("ตู้ดูดควันไอกรดสารเคมี");
        assertThat(savedEquipment.getEquipmentGroup()).isEqualTo("FA17");
        assertThat(savedEquipment.getCost()).isEqualTo(27781.0);
        assertThat(savedEquipment.getLocation()).isEqualTo("อาคาร 6 ห้อง 202/1");
        assertThat(savedEquipment.getDate()).isEqualTo("31.01.2013");
        assertThat(savedEquipment.getCompany()).isEqualTo("บริษัท นีโอ ไดแด็กติค จำกัด");
        verify(equipmentRepository, times(1)).save(newEquipment);
    }


    @Test
    void testUpdateEquipmentSuccess(){
        //Given
        Equipment oldEquipment = new Equipment();
        oldEquipment.setSerialNo("1-C0200-FA17-66400310001-001-56");
        oldEquipment.setEquipmentGroup("FA17");
        oldEquipment.setName("ตู้ดูดควันไอกรดสารเคมี");
        oldEquipment.setCost(27781.0);
        oldEquipment.setLocation("อาคาร 6 ห้อง 202/1");
        oldEquipment.setDate("31.01.2013");
        oldEquipment.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");

        Equipment update = new Equipment();
        update.setSerialNo("1-C0200-FA17-66400310001-001-56");
        update.setEquipmentGroup("FA17");
        update.setName("ตู้ดูดควันไอกรดสารเคมี");
        update.setCost(27781.0);
        update.setLocation("this is updated location");
        update.setDate("31.01.2013");
        update.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");


        given(equipmentRepository.findById("1-C0200-FA17-66400310001-001-56")).willReturn(Optional.of(oldEquipment));
        given(equipmentRepository.save(oldEquipment)).willReturn(oldEquipment);

        //When

        Equipment updatedEquipment = equipmentService.updateEquipment("1-C0200-FA17-66400310001-001-56",update);

        assertThat(updatedEquipment.getSerialNo()).isEqualTo(update.getSerialNo());
        assertThat(updatedEquipment.getLocation()).isEqualTo(update.getLocation());

        verify(equipmentRepository,times(1)).findById("1-C0200-FA17-66400310001-001-56");
        verify(equipmentRepository, times(1)).save(oldEquipment);
    }

    @Test
    void testUpdateEquipmentNotFound(){
        //Given
        Equipment update = new Equipment();
        update.setSerialNo("1-C0200-FA17-66400310001-001-56");
        update.setEquipmentGroup("FA17");
        update.setName("ตู้ดูดควันไอกรดสารเคมี");
        update.setCost(27781.0);
        update.setLocation("this is updated location");
        update.setDate("31.01.2013");
        update.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");

        given(equipmentRepository.findById("1-C0200-FA17-66400310001-001-56")).willReturn(Optional.empty());

        assertThrows(EquipmentNotFoundException.class, ()-> {
            equipmentService.updateEquipment("1-C0200-FA17-66400310001-001-56",update);
        });

        verify(equipmentRepository,times(1)).findById("1-C0200-FA17-66400310001-001-56");
    }

    @Test
    void testDeleteEquipmentSuccess(){
        Equipment equipment = new Equipment();
        equipment.setSerialNo("1-C0200-FA17-66400310001-001-56");
        equipment.setEquipmentGroup("FA17");
        equipment.setName("ตู้ดูดควันไอกรดสารเคมี");
        equipment.setCost(27781.0);
        equipment.setLocation("this is updated location");
        equipment.setDate("31.01.2013");
        equipment.setCompany("บริษัท นีโอ ไดแด็กติค จำกัด");

        given(equipmentRepository.findById("1-C0200-FA17-66400310001-001-56")).willReturn(Optional.of(equipment));
        doNothing().when(equipmentRepository).deleteById("1-C0200-FA17-66400310001-001-56");

        //When
        equipmentService.deleteEquipment("1-C0200-FA17-66400310001-001-56");

        //Then
        verify(equipmentRepository,times(1)).deleteById("1-C0200-FA17-66400310001-001-56");
    }

    @Test
    void testDeleteEquipmentNotFound(){
        given(equipmentRepository.findById("1-C0200-FA17-66400310001-001-56")).willReturn(Optional.empty());

        assertThrows(EquipmentNotFoundException.class, ()->{
            equipmentService.deleteEquipment("1-C0200-FA17-66400310001-001-56");
        });

        verify(equipmentRepository,times(1)).findById("1-C0200-FA17-66400310001-001-56");
    }
}