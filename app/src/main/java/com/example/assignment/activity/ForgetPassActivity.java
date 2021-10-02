package com.example.assignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.R;
import com.example.assignment.model.User;
import com.example.assignment.presenter.forgetPass.ForgetPasswordInterface;
import com.example.assignment.presenter.forgetPass.ForgetPasswordPresenter;

public class ForgetPassActivity extends AppCompatActivity implements ForgetPasswordInterface {

    private EditText passwordNew, phoneForget, userForget, rePasswordNew;
    private Button btnCom, btnCan;
    private ForgetPasswordPresenter mForgetPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_forget_password);
        
        //Khai báo ánh xạ
        mapped();
        mForgetPasswordPresenter=new ForgetPasswordPresenter(this);

        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user =new User(1,userForget.getText().toString().trim(),
                        passwordNew.getText().toString().trim(),
                        1,
                        phoneForget.getText().toString().trim(),
                        "");
                mForgetPasswordPresenter.changePassword(user,rePasswordNew.getText().toString().trim());
            }
        });
        
    }
    
    private void mapped(){
        userForget = findViewById(R.id.forget_edtUser);
        phoneForget = findViewById(R.id.forget_edtPhone);
        passwordNew = findViewById(R.id.forget_edtPass);
        rePasswordNew = findViewById(R.id.forget_edtRePass);
        btnCom = findViewById(R.id.forget_btnComfirm);
        btnCan = findViewById(R.id.forget_btnCancel);
    }

    @Override
    public void changePasswordSuccess() {
        Toast.makeText(ForgetPassActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void changePasswordError() {
        Toast.makeText(this, "Thông tin bạn nhập không chính xác!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changePasswordNull() {
        Toast.makeText(this, "Bạn không được bỏ trống dữ liệu!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changePasswordFailBecauseAPI() {
        Toast.makeText(this, "Server đang lỗi,Bạn hãy thử lại sau ít phút!", Toast.LENGTH_SHORT).show();
    }
}