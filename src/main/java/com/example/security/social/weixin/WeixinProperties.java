package com.example.security.social.weixin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-05-19
 */
@Data
@ConfigurationProperties(prefix = "social.weixin")
public class WeixinProperties {
    private String providerId = "weixin";
    private String appId;
    private String appSecret;
    private String signupUrl;
}
