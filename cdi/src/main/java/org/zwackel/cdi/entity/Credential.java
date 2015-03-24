package org.zwackel.cdi.entity;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class Credential {

    @Id
    private String id;

    private String name;

    private double volProz;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolProz() {
        return this.volProz;
    }

    public void setVolProz(double volProz) {
        this.volProz = volProz;
    }
}
