package com.turing.code.admin.pojo.vo;

import com.turing.code.user.pojo.UserInfo;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;


public class UserInfoVo extends UserInfo {

        private Integer parent;//业务登记 1表示一级业务员  2表示二级业务员
        private Integer count;//开单总数量
        private BigDecimal countAmount; //开单总业绩
        private Integer dayCount; //今日开单数量
        private BigDecimal monthAmount;//本月开单业绩

        private String  upName;

        private String agentUser;

        /**
         * 是否vip 0 否 1 是
         */
        private Integer isVip;

        /**
         * 是否创建名片
         */
        private Boolean isCreateCard;

        /**
         * vip购买时间
         */
        private Date openVipTime;

        /**
         * vip过期时间
         */
        private Date maturityTime;

        public String getAgentUser() {
                return agentUser;
        }

        public void setAgentUser(String agentUser) {
                this.agentUser = agentUser;
        }

        public String getUpName() {
                return upName;
        }

        public void setUpName(String upName) {
                this.upName = upName;
        }

        public Integer getCount() {
                return count;
        }

        public void setCount(Integer count) {
                this.count = count;
        }

        public BigDecimal getCountAmount() {
                return countAmount;
        }

        public void setCountAmount(BigDecimal countAmount) {
                this.countAmount = countAmount;
        }

        public Integer getDayCount() {
                return dayCount;
        }

        public void setDayCount(Integer dayCount) {
                this.dayCount = dayCount;
        }

        public BigDecimal getMonthAmount() {
                return monthAmount;
        }

        public void setMonthAmount(BigDecimal monthAmount) {
                this.monthAmount = monthAmount;
        }

        public Integer getParent() {
                return parent;
        }

        public void setParent(Integer parent) {
                this.parent = parent;
        }

        public Integer getIsVip() {
                return isVip;
        }

        public void setIsVip(Integer isVip) {
                this.isVip = isVip;
        }

        public Boolean getIsCreateCard() {
                return isCreateCard;
        }

        public void setIsCreateCard(Boolean createCard) {
                isCreateCard = createCard;
        }

        public Boolean getCreateCard() {
                return isCreateCard;
        }

        public void setCreateCard(Boolean createCard) {
                isCreateCard = createCard;
        }

        public Date getOpenVipTime() {
                return openVipTime;
        }

        public void setOpenVipTime(Date openVipTime) {
                this.openVipTime = openVipTime;
        }

        public Date getMaturityTime() {
                return maturityTime;
        }

        public void setMaturityTime(Date maturityTime) {
                this.maturityTime = maturityTime;
        }
}