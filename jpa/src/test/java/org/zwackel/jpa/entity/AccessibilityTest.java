package org.zwackel.jpa.entity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AccessibilityTest {
    @Test
    public void testEquals() throws Exception {
        Accessibility a1 = new Accessibility();
        a1.setType(AccessibilityType.email);
        Accessibility a2 = new Accessibility();
        a2.setType(AccessibilityType.email);

        assertThat(a1, is(equalTo(a1)));
        assertThat(a1, is(equalTo(a2)));
        assertThat(a2, is(equalTo(a1)));

        assertThat(new Accessibility(), is(not(equalTo(new Accessibility()))));
    }
}
