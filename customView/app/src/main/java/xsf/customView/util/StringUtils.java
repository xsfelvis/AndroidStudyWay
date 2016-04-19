package xsf.customView.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xushangfei
 * @time created at 2016/4/19.
 * @email xsf_uestc_ncl@163.com
 */
public class StringUtils {
    /**
     * 将字符串进行md5转换
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(str.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(str.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s.trim());
    }
}
