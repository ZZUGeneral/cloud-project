package top.yhl.cloud.common.util;


import org.springframework.lang.Nullable;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * DES加解密处理工具
 *
 * @author zhang_ht2
 * @date 2020/09/14
 */
public class DesTool {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 数字签名，密钥算法
     */
    public static final String DES_ALGORITHM = "DES";





    /**
     * DES加密
     *
     * @param data     byte array
     * @param password 密钥
     * @return des hex
     */
    public static String encryptToBase64(byte[] data, String password) {
        return Base64Utils.encodeToString(encrypt(data, password));
    }

    /**
     * DES加密
     *
     * @param data     字符串内容
     * @param password 密钥
     * @return des hex
     */
    @Nullable
    public static String encryptToBase64(@Nullable String data, String password) {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        byte[] dataBytes = data.getBytes(DEFAULT_CHARSET);
        return encryptToBase64(dataBytes, password);
    }

    /**
     * DES解密
     *
     * @param data     字符串内容
     * @param password 密钥
     * @return des context
     */
    public static byte[] decryptFormBase64(byte[] data, String password) {
        byte[] dataBytes = Base64Utils.decode(data);
        return decrypt(dataBytes, password);
    }

    /**
     * DES解密
     *
     * @param data     字符串内容
     * @param password 密钥
     * @return des context
     */
    @Nullable
    public static String decryptFormBase64(@Nullable String data, String password) {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        byte[] dataBytes = Base64Utils.decodeFromString(data);
        return new String(decrypt(dataBytes, password), DEFAULT_CHARSET);
    }

    /**
     * DES加密
     *
     * @param data   内容
     * @param desKey 密钥
     * @return byte array
     */
    public static byte[] encrypt(byte[] data, byte[] desKey) {
        return des(data, desKey, Cipher.ENCRYPT_MODE);
    }

    /**
     * DES加密
     *
     * @param data   内容
     * @param desKey 密钥
     * @return byte array
     */
    public static byte[] encrypt(byte[] data, String desKey) {
        return encrypt(data, Objects.requireNonNull(desKey).getBytes(DEFAULT_CHARSET));
    }

    /**
     * DES解密
     *
     * @param data   内容
     * @param desKey 密钥
     * @return byte array
     */
    public static byte[] decrypt(byte[] data, byte[] desKey) {
        return des(data, desKey, Cipher.DECRYPT_MODE);
    }

    /**
     * DES解密
     *
     * @param data   内容
     * @param desKey 密钥
     * @return byte array
     */
    public static byte[] decrypt(byte[] data, String desKey) {
        return decrypt(data, Objects.requireNonNull(desKey).getBytes(DEFAULT_CHARSET));
    }

    /**
     * DES加密/解密公共方法
     *
     * @param data   byte数组
     * @param desKey 密钥
     * @param mode   加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     * @return des
     */
    private static byte[] des(byte[] data, byte[] desKey, int mode) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            DESKeySpec desKeySpec = new DESKeySpec(desKey);
            cipher.init(mode, keyFactory.generateSecret(desKeySpec),Holder.SECURE_RANDOM);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
