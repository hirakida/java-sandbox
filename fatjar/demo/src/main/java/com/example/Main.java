package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hirakida.org.apache.commons.codec.digest.DigestUtils;
import com.github.hirakida.org.apache.commons.lang3.StringUtils;
import com.github.hirakida.util.DigestUtil;
import com.github.hirakida.util.StringUtil;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        log.info("{}", DigestUtil.md5("hello"));
        log.info("{}", DigestUtils.md5("hello"));

        log.info("{}", StringUtil.isEmpty(""));
        log.info("{}", StringUtils.isEmpty(""));
    }
}
