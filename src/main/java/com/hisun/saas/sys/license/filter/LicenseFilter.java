package com.hisun.saas.sys.license.filter;

import com.hisun.saas.sys.tenant.tenant.service.TenantService;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.util.ApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

/**
 * <p>类名称:LicenseFilter</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:16/7/14上午11:41
 * @创建人联系方式:init@hn-hisun.com
 */
public class LicenseFilter extends OncePerRequestFilter {

    Logger logger = Logger.getLogger(LicenseFilter.class);

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    protected static String LICENSE_ENCRYPT = null;//日期加密后字符串

    protected static String publicKeyStr = null;//公钥Base64 encode之后

    protected static PublicKey PUBLICKEY = null;

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        Properties properties = new Properties();
        InputStream in = getServletContext().getResourceAsStream("/WEB-INF/classes/conf/properties/license.properties");
        try {
            properties.load(in);
            LICENSE_ENCRYPT = properties.getProperty("license.encrypt").trim();
            publicKeyStr = properties.getProperty("license.publickey").trim();
            BASE64Decoder base64Decoder = new BASE64Decoder();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec=new X509EncodedKeySpec(base64Decoder.decodeBuffer(publicKeyStr));
            PUBLICKEY=keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            logger.error("试用期限过滤器初始化报错",e);
            throw new ServletException("试用期限过滤器初始化报错"+e.getMessage());
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean canNextFilter = true;//是否继续过滤器
        String requestType = request.getHeader("X-Requested-With");
        boolean isAjax = StringUtils.isNotBlank(requestType)&&"XMLHttpRequest".equals(requestType);
        String requestUrl = request.getRequestURI();
        if(!requestUrl.startsWith("/api") && !requestUrl.startsWith("/WEB-INF") && !isAjax && !requestUrl.endsWith(".png")
                && !requestUrl.endsWith(".js") && !requestUrl.startsWith("/license")){
            //不是调用API开头的才进来，因为拦截API，可能导致自动化回调出现问题
            try {
                BASE64Decoder base64Decoder = new BASE64Decoder();
                String encryptStr = decrypt(PUBLICKEY, base64Decoder.decodeBuffer(LICENSE_ENCRYPT));//租户名yyyy-MM-dd格式
                String dateStr = encryptStr.substring(encryptStr.length()-10);
                String tenantName = encryptStr.substring(0, encryptStr.length()-10);
                DateTime limitDate = new DateTime(dateStr);
                TenantService tenantService = ApplicationContextUtil.getBean(TenantService.class);
                PagerVo<Tenant> tenantPager = tenantService.listPager(null, TombstoneEntity.TOMBSTONE_FALSE, null,null,1 ,1);
                if(tenantPager.getTotal()>0){
                    Tenant tenant = tenantPager.getDatas().get(0);
                    if(tenantName.equals(tenant.getName())){
                        if(limitDate.isBeforeNow()){
                            //少于现在时间，试用到期
                            canNextFilter = false;
                        }else {
                            canNextFilter = true;
                        }
                    }else{
                        canNextFilter = false;
                    }
                }else{
                    canNextFilter = false;
                }
            } catch (Exception e) {
                logger.error("license报错",e);
                //报错通常情况下，公钥等信息被修改了，或者设置为空了。这时候要也当作不可用。
                canNextFilter =false;
            }
        }
        if(canNextFilter){
            filterChain.doFilter(request, response);
        }else{
            //重定向
            request.getRequestDispatcher("/WEB-INF/jsp/error/licenseOver.jsp").forward(request, response);
        }
    }

    public String decrypt(Key k, byte[] data) throws Exception {
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

    public static void setPublickey(PublicKey publickey) {
        PUBLICKEY = publickey;
    }

    public static void setLicenseEncrypt(String licenseEncrypt) {
        LICENSE_ENCRYPT = licenseEncrypt;
    }
}
