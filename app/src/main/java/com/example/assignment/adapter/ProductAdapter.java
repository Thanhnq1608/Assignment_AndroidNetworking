package com.example.assignment.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.model.Product;
import com.example.assignment.model.RespondStatus;
import com.example.assignment.utilities.ApiService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> productList;
    private Dialog dialog;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Set view cho item tại adapter
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_layout_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        //Sắp xếp sản phẩm theo thứ tự A,B,C...
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        //Lấy data tại sản phẩm đã chọn
        Product product = productList.get(position);
        if (product == null) {
            return;
        }

        //Set image Bimap vào ImageView
        setImageBitmap(product.getAvatar(), holder.avaProd);

        holder.namePod.setText(product.getName());
        holder.priceProd.setText("Giá: " + product.getPrice());
        holder.soLuongTonProd.setText("Số lượng tồn: " + product.getSoLuongTon());
        holder.desProd.setText("Mô tả: " + product.getDescription());
        holder.section.setText(product.getName().substring(0, 1).toUpperCase());
        holder.updProd.setVisibility(View.INVISIBLE);
        holder.delProd.setVisibility(View.INVISIBLE);

        //hiện thanh tùy chọn update and delete khi giữ item
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.updProd.setVisibility(View.VISIBLE);
                holder.delProd.setVisibility(View.VISIBLE);
                holder.layout.setBackgroundResource(R.color.blue);
                return true;
            }
        });
        //ẩn thanh tùy chọn update and delete khi click item
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.updProd.setVisibility(View.INVISIBLE);
                holder.delProd.setVisibility(View.INVISIBLE);
                holder.layout.setBackgroundResource(R.color.white);
            }
        });

        //Ẩn thẻ title khi name cùng bắt đầu bằng chữ cái như nhau
        if (position > 0) {
            int i = position - 1;
            if (i < productList.size() && product.getName().substring(0, 1).equals(productList.get(i).getName().substring(0, 1))) {
                holder.section.setVisibility(View.GONE);
            }
        }

        //dialog update item

        holder.updProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialogUpdate(product);
            }
        });

        //Delete data trên database qua API
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
        aBuilder.setTitle("Bạn có muốn xóa sản phẩm này không?");
        aBuilder.setCancelable(true);
        aBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ApiService.apiService.delCall(product.getId())
                        .enqueue(new Callback<RespondStatus>() {
                            @Override
                            public void onResponse(Call<RespondStatus> call, Response<RespondStatus> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                                    productList.remove(position);
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RespondStatus> call, Throwable t) {
                                Toast.makeText(context, "Lỗi" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
        aBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Bạn đã xóa sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = aBuilder.create();
        holder.delProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    //Set image Bimap vào ImageView
    private void setImageBitmap(String ava, ImageView image) {
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // lấy dứ liệu về
                final Bitmap b = ImageLoading(ava);
                //đẩy lên giao diện
                image.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Thanh Cong", Toast.LENGTH_SHORT).show();
                        image.setImageBitmap(b);
                    }
                });
            }
        });
        t1.start();//bắt đầu thực hiện tiến trình
    }

    Bitmap b = null;

    private Bitmap ImageLoading(String str) {
        URL url;
        try {
            url = new URL(str);
            b = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    private void setDialogUpdate(Product product) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_dialog_update);
        dialog.setTitle("Update Product");
        dialog.setCancelable(true);
        ImageView imgAva;
        TextView tvName;
        EditText edtUpDes, edtUpPrice, edtUpStock;
        Button btnUp, btnCancel;
        imgAva = dialog.findViewById(R.id.update_avatarProd);
        tvName = dialog.findViewById(R.id.update_tv_nameProd);
        edtUpPrice = dialog.findViewById(R.id.update_priceProd);
        edtUpStock = dialog.findViewById(R.id.update_soLuongTonProd);
        edtUpDes = dialog.findViewById(R.id.update_desProd);
        btnUp = dialog.findViewById(R.id.update_btnUpdate);
        btnCancel = dialog.findViewById(R.id.update_btnCancel);

        tvName.setText(product.getName());
        setImageBitmap(product.getAvatar(), imgAva);

        edtUpPrice.setText(String.valueOf(product.getPrice()));
        edtUpStock.setText(String.valueOf(product.getSoLuongTon()));
        edtUpDes.setText(product.getDescription());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update item vào Database qua API
                ApiService.apiService.updateCall(
                        Integer.parseInt(edtUpPrice.getText().toString()),
                        Integer.parseInt(edtUpStock.getText().toString()),
                        edtUpDes.getText().toString(), product.getId())
                        .enqueue(new Callback<RespondStatus>() {
                            @Override
                            public void onResponse(Call<RespondStatus> call, Response<RespondStatus> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "" + response.body().getStatus() + "Product: " + product.getName(), Toast.LENGTH_SHORT).show();
                                    product.setPrice(Integer.parseInt(edtUpPrice.getText().toString()));
                                    product.setSoLuongTon(Integer.parseInt(edtUpStock.getText().toString()));
                                    product.setDescription(edtUpDes.getText().toString());
                                    notifyDataSetChanged();
                                    dialog.dismiss();

                                } else {
                                    Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RespondStatus> call, Throwable t) {
                                Log.e("Lỗi", t.getMessage());
                            }
                        });

            }
        });

        dialog.show();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView avaProd, updProd, delProd;
        TextView namePod, priceProd, soLuongTonProd, section, desProd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layoutItem);
            updProd = itemView.findViewById(R.id.btn_updateProd);
            delProd = itemView.findViewById(R.id.btn_deleteProd);
            avaProd = itemView.findViewById(R.id.avatarProd);
            namePod = itemView.findViewById(R.id.nameProd);
            priceProd = itemView.findViewById(R.id.priceProd);
            soLuongTonProd = itemView.findViewById(R.id.soLuongTonProd);
            section = itemView.findViewById(R.id.mSection);
            desProd = itemView.findViewById(R.id.desProd);
        }
    }
}
