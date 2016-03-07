package org.schreibvehler.complexV1;


import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.FIELD)
public class OrganizationStructureComplexV1
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @OneToOne
    private OrganizationComplexV1 parent;

    @OneToOne
    private OrganizationComplexV1 child;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date begin;

    @Temporal(TemporalType.DATE)
    private Date end;


    public Integer getId()
    {
        return id;
    }


    public void setId(Integer id)
    {
        this.id = id;
    }


    public Date getBegin()
    {
        return begin;
    }


    public void setBegin(Date begin)
    {
        this.begin = begin;
    }


    public Date getEnd()
    {
        return end;
    }


    public void setEnd(Date end)
    {
        this.end = end;
    }


    public OrganizationComplexV1 getParent()
    {
        return parent;
    }


    public void setParent(OrganizationComplexV1 parent)
    {
        this.parent = parent;
    }


    public OrganizationComplexV1 getChild()
    {
        return child;
    }


    public void setChild(OrganizationComplexV1 child)
    {
        this.child = child;
    }
}
