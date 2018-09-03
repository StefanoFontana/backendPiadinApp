package com.piadinapp.backendpiadinapp.model;

public class Fascia {
    private String timeBegin;
    private String timeEnd;
    private String dayOfWeek;
    private float multiplier;

    public Fascia(String begin,String end,String day,float mult)
    {
        timeBegin = begin;
        timeEnd = end;
        dayOfWeek = day;
        multiplier = mult;
    }

    public String getTimeBegin()
    {
        return timeBegin;
    }
    public String getTimeEnd()
    {
        return timeEnd;
    }
    public String getDayOfWeek()
    {
        return dayOfWeek;
    }
    public float getMultiplier()
    {
        return multiplier;
    }
}
