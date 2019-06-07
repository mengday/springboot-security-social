package com.example.security.social;

import com.example.security.social.MyConnecitonSignUp;
import com.example.security.social.MySpringSocialConfigurer;
import com.example.security.social.qq.QQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import javax.sql.DataSource;

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
@EnableSocial
public class SocialConfiguration extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private QQProperties properties;

    @Autowired
    private MyConnecitonSignUp myConnecitonSignUp;

    /**
     * 建表 spring-social-core-1.1.4.RELEASE.jar/org/springframework/social/connect/jdbc/JdbcUsersConnectionRepository.sql
     *
     * create table UserConnection (userId varchar(255) not null,
     * 	providerId varchar(255) not null,
     * 	providerUserId varchar(255),
     * 	rank int not null,
     * 	displayName varchar(255),
     * 	profileUrl varchar(512),
     * 	imageUrl varchar(512),
     * 	accessToken varchar(512) not null,
     * 	secret varchar(512),
     * 	refreshToken varchar(512),
     * 	expireTime bigint,
     * 	primary key (userId, providerId, providerUserId));
     * create unique index UserConnectionRank on UserConnection(userId, providerId, rank);
     * @param connectionFactoryLocator
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // Encryptors.noOpText() 对数据不加密
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        // 只能设置表前缀，但是不能修改表名
        jdbcUsersConnectionRepository.setTablePrefix("");
        if (myConnecitonSignUp != null) {
            jdbcUsersConnectionRepository.setConnectionSignUp(myConnecitonSignUp);
        }
        return jdbcUsersConnectionRepository;
    }

    @Bean
    public MySpringSocialConfigurer mySpringSocialConfigurer() {
        MySpringSocialConfigurer springSocialConfigurer = new MySpringSocialConfigurer(properties.getFilterProcessesUrl());
        springSocialConfigurer.userIdSource(getUserIdSource());
        springSocialConfigurer.signupUrl(properties.getSignupUrl());
        return springSocialConfigurer;
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }
}
