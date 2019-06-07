package com.example.security.social.qq;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;


/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-05-19
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        String url = String.format(URL_GET_OPENID, accessToken);
        // callback( {"client_id":"xxx","openid":"xxxx"} );
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
        String[] items = result.split("openid");
        String openid = items[1].substring(3, items[1].length() - 6);
        this.openId = openid;
    }
    /**
     * QQ 互联
     * https://connect.qq.com/ 文档资料 Api文档/Api列表/访问用户资料(get_user_info)
     * @return
     */
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
        QQUserInfo userInfo = JSONObject.parseObject(result, QQUserInfo.class);
        userInfo.setOpenId(openId);

        return userInfo;
    }
}
