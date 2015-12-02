package org.schreibvehler.boundary;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class Accessibility {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
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
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        Accessibility other = (Accessibility) obj;
        if(type == null || other.getType() == null){
            return false;
        }
        return type == other.getType() ;
    }


}
