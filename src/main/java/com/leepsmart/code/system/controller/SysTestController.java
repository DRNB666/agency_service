package com.leepsmart.code.system.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.leepsmart.code.common.liebao.service.LieBaoService;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ApiIgnore
@Api(tags = "系统-测试")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "system/public", produces = "text/plain;charset=utf-8")
public class SysTestController {

    @Resource
    private LieBaoService lieBaoService;
    
    @ApiOperation("测试")
    @PostMapping("test")
    public void test(HttpServletResponse response) throws IOException {
        QrCodeUtil.generate("https://url/path", 300, 300, "png", response.getOutputStream());
    }


}
