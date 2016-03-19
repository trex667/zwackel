package org.schreibvehler.boundary;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by christian on 12.03.16.
 */
public class TimeIntervalTest {
    private TimeInterval timeInterval;

    private Date start = DateUtils.addDays(new Date(), -10);
    private Date end = DateUtils.addDays(new Date(), 10);

    @Before
    public void startup() {
        timeInterval = new TimeInterval(start.getTime(), end.getTime());
    }

    @Test
    public void testGetStart() {
        assertEquals(start.getTime(), timeInterval.getStart());
    }

    @Test
    public void testGetEnd() {
        assertEquals(end.getTime(), timeInterval.getEnd());
    }
}