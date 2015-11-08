package com.hangover;

/**
 * Created by leechunhoe on 8/11/15.
 */
public class Event
{
    private int id;
    private String name;
    private String description;
    private String date;
    private String address;
    private String geocode;
    private String eventphoto;
    private double eventprice;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getGeocode()
    {
        return geocode;
    }

    public void setGeocode(String geocode)
    {
        this.geocode = geocode;
    }

    public String getEventphoto()
    {
        return eventphoto;
    }

    public void setEventphoto(String eventphoto)
    {
        this.eventphoto = eventphoto;
    }

    public double getEventprice()
    {
        return eventprice;
    }

    public void setEventprice(double eventprice)
    {
        this.eventprice = eventprice;
    }
}
