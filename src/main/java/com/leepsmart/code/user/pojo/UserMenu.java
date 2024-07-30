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
@ApiModel(value="UserMenu对象", description="管理员-菜单")
public class UserMenu implements Serializable {

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

    @ApiModelProperty(value = "菜单英文名称")
    private String name;

    public Integer getId() {
        return id;
    }

    public UserMenu setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getMenuName() {
        return menuName;
    }

    public UserMenu setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }
    public String getRequestUrl() {
        return requestUrl;
    }

    public UserMenu setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }
    public String getMenuIcon() {
        return menuIcon;
    }

    public UserMenu setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
        return this;
    }
    public Integer getParentId() {
        return parentId;
    }

    public UserMenu setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }
    public String getComponent() {
        return component;
    }

    public UserMenu setComponent(String component) {
        this.component = component;
        return this;
    }
    public Integer getFlag() {
        return flag;
    }

    public UserMenu setFlag(Integer flag) {
        this.flag = flag;
        return this;
    }
    public Integer getSort() {
        return sort;
    }

    public UserMenu setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserMenu setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserMenu setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getVersion() {
        return version;
    }

    public UserMenu setVersion(Integer version) {
        this.version = version;
        return this;
    }
    public String getName() {
        return name;
    }

    public UserMenu setName(String name) {
        this.name = name;
        return this;
    }

    public static final String ID = "user_menu.id";
    public static final String MENU_NAME = "user_menu.menu_name";
    public static final String REQUEST_URL = "user_menu.request_url";
    public static final String MENU_ICON = "user_menu.menu_icon";
    public static final String PARENT_ID = "user_menu.parent_id";
    public static final String COMPONENT = "user_menu.component";
    public static final String FLAG = "user_menu.flag";
    public static final String SORT = "user_menu.sort";
    public static final String UPDATE_TIME = "user_menu.update_time";
    public static final String CREATE_TIME = "user_menu.create_time";
    public static final String VERSION = "user_menu.version";
    public static final String NAME = "user_menu.name";


    @Override
    public String toString() {
        return "UserMenu{" +
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
            ", name=" + name +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserMenu.ID
            ,UserMenu.MENU_NAME
            ,UserMenu.REQUEST_URL
            ,UserMenu.MENU_ICON
            ,UserMenu.PARENT_ID
            ,UserMenu.COMPONENT
            ,UserMenu.FLAG
            ,UserMenu.SORT
            ,UserMenu.UPDATE_TIME
            ,UserMenu.CREATE_TIME
            ,UserMenu.VERSION
            ,UserMenu.NAME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}