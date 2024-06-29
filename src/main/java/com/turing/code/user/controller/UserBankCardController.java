package com.turing.code.user.controller;

import com.turing.code.common.annotation.repeat.PreventRepeat;
import com.turing.code.common.utils.VerifyUtil;
import com.turing.code.system.mapper.SysBankMapper;
import com.turing.code.system.pojo.SysBank;
import com.turing.code.user.mapper.UserBankCardMapper;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.pojo.UserWithdraw;
import com.turing.code.user.service.UserWithdrawService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.turing.code.user.service.UserBankCardService;
import com.turing.code.user.pojo.UserBankCard;
import javax.servlet.http.HttpServletRequest;
import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-04
 */
@Api(tags = "用户-银行卡")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "user/userBankCard", produces = "text/plain;charset=utf-8")
public class UserBankCardController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UserBankCardService userBankCardService;
    @Resource
    private SysBankMapper sysBankMapper;
    @Resource
    private UserBankCardMapper userBankCardMapper;
    @Resource
    private UserWithdrawService userWithdrawService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<UserBankCard> pageResult = new PageUtil<UserBankCard>(pageInfo).startPage((page, queryWrapper) -> userBankCardService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @PostMapping("addOrUpdate")
    @ApiOperation("添加或修改银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "id", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "bankId", value = "bankId", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "cardNum", value = "卡号", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "name", value = "持卡人", required = true)
    })
    @ParameterVerify(notNull = {"cardNum", "name", "bankId"})
    public String addOrUpdate(Long id, String cardNum, String name, Integer bankId) {
        cardNum = cardNum.trim();
        name = name.trim();
        if (!VerifyUtil.verify("^[\\u4e00-\\u9fa5]{2,8}$", name)) {
            return ReturnBody.error("持卡人不正确！");
        }
        if (!VerifyUtil.verifyBank(cardNum)) {
            return ReturnBody.error("银行卡号不正确！");
        }
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        UserBankCard userBankCard = UserBankCard.builder().id(id).usId(userInfo.getId()).cardNum(cardNum).bankId(bankId).name(name).build();
        List<SysBank> sysBankList = sysBankMapper.selectSelective(SysBank.builder().bankId(bankId).build());
        if (sysBankList.isEmpty()) {
            return ReturnBody.error("没有银行数据");
        }
        userBankCard.setCardName(sysBankList.get(0).getBank());

        if (userBankCard.getId() == null) {
            if (userBankCardMapper.selectCountSelective(UserBankCard.builder().usId(userInfo.getId()).build()) > 0) {
                return ReturnBody.error("您已有银行卡");
            }
            if (userBankCardMapper.insert(userBankCard) != 1) {
                return ReturnBody.error();
            }
        } else {
            if (userBankCardMapper.updateById(userBankCard) != 1) {
                return ReturnBody.error();
            }
        }
        return ReturnBody.success();
    }

    @PostMapping("delete")
    @ApiOperation("删除")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Long.class, name = "id", value = "id", required = true)
    @ParameterVerify(notNull = "id")
    public String delete(Long id) {
        Long usId = (Long) request.getAttribute("id");
        UserBankCard userBankCard = userBankCardService.getByIdForUpdate(id);
        if (!userBankCard.getUsId().equals(usId)) {
            return ReturnBody.error("无权操作!");
        }
        if (!userBankCardService.removeById(id)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("提现")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = BigDecimal.class, name = "money", value = "提现金额", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "type", value = "1提现到微信余额；2提现到银行卡", required = true),
    })
    @ParameterVerify(notNull = {"money", "type"})
    @PostMapping("withdraw")
    @PreventRepeat
    public String withdraw(BigDecimal money, Integer type) {
        money = money.setScale(2, BigDecimal.ROUND_DOWN);
        if (money.compareTo(BigDecimal.TEN) < 0) {
            return ReturnBody.error("提现金额最低为10元");
        }
        if (money.compareTo(BigDecimal.valueOf(20000)) > 0) {
            return ReturnBody.error("每天限额2万元");
        }
        UserWithdraw userWithdraw = UserWithdraw.builder().usType(0).money(money).type(type).build();
        return userWithdrawService.withdraw(userWithdraw);
    }

}
