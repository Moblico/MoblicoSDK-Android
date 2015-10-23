package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.ListItem;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListItemsService {
    private ListItemsService() {
    }

    //GET lists<id>/listItems - returns list of items for a given list
    //curl -X GET 'https://moblicosandbox.com/services/v4/lists/9/listItems?token=CXJtaGhuZWlraW9oZG1scmRqcm1qaXJob25lanIVDBQTEhk%3d'
    public static void getListItems(final long listId, final Callback<List<ListItem>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("lists/" + listId + "/listItems", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<ListItem>>() {
                        }.getType();
                        List<ListItem> listItems = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(listItems);
                    }
                });
            }
        });
    }

    //POST lists/<id>/listItems?name=corn - add item to shopping list
    //curl -X POST 'https://moblicosandbox.com/services/v4/lists/12/listItems?token=CXJtaGhuZWlraW9oZG1scmRqcm1qaXJob25lanIVDBQTEhk%3d&name=corn'
    public static void addItemToList(final ListItem item, final long listId, final String name, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.putAll(getItemParams(item));
                HttpRequest.post("lists/" + listId + "/listItems", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    //* PUT listItems/<listItemId>?name=corn - update a line items already in a list
    // curl -X PUT 'https://moblicosandbox.com/services/v4/listItems/11?token=CXJtaGhuZWlraW9oZG1scmRqcm1qaXJob25lanIVDBQTEhk%3d&name=corny'
    public static void updateItemInList(final ListItem item, final long listItemId, final String name, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.putAll(getItemParams(item));
                HttpRequest.put("listItems/" + listItemId, params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    //* DELETE listItems/<listItemId> - delete line item
    //curl -X DELETE 'https://moblicosandbox.com/services/v4/listItems/11?token=CXJtaGhuZWlraW9oZG1scmRqcm1qaXJob25lanIVDBQTEhk%3d'
    public static void removeItemFromList(final long listItemId, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.delete("listItems/" + listItemId, null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    private static Map<String, String> getItemParams(ListItem item) {
        Map<String, String> params = new HashMap<String, String>();
        for (Field field : ListItem.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.getType().equals(boolean.class)) {
                    params.put(field.getName(), field.getBoolean(item) ? "YES" : "NO");
                } else if (field.getName().equals("id")) {
                    // Skip id!
                } else if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    // Skip static fields!
                } else {
                    Object value = field.get(item);
                    if (value != null) {
                        params.put(field.getName(), value.toString());
                    }
                }
            } catch (IllegalAccessException e) {
            }
        }
        return params;
    }

}
