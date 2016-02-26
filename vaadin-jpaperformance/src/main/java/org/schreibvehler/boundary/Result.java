/*
 * Result.java
 *
 * created at 25.02.2016 by froehlich <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package org.schreibvehler.boundary;


import java.util.*;


public class Result<T>
{
    private final Collection<T> list;

    private final TimeInterval timeInterval;


    public Result(TimeInterval interval, Collection<T> list)
    {
        this.list = list;
        this.timeInterval = interval;
    }


    public TimeInterval getTimeInterval()
    {
        return timeInterval;
    }


    public Collection<T> getList()
    {
        return list;
    }
}
