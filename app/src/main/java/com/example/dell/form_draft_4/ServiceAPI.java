package com.example.dell.form_draft_4;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceAPI {

    //For Reddit Login
    @POST("{user}")
    Call<CheckLogin> signIn(
            @HeaderMap Map<String, String> headers,
            @Path("user") String username,
            @Query("user") String user,
            @Query("passwd") String password,
            @Query("api_type") String type
    );

    //For Control Panel Login
    @FormUrlEncoded
    @POST("/CpServices.asmx/CheckLoginCredendials")
    Call<String> userLogin(
            @HeaderMap Map<String, String> headers,
            @Field("userId") String username,
            @Field("password") String password
    );

}
