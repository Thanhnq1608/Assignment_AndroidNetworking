package com.example.assignment.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.R;
import com.example.assignment.model.Product;
import com.example.assignment.presenter.insertProduct.InsertInterface;
import com.example.assignment.presenter.insertProduct.InsertPresenter;

public class InsertActivity extends AppCompatActivity implements InsertInterface {
    private ImageView imgAvaProd;
    private EditText edtName, edtPrice, edtStock,edtAva,edtDes;
    private Button btnInsert, btnCancel;
    private Dialog dialog;
    private InsertPresenter mInsertPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        mapped();
        mInsertPresenter = new InsertPresenter(this);

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
                Product product = new Product(1,edtAva.getText().toString().trim(),
                        edtName.getText().toString().trim(),
                        Integer.parseInt(edtPrice.getText().toString().trim()),
                        Integer.parseInt(edtStock.getText().toString().trim()),
                        edtDes.getText().toString().trim());
                mInsertPresenter.insertProductData(product);
            }
        });

    }

    private void mapped(){
        edtAva=findViewById(R.id.insert_avatarProd);
        edtDes=findViewById(R.id.insert_desProd);
        edtName = findViewById(R.id.insert_nameProd);
        edtPrice = findViewById(R.id.insert_priceProd);
        edtStock = findViewById(R.id.insert_soLuongProd);
        btnInsert = findViewById(R.id.insert_btnInsert);
        btnCancel = findViewById(R.id.insert_btnCancel);
    }


    @Override
    public void insertProductSuccess() {
        Toast.makeText(this, "Bạn thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
        Intent intent =new Intent(InsertActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void insertProductError() {
        Toast.makeText(this, "Các trường dữ liệu không được bỏ trống", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void insertProductFailBecauseAPI() {
        Toast.makeText(this, "Server phát sinh lỗi, Bạn hãy thử lại sau ít phút!", Toast.LENGTH_SHORT).show();
    }
}