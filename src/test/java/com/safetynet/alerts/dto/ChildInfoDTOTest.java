package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChildInfoDTOTest {

    @Test
    void testChildInfoDTO() {

        ChildInfoDTO.FamilyMember familyMember1 = new ChildInfoDTO.FamilyMember("John", "Doe");
        ChildInfoDTO.FamilyMember familyMember2 = new ChildInfoDTO.FamilyMember("Jane", "Doe");

        ChildInfoDTO child = new ChildInfoDTO("Alice", "Doe", 10, List.of(familyMember1, familyMember2));

        assertEquals("Alice", child.getFirstName());
        assertEquals("Doe", child.getLastName());
        assertEquals(10, child.getAge());
        assertEquals(2, child.getFamilyMembers().size());
        assertEquals("John", child.getFamilyMembers().get(0).getFirstName());
        assertEquals("Doe", child.getFamilyMembers().get(0).getLastName());
        assertEquals("Jane", child.getFamilyMembers().get(1).getFirstName());
        assertEquals("Doe", child.getFamilyMembers().get(1).getLastName());
    }

    @Test
    void testSettersAndGetters() {

        ChildInfoDTO child = new ChildInfoDTO(null, null, 0, null);

        child.setFirstName("Alice");
        child.setLastName("Doe");
        child.setAge(10);
        child.setFamilyMembers(List.of(
                new ChildInfoDTO.FamilyMember("John", "Doe"),
                new ChildInfoDTO.FamilyMember("Jane", "Doe")
        ));

        assertEquals("Alice", child.getFirstName());
        assertEquals("Doe", child.getLastName());
        assertEquals(10, child.getAge());
        assertEquals(2, child.getFamilyMembers().size());
        assertEquals("John", child.getFamilyMembers().get(0).getFirstName());
        assertEquals("Jane", child.getFamilyMembers().get(1).getFirstName());
    }

    @Test
    void testFamilyMemberSettersAndGetters() {

        ChildInfoDTO.FamilyMember familyMember = new ChildInfoDTO.FamilyMember("John", "Doe");

        assertEquals("John", familyMember.getFirstName());
        assertEquals("Doe", familyMember.getLastName());

        familyMember.setFirstName("Jane");
        familyMember.setLastName("Smith");

        assertEquals("Jane", familyMember.getFirstName());
        assertEquals("Smith", familyMember.getLastName());
    }

    @Test
    void testEmptyFamilyMembers() {

        ChildInfoDTO child = new ChildInfoDTO("Alice", "Doe", 10, List.of());

        // Validate values
        assertEquals("Alice", child.getFirstName());
        assertEquals("Doe", child.getLastName());
        assertEquals(10, child.getAge());
        assertTrue(child.getFamilyMembers().isEmpty());
    }
}
