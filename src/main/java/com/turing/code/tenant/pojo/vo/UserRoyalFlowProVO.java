package com.turing.code.tenant.pojo.vo;

import java.math.BigDecimal;
import java.util.Date;

public class UserRoyalFlowProVO  {

    private Long id;

    private Long agentId;

    private Long tenantId;

    private Long orderId;

    private BigDecimal secAmount;

    private BigDecimal orderAmount;

    private Integer type;

    private Integer status;

    private Date createTime;


    private String nick;

    private String avatar;

    private String mobile;


    private Integer grade;

    private String realName;

    private Integer isInside;

    //等级直推权益
    private BigDecimal power;
    //等级间推权益
    private BigDecimal inPower;
    //升级团队条件
    private Integer upGradeTeam;
    //升级个人条件
    private Integer upGradePerson;

    private Integer queryType;

    private String condition;

    //平台推广权益
    private String autho;

    //1集体应用，2市级代理应用 , 3商家分销应用，11拼团应用，12秒杀应用，13优惠券应用   0分销推广商品
    private Integer sourceType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getSecAmount() {
        return secAmount;
    }

    public void setSecAmount(BigDecimal secAmount) {
        this.secAmount = secAmount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getIsInside() {
        return isInside;
    }

    public void setIsInside(Integer isInside) {
        this.isInside = isInside;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public BigDecimal getInPower() {
        return inPower;
    }

    public void setInPower(BigDecimal inPower) {
        this.inPower = inPower;
    }

    public Integer getUpGradeTeam() {
        return upGradeTeam;
    }

    public void setUpGradeTeam(Integer upGradeTeam) {
        this.upGradeTeam = upGradeTeam;
    }

    public Integer getUpGradePerson() {
        return upGradePerson;
    }

    public void setUpGradePerson(Integer upGradePerson) {
        this.upGradePerson = upGradePerson;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAutho() {
        return autho;
    }

    public void setAutho(String autho) {
        this.autho = autho;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }
}
