package com.example.falhafez;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface GetData {
    String BaseUrl="https://ganjgah.ir/api/ganjoor/poets/";
    @GET ("poets.json")
    Call<List<poetsdetails>> getpoets();

}
