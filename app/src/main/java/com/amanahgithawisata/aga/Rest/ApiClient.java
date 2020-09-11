package com.amanahgithawisata.aga.Rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public  static Retrofit retrofit;
    public  static final String BASE_URL = "http://kaffah.amanahgitha.com/~androidwisata/";


    public static Retrofit getRetrofitInstance(){

        if( retrofit == null ){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
