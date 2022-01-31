package com.moblico.sdk.services;

import android.content.Context;
import android.util.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanToListService {

    public static class Order {
        public static class Product {
            public final String name;
            public final int quantity;
            public final String note;
            public final String photoPath;
            public final String productId;
            public final Integer maxQuantity;
            public final List<String> photoPaths;

            public Product(String name, int quantity, String note, String photoPath, String productId, Integer maxQuantity, List<String> photoPaths) {
                this.name = name;
                this.quantity = quantity;
                this.note = note;
                this.photoPath = photoPath;
                this.productId = productId;
                this.maxQuantity = maxQuantity;
                this.photoPaths = photoPaths;
            }

            @Override
            public int hashCode() {
                if (name != null) {
                    return name.hashCode();
                }
                if (productId != null) {
                    return productId.hashCode();
                }
                return 0;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Product product = (Product) o;

                return name.equals(product.name);
            }
        }

        public final String emailAddress;
        public final Map<String, String> profileFields;
        public final Map<String, String> customProfileFields;
        public final List<Product> products;
        public final String comments;
        public final boolean hasName;
        public final Map<String, String> extraFields;

        public Order(String emailAddress, Map<String, String> profileFields, Map<String, String> customProfileFields, List<Product> products, String comments, boolean hasName, Map<String, String> extraFields) {
            this.emailAddress = emailAddress;
            this.profileFields = profileFields;
            this.customProfileFields = customProfileFields;
            this.products = products;
            this.comments = comments;
            this.hasName = hasName;
            this.extraFields = extraFields;
        }
    }

    private ScanToListService() {
    }

    public static void SendOrder(final Order order,
                                 final Context context,
                                 final Callback<String> callback) {

        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                JsonObject obj = new JsonObject();
                for (Map.Entry<String, String> field : order.profileFields.entrySet()) {
                    // primary field names need tweaking:
                    String name = field.getKey();
                    name = name.replaceAll("\\s","");
                    name = name.substring(0,1).toLowerCase() + name.substring(1);
                    obj.addProperty(name, field.getValue());
                }
                JsonArray customFields = new JsonArray();
                for (Map.Entry<String, String> field : order.customProfileFields.entrySet()) {
                    if ("Order Type".contentEquals(field.getKey())) {
                        // We don't show order type in custom fields!  Show it in top level instead.
                        if ("Daily".contentEquals(field.getValue())) {
                            obj.addProperty("orderType", "Daily");
                        }
                        continue;
                    }
                    JsonObject customField = new JsonObject();
                    customField.addProperty("name", field.getKey());
                    customField.addProperty("value", field.getValue());
                    customFields.add(customField);
                }
                obj.add("customFields", customFields);
                JsonArray jsonProducts = new JsonArray();
                for (Order.Product product : order.products) {
                    JsonObject jsonProduct = new JsonObject();
                    if (product.name != null) {
                        jsonProduct.addProperty("name", product.name);
                    }
                    if (product.productId != null) {
                        jsonProduct.addProperty("productId", product.productId);
                    }
                    jsonProduct.addProperty("quantity", product.quantity);
                    jsonProduct.addProperty("note", product.note);
                    String imageData = imageToBase64(product.photoPath, context);
                    if (imageData != null) {
                        jsonProduct.addProperty("imageData", imageData);
                    }
                    if (product.photoPaths != null) {
                        JsonArray jsonImages = new JsonArray();
                        for(String path : product.photoPaths) {
                            imageData = imageToBase64(path, context);
                            if (imageData != null) {
                                JsonObject jsonImageData = new JsonObject();
                                jsonImageData.addProperty("imageData", imageData);
                                jsonImages.add(jsonImageData);
                            }
                        }
                        jsonProduct.add("images", jsonImages);
                    }
                    if (product.maxQuantity != null) {
                        jsonProduct.remove("quantity");
                        jsonProduct.addProperty("baseQuantity", product.maxQuantity);
                        jsonProduct.addProperty("inventoryQuantity", product.quantity);
                        jsonProduct.addProperty("quantity", product.maxQuantity - product.quantity);
                    }
                    jsonProducts.add(jsonProduct);
                }
                obj.add("products", jsonProducts);
                if (order.comments != null && !order.comments.trim().isEmpty()) {
                    obj.addProperty("comments", order.comments);
                }
                obj.addProperty("emailToAddress", order.emailAddress);
                if (order.extraFields != null) {
                    for (Map.Entry<String, String> field : order.extraFields.entrySet()) {
                        obj.addProperty(field.getKey(), field.getValue());
                    }
                }
                if (order.hasName) {
                    obj.addProperty("name", true);
                }

                Map<String, String> params = new HashMap<>();
                params.put("json", obj.toString());
                HttpRequest.post("outofband/sendOrder", null, obj.toString(), new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(result);
                    }
                });
            }
        });
    }

    private static String imageToBase64(String imagePath, Context context) {
        if (imagePath == null) {
            return null;
        }
        try {
            FileInputStream photoStream = context.openFileInput(imagePath);
            byte[] buffer = new byte[photoStream.available()];
            photoStream.read(buffer);
            return Base64.encodeToString(buffer, Base64.NO_WRAP);
        } catch (Exception e) {
            // Just don't attach the image
            return null;
        }
    }
}
