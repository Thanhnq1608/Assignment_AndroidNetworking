package com.example.assignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.adapter.ProductAdapter;
import com.example.assignment.utilities.GetAllProduct;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recProduct;
    private ProductAdapter adapter;
    private GetAllProduct mGetAllProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Products");

        recProduct = findViewById(R.id.recProd);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recProduct.setLayoutManager(gridLayoutManager);
        adapter = new ProductAdapter(MainActivity.this, mGetAllProduct.getListAllProduct());
        adapter.notifyDataSetChanged();
        recProduct.setAdapter(adapter);


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
}