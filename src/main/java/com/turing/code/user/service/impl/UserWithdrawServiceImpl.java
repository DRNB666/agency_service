package com.turing.code.user.service.impl;

import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.CacheConstant;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.system.mapper.SysParamsMapper;
import com.turing.code.system.pojo.SysParams;
import com.turing.code.user.mapper.UserBankCardMapper;
import com.turing.code.user.mapper.UserInfoMapper;
import com.turing.code.user.mapper.UserRoyalFlowMapper;
import com.turing.code.user.pojo.*;
import com.turing.code.user.mapper.UserWithdrawMapper;
import com.turing.code.user.service.UserWithdrawService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-04
 */
@Service
public class UserWithdrawServiceImpl extends CommonServiceImpl<UserWithdrawMapper, UserWithdraw> implements UserWithdrawService {


    @Resource
    private HttpServletRequest request;
    @Resource
    private UserBankCardMapper userBankCardMapper;
    @Resource
    private SysParamsMapper sysParamsMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserRoyalFlowMapper userRoyalFlowMapper;
    /**
     * 用户提现
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String withdraw(UserWithdraw userWithdraw) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        userWithdraw.setId(Long.valueOf(CommUtil.getUUID()));
        userWithdraw.setUsId(userInfo.getId());

        BigDecimal totalMoney = userWithdraw.getMoney();
        if (userWithdraw.getType() == 2) {
            List<UserBankCard> userBankCardList = userBankCardMapper.selectSelective(UserBankCard.builder().usId(userInfo.getId()).build());
            if (userBankCardList.isEmpty()) {
                return ReturnBody.error("请填写银行卡信息");
            }
            UserBankCard bankCard = userBankCardList.get(0);
            userWithdraw.setCardName(bankCard.getCardName());
            userWithdraw.setCardNum(bankCard.getCardNum());
            userWithdraw.setBankId(bankCard.getBankId());
            userWithdraw.setName(bankCard.getName());

            //提现手续费
            BigDecimal serviceCharge =
                    userWithdraw.getMoney().multiply(new BigDecimal(sysParamsMapper.selectById(CacheConstant.USER_COST_PROPORTION).getValue()))
                    .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN);
            //最低手续费
            BigDecimal serviceChargeLimit = new BigDecimal(sysParamsMapper.selectById(CacheConstant.USER_COST_LIMIT).getValue());
            if (serviceCharge.compareTo(serviceChargeLimit) < 0) {
                serviceCharge = serviceChargeLimit;
            }
            userWithdraw.setServiceCharge(serviceCharge);
            totalMoney = totalMoney.add(serviceCharge);
        } else {
            BigDecimal userCashMoney = new BigDecimal(sysParamsMapper.selectById(CacheConstant.USER_CASH_MONEY).getValue());
            if (totalMoney.compareTo(userCashMoney) > 0) {
                return ReturnBody.error("提现到零钱最大金额：" + userCashMoney);
            }
        }
        if (userInfo.getRoyal().compareTo(totalMoney) < 0) {
            return ReturnBody.error("余额不足");
        }

        if (userInfoMapper.updateById(new UserInfo().setId(userInfo.getId()).setRoyal(userInfo.getRoyal().subtract(totalMoney)))!=1) {
            throw new ServiceException();
        }
        //添加提现流水
        UserRoyalFlow userRoyalFlow = UserRoyalFlow.builder().type(1).userId(userInfo.getId()).status(0).remark("提现").amount(userWithdraw.getMoney()).build();
        if (userRoyalFlowMapper.insert(userRoyalFlow) != 1) {
            throw new ServiceException();
        }
        if (userWithdraw.getType() == 2 && userWithdraw.getServiceCharge().compareTo(BigDecimal.ZERO) > 0) {
            UserRoyalFlow userRoyalFlowService = UserRoyalFlow.builder().type(1).userId(userInfo.getId()).status(0).remark("提现手续费").amount(userWithdraw.getServiceCharge()).build();
            if (userRoyalFlowMapper.insert(userRoyalFlowService) != 1) {
                throw new ServiceException();
            }
        }

        if (!save(userWithdraw)) {
            throw new ServiceException();
        }
        return ReturnBody.success();
    }
}
