package com.turing.code.common.timer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.turing.code.common.utils.LogUtil;
import com.turing.code.user.pojo.UserVipLevel;
import com.turing.code.user.service.UserVipLevelService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class generalTime {

    @Resource
    private UserVipLevelService userVipLevelService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void taskZero(){
        LogUtil.info("凌晨0点定时任务开始执行......");
        vipLevelExpired();
        LogUtil.info("凌晨0点定时任务开始执行......");
    }


    /**
     * 会员等级过期
     */
    public void vipLevelExpired(){
        UpdateWrapper<UserVipLevel> eq = new UpdateWrapper<UserVipLevel>()
                .ge(UserVipLevel.DUE_TIME, new Date()).set(UserVipLevel.STATUS,0);
        userVipLevelService.update(eq);
        LogUtil.info("会员等级过期方法执行完毕!");
    }
}
