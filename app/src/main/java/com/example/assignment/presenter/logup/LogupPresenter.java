package com.example.assignment.presenter.logup;

import android.telephony.SmsManager;
import android.util.Log;

import com.example.assignment.model.RespondStatus;
import com.example.assignment.model.User;
import com.example.assignment.utilities.ApiService;
import com.example.assignment.utilities.GetAllUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogupPresenter {
    private LogupInterface mLogupInterface;
    private GetAllUser mGetAllUser;

    public LogupPresenter(LogupInterface mLogupInterface) {
        this.mLogupInterface = mLogupInterface;
    }

    public void insertUser(User user, String rePass, String otp) {

        if (!(user.isValidUsername() && user.isValidPass() && user.isValidPhone()
                && user.isValidGmail() && rePass != null)) {
            mLogupInterface.logUpNull();
        } else if (mGetAllUser.getListAllUser() == null) {
            Log.e("Erorr", "Mất kết nối với API");
        } else {
            for (int i = 0; i < mGetAllUser.getListAllUser().size(); i++) {
                if (user.getUsername().equalsIgnoreCase(mGetAllUser.getListAllUser().get(i).getUsername())) {
                    mLogupInterface.logUpCheckUsername();
                    break;
                } else {
                    if (i + 1 == mGetAllUser.getListAllUser().size()) {
                        if (user.getPassword().equals(rePass.trim())) {
                            mLogupInterface.logUpCheckRepeatPass();
                        } else if (checkOtp(otp)) {
                            mLogupInterface.logUpCheckOTP();
                        } else {
                            addUser(user);
                            mLogupInterface.logUpSuccess();
                        }
                    }
                }
            }
        }
    }

    private void addUser(User user) {
        ApiService.apiService.addUser(
                user.getUsername(),
                user.getPassword(),
                user.getPosition(),
                user.getPhonenumber(),
                user.getGmail())
                .enqueue(new Callback<RespondStatus>() {
                    @Override
                    public void onResponse(Call<RespondStatus> call, Response<RespondStatus> response) {
                        if (response.isSuccessful()) {
                            mLogupInterface.logUpSuccess();
                        } else {
                            Log.e("Error LogupPresenter", "" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<RespondStatus> call, Throwable t) {
                        Log.e("Error", "" + t.getMessage());
                    }
                });
    }

    private final String[] testOTP = {""};

    public void sendOTP(String phone) {
        double temp = Math.floor(Math.random() * (10000 - 1000 + 1) + 1000);
        String otp = String.valueOf(temp).substring(0, 4);
        testOTP[0] = otp;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, "Mã OTP của bạn là: " + otp, null, null);
            mLogupInterface.logUpSendOTPSuccess();
        } catch (Exception e) {
            Log.e("send Sms: ", "gửi không thành công");
            e.printStackTrace();
        }
    }

    private boolean checkOtp(String otp) {
        boolean temp;
        if (otp != null) {
            if (otp.equalsIgnoreCase(testOTP[0])) {
                mLogupInterface.logUpSendOTPSuccess();
                temp=false;
            } else {
                temp=true;
            }
        } else {
            temp=true;
        }
        return temp;
    }

}
