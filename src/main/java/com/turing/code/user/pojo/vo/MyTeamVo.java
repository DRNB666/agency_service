package com.turing.code.user.pojo.vo;

import com.turing.code.user.pojo.UserInfo;

public class MyTeamVo extends UserInfo {


        /** 今日开单*/
        private Integer dayCount;
        /** 总开单*/
        private Integer totalCount;
        //间推人数
        private Integer indriectProCount;
        //直推人数
        private Integer driectProCount;

        public Integer getDayCount() {
            return dayCount;
        }

        public void setDayCount(Integer dayCount) {
            this.dayCount = dayCount;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public Integer getIndriectProCount() {
            return indriectProCount;
        }

        public void setIndriectProCount(Integer indriectProCount) {
            this.indriectProCount = indriectProCount;
        }

        public Integer getDriectProCount() {
            return driectProCount;
        }

        public void setDriectProCount(Integer driectProCount) {
            this.driectProCount = driectProCount;
        }


}