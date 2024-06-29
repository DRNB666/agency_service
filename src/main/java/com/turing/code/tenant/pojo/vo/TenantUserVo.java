package com.turing.code.tenant.pojo.vo;

import com.turing.code.user.pojo.UserInfo;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantUserVo extends UserInfo {
    private String userName;

}
