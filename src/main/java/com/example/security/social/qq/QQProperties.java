package com.example.security.social.qq;

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
@ConfigurationProperties(prefix = "social.qq")
public class QQProperties {
    private String filterProcessesUrl = "/auth";
    private String providerId = "qq";
    private String appId;
    private String appSecret;
    private String signupUrl;
}
