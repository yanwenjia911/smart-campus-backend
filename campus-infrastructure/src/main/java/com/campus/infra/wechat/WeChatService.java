package com.campus.infra.wechat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeChatService {

    @Value("${wechat.appid:demo-appid}")
    private String appid;

    @Value("${wechat.secret:demo-secret}")
    private String secret;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 通过 code 换取 openid
     * 开发环境：mock
     * 生产环境：调用微信接口
     */
    public String getOpenid(String code) {

        if (code == null || code.isEmpty()) {
            return null;
        }

        // ===== 开发阶段（本地调试推荐）=====
        if ("dev".equals(System.getProperty("spring.profiles.active"))) {
            return "mock_openid_" + code;
        }

        // ===== 正式调用微信接口 =====
        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session" +
                        "?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appid, secret, code);

        try {
            ResponseEntity<Map<String, Object>> responseEntity =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<Map<String, Object>>() {}
                    );

            Map<String, Object> response = responseEntity.getBody();

            if (response != null && response.get("openid") != null) {
                return (String) response.get("openid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}