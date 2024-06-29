package com.turing.code.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.tenant.mapper.TenantShopMapper;
import com.turing.code.tenant.pojo.TenantShop;
import com.turing.code.user.mapper.UserInfoMapper;
import com.turing.code.user.mapper.UserOrderPayMapper;
import com.turing.code.user.mapper.UserRoyalFlowProMapper;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.pojo.UserOrderPay;
import com.turing.code.user.pojo.UserRoyalFlow;
import com.turing.code.user.mapper.UserRoyalFlowMapper;
import com.turing.code.user.pojo.vo.UserRoyalFlowVO;
import com.turing.code.user.service.UserRoyalFlowService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-05-31
 */
@Service
public class UserRoyalFlowServiceImpl extends CommonServiceImpl<UserRoyalFlowMapper, UserRoyalFlow> implements UserRoyalFlowService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserRoyalFlowProMapper userRoyalFlowProMapper;
    @Resource
    private UserOrderPayMapper userOrderPayMapper;
    @Resource
    private TenantShopMapper tenantShopMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String selectFlowWithAllByMap(PageInfo pageInfo, Map<String, Object> map) {
        UserInfo userInfo = userInfoMapper.selectById((Long) map.get("userId"));
        PageResult<UserRoyalFlowVO> pageResult = new PageResult<>();
        Page<UserRoyalFlowVO> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<UserRoyalFlowVO> iPage = userRoyalFlowProMapper.selectFlowWithAllByMap(page,map);
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        List<UserRoyalFlowVO> userRoyalFlowVOList = pageResult.getList();
        for (int i = 0; i < userRoyalFlowVOList.size(); i++) {
            UserRoyalFlowVO userRoyalFlowVO = userRoyalFlowVOList.get(i);
            //type : 0 审核中 1通过 -1拒绝  2已付款
            //queryType : 4商品佣金 5商家入驻
            switch (userRoyalFlowVO.getQueryType()) {
                case 4:
                    UserOrderPay orderPay = userOrderPayMapper.selectById(userRoyalFlowVO.getOrderId());
                    if (orderPay != null) {
                        userRoyalFlowVO.setName(orderPay.getProInfo());
                        this.getShopInfo(userRoyalFlowVO, userRoyalFlowVO.getQueryType());
                    } else {
                        userRoyalFlowVO.setName("VIP服务");
                        this.getShopInfo(userRoyalFlowVO);
                    }
                    break;
                default:
                    break;
            }
        }
        return ReturnBody.success(pageResult);
    }
    private void getShopInfo(UserRoyalFlowVO userRoyalFlowVO) {
        userRoyalFlowVO.setQueryType(3);
        getShop(userRoyalFlowVO);
    }
    private void getShopInfo(UserRoyalFlowVO userRoyalFlowVO, int queryType) {
        //返佣订单
        if (queryType == 4) {
            userRoyalFlowVO.setQueryType(4);
        }else{
            userRoyalFlowVO.setQueryType(3);
        }
        getShop(userRoyalFlowVO);
    }
    private void getShop(UserRoyalFlowVO userRoyalFlowVO) {
        if (userRoyalFlowVO.getSourceId() != null) {
            List<TenantShop> merchantShopList =
                    tenantShopMapper.selectSelective(new TenantShop().setTenantId(userRoyalFlowVO.getSourceId()));
            if (!merchantShopList.isEmpty()) {
                userRoyalFlowVO.setNick(merchantShopList.get(0).getName());
                userRoyalFlowVO.setAvatar(merchantShopList.get(0).getShopPhoto());
            }
        }
    }

}
