package com.leepsmart.code.admin.pojo;

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
 * @author leepsmart generator
 * @since 2021-11-03
 */
@ApiModel(value = "AdMenu对象", description = "管理员-菜单")
public class AdMenu implements Serializable {

    public static final String ID = "ad_menu.id";
    public static final String MENU_NAME = "ad_menu.menu_name";
    public static final String REQUEST_URL = "ad_menu.request_url";
    public static final String MENU_ICON = "ad_menu.menu_icon";
    public static final String PARENT_ID = "ad_menu.parent_id";
    public static final String COMPONENT = "ad_menu.component";
    public static final String FLAG = "ad_menu.flag";
    public static final String SORT = "ad_menu.sort";
    public static final String UPDATE_TIME = "ad_menu.update_time";
    public static final String CREATE_TIME = "ad_menu.create_time";
    public static final String VERSION = "ad_menu.version";
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

    public final static String[] getFields(String... noField) {
        List<String> list = new ArrayList<>(Arrays.asList(
                AdMenu.ID
                , AdMenu.MENU_NAME
                , AdMenu.REQUEST_URL
                , AdMenu.MENU_ICON
                , AdMenu.PARENT_ID
                , AdMenu.COMPONENT
                , AdMenu.FLAG
                , AdMenu.SORT
                , AdMenu.UPDATE_TIME
                , AdMenu.CREATE_TIME
                , AdMenu.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }

    public Integer getId() {
        return id;
    }

    public AdMenu setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getMenuName() {
        return menuName;
    }

    public AdMenu setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public AdMenu setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public AdMenu setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public AdMenu setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public AdMenu setComponent(String component) {
        this.component = component;
        return this;
    }

    public Integer getFlag() {
        return flag;
    }

    public AdMenu setFlag(Integer flag) {
        this.flag = flag;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public AdMenu setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AdMenu setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AdMenu setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public AdMenu setVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "AdMenu{" +
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
}