package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.getData.InterfaceUser;
import com.example.assignment.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private Button btnLogIn, btnLogUp;
    private TextView tvForget;
    private List<User> userList = new ArrayList<>();

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
        userList=null;

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
                validateForm();
            }
        });
    }

    private void validateForm() {
        boolean temp=true;
        if (edtUser.getText().toString().trim().equals("") || edtPass.getText().toString().trim().equals("")) {
            Toast.makeText(LoginActivity.this, "Bạn không được bỏ trống các ô!", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < userList.size(); i++) {
                if (edtUser.getText().toString().equalsIgnoreCase(userList.get(i).getUsername())) {
                    if (edtPass.getText().toString().equalsIgnoreCase(userList.get(i).getPassword())) {
                        temp=false;
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                        edtPass.setBackgroundResource(R.color.red);
                        break;
                    }
                }
            }
            if (temp){
                Toast.makeText(LoginActivity.this, "Tài Khoản không tồn tại!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}