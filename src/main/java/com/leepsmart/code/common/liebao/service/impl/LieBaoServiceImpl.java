package com.leepsmart.code.common.liebao.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.leepsmart.code.common.ex.ServiceException;
import com.leepsmart.code.common.liebao.LieBaoApi;
import com.leepsmart.code.common.liebao.service.LieBaoService;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.LogUtil;
import com.leepsmart.code.system.pojo.SysLieBaoAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class LieBaoServiceImpl implements LieBaoService {

    @Value("${lieBao.appId}")
    private String appId;
    @Value("${lieBao.secret}")
    private String secret;
    @Value("${lieBao.prefix}")
    private String prefix;
    @Resource
    private RestTemplate restTemplate;


    @Override
    public String lieBaoAccessToken() {
        String accessToken = "";
        // 构建包含查询参数的URL
        String url = prefix + LieBaoApi.ACCESS_TOKEN.getUrl();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParam("appid", appId).queryParam("secret", secret);

        //获取当前时间戳（s）
        long timestamp = System.currentTimeMillis() / 1000;
        try {
            String signature = CommUtil.generateSignature(appId, secret, String.valueOf(timestamp));
            uriBuilder.queryParam("signature", signature);
            uriBuilder.queryParam("timestamp", timestamp);
        } catch (NoSuchAlgorithmException e) {
            LogUtil.error("获取猎豹token失败,生成sign签错误:", e);
            throw new ServiceException();
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, entity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                LogUtil.info("猎豹接口请求:{},返回的请求信息:{}", url,responseEntity.getBody());
                JSONObject res = JSONObject.parseObject(responseEntity.getBody());
                if (res != null) {
                    accessToken = res.getJSONObject("data").getString("access_token");
                }
            }
        } catch (Exception e) {
            LogUtil.error("猎豹接口请求失败:{},错误原因:{}",url, e);
            throw new ServiceException();
        }
        return accessToken;
    }

    @Override
    public String OELink() {
        String result = "";
        String url = prefix + LieBaoApi.CREATE_OE_LINK.getUrl();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", lieBaoAccessToken());
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                LogUtil.info("猎豹接口请求:{},返回的请求信息:{}", url,responseEntity.getBody());
                JSONObject res = JSONObject.parseObject(responseEntity.getBody()).getJSONObject("data");
                return res.getString("open_account_link");
            }
        } catch (Exception e) {
            LogUtil.error("猎豹接口请求失败:{},错误原因:{}",url, e);
            throw new ServiceException();
        }
        return result;
    }

    @Override
    public String fbAccountList() {
        String result = "";
        String url = prefix + LieBaoApi.FACEBOOK_ACCOUNT_LIST.getUrl();
        try {
            // 默认获取1000条
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("page", "1");

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", lieBaoAccessToken());
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                LogUtil.info("猎豹接口请求:{},返回的请求信息:{}", url, responseEntity.getBody());
                JSONObject res = JSONObject.parseObject(responseEntity.getBody()).getJSONObject("data");
                return res.getString("list");
            }
        } catch (Exception e) {
            LogUtil.error("猎豹接口请求失败:{},错误原因:{}", url, e);
            throw new ServiceException();
        }
        return result;
    }

    @Override
    public String facebookAccountSingle(Long accountId) {
        String result = "";
        String url = prefix + LieBaoApi.FACEBOOK_ACCOUNT_SINGLE.getUrl();
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("account_id", accountId);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", lieBaoAccessToken());
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                LogUtil.info("猎豹接口请求:{},返回的请求信息:{}", url, responseEntity.getBody());
                JSONArray array = JSONObject.parseObject(responseEntity.getBody()).getJSONArray("data");
                return array.get(0).toString();
            }
        } catch (Exception e) {
            LogUtil.error("猎豹接口请求失败:{},错误原因:{}", url, e);
            throw new ServiceException();
        }
        return result;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean facebookAccountRecharge(String accountId, BigDecimal amount) {
        String url = prefix + LieBaoApi.FACEBOOK_ACCOUNT_RECHARGE.getUrl();
        try {
            // 默认获取1000条
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("account_id", accountId)
                    .queryParam("spend_cap", amount);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", lieBaoAccessToken());
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                LogUtil.info("猎豹接口请求:{},返回的请求信息:{}", url, responseEntity.getBody());
                String msg = JSONObject.parseObject(responseEntity.getBody()).getString("msg");
                return msg.equals("成功");
            }
        } catch (Exception e) {
            LogUtil.error("猎豹接口请求失败:{},错误原因:{}", url, e);
            throw new ServiceException();
        }
        return false;
    }

    @Override
    public boolean facebookAccountGrant(String accountId, String bmId, Integer type) {
        String url = prefix + LieBaoApi.FACEBOOK_ACCOUNT_GRANT.getUrl();
        try {
            // 默认获取1000条
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("account_id", accountId)
                    .queryParam("business_id", bmId)
                    .queryParam("type", type);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", lieBaoAccessToken());
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                LogUtil.info("猎豹接口请求:{},返回的请求信息:{}", url, responseEntity.getBody());
                String msg = JSONObject.parseObject(responseEntity.getBody()).getString("msg");
                return msg.equals("成功");
            }
        } catch (Exception e) {
            LogUtil.error("猎豹接口请求失败:{},错误原因:{}", url, e);
            throw new ServiceException();
        }
        return false;
    }

    @Override
    public String businessAccountBindings(String accountId) {
        String result = "";
        String url = prefix + LieBaoApi.BUSINESS_ACCOUNT_BINDINGS.getUrl();
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("account_id", accountId);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", lieBaoAccessToken());
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                LogUtil.info("猎豹接口请求:{},返回的请求信息:{}", url, responseEntity.getBody());
                JSONArray res = JSONObject.parseObject(responseEntity.getBody()).getJSONArray("data");
                return res.toString();
            }
        } catch (Exception e) {
            LogUtil.error("猎豹接口请求失败:{},错误原因:{}", url, e);
            throw new ServiceException();
        }
        return result;
    }
}
