package com.turing.code.tenant.pojo.vo;

import com.turing.code.user.pojo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSearchVo extends UserInfo {
    private Long parentUsId;
    private String parentName;
    private String parentAvatar;
    private Integer parentLevel;
    private String nameAndPhone;
}
