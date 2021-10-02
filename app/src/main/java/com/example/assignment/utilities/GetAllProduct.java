package com.example.assignment.utilities;

import android.util.Log;

import com.example.assignment.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllProduct {
    public ArrayList<Product> getListAllProduct() {
        ArrayList<Product> list = new ArrayList<>();
        ApiService.apiService.getJsonProd().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    list.addAll(response.body());
                } else {
                    Log.e("GetAllProductError", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("GetAllProductError", t.getMessage());
            }
        });
        return list;
    }
}
