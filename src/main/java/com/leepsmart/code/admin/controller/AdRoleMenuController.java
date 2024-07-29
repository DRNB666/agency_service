package com.leepsmart.code.admin.controller;

import com.leepsmart.code.admin.service.AdRoleMenuService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author leepsmart generator
 */
@Api(tags = "后台-角色菜单权限")
@RestController
@RequestMapping("admin/adRoleMenu")
public class AdRoleMenuController {

    @Resource
    private AdRoleMenuService adRoleMenuService;

}
