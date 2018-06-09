package com.jeny.hang.appmusic.Service;

public class APIService {
    private static  String base_url = "https://hangitntu.000webhostapp.com/Server/";
    public static Dataservice getService(){
        return  APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }

}
