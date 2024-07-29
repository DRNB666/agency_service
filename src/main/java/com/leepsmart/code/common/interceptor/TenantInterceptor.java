package com.leepsmart.code.common.interceptor;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.leepsmart.code.admin.pojo.AdInfo;
import com.leepsmart.code.admin.pojo.AdMenu;
import com.leepsmart.code.admin.pojo.AdRoleMenu;
import com.leepsmart.code.admin.service.AdRoleMenuService;
import com.leepsmart.code.admin.tools.enums.AdMenuFlag;
import com.leepsmart.code.common.redis.utils.RedisUtil;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@Component
public class TenantInterceptor implements HandlerInterceptor {

//    @Resource
//    private TenantRoleMenuService tenantRoleMenuService;
    @Resource
    private RedisUtil redisUtil;
//    @Resource
//    private TenantMenuService tenantMenuService;

    /**
     * 预处理回调方法，实现处理器的预处理
     * 返回值：true表示继续流程；false表示流程中断，不会继续调用其他的拦截器或处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取当前登录用户id,根据id查询出如果当前remark  不等于空而且等于admin就放心,允许所有操作
        // 配置不需要角色权限的路径
        //权限管理，暂时注释
//        List<String> list = Lists.newArrayList("/admin/adInfo/updatePassword");
//        String requestUrl = request.getRequestURI();
//        if (requestUrl.contains("/public") || list.contains(requestUrl)) {
//            return true;
//        }
//        Integer roleId = (Integer) request.getAttribute(TenantInfo.ROLE_ID);
//        if (roleId == 1) {
//            return true;
//        }
//        List<TenantMenu> tenantMenuList;
//        Object object = redisUtil.hget("roleMenu", roleId.toString());
//        if (CommUtil.checkNull(object)) {
//            tenantMenuList = (List<TenantMenu>) object;
//        } else {
//            TenantRoleMenu tenantRoleMenu = new TenantRoleMenu();
//            tenantRoleMenu.setRoleId(roleId);
//            tenantMenuList = tenantRoleMenuService.selectRoleMenu(tenantRoleMenu);
//            redisUtil.hset("roleMenu", roleId.toString(), tenantMenuList);
//        }
//        QueryWrapper<TenantMenu> eq = new QueryWrapper<TenantMenu>().eq(TenantMenu.FLAG, 2).eq(TenantMenu.REQUEST_URL,requestUrl);
//        TenantMenu authorityInterface = tenantMenuService.getOne(eq);
//        //权限接口已添加的情况下才进行权限查询，如果接口没有进行权限管理，那么直接放行
//        if (CommUtil.checkNull(authorityInterface)){
//            TenantMenu currentMenu = tenantMenuList.stream().filter(
//                    adMenu -> requestUrl.equals(adMenu.getRequestUrl()))
//                    .findFirst().orElse(null);
//            if (currentMenu != null) {
//                return true;
//            }
//        }else {
//            return true;
//        }


        // 脱离Spring MVC的返回流程，重新编码
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(ReturnBody.error(ResultCodeInfo.NO_AUTHORITY_OPERATE));
        out.flush();
        out.close();
        return false;
    }

    /**
     * 后处理回调方法，实现处理器（controller）的后处理，但在渲染视图之前
     * 此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，
     * 如性能监控中我们可以在此记录结束时间并输出消耗时间，
     * 还可以进行一些资源清理，类似于try-catch-finally中的finally，
     * 但仅调用处理器执行链中
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
