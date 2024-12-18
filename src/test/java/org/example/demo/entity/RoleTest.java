package org.example.demo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    @Test
    public void testEnumValues() {
        Role[] roles = Role.values();

        assertEquals(2, roles.length, "There should be exactly 2 roles.");
        assertTrue(roleContains(roles, Role.PATRON), "The enum should contain PATRON.");
        assertTrue(roleContains(roles, Role.ADMIN), "The enum should contain ADMIN.");
    }

    @Test
    public void testEnumName() {
        assertEquals("PATRON", Role.PATRON.name(), "The name of the PATRON role should be 'PATRON'.");
        assertEquals("ADMIN", Role.ADMIN.name(), "The name of the ADMIN role should be 'ADMIN'.");
    }

    @Test
    public void testEnumOrdinal() {
        assertEquals(0, Role.PATRON.ordinal(), "The ordinal of PATRON should be 0.");
        assertEquals(1, Role.ADMIN.ordinal(), "The ordinal of ADMIN should be 1.");
    }

    @Test
    public void testEnumComparison() {
        assertSame(Role.PATRON, Role.PATRON, "The PATRON role should be the same instance.");
        assertNotSame(Role.PATRON, Role.ADMIN, "The PATRON and ADMIN roles should be different instances.");
    }

    @Test
    public void testEnumValueOf() {
        assertEquals(Role.PATRON, Role.valueOf("PATRON"), "valueOf should return the correct enum constant.");
        assertThrows(IllegalArgumentException.class, () -> Role.valueOf("INVALID"), "valueOf should throw IllegalArgumentException for invalid input.");
    }

    private boolean roleContains(Role[] roles, Role role) {
        for (Role r : roles) {
            if (r == role) {
                return true;
            }
        }
        return false;
    }
}
