/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth.service;

import com.hisun.saas.sys.auth.vo.PasswordSecurity;
import com.hisun.saas.sys.entity.AbstractUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String algorithmName = "md5";
    private int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    /**
     * 加密
     * @param user
     */
    public void encryptPassword(AbstractUser user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }
    
    /**
     * 校验输入的原密码是否正确
     * @param user
     * @param confirmPassword
     * @return
     */
    public boolean credentialsPassword(AbstractUser user,String confirmPassword){
		String newCredentials = new SimpleHash(algorithmName, confirmPassword, ByteSource.Util.bytes(user.getSalt()), hashIterations).toHex();
    	return Arrays.equals(user.getPassword().getBytes(),newCredentials.getBytes());
    }

    public PasswordSecurity encryptPassword(String originalPassword){
        String salt = randomNumberGenerator.nextBytes().toHex();

        String newPassword = new SimpleHash(
                algorithmName,
                originalPassword,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();

        PasswordSecurity security = new PasswordSecurity();
        security.setPassword(newPassword);
        security.setSalt(salt);
        return security;
    }
}