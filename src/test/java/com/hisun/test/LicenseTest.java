package com.hisun.test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;

/**
 * <p>类名称:LicenseTest</p>
 * <p>类描述: 试用期限生成</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:16/7/14下午2:01
 * @创建人联系方式:init@hn-hisun.com
 */
public class LicenseTest {

    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static void main(String[] arg) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String plublicKeyStr = new BASE64Encoder().encode(publicKey.getEncoded());
        String privateKeyStr = new BASE64Encoder().encode(privateKey.getEncoded());
        System.out.println("公钥:"+plublicKeyStr);
        System.out.println("私钥:"+privateKeyStr);
        String jiamiString = encrypt(privateKey, "2015-01-01".getBytes("UTF-8"));
        System.out.println("加密后限期:"+jiamiString);
        String jiemiString = decrypt(publicKey, new BASE64Decoder().decodeBuffer(jiamiString));
        System.out.println("解密限期:"+jiemiString);
    }

    public static String encrypt(Key k, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, k);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new BASE64Encoder().encode(decryptedData);
    }

    public static String decrypt(Key k, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, k);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }
}
