package com.moblico.sdk.services;

import android.support.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScanToListService {

    public static class Product {
        public final String name;
        public final int quantity;
        public final String note;

        public Product(String name, int quantity, String note) {
            this.name = name;
            this.quantity = quantity;
            this.note = note;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Product product = (Product) o;

            return name.equals(product.name);
        }
    }
    private ScanToListService() {
    }

    public static void SendOrder(final String emailAddress, final Map<String, String> profileFields,
                                 final Map<String, String> customProfileFields,
                                 final Set<Product> products,
                                 final String comments,
                                 final Callback<String> callback) {

        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                JsonObject obj = new JsonObject();
                for (Map.Entry<String, String> field : profileFields.entrySet()) {
                    // primary field names need tweaking:
                    String name = field.getKey();
                    name = name.replaceAll("\\s","");
                    name = name.substring(0,1).toLowerCase() + name.substring(1);
                    obj.addProperty(name, field.getValue());
                }
                JsonArray customFields = new JsonArray();
                for (Map.Entry<String, String> field : customProfileFields.entrySet()) {
                    JsonObject customField = new JsonObject();
                    customField.addProperty("name", field.getKey());
                    customField.addProperty("value", field.getValue());
                    customFields.add(customField);
                }
                obj.add("customFields", customFields);
                JsonArray jsonProducts = new JsonArray();
                for (Product product : products) {
                    JsonObject jsonProduct = new JsonObject();
                    jsonProduct.addProperty("productId", product.name);
                    jsonProduct.addProperty("quantity", product.quantity);
                    jsonProduct.addProperty("note", product.note);
                    jsonProducts.add(jsonProduct);
                }
                obj.add("products", jsonProducts);
                if (comments != null && !comments.trim().isEmpty()) {
                    obj.addProperty("comments", comments);
                }
                obj.addProperty("emailToAddress", emailAddress);

                Map<String, String> params = new HashMap<>();
                params.put("json", obj.toString());
                HttpRequest.get("outofband/sendOrder", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(result);
                    }
                });
            }
        });
    }
}
