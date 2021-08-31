package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.adapter.ProductAdapter;
import com.example.assignment.getData.InterfaceProd;
import com.example.assignment.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recProduct;
    ProductAdapter adapter;
    ArrayList<Product> list = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Products");

        recProduct = findViewById(R.id.recProd);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recProduct.setLayoutManager(gridLayoutManager);
        loadJson();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.insertProd_itemMenu) {
            Intent intent = new Intent(MainActivity.this, InsertActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void loadJson() {
        //tao doi tuong
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://infinite-waters-71846.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //lay request
        InterfaceProd interfaceGetProd = retrofit.create(InterfaceProd.class);
        //xu ly request
        Call<List<Product>> call = interfaceGetProd.getJsonProd();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        list=new ArrayList<Product>(response.body());
                        adapter = new ProductAdapter(MainActivity.this, list);
                        adapter.notifyDataSetChanged();
                        recProduct.setAdapter(adapter);

                    }else {
                        Toast.makeText(MainActivity.this, "Database không có dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.e("Error",""+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Lỗi: ", t.getMessage());
            }
        });
    }

}