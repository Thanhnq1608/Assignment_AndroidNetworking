package com.example.assignment.presenter.forgetPass;

import android.util.Log;

import com.example.assignment.model.RespondStatus;
import com.example.assignment.model.User;
import com.example.assignment.utilities.ApiService;
import com.example.assignment.utilities.GetAllUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordPresenter {
    private ForgetPasswordInterface mForgetPasswordInterface;
    private GetAllUser mGetAllUser;
    private ArrayList<User> list = new ArrayList<>();

    public ForgetPasswordPresenter(ForgetPasswordInterface mForgetPasswordInterface) {
        this.mForgetPasswordInterface = mForgetPasswordInterface;
    }

    public void changePassword(User user, String rePass) {
        boolean temp = true;
        list.addAll(mGetAllUser.getListAllUser());
        if (user.isValidUsername() && user.isValidPhone()
                && user.isValidPass() && !rePass.trim().equalsIgnoreCase("")) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUsername().equalsIgnoreCase(user.getUsername())) {
                    temp = false;
                    if (!list.get(i).getPhonenumber().equalsIgnoreCase(user.getPhonenumber())) {
                        mForgetPasswordInterface.changePasswordError();
                    }
                } else if (!rePass.trim().equals(user.getPassword())) {
                    mForgetPasswordInterface.changePasswordError();
                } else {
                    ApiService.apiService.changePass(user.getPassword(), list.get(i).getId())
                            .enqueue(new Callback<RespondStatus>() {
                                @Override
                                public void onResponse(Call<RespondStatus> call, Response<RespondStatus> response) {
                                    if (response.isSuccessful()) {
                                        mForgetPasswordInterface.changePasswordSuccess();
                                    } else {
                                        Log.e("ForgetPass", "" + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<RespondStatus> call, Throwable t) {
                                    Log.e("Error ForgetPass", t.getMessage());
                                }
                            });
                }
                break;
            }
            if (temp == true) {
                mForgetPasswordInterface.changePasswordError();
            }
        } else {
            mForgetPasswordInterface.changePasswordNull();
        }
    }


}
