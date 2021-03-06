package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Deal;
import com.moblico.sdk.entities.Location;
import com.moblico.sdk.entities.Reward;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RewardsService {

    private RewardsService() {
    }

    public static final Comparator<Reward> rewardComparator = new Comparator<Reward>() {
        @Override
        public int compare(Reward lhs, Reward rhs) {
            if (lhs == null) {
                return rhs == null ? 0 : Integer.MAX_VALUE;
            }
            if (rhs == null) {
                return Integer.MIN_VALUE;
            }

            return lhs.compareTo(rhs);
        }
    };

    public static void getReward(final long id, final Callback<Reward> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("rewards/" + id, null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Reward reward = Moblico.getGson().fromJson(result, Reward.class);
                        callback.onSuccess(reward);
                    }
                });
            }
        });
    }

    public static void getRewards(final Callback<List<Reward>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("rewards", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Reward>>() {}.getType();
                        List<Reward> rewards = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(rewards);
                    }
                });
            }
        });
    }

    public static void getRewardsAtLocation(final Location location, final Callback<List<Reward>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("locations/" + location.getId() + "/rewards", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Reward>>() {}.getType();
                        List<Reward> rewards = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(rewards);
                    }
                });
            }
        });
    }

    public static void redeemReward(final Reward reward, final boolean autoPurchase, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("offerCode", reward.getOfferCode());
                if (autoPurchase) {
                    params.put("autoPurchase", "true");
                }
                HttpRequest.put("rewards/" + reward.getId() + "/redeem", params, new ErrorForwardingCallback<String>(callback) {
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

    public static void purchaseReward(final Reward reward, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.put("rewards/" + reward.getId() + "/purchase", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }
}
