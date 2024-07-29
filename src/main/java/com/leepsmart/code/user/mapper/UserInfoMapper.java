package com.leepsmart.code.user.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leepsmart.code.common.mybatisplus.methods.CommonMapper;
import com.leepsmart.code.user.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leepsmart generator
 * @since 2021-12-18
 */
public interface UserInfoMapper extends CommonMapper<UserInfo> {

    BigDecimal selectRoayalByDay(Map<String, Object> map);

    IPage<UserInfo> selectSelective(Page<UserInfo> page, @Param("userInfo") UserInfo userInfo);

    List<UserInfo> selectSelective(UserInfo build);

    Integer selectRoyalCount(Map<String, Object> map);

    int selectCountSelective(@Param("userInfo")UserInfo setStatus);

    Integer directCount(Long userId);

    Integer indirectCount(Long userId);

    //下级的id列表
    List<Integer> selectByInviterId(@Param("inviterId") Integer inviterId);

    IPage<UserInfo> queryPublic(Page<UserInfo> page, @Param("userInfo") UserInfo userInfo);

    IPage<UserInfo> userList(Page<UserInfo> page,@Param("param") HashMap<String, Object> stringObjectHashMap);
}
