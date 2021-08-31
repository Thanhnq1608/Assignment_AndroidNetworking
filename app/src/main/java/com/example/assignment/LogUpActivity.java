package com.example.assignment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.assignment.getData.InterfaceUser;
import com.example.assignment.model.User;
import com.example.assignment.model.UserAdd;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogUpActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    Button btnCom, btnCancel;
    private static final String LOG_TAG = "AndroidExample";
    EditText edtUser, edtPass, edtRePass, edtPhone, edtGmail;
    private List<User> userList = new ArrayList<>();
    TextView errUser, errRePass;
    Dialog dialog;
    boolean checkOTP = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_up);

        checkOTP = false;
        errUser = findViewById(R.id.logup_err_user);
        errRePass = findViewById(R.id.logup_err_rePass);
        btnCom = findViewById(R.id.logup_btnComfirm);
        btnCancel = findViewById(R.id.logup_btnCancel);
        edtUser = findViewById(R.id.logup_edtUser);
        edtPass = findViewById(R.id.logup_edtPass);
        edtRePass = findViewById(R.id.logup_edtRePass);
        edtPhone = findViewById(R.id.logup_edtPhone);
        edtGmail = findViewById(R.id.logup_edtGmail);
        userList = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://infinite-waters-71846.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceUser interfaceUser = retrofit.create(InterfaceUser.class);
        Call<List<User>> call = interfaceUser.getJsonUser();
        call.enqueue(new Callback<List<User>>() {
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertUser();
            }
        });
    }

    public void insertUser() {

        if (edtUser.getText().toString().trim().equals("") || edtPass.getText().toString().trim().equals("")
                || edtRePass.getText().toString().trim().equals("") || edtPhone.getText().toString().trim().equals("")
                || edtGmail.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Bạn không được bỏ trống dữ liệu!", Toast.LENGTH_SHORT).show();
        } else {
            askPermissionAndSendSMS();
        }

    }

    public void addUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://infinite-waters-71846.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //lay request
        InterfaceUser interfaceUser = retrofit.create(InterfaceUser.class);
        //xu ly request
        Call<UserAdd> call = interfaceUser.addUser(
                edtUser.getText().toString(),
                edtPass.getText().toString(),
                edtPhone.getText().toString(),
                edtGmail.getText().toString());
        call.enqueue(new Callback<UserAdd>() {
            @Override
            public void onResponse(Call<UserAdd> call, Response<UserAdd> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LogUpActivity.this, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogUpActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LogUpActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAdd> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }

    private void askPermissionAndSendSMS() {

        // With Android Level >= 23, you have to ask the user
        // for permission to send SMS.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have send SMS permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSION_REQUEST_CODE_SEND_SMS
                );
                return;
            }
        }
        this.sendOTP();
    }

    public void sendOTP() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_comfirm);
        dialog.setCancelable(false);
        dialog.setTitle("Xác nhận số điện thoại hoặc email");
        Button btnSMS, btnEmail, btnCom;
        EditText edtOTP;
        btnSMS = dialog.findViewById(R.id.btn_sendSMS);
        btnCom = dialog.findViewById(R.id.btn_comfirmOTP);
        btnEmail = dialog.findViewById(R.id.btn_sendEmail);
        edtOTP = dialog.findViewById(R.id.edt_maOTP);
        final String[] testOTP = {""};

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double temp = Math.floor(Math.random() * (10000 - 1000 + 1) + 1000);
                String otp = String.valueOf(temp).substring(0, 4);
                testOTP[0] = otp;

                Log.e("otp", "" + otp);
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(edtPhone.getText().toString(), null, "Mã OTP của bạn là: " + otp, null, null);
                    Log.e("send Sms: ", "gửi thành công");
                } catch (Exception e) {
                    Log.e("send Sms: ", "gửi không thành công");
                    e.printStackTrace();
                }
            }
        });
        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtOTP.getText().toString() != null) {
                    if (edtOTP.getText().toString().equalsIgnoreCase(testOTP[0])) {
                        Toast.makeText(LogUpActivity.this, "xác nhận số điện thoại thành công", Toast.LENGTH_SHORT).show();
                        if (userList != null) {
                            for (int i = 0; i < userList.size(); i++) {
                                if (!edtUser.getText().toString().equalsIgnoreCase(userList.get(i).getUsername())) {
                                    if (i + 1 == userList.size()) {
                                        if (edtPass.getText().toString().trim().equals(edtRePass.getText().toString().trim())) {
                                            addUser();
                                            Intent intent = new Intent(LogUpActivity.this,LoginActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(LogUpActivity.this, "Mật khẩu nhập lại không giống nhau", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(LogUpActivity.this, "Username dđã tồn tại!", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        } else {
                            Log.e("Erorr", "Mất kết nối với API");
                        }
                        dialog.dismiss();
                    } else {
                        Toast.makeText(LogUpActivity.this, "Mã OTP không chính xác!", Toast.LENGTH_SHORT).show();
                        checkOTP = false;

                    }
                } else {
                    Toast.makeText(LogUpActivity.this, "Bạn chưa nhập mã OTP!", Toast.LENGTH_SHORT).show();
                    checkOTP = false;
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE_SEND_SMS: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (SEND_SMS).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i(LOG_TAG, "Permission granted!");
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();

                    this.sendOTP();
                }
                // Cancelled or denied.
                else {
                    Log.i(LOG_TAG, "Permission denied!");
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

}