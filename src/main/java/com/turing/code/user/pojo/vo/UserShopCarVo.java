package com.turing.code.user.pojo.vo;

import com.turing.code.user.pojo.UserShopCar;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserShopCarVo extends UserShopCar {
    private String proCover;
    private String skuName;
    private BigDecimal price;
    private String proName;
    private BigDecimal vipPrice;
}
