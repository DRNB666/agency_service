package com.turing.code.admin.controller;

import com.turing.code.admin.service.AdRoleMenuService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author turing generator
 * @since 2021-02-03
 */
@Api(tags = "管理员-角色菜单权限")
@RestController
@RequestMapping("admin/adRoleMenu")
public class AdRoleMenuController {

    @Resource
    private AdRoleMenuService adRoleMenuService;

}
