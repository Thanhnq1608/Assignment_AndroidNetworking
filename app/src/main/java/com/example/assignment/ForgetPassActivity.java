package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.getData.InterfaceUser;
import com.example.assignment.model.ResData;
import com.example.assignment.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetPassActivity extends AppCompatActivity {

    EditText userForget, phoneForget, passForget, rePassForget;
    Button btnCom, btnCan;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_forget_password);

        userForget = findViewById(R.id.forget_edtUser);
        phoneForget = findViewById(R.id.forget_edtPhone);
        passForget = findViewById(R.id.forget_edtPass);
        rePassForget = findViewById(R.id.forget_edtRePass);
        btnCom = findViewById(R.id.forget_btnComfirm);
        btnCan = findViewById(R.id.forget_btnCancel);

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
                checkUser();
            }
        });

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
    }

    public void checkUser() {
        boolean temp = true;
        if (userForget.getText().toString().trim().equalsIgnoreCase("") || phoneForget.getText().toString().trim().equalsIgnoreCase("")
                || passForget.getText().toString().trim().equalsIgnoreCase("") || rePassForget.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Bạn không được bỏ trống dữ liệu!", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUsername().equalsIgnoreCase(userForget.getText().toString())) {
                    temp = false;
                    if (userList.get(i).getPhonenumber().equalsIgnoreCase(phoneForget.getText().toString())) {
                        if (rePassForget.getText().toString().trim().equals(passForget.getText().toString().trim())) {
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://infinite-waters-71846.herokuapp.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            InterfaceUser interfaceUser = retrofit.create(InterfaceUser.class);

                            Call<ResData> call1 = interfaceUser.changePass(passForget.getText().toString(), userList.get(i).getId());
                            call1.enqueue(new Callback<ResData>() {
                                @Override
                                public void onResponse(Call<ResData> call, Response<ResData> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(ForgetPassActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Log.e("ForgetPass", "" + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResData> call, Throwable t) {
                                    Log.e("Error ForgetPass", t.getMessage());
                                }
                            });
                        } else {
                            Toast.makeText(this, "Mật khẩu nhập lại không trùng khớp!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Số điện thoại không chính xác!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
            if (temp == true) {
                Toast.makeText(this, "Username không tồn tại!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}