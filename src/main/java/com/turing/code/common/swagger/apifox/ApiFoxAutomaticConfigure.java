package com.turing.code.common.swagger.apifox;

import com.alibaba.fastjson.JSONObject;
import com.turing.code.common.utils.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * apiFox自动更新配置类
 * @date 2024.4.17 xy
 */
@Component
public class ApiFoxAutomaticConfigure {


    @Value("${ApiFox.enabled}")
    private Boolean enabled;
    @Value("${ApiFox.projectId}")
    private String projectId;
    @Value("${ApiFox.Authorization}")
    private String Authorization;
    @Value("${spring.profiles.active}")
    private String env;
    @Value("${server.port}")
    private String port;


    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) throws Exception {
        if ("dev".equals(env)&&enabled) {
            LogUtil.info("开始解析swagger文档......");

            InetAddress inetAddress;
            inetAddress = InetAddress.getLocalHost();

            String swaggerUrl = String.format("http://%s:%s/%s", inetAddress.getHostAddress(), port, "v2/api-docs");

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(swaggerUrl, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()&& Objects.requireNonNull(responseEntity.getBody()).contains("swagger")) {
                LogUtil.info("swagger文档解析完毕");
                synchronizationUpdate(responseEntity.getBody());
            } else {
                LogUtil.error("swagger文档解析失败,已获取到的内容为:{}", responseEntity.getBody());
            }
        }
    }

    public void synchronizationUpdate(String swaggerJson) {
        LogUtil.info("(仅开发环境)ApiFox接口文档开始更新......");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Apifox-Version", ApiFoxVersion.V2.getValue());
        headers.add("Authorization", "Bearer " + Authorization);

        JSONObject requestData = new JSONObject(true);
        requestData.put("importFormat", "openapi");
        requestData.put("input", swaggerJson);
        JSONObject options = new JSONObject(true);
        //接口匹配更新策略（接口路径一致时）：不做更改
        options.put("endpointOverwriteBehavior",ApiFoxEndpointOverwriteBehavior.KEEP_EXISTING);
        //数据模型更新策略（接口路径一致时）：不做更改
        options.put("schemaOverwriteBehavior",ApiFoxEndpointOverwriteBehavior.KEEP_EXISTING);
        requestData.put("options",options);

        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(requestData.toJSONString(), headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "https://api.apifox.com/v1/projects/" + projectId + "/import-openapi",
                    HttpMethod.POST, requestEntity, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                JSONObject res = JSONObject.parseObject(responseEntity.getBody());
                JSONObject apiCollection = res.getJSONObject("data");
                JSONObject counters = apiCollection.getJSONObject("counters");

                LogUtil.info("(仅开发环境)ApiFox接口文档更新完毕，总更新接口：{}个，总更新接口目录：{}个，" +
                                "新增的接口：{}个，修改的接口：{}个，忽略的接口：{}个，导入出错的接口：{}个。",
                        counters.getInteger("endpointCreated")+counters.getInteger("endpointUpdated"),
                        counters.getInteger("endpointFolderCreated")+counters.getInteger("endpointFolderUpdated"),
                        counters.getInteger("endpointCreated"),counters.getInteger("endpointUpdated"),
                        counters.getInteger("endpointFailed"),counters.getInteger("endpointIgnored"));
            } else {
                LogUtil.warn("(仅开发环境)ApiFox接口文档更新失败,原因:{}", responseEntity.getBody());
            }
        }catch (Exception e){
            LogUtil.warn("(仅开发环境)ApiFox接口文档更新失败,原因:{}", e.getMessage());
        }

    }


}
