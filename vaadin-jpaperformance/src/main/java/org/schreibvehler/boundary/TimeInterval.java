package org.schreibvehler.boundary;


public class TimeInterval {

    private final long start;
    private final long end;


    public TimeInterval(long start, long end) {
        this.start = start;
        this.end = end;
    }


    public long getEnd() {
        return end;
    }

    public long getStart() {
        return start;
    }

}
