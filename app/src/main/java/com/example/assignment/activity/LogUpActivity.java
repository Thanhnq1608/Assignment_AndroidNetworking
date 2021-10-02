package com.example.assignment.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.assignment.R;
import com.example.assignment.model.User;
import com.example.assignment.presenter.logup.LogupInterface;
import com.example.assignment.presenter.logup.LogupPresenter;

public class LogUpActivity extends AppCompatActivity implements LogupInterface {
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    private static final String LOG_TAG = "AndroidExample";

    private Button btnCom, btnCancel;
    private EditText edtUser, edtPass, edtRePass, edtPhone, edtGmail;
    private Dialog dialog;

    private LogupPresenter mLogupPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_up);

        btnCom = findViewById(R.id.logup_btnComfirm);
        btnCancel = findViewById(R.id.logup_btnCancel);
        edtUser = findViewById(R.id.logup_edtUser);
        edtPass = findViewById(R.id.logup_edtPass);
        edtRePass = findViewById(R.id.logup_edtRePass);
        edtPhone = findViewById(R.id.logup_edtPhone);
        edtGmail = findViewById(R.id.logup_edtGmail);
        mLogupPresenter = new LogupPresenter(this);


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
                askPermissionAndSendSMS();
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

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogupPresenter.sendOTP(edtPhone.getText().toString().trim());
            }
        });
        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(1,edtUser.getText().toString().trim(),
                        edtPass.getText().toString().trim(),
                        1,edtPhone.getText().toString().trim(),
                        edtGmail.getText().toString().trim());
                mLogupPresenter.insertUser(user,edtRePass.getText().toString(),edtOTP.getText().toString().trim());
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

    @Override
    public void logUpSuccess() {
        Toast.makeText(LogUpActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LogUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void logUpError() {
        Toast.makeText(LogUpActivity.this, "Đăng kí không thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logUpNull() {
        Toast.makeText(this, "Bạn không được bỏ trống dữ liệu!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logUpSendOTPSuccess() {
        Toast.makeText(this, "Mã OTP đã được gửi đi!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logUpCheckUsername() {
        Toast.makeText(LogUpActivity.this, "Username đã tồn tại!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logUpCheckRepeatPass() {
        Toast.makeText(LogUpActivity.this, "Mật khẩu nhập lại không giống nhau", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logUpCheckOTP() {
        Toast.makeText(LogUpActivity.this, "Mã OTP không chính xác!", Toast.LENGTH_SHORT).show();
    }
}