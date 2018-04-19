package com.hisun.saas.sys.license.controller;

import com.hisun.saas.sys.license.filter.LicenseFilter;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.service.TenantService;
import com.hisun.base.controller.BaseController;
import com.hisun.base.exception.GenericException;
import com.hisun.util.ApplicationContextUtil;
import com.hisun.util.WrapWebUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.*;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>类名称:LicenseController</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:16/7/26上午8:56
 * @创建人联系方式:init@hn-hisun.com
 */
@Controller
@RequestMapping("/license")
public class LicenseController extends BaseController {

    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    @RequestMapping("/activate")
    public @ResponseBody Map<String,Object> activate(String key, String encrypt){
        Map<String,Object> map = new HashMap<String,Object>();
        boolean success = false;
        try {
            Properties properties = getLicenseProperties();
            BASE64Decoder base64Decoder = new BASE64Decoder();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec=new X509EncodedKeySpec(base64Decoder.decodeBuffer(properties.getProperty("license.publickey")));
            PublicKey publicKey =keyFactory.generatePublic(keySpec);
            String encryptStr = decrypt(publicKey, base64Decoder.decodeBuffer(encrypt));//租户名yyyy-MM-dd格式
            String dateStr = encryptStr.substring(encryptStr.length()-10);
            String tenantName = encryptStr.substring(0, encryptStr.length()-10);
            DateTime limitDate = new DateTime(dateStr);
            TenantService tenantService = ApplicationContextUtil.getBean(TenantService.class);
            Tenant tenant = tenantService.getByName(tenantName);
            if(tenant != null){
                if(limitDate.isAfter(new DateTime())){
                    success = true;
                    updateProperties(encrypt);
                    LicenseFilter.setPublickey(publicKey);
                    LicenseFilter.setLicenseEncrypt(encrypt);
                }
            }
            if(success){
                map.put("code",1);
            }else {
                map.put("code", -1);
                map.put("message", "激活码无效");
            }
        } catch (IOException e){
            logger.error("license写入文件报错", e);
            map.put("code", -1);
            map.put("message", "激活码写入文件错误");
        }catch (Exception e) {
            logger.error("license报错",e);
            map.put("code", -1);
            map.put("message", "激活码写入文件错误");
        }
        return map;
    }

    private void updateProperties(String encrypt) throws IOException, GenericException {
        Properties licenseProperteis = getLicenseProperties();
        // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
        // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        String path = WrapWebUtils.getRequest().getServletContext().getRealPath("/")+ "/WEB-INF/classes/conf/properties/license.properties";
        OutputStream fos = new FileOutputStream(path);
        licenseProperteis.setProperty("license.encrypt", encrypt);
        // 以适合使用 load 方法加载到 Properties 表中的格式，
        // 将此 Properties 表中的属性列表（键和元素对）写入输出流
        licenseProperteis.store(fos, "Update license.encrypt value");
    }

    private Properties getLicenseProperties() throws GenericException {
        Properties properties = new Properties();
        InputStream in = WrapWebUtils.getRequest().getServletContext().getResourceAsStream("/WEB-INF/classes/conf/properties/license.properties");
        try {
            properties.load(in);
        } catch (Exception e) {
            logger.error("试用期限过滤器初始化报错",e);
            throw new GenericException("读取license资源文件失败");
        }
        return  properties;
    }

    private String decrypt(Key k, byte[] data) throws Exception {
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
