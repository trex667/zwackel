package de.schreibvehler.accounting.backend.data;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class Evidence implements Serializable {

    private static final long serialVersionUID = 4769762068328784812L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EvidenceType getType() {
        return type;
    }

    public void setType(EvidenceType type) {
        this.type = type;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    private Integer id;
    
    @NotNull
    private EvidenceType type;

    private Float value;
    
    private Date date;
    
    private String description;
}
