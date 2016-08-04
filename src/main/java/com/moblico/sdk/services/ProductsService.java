package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Product;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsService {

    private ProductsService() {

    }

    public static void findAll(final String filters, final String productTypes, final Callback<List<Product>> callback) {
        final Map<String, String> params = new HashMap<>();
        if(filters != null) params.put("filters", filters);
        if(productTypes != null) params.put("productTypes", productTypes);

        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("products", params, new ErrorForwardingCallback<String>(callback) {

                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Product>>() { }.getType();
                        List<Product> products = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(products);
                    }
                });
            }
        } );
    }
}
