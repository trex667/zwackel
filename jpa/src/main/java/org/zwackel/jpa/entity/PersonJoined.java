package org.zwackel.jpa.entity;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE")
public abstract class PersonJoined {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
