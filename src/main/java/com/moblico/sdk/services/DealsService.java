package com.moblico.sdk.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Deal;
import com.moblico.sdk.entities.Location;
import com.moblico.sdk.entities.Status;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DealsService {

    private DealsService() {
    }

    public static void getDeals(final Callback<List<Deal>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("deals", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Deal>>() {}.getType();
                        List<Deal> deals = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(deals);
                    }
                });
            }
        });
    }

    public static void getDeal(final long dealId, final Callback<Deal> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("deals/" + dealId, null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Deal deal = Moblico.getGson().fromJson(result, Deal.class);
                        callback.onSuccess(deal);
                    }
                });
            }
        });
    }

    public static void getDealsAtLocation(final Location location, final Callback<List<Deal>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("locations/" + location.getId() + "/deals", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Deal>>() {}.getType();
                        List<Deal> deals = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(deals);
                    }
                });
            }
        });
    }

    public static void redeemDeal(final Deal deal, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("offerCode", deal.getOfferCode());
                HttpRequest.put("deals/" + deal.getId() + "/redeem", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        if (callback != null) {
                            callback.onSuccess(null);
                        }
                    }
                });
            }
        });
    }

    public static void getDealMessage(final Deal deal, final Callback<String> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("type", "SHARE");
                HttpRequest.get("deals/" + deal.getId() + "/message", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        JsonObject jobj = Moblico.getGson().fromJson(result, JsonObject.class);
                        if (!jobj.has("text")) {
                            callback.onFailure(new StatusCodeException(new Status(Status.StatusType.UNSUPPORTED_MESSAGE_TYPE)));
                            return;
                        }
                        final String text = jobj.get("text").getAsString();
                        if (text.trim().isEmpty()) {
                            callback.onFailure(new StatusCodeException(new Status(Status.StatusType.UNSUPPORTED_MESSAGE_TYPE)));
                            return;
                        }
                        callback.onSuccess(text);
                    }
                });
            }
        });
    }
}
