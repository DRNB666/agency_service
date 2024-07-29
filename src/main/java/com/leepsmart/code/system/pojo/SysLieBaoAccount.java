package com.leepsmart.code.system.pojo;

import java.math.BigDecimal;
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
 * @since 2024-07-29
 */
@ApiModel(value="SysLieBaoAccount对象", description="系统-猎豹-广告账户")
public class SysLieBaoAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "广告账户ID")
    private Long accountId;

    @ApiModelProperty(value = "广告账户状态")
    private Integer accountStatus;

    @ApiModelProperty(value = "广告账户名称")
    private String accountName;

    @ApiModelProperty(value = "花费上限")
    private BigDecimal spendCap;

    @ApiModelProperty(value = "已花费金额")
    private BigDecimal amountSpent;

    @ApiModelProperty(value = "广告账户开户以来的累计花费金额")
    private BigDecimal totalAmountSpent;

    @ApiModelProperty(value = "禁用原因")
    private Integer disableReason;

    @ApiModelProperty(value = "账户创建时间")
    private Long createdTime;

    @ApiModelProperty(value = "货币")
    private String currency;

    @ApiModelProperty(value = "与UTC的时区差异")
    private BigDecimal timezoneOffsetHoursUtc;

    @ApiModelProperty(value = "FB应付账单金额")
    private BigDecimal balance;

    @ApiModelProperty(value = "推广页ID")
    private String promotablePageIds;

    @ApiModelProperty(value = "推广链接")
    private String promotableUrls;

    @ApiModelProperty(value = "开户主体营业执照中文名称")
    private String companyCn;

    @ApiModelProperty(value = "开户主体营业执照英文名称")
    private String companyEn;

    @ApiModelProperty(value = "资金来源类型")
    private Integer fundingSourceDetail;

    @ApiModelProperty(value = "营业执照ID")
    private String businessRegistrationId;

    @ApiModelProperty(value = "FB开户的request_id")
    private String requestId;

    @ApiModelProperty(value = "封户取款金额")
    private BigDecimal disabledTransferAmount;

    @ApiModelProperty(value = "OE参考编号")
    private String oeId;

    private Date createTime;

    @Version
    private Long version;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    public Long getId() {
        return id;
    }

    public SysLieBaoAccount setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getAccountId() {
        return accountId;
    }

    public SysLieBaoAccount setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }
    public Integer getAccountStatus() {
        return accountStatus;
    }

    public SysLieBaoAccount setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
        return this;
    }
    public String getAccountName() {
        return accountName;
    }

    public SysLieBaoAccount setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }
    public BigDecimal getSpendCap() {
        return spendCap;
    }

    public SysLieBaoAccount setSpendCap(BigDecimal spendCap) {
        this.spendCap = spendCap;
        return this;
    }
    public BigDecimal getAmountSpent() {
        return amountSpent;
    }

    public SysLieBaoAccount setAmountSpent(BigDecimal amountSpent) {
        this.amountSpent = amountSpent;
        return this;
    }
    public BigDecimal getTotalAmountSpent() {
        return totalAmountSpent;
    }

    public SysLieBaoAccount setTotalAmountSpent(BigDecimal totalAmountSpent) {
        this.totalAmountSpent = totalAmountSpent;
        return this;
    }
    public Integer getDisableReason() {
        return disableReason;
    }

    public SysLieBaoAccount setDisableReason(Integer disableReason) {
        this.disableReason = disableReason;
        return this;
    }
    public Long getCreatedTime() {
        return createdTime;
    }

    public SysLieBaoAccount setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
        return this;
    }
    public String getCurrency() {
        return currency;
    }

    public SysLieBaoAccount setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
    public BigDecimal getTimezoneOffsetHoursUtc() {
        return timezoneOffsetHoursUtc;
    }

    public SysLieBaoAccount setTimezoneOffsetHoursUtc(BigDecimal timezoneOffsetHoursUtc) {
        this.timezoneOffsetHoursUtc = timezoneOffsetHoursUtc;
        return this;
    }
    public BigDecimal getBalance() {
        return balance;
    }

    public SysLieBaoAccount setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
    public String getPromotablePageIds() {
        return promotablePageIds;
    }

    public SysLieBaoAccount setPromotablePageIds(String promotablePageIds) {
        this.promotablePageIds = promotablePageIds;
        return this;
    }
    public String getPromotableUrls() {
        return promotableUrls;
    }

    public SysLieBaoAccount setPromotableUrls(String promotableUrls) {
        this.promotableUrls = promotableUrls;
        return this;
    }
    public String getCompanyCn() {
        return companyCn;
    }

    public SysLieBaoAccount setCompanyCn(String companyCn) {
        this.companyCn = companyCn;
        return this;
    }
    public String getCompanyEn() {
        return companyEn;
    }

    public SysLieBaoAccount setCompanyEn(String companyEn) {
        this.companyEn = companyEn;
        return this;
    }
    public Integer getFundingSourceDetail() {
        return fundingSourceDetail;
    }

    public SysLieBaoAccount setFundingSourceDetail(Integer fundingSourceDetail) {
        this.fundingSourceDetail = fundingSourceDetail;
        return this;
    }
    public String getBusinessRegistrationId() {
        return businessRegistrationId;
    }

    public SysLieBaoAccount setBusinessRegistrationId(String businessRegistrationId) {
        this.businessRegistrationId = businessRegistrationId;
        return this;
    }
    public String getRequestId() {
        return requestId;
    }

    public SysLieBaoAccount setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }
    public BigDecimal getDisabledTransferAmount() {
        return disabledTransferAmount;
    }

    public SysLieBaoAccount setDisabledTransferAmount(BigDecimal disabledTransferAmount) {
        this.disabledTransferAmount = disabledTransferAmount;
        return this;
    }
    public String getOeId() {
        return oeId;
    }

    public SysLieBaoAccount setOeId(String oeId) {
        this.oeId = oeId;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public SysLieBaoAccount setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public SysLieBaoAccount setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public SysLieBaoAccount setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Long getUserId() {
        return userId;
    }

    public SysLieBaoAccount setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public static final String ID = "sys_lie_bao_account.id";
    public static final String ACCOUNT_ID = "sys_lie_bao_account.account_id";
    public static final String ACCOUNT_STATUS = "sys_lie_bao_account.account_status";
    public static final String ACCOUNT_NAME = "sys_lie_bao_account.account_name";
    public static final String SPEND_CAP = "sys_lie_bao_account.spend_cap";
    public static final String AMOUNT_SPENT = "sys_lie_bao_account.amount_spent";
    public static final String TOTAL_AMOUNT_SPENT = "sys_lie_bao_account.total_amount_spent";
    public static final String DISABLE_REASON = "sys_lie_bao_account.disable_reason";
    public static final String CREATED_TIME = "sys_lie_bao_account.created_time";
    public static final String CURRENCY = "sys_lie_bao_account.currency";
    public static final String TIMEZONE_OFFSET_HOURS_UTC = "sys_lie_bao_account.timezone_offset_hours_utc";
    public static final String BALANCE = "sys_lie_bao_account.balance";
    public static final String PROMOTABLE_PAGE_IDS = "sys_lie_bao_account.promotable_page_ids";
    public static final String PROMOTABLE_URLS = "sys_lie_bao_account.promotable_urls";
    public static final String COMPANY_CN = "sys_lie_bao_account.company_cn";
    public static final String COMPANY_EN = "sys_lie_bao_account.company_en";
    public static final String FUNDING_SOURCE_DETAIL = "sys_lie_bao_account.funding_source_detail";
    public static final String BUSINESS_REGISTRATION_ID = "sys_lie_bao_account.business_registration_id";
    public static final String REQUEST_ID = "sys_lie_bao_account.request_id";
    public static final String DISABLED_TRANSFER_AMOUNT = "sys_lie_bao_account.disabled_transfer_amount";
    public static final String OE_ID = "sys_lie_bao_account.oe_id";
    public static final String CREATE_TIME = "sys_lie_bao_account.create_time";
    public static final String VERSION = "sys_lie_bao_account.version";
    public static final String UPDATE_TIME = "sys_lie_bao_account.update_time";
    public static final String USER_ID = "sys_lie_bao_account.user_id";


    @Override
    public String toString() {
        return "SysLieBaoAccount{" +
            "id=" + id +
            ", accountId=" + accountId +
            ", accountStatus=" + accountStatus +
            ", accountName=" + accountName +
            ", spendCap=" + spendCap +
            ", amountSpent=" + amountSpent +
            ", totalAmountSpent=" + totalAmountSpent +
            ", disableReason=" + disableReason +
            ", createdTime=" + createdTime +
            ", currency=" + currency +
            ", timezoneOffsetHoursUtc=" + timezoneOffsetHoursUtc +
            ", balance=" + balance +
            ", promotablePageIds=" + promotablePageIds +
            ", promotableUrls=" + promotableUrls +
            ", companyCn=" + companyCn +
            ", companyEn=" + companyEn +
            ", fundingSourceDetail=" + fundingSourceDetail +
            ", businessRegistrationId=" + businessRegistrationId +
            ", requestId=" + requestId +
            ", disabledTransferAmount=" + disabledTransferAmount +
            ", oeId=" + oeId +
            ", createTime=" + createTime +
            ", version=" + version +
            ", updateTime=" + updateTime +
            ", userId=" + userId +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            SysLieBaoAccount.ID
            ,SysLieBaoAccount.ACCOUNT_ID
            ,SysLieBaoAccount.ACCOUNT_STATUS
            ,SysLieBaoAccount.ACCOUNT_NAME
            ,SysLieBaoAccount.SPEND_CAP
            ,SysLieBaoAccount.AMOUNT_SPENT
            ,SysLieBaoAccount.TOTAL_AMOUNT_SPENT
            ,SysLieBaoAccount.DISABLE_REASON
            ,SysLieBaoAccount.CREATED_TIME
            ,SysLieBaoAccount.CURRENCY
            ,SysLieBaoAccount.TIMEZONE_OFFSET_HOURS_UTC
            ,SysLieBaoAccount.BALANCE
            ,SysLieBaoAccount.PROMOTABLE_PAGE_IDS
            ,SysLieBaoAccount.PROMOTABLE_URLS
            ,SysLieBaoAccount.COMPANY_CN
            ,SysLieBaoAccount.COMPANY_EN
            ,SysLieBaoAccount.FUNDING_SOURCE_DETAIL
            ,SysLieBaoAccount.BUSINESS_REGISTRATION_ID
            ,SysLieBaoAccount.REQUEST_ID
            ,SysLieBaoAccount.DISABLED_TRANSFER_AMOUNT
            ,SysLieBaoAccount.OE_ID
            ,SysLieBaoAccount.CREATE_TIME
            ,SysLieBaoAccount.VERSION
            ,SysLieBaoAccount.UPDATE_TIME
            ,SysLieBaoAccount.USER_ID
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}