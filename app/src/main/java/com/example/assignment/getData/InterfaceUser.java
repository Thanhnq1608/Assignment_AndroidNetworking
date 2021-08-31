package com.example.assignment.getData;

import com.example.assignment.model.ResData;
import com.example.assignment.model.User;
import com.example.assignment.model.UserAdd;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface InterfaceUser {
    @GET("getUser")
    Call<List<User>> getJsonUser();

    @FormUrlEncoded
    @POST("insertUser")
    Call<UserAdd> addUser(@Field("username") String username,
                          @Field("password") String password,
                          @Field("phonenumber") String phonenumber,
                          @Field("gmail") String gmail);

    @FormUrlEncoded
    @POST("deleteUser")
    Call<ResData> delUser(@Field("id") int id);

    @FormUrlEncoded
    @PUT("updateUser")
    Call<ResData> updateUser(
            @Field("password") String password,
            @Field("phonenumber") String  phonenumber,
            @Field("gmail") String gmail,
            @Field("id") int id);

    @FormUrlEncoded
    @POST("changePass")
    Call<ResData> changePass(@Field("password") String password,
                             @Field("id") int id);
}
