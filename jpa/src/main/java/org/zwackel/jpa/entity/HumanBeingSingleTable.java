package org.zwackel.jpa.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue(value="Humanbeing")
public class HumanBeingSingleTable extends PersonSingleTable {

    private String firstName;
    private String lastName;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%d; firstName=%s; lastName=%s; birthDate=%s]", HumanBeingSingleTable.class.getCanonicalName(), getId(), getFirstName(), getLastName(), getBirthdate());
    }
}
