package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Deal;
import com.moblico.sdk.entities.Reward;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;

public final class RewardsService {

    private RewardsService() {
    }

    public static final Comparator<Reward> rewardComparator = new Comparator<Reward>() {
        @Override
        public int compare(Reward lhs, Reward rhs) {
            if (lhs == null) {
                return rhs == null ? 0 : 1;
            } else if (rhs == null) {
                return -1;
            }
            if (lhs.isRedeemable() != rhs.isRedeemable()) {
                return lhs.isRedeemable() ? -1 : 1;
            }
            return lhs.getEndDate().compareTo(rhs.getEndDate());
        }
    };

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
}
