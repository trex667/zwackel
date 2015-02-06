package org.zwackel.jpa.entity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AccessibilityTest {
    @Test
    public void testEquals() throws Exception {
        Accessibility a1 = new Accessibility();
        Accessibility a2 = new Accessibility();

        assertThat(a1, is(equalTo(a2)));
    }
}
