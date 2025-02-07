package com.leepsmart.code.common.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;

import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;

import com.leepsmart.code.user.pojo.UserMenu;
import com.leepsmart.code.user.pojo.UserRoleMenu;
import com.leepsmart.code.user.service.UserMenuService;
import com.leepsmart.code.user.service.UserRoleMenuService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Component
public class UsInterceptor implements HandlerInterceptor {


    @Resource
    private UserMenuService userMenuService;
    @Resource
    private UserRoleMenuService userRoleMenuService;
    public static final String SUPER_ADMIN = "admin";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        //公开接口直接放行
        if (requestUrl.contains("/public")) {
            return true;
        }

        QueryWrapper<UserMenu> eq = new QueryWrapper<UserMenu>().eq(UserMenu.REQUEST_URL, requestUrl).eq(UserMenu.FLAG,2);
        UserMenu userMenu = userMenuService.getOne(eq);
        //没有在数据库添加该接口的情况下，默认不做权限校验，直接放行
        if (!CommUtil.checkNull(userMenu)){
            return true;
        }


        //该接口已被数据库添加做权限管理，查询用户是否具有该角色菜单权限，访问量增多的情况下考虑redis缓存
        Long roleId = (Long) request.getAttribute("roleId");
        QueryWrapper<UserRoleMenu> roleMenuEq = new QueryWrapper<UserRoleMenu>().eq(UserRoleMenu.ROLE_ID,roleId).eq(UserRoleMenu.MENU_ID,userMenu.getId());
        UserRoleMenu userRoleMenu = userRoleMenuService.getOne(roleMenuEq);
        if (CommUtil.checkNull(userRoleMenu)){
            //不为空，放行
            return true;
        }

        //所有流程走完后未返回则说明无权操作，脱离Spring MVC的返回流程，重新编码无权操作返回
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
