package org.zwackel.jpa.entity;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue(value="Company")
public class CompanySingleTable extends PersonSingleTable {
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%d; name=%s]", CompanySingleTable.class.getCanonicalName(), getId(), getName());
    }
}
