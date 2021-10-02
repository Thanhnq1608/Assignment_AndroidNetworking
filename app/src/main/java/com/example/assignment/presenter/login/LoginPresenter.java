package com.example.assignment.presenter.login;

import android.util.Log;

import com.example.assignment.utilities.ApiService;
import com.example.assignment.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private LoginInterface mLoginInterface;
    private List<User> userList = new ArrayList<>();

    public LoginPresenter(LoginInterface loginInterface) {
        this.mLoginInterface = loginInterface;
    }

    public void login(String user,String pass) {
        ApiService.apiService.getJsonUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        userList = new ArrayList<User>(response.body());
                        Log.e("Size", "" + userList.size());
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

        if (user!=null && pass!=null) {
            for (int i = 0; i < userList.size(); i++) {
                if (user.equalsIgnoreCase(userList.get(i).getUsername())) {
                    if (pass.equalsIgnoreCase(userList.get(i).getPassword())) {
                        mLoginInterface.loginSuccess();
                    } else {
                        mLoginInterface.loginError();
                        break;
                    }
                }
            }
        } else {
            mLoginInterface.loginNull();
        }
    }
}
