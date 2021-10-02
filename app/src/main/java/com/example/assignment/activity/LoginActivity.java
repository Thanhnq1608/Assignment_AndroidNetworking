package com.example.assignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.R;
import com.example.assignment.presenter.login.LoginInterface;
import com.example.assignment.presenter.login.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginInterface {
    private EditText edtUser, edtPass;
    private Button btnLogIn, btnLogUp;
    private TextView tvForget;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        edtUser = findViewById(R.id.login_edtUser);
        edtPass = findViewById(R.id.login_edtPass);
        btnLogIn = findViewById(R.id.login_btnLogin);
        btnLogUp = findViewById(R.id.login_btnLogup);
        tvForget = findViewById(R.id.login_tvForget);
        mLoginPresenter = new LoginPresenter(this);

        btnLogUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LogUpActivity.class);
                startActivity(intent);
            }
        });
        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassActivity.class);
                startActivity(intent);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUser.getText().toString().trim();
                String password = edtPass.getText().toString().trim();

                mLoginPresenter.login(username,password);
            }
        });
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginError() {
        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginNull() {
        Toast.makeText(LoginActivity.this, "Bạn không được bỏ trống các ô!", Toast.LENGTH_SHORT).show();
    }
}