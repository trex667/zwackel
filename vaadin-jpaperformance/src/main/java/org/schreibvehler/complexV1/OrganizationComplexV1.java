package org.schreibvehler.complexV1;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.FIELD)
public class OrganizationComplexV1 {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Type type;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date begin;

    @Temporal(TemporalType.DATE)
    private Date end;

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        FACILITY, DEVELOPMENT, PRODUCTION, MARKETING, FINANCE, HUMAN_RESOURCES, ADMINISTRATION, DEPARTMENT
    }

    @Override
    public String toString() {
        return String.format("%s[%d]: %s [%s - %s]", type.name(), id, name, begin.toString(),
                end != null ? end.toString() : "");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OrganizationComplexV1)) {
            return false;
        }
        OrganizationComplexV1 other = (OrganizationComplexV1) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
