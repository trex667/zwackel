package org.zwackel.jpa.entity;

import javax.persistence.*;

@Entity
public class User {
    @Id
    private int id;
    private String shortName;

    public void setId(int id) {
        this.id = id;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("User[id=%d; shortname=%s]", id, shortName);
    }


}
