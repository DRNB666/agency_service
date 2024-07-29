package com.leepsmart.code.system.tools.build;

import com.leepsmart.code.system.pojo.SysParams;
import com.leepsmart.code.system.service.SysParamsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("SysParamsBuild")
public class SysParamsBuild {

    private static SysParamsService staticParamsService;
    private SysParamsService sysParamsService;

    // 提供对外暴露的静态内部类接口
    // 获取 系统参数列表
    public static Map<String, String> getParamsMap() {
        return NewParamBuild.getParams();
    }

    public static String getParamsByKey(String key) {
        return NewParamBuild.getParams().get(key);
    }

    // 刷新
    public static void reload() {
        NewParamBuild.reload();
    }

    @PostConstruct
    private void init() {
        staticParamsService = sysParamsService;
    }

    // 静态内部类
    private static class NewParamBuild {
        public static Map<String, String> map;

        static {
            if (map == null) {
                init();
            }
        }

        private static void init() {
            List<SysParams> list = staticParamsService.list();
            map = new HashMap<>();
            list.forEach(item -> map.put(item.getName(), item.getValue()));
        }

        private static Map<String, String> getParams() {
            return map;
        }

        private static void reload() {
            init();
        }
    }

}
