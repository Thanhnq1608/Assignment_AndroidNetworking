package com.example.assignment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.getData.InterfaceProd;
import com.example.assignment.model.ProdAdd;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertActivity extends AppCompatActivity {
    ImageView imgAvaProd;
    EditText edtName, edtPrice, edtStock,edtAva,edtDes;
    Button btnInsert, btnCancel;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        edtAva=findViewById(R.id.insert_avatarProd);
        edtDes=findViewById(R.id.insert_desProd);
        edtName = findViewById(R.id.insert_nameProd);
        edtPrice = findViewById(R.id.insert_priceProd);
        edtStock = findViewById(R.id.insert_soLuongProd);
        btnInsert = findViewById(R.id.insert_btnInsert);
        btnCancel = findViewById(R.id.insert_btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callData();
            }
        });

    }

    public void callData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://infinite-waters-71846.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceProd interfaceProd = retrofit.create(InterfaceProd.class);

        Call<ProdAdd> prodAddCall =interfaceProd.addProduct(
                edtAva.getText().toString(),
                edtName.getText().toString(),
                Integer.parseInt(edtPrice.getText().toString()),
                Integer.parseInt(edtStock.getText().toString()),
                edtDes.getText().toString());
        prodAddCall.enqueue(new Callback<ProdAdd>() {
            @Override
            public void onResponse(Call<ProdAdd> call, Response<ProdAdd> response) {
                Toast.makeText(InsertActivity.this, ""+response.body().getStatus(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ProdAdd> call, Throwable t) {
                Toast.makeText(InsertActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}