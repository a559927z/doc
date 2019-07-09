package net.chinahrd.common.utils;

import org.apache.shiro.crypto.hash.Sha512Hash;

public class EncodeUitl {

    /**
     * 获取加密后面的密码
     *
     * @param password
     * @param username
     * @return
     */
    public static String getEncodePassword(String password, String username) {
        Sha512Hash encodedpassword = new Sha512Hash(password, username, 1024);
        String result = encodedpassword.toBase64();
        return result;
    }
}
