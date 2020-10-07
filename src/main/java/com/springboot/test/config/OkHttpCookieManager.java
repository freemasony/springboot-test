package com.springboot.test.config;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 始皇
 */
public class OkHttpCookieManager implements CookieJar {

    Map<String, List<Cookie>> cacheCookie = new HashMap<>();

    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl url) {
        return putCookie(url.host(), null);
    }

    @Override
    public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
        putCookie(url.host(), cookies);
    }

    private List<Cookie> putCookie(String domainSp2, List<Cookie> cookieAppend) {
        List<Cookie> cookies = cacheCookie.get(domainSp2);
        if (CollectionUtils.isEmpty(cookies)) {
            cookies = new ArrayList<>();
        }
        if (cookieAppend != null) {
            cookies.addAll(cookieAppend);
        }

        cacheCookie.put(domainSp2, cookies);
        return cookies;
    }
}
