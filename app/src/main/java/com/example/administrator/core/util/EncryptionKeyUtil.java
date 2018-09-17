package com.example.administrator.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 林颖 on 2018/8/7.
 *
 */

public class EncryptionKeyUtil {

    /**
     * MD5加密
     * @param key
     * @return
     */
    public static String hashKeyForDisk(String key){
        String cacheKey;
        try {
            MessageDigest mDigest=MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey=bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey=String.valueOf(key.hashCode());

        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] digest) {
        StringBuilder mBuilder=new StringBuilder();
        for(int i=0;i<digest.length;i++){
            String hex= Integer.toHexString(0XFF&digest[i]);
            if(hex.length()==1){
                mBuilder.append("0");
            }
            mBuilder.append(hex);
        }
        return  mBuilder.toString();
    }
}
