package org.zwackel.jpa.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AccessibilityTest {
    @Test
    public void testEquals() throws Exception {
        Accessibility a1 = new Accessibility();
        a1.setType(AccessibilityType.email);
        Accessibility a2 = new Accessibility();
        a2.setType(AccessibilityType.email);

        assertThat(a1).isEqualTo(a1);
        assertThat(a1).isEqualTo(a2);
        assertThat(a2).isEqualTo(a1);

        assertThat(new Accessibility()).isNotEqualTo(new Accessibility());
    }
}
