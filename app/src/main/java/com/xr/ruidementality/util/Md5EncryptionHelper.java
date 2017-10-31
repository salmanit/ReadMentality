package com.xr.ruidementality.util;



import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Description: MD5加密工具类
 * Modification  History:
 * -----------------------------------------------------------------------------------
 * Why & What is modified:
 */
public class Md5EncryptionHelper {
    public static final String KEY = "srgroup.cn@@admin_it";
    public static final String TAG = Md5EncryptionHelper.class.getSimpleName();
    public static final String VIDRO_KEY = "DERUIMU-*&^%$XINRONGJI6868686868TUANDERUIMU<>?!@SAA";
    /**
     * 获取验证码access串
     *
     * @param uuid  设备ID
     * @param phone 手机号
     * @return access
     */
    public static String getAuthCodeAccess(String uuid, String phone) {
        String content = uuid + KEY;
        return getMD5(getMD5(content) + phone);
    }

    /**
     * 获取密码字串的MD5
     *
     * @param content 需要转换的字符串
     * @return String
     */
    public static String getPwdMD5(String content) {
        return getMD5(content);
    }

    /**
     * 获取验证码MD5
     *
     * @param content 需要转换的字符串
     * @return String
     */
    public static String getCodeMD5(String content, String time) {
        String data = "srgroup" + content + time ;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(data.getBytes());
            return getHashString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String md5(String content, String time) {
        String data = "srgroup" + content + time ;
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(data.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return "0x"+hex.toString();
    }
    /**
     * 获取MD5字符串
     *
     * @param content 需要转换的字符串
     * @return String
     */
    public static String getMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }
//    字符串转为base64字符串
    public static String changebase64(String str) {
//        String str = "Hello World";
        str = "deruimu"+str;
        try{
            String base64Token = Base64.encodeToString(str.getBytes("UTF-8"), Base64.DEFAULT);//  编码后
//            byte[] encodeBase64 = Base64.encodeBase64(str.getBytes("UTF-8"));
//            System.out.println("RESULT: " + new String(encodeBase64));
            return base64Token;
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 获取视频的SIgn
     *
     * @param content 需要转换的字符串
     * @return String
     */
    public static String getVideosign(String course_id,String courseware_id,String request_time) {
        try {
            String content = VIDRO_KEY+course_id+courseware_id+request_time;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取密码字串的MD5
     *
     * @param content 需要转换的字符串
     * @return String
     */
    public static String getToken(String content) {
        return getMD5(content);
    }
}
