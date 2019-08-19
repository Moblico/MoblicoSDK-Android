package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.ItemList;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListsService {

    private ListsService() {
    }

    //GET lists - returns list of lists
    //curl -X GET 'https://moblicosandbox.com/services/v4/lists?token=CXJtaGhuZWlraW9oZG1scmRqcm1qaXJob25lanIVDBQTEhk%3d'
    public static void getLists(final Callback<List<ItemList>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("lists", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<ItemList>>() {
                        }.getType();
                        List<ItemList> itemLists = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(itemLists);
                    }
                });
            }
        });
    }

    //POST lists?name=jims list - add new shopping list
    //curl -X POST 'https://moblicosandbox.com/services/v4/lists?token=CXJtaGhuZWlraW9oZG1scmRqcm1qaXJob25lanIVDBQTEhk%3d&name=jimslist
    public static void addList(final String name, final Callback<ItemList> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name.trim());
                HttpRequest.post("lists", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        ItemList list = Moblico.getGson().fromJson(result,ItemList.class);
                        callback.onSuccess(list);
                    }
                });
            }
        });
    }


}
