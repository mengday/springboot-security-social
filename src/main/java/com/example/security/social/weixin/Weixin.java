package com.example.security.social.weixin;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-06-06
 */
public interface Weixin {
    WeixinUserInfo getUserInfo(String openId);
}
