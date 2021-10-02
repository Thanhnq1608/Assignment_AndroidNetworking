package com.example.assignment.utilities;

import android.util.Log;

import com.example.assignment.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllUser {
    public ArrayList<User> getListAllUser(){
        ArrayList<User> list =new ArrayList<>();
        ApiService.apiService.getJsonUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        list.addAll(response.body());
                        Log.e("Size", "" + list.size());
                    } else {
                        Log.e("Loi", "" + response.code());
                    }
                } else {
                    Log.e("Error", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Error:", "" + t.getMessage());
            }
        });
        return list;
    }
}
