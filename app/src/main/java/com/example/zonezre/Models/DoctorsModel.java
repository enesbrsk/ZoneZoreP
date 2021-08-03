package com.example.zonezre.Models;

import java.util.List;

public class DoctorsModel{
    private List<DoctorsItem> doctors;

    public void setDoctors(List<DoctorsItem> doctors){
        this.doctors = doctors;
    }

    public List<DoctorsItem> getDoctors(){
        return doctors;
    }

    @Override
     public String toString(){
        return 
            "DoctorsModel{" + 
            "doctors = '" + doctors + '\'' + 
            "}";
        }
}