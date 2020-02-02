package com.example.project1;

import java.io.Serializable;

public class CgetOwner implements Serializable
{
    String OwnerName;

    public CgetOwner()
    {
    }

    public CgetOwner(String ownerName)
    {
        OwnerName = ownerName;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }
}
