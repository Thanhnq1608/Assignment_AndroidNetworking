package com.example.assignment.getData;

import com.example.assignment.model.ProdAdd;
import com.example.assignment.model.Product;
import com.example.assignment.model.ResData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface InterfaceProd {
    @GET("getProd")
    Call<List<Product>> getJsonProd();

    @FormUrlEncoded
    @POST("insertProd")
    Call<ProdAdd> addProduct(@Field("avatar") String avatar,
                             @Field("name") String name,
                             @Field("price") int price,
                             @Field("soLuongTon") int soLuongTon,
                             @Field("description") String description);

    @FormUrlEncoded
    @POST("deleteProd")
    Call<ResData> delCall(@Field("id") int id);

    @FormUrlEncoded
    @PUT("updateProd")
    Call<ResData> updateCall(
            @Field("price") int price,
            @Field("soLuongTon") int soLuongTon,
            @Field("description") String description,
            @Field("id") int id);

}
