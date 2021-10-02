package com.example.assignment.utilities;

import com.example.assignment.model.Product;
import com.example.assignment.model.RespondStatus;
import com.example.assignment.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://apiandroidnetworking.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET("getUser")
    Call<List<User>> getJsonUser();

    @FormUrlEncoded
    @POST("insertUser")
    Call<RespondStatus> addUser(@Field("username") String username,
                          @Field("password") String password,
                          @Field("position") int position,
                          @Field("phonenumber") String phonenumber,
                          @Field("gmail") String gmail);

    @FormUrlEncoded
    @POST("deleteUser")
    Call<RespondStatus> delUser(@Field("id") int id);

    @FormUrlEncoded
    @PUT("updateUser")
    Call<RespondStatus> updateUser(
            @Field("password") String password,
            @Field("phonenumber") String phonenumber,
            @Field("gmail") String gmail,
            @Field("id") int id);

    @FormUrlEncoded
    @POST("changePass")
    Call<RespondStatus> changePass(@Field("password") String password,
                                   @Field("id") int id);

    //Product==============================================
    @GET("getProd")
    Call<List<Product>> getJsonProd();

    @FormUrlEncoded
    @POST("insertProd")
    Call<RespondStatus> addProduct(@Field("avatar") String avatar,
                             @Field("name") String name,
                             @Field("price") int price,
                             @Field("soLuongTon") int soLuongTon,
                             @Field("description") String description);

    @FormUrlEncoded
    @POST("deleteProd")
    Call<RespondStatus> delCall(@Field("id") int id);

    @FormUrlEncoded
    @PUT("updateProd")
    Call<RespondStatus> updateCall(
            @Field("price") int price,
            @Field("soLuongTon") int soLuongTon,
            @Field("description") String description,
            @Field("id") int id);

}
