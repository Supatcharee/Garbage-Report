package com.example.letusknow;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;


/**
 * Created by Angel Beast's on 17/3/2558.
 */
public interface ApiService {

    @GET("/LoginSW.php")
    void getPasscodeWithCallback(Callback<List<Passcode>> callback);

}
