package com.turing.code.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author turing generator
 * @since 2021-11-03
 */
@ApiModel(value = "AdRoleMenu对象", description = "管理员-角色菜单权限")
public class AdRoleMenu implements Serializable {

    public static final String ID = "ad_role_menu.id";
    public static final String ROLE_ID = "ad_role_menu.role_id";
    public static final String MENU_ID = "ad_role_menu.menu_id";
    public static final String STATUS = "ad_role_menu.status";
    public static final String UPDATE_TIME = "ad_role_menu.update_time";
    public static final String CREATE_TIME = "ad_role_menu.create_time";
    public static final String VERSION = "ad_role_menu.version";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
    @ApiModelProperty(value = "菜单id")
    private Integer menuId;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "版本")
    @Version
    private Integer version;

    public final static String[] getFields(String... noField) {
        List<String> list = new ArrayList<>(Arrays.asList(
                AdRoleMenu.ID
                , AdRoleMenu.ROLE_ID
                , AdRoleMenu.MENU_ID
                , AdRoleMenu.STATUS
                , AdRoleMenu.UPDATE_TIME
                , AdRoleMenu.CREATE_TIME
                , AdRoleMenu.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }

    public Integer getId() {
        return id;
    }

    public AdRoleMenu setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public AdRoleMenu setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public AdRoleMenu setMenuId(Integer menuId) {
        this.menuId = menuId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AdRoleMenu setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AdRoleMenu setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AdRoleMenu setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public AdRoleMenu setVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "AdRoleMenu{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", menuId=" + menuId +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", version=" + version +
                "}";
    }
}