package com.example.mayn.myfisrtapp.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author lwc
 * 2019/3/19
 * 描述：$des$
 */
public class EncrypteTools {


    @SuppressLint("NewApi")
    public static String encryptedString() {
        String timestamp = "" + System.currentTimeMillis();
        String secret = "secret";
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA1");
        Mac mac = null;
        String encrypted = null;

        try {
            mac = Mac.getInstance("HmacSHA1");
            if (mac != null)
                mac.init(secretKeySpec);

            encrypted = Base64.getEncoder().encodeToString(mac.doFinal(timestamp.getBytes("UTF-8")));
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("EncrypteTools","Exception =>"+e.getMessage());
        }
        return encrypted;

    }

}
