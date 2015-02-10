package org.zwackel.jpa.entity;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class CompanyTablePerClass extends PersonTablePerClass {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%d; name=%s]", CompanyTablePerClass.class.getCanonicalName(), getId(), getName());
    }
}
