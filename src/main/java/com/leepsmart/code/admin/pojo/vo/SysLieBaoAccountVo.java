package com.leepsmart.code.admin.pojo.vo;

import com.leepsmart.code.system.pojo.SysLieBaoAccount;
import lombok.*;

/**
 * 后台猎豹账户列表实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysLieBaoAccountVo extends SysLieBaoAccount {

    /**
     * 用户邮箱
     */
    private String usEmail;

    /**
     * 用户手机号
     */
    private String usMobile;

    /**
     * 用户账号
     */
    private String usAccount;

}
