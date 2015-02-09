package org.zwackel.jpa.entity;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class Aircraft {
    
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    private AircraftType type;


}
