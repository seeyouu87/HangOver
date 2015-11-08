package com.hangover;

import java.io.Serializable;

/**
 * Created by leechunhoe on 7/11/15.
 */
public class User implements Serializable
{
    private int id;
    private String name;
    private String gender;
    private String dob;
    private String email;
    private String phone;
    private String ImgUrl;
    private String  address;
    private String geocode;
    private String emgcontact;
    private String emgrelationship;
    private String fbtoken;
    private String ubertoken;
    private String status;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getDob()
    {
        return dob;
    }

    public void setDob(String dob)
    {
        this.dob = dob;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getImgUrl()
    {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        ImgUrl = imgUrl;
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

    public String getEmgcontact()
    {
        return emgcontact;
    }

    public void setEmgcontact(String emgcontact)
    {
        this.emgcontact = emgcontact;
    }

    public String getEmgrelationship()
    {
        return emgrelationship;
    }

    public void setEmgrelationship(String emgrelationship)
    {
        this.emgrelationship = emgrelationship;
    }

    public String getFbtoken()
    {
        return fbtoken;
    }

    public void setFbtoken(String fbtoken)
    {
        this.fbtoken = fbtoken;
    }

    public String getUbertoken()
    {
        return ubertoken;
    }

    public void setUbertoken(String ubertoken)
    {
        this.ubertoken = ubertoken;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public boolean equals(Object o)
    {
        return o != null && this.getId() == ((User) o).getId();
    }
}
