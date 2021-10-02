package com.example.assignment.presenter.insertProduct;

import com.example.assignment.model.Product;
import com.example.assignment.model.RespondStatus;
import com.example.assignment.utilities.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertPresenter {
    private InsertInterface mInsertInterface;

    public InsertPresenter(InsertInterface mInsertInterface) {
        this.mInsertInterface = mInsertInterface;
    }

    public void insertProductData(Product product) {
        if (product.isValidAva()&& product.isValidName()
                && product.isValidPrice()&& product.isValidSoluongton()
                && product.isValidDes()){
            ApiService.apiService.addProduct(
                    product.getAvatar(),
                    product.getName(),
                    product.getPrice(),
                    product.getSoLuongTon(),
                    product.getDescription())
                    .enqueue(new Callback<RespondStatus>() {
                        @Override
                        public void onResponse(Call<RespondStatus> call, Response<RespondStatus> response) {
                            if (response.isSuccessful()) {
                                mInsertInterface.insertProductSuccess();
                            } else {
                                mInsertInterface.insertProductFailBecauseAPI();
                            }
                        }

                        @Override
                        public void onFailure(Call<RespondStatus> call, Throwable t) {
                            mInsertInterface.insertProductFailBecauseAPI();
                        }
                    });
        }else {
            mInsertInterface.insertProductError();
        }
    }
}
