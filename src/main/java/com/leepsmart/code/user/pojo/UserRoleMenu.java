package com.leepsmart.code.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * @author leepsmart generator
 * @since 2024-07-30
 */
@ApiModel(value="UserRoleMenu对象", description="管理员-角色菜单权限")
public class UserRoleMenu implements Serializable {

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

    public Integer getId() {
        return id;
    }

    public UserRoleMenu setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public UserRoleMenu setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }
    public Integer getMenuId() {
        return menuId;
    }

    public UserRoleMenu setMenuId(Integer menuId) {
        this.menuId = menuId;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserRoleMenu setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserRoleMenu setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserRoleMenu setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getVersion() {
        return version;
    }

    public UserRoleMenu setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public static final String ID = "user_role_menu.id";
    public static final String ROLE_ID = "user_role_menu.role_id";
    public static final String MENU_ID = "user_role_menu.menu_id";
    public static final String STATUS = "user_role_menu.status";
    public static final String UPDATE_TIME = "user_role_menu.update_time";
    public static final String CREATE_TIME = "user_role_menu.create_time";
    public static final String VERSION = "user_role_menu.version";


    @Override
    public String toString() {
        return "UserRoleMenu{" +
            "id=" + id +
            ", roleId=" + roleId +
            ", menuId=" + menuId +
            ", status=" + status +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            ", version=" + version +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserRoleMenu.ID
            ,UserRoleMenu.ROLE_ID
            ,UserRoleMenu.MENU_ID
            ,UserRoleMenu.STATUS
            ,UserRoleMenu.UPDATE_TIME
            ,UserRoleMenu.CREATE_TIME
            ,UserRoleMenu.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}