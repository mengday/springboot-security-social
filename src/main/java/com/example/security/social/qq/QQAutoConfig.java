package com.example.security.social.qq;

import com.example.security.social.binding.MyConnectedView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.servlet.View;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-05-19
 */
@Configuration
@ConditionalOnProperty(prefix = "social.qq", name = "appId")
@EnableConfigurationProperties(QQProperties.class)
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Autowired
    private QQProperties properties;

    protected ConnectionFactory<?> createConnectionFactory() {
        return new QQConnectionFactory(properties.getProviderId(), properties.getAppId(), properties.getAppSecret());
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(this.createConnectionFactory());
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }

    /**
     * 关于bean的名称spring-social-web不同的版本可能不一样，具体是什么可以通过下面的类打端点看一下
     * org.springframework.social.connect.web.ConnectController#connectionStatusRedirect(java.lang.String, org.springframework.web.context.request.NativeWebRequest)
     * @return
     */
    @Bean({"connect/callback.do.doConnect", "connect/callback.doConnected"})
    public View qqConnectedView() {
        return new MyConnectedView();
    }

}
