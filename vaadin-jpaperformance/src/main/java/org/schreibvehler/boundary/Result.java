/*
 * Result.java
 *
 * created at 25.02.2016 by froehlich <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package org.schreibvehler.boundary;


import java.util.List;


public class Result<T>
{
    private final List<T> list;

    private final TimeInterval timeInterval;


    public Result(TimeInterval interval, List<T> list)
    {
        this.list = list;
        this.timeInterval = interval;
    }


    public TimeInterval getTimeInterval()
    {
        return timeInterval;
    }


    public List<T> getList()
    {
        return list;
    }
}
