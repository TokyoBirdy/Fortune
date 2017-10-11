package info.androidhive.retrofit.rest;

import java.util.List;

import info.androidhive.retrofit.model.Fortune;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("jbtcp")
    Call<List<Fortune>>getItems();

}
