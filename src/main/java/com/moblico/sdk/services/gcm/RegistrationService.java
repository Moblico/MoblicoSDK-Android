package com.moblico.sdk.services.gcm;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.moblico.sdk.R;
import com.moblico.sdk.services.GcmService;
import com.moblico.sdk.services.UsersService;

import java.io.IOException;

public class RegistrationService extends IntentService {
    public static final String EXTRA_UNREGISTER = "EXTRA_UNREGISTER";

    private static final String TAG = RegistrationService.class.getName();

    public RegistrationService() {
        super(TAG);
    }

    /**
     * Update Moblico with the latest GCM token.  This is done on a Service based on the GCM
     * recommendations.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getBooleanExtra(EXTRA_UNREGISTER, false)) {
            try {
                InstanceID.getInstance(this).deleteInstanceID();
            } catch (IOException e) {
            }
        } else {
            try {
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_sender_id),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                GcmService.registerDevice(token, null);
            } catch (IOException e) {
            }
        }
    }
}
