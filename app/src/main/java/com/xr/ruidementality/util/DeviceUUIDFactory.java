package com.xr.ruidementality.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class DeviceUUIDFactory {

    public static String getUUID(Context context) {
        UUID uuid=UUID.randomUUID();
        synchronized (DeviceUUIDFactory.class) {
            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.i("android===uuid======", androidId);
            try {
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    final String deviceId = ((TelephonyManager) context
                            .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID
                            .randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        return uuid.toString();
    }

}
