package com.turing.code.user.pojo.vo;

import com.turing.code.user.pojo.UserOrderPay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserOrderPayDevVo extends UserOrderPay {
    private Integer hasDevCount;
}
