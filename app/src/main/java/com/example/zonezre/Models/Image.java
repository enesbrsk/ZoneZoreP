package com.example.zonezre.Models;

public class Image{
    private String url;

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    @Override
     public String toString(){
        return 
            "Image{" + 
            "url = '" + url + '\'' + 
            "}";
        }
}
