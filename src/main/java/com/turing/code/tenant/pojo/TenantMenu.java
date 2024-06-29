package com.turing.code.tenant.pojo;

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
 * @author turing generator
 * @since 2024-05-28
 */
@ApiModel(value="TenantMenu对象", description="租户-菜单")
public class TenantMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单路由或接口路径")
    private String requestUrl;

    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    @ApiModelProperty(value = "父级id")
    private Integer parentId;

    @ApiModelProperty(value = "对应的组件")
    private String component;

    @ApiModelProperty(value = "标记:  1-菜单   2-接口")
    private Integer flag;

    @ApiModelProperty(value = "排序,   值越大排越前")
    private Integer sort;

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

    public TenantMenu setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getMenuName() {
        return menuName;
    }

    public TenantMenu setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }
    public String getRequestUrl() {
        return requestUrl;
    }

    public TenantMenu setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }
    public String getMenuIcon() {
        return menuIcon;
    }

    public TenantMenu setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
        return this;
    }
    public Integer getParentId() {
        return parentId;
    }

    public TenantMenu setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }
    public String getComponent() {
        return component;
    }

    public TenantMenu setComponent(String component) {
        this.component = component;
        return this;
    }
    public Integer getFlag() {
        return flag;
    }

    public TenantMenu setFlag(Integer flag) {
        this.flag = flag;
        return this;
    }
    public Integer getSort() {
        return sort;
    }

    public TenantMenu setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantMenu setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantMenu setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getVersion() {
        return version;
    }

    public TenantMenu setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public static final String ID = "tenant_menu.id";
    public static final String MENU_NAME = "tenant_menu.menu_name";
    public static final String REQUEST_URL = "tenant_menu.request_url";
    public static final String MENU_ICON = "tenant_menu.menu_icon";
    public static final String PARENT_ID = "tenant_menu.parent_id";
    public static final String COMPONENT = "tenant_menu.component";
    public static final String FLAG = "tenant_menu.flag";
    public static final String SORT = "tenant_menu.sort";
    public static final String UPDATE_TIME = "tenant_menu.update_time";
    public static final String CREATE_TIME = "tenant_menu.create_time";
    public static final String VERSION = "tenant_menu.version";


    @Override
    public String toString() {
        return "TenantMenu{" +
            "id=" + id +
            ", menuName=" + menuName +
            ", requestUrl=" + requestUrl +
            ", menuIcon=" + menuIcon +
            ", parentId=" + parentId +
            ", component=" + component +
            ", flag=" + flag +
            ", sort=" + sort +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            ", version=" + version +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantMenu.ID
            ,TenantMenu.MENU_NAME
            ,TenantMenu.REQUEST_URL
            ,TenantMenu.MENU_ICON
            ,TenantMenu.PARENT_ID
            ,TenantMenu.COMPONENT
            ,TenantMenu.FLAG
            ,TenantMenu.SORT
            ,TenantMenu.UPDATE_TIME
            ,TenantMenu.CREATE_TIME
            ,TenantMenu.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}