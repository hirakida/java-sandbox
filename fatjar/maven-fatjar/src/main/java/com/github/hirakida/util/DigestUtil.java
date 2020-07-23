package com.github.hirakida.util;

import org.apache.commons.codec.digest.DigestUtils;

public final class DigestUtil {

    public static byte[] md5(String data) {
        return DigestUtils.md5(data);
    }
}
