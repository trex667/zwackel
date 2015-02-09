package org.zwackel.jpa.entity;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class Flight {

    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private Integer id;
    
}
