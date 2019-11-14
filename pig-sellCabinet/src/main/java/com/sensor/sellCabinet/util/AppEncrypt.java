package com.sensor.sellCabinet.util;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.net.URLEncoder;

public class AppEncrypt {

    //static String data = "123456RWEQR";
    static String key = "thanks,pig4cloud";  //16位
    static String iv = "thanks,pig4cloud";  //16位

    public static String getEncrytAES(String content) throws Exception {
        String encryptAES = encryptAES(content);
//        String encode = URLEncoder.encode(encryptAES, "UTF-8");
        return encryptAES;
    }

    public static String encryptAES(String data) throws Exception {

        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");   //参数分别代表 算法名称/加密模式/数据填充方式
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            System.out.println(binary(encrypted,16));
            return new sun.misc.BASE64Encoder().encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptAES(String data) throws Exception {
        try {
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String binary(byte[] bytes, int radix){

        return new BigInteger(1, bytes).toString(radix);    // 这里的1代表正数

    }
}
