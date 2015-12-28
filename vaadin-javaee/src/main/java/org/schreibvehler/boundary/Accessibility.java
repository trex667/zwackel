package org.schreibvehler.boundary;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class Accessibility extends AbstractEntity {
    private static final long serialVersionUID = 107917022152535532L;

    @Enumerated(EnumType.STRING)
    private AccessibilityType type;
    private String value;

    public AccessibilityType getType() {
        return type;
    }

    public void setType(AccessibilityType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
