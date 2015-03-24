package org.zwackel.cdi.entity;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.FIELD)
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @NotNull
    private String name;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Credential> credentials;
}
