package com.turing.code.tenant.pojo.vo;

import com.turing.code.user.pojo.UserOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserOrderVo extends UserOrder {
    private String userName;
    private BigDecimal collection;
    private BigDecimal unCollection;
}
