package com.leepsmart.code.common.controller;

import com.leepsmart.code.common.annotation.limiter.RequestLimiter;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.config.KeyConfig;
import com.leepsmart.code.common.liebao.service.LieBaoService;
import com.leepsmart.code.common.redis.utils.RedisUtil;
import com.leepsmart.code.common.security.config.SecurityConfig;
import com.leepsmart.code.common.security.jwt.JwtTokenUtil;
import com.leepsmart.code.common.swagger.SwaggerConfig;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.FileUtil;
import com.leepsmart.code.common.utils.QrCodeUtil;
import com.leepsmart.code.common.utils.encrypt.RSAUtil;
import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.user.mapper.UserInfoMapper;
import com.leepsmart.code.user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Swagger;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author leepsmart
 */
@RestController
@RequestMapping(value = {"common"}, produces = "text/plain;charset=utf-8")
@Api(tags = "通用-公共模块")
public class CommonController {
    @Value("${spring.profiles.active}")
    private String env;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JavaMailSender emailSender;
    @Resource
    private LieBaoService lieBaoService;
    @Resource
    private RedisUtil redisUtil;


    @ApiOperation("图片上传")
    @PostMapping({"us/upload/image"})
    @ParameterVerify(notNull = "file")
    public String upload(MultipartFile file, HttpServletRequest request) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String path = FileUtil.upload("/article/" + format.format(new Date()) + "/", file, "image", request);
        return ReturnBody.success(path);
    }


    @ApiOperation("获取token")
    @PostMapping("public/getToken")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "1用户 2管理员 3租户", required = true),
            @ApiImplicitParam(name = "account", value = "账号", required = true),
    })
    public String getToken(Integer type, String account) {
//        if (!"dev".equals(env)) {
//            return ReturnBody.success();
//        }
        UsernamePasswordAuthenticationToken upToken;
        Authentication authentication;
        UserDetails userDetails;
        switch (type) {
            case 1:
                if (!CommUtil.checkNull(account)) {
                    account = "o_PUA5Anlvy7T-4exNVH185dGtuc1";
                }
                upToken = new UsernamePasswordAuthenticationToken(account + SecurityConfig.ROLE_USER,
                        "123456" + KeyConfig.KEY_PWD);
                authentication = authenticationManager.authenticate(upToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                userDetails = userDetailsService.loadUserByUsername(account + SecurityConfig.ROLE_USER);
                return "Bearer_" + JwtTokenUtil.generateToken(userDetails);
            case 2:
                if (!CommUtil.checkNull(account)) {
                    account = "admin";
                }
                upToken = new UsernamePasswordAuthenticationToken(account + SecurityConfig.ROLE_ADMIN,
                        "123123" + KeyConfig.KEY_PWD);
                authentication = authenticationManager.authenticate(upToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                userDetails = userDetailsService.loadUserByUsername(account + SecurityConfig.ROLE_ADMIN);
                return "Bearer_" + JwtTokenUtil.generateToken(userDetails);
            case 3:
                if (!CommUtil.checkNull(account)) {
                    account = "admin";
                }
                upToken = new UsernamePasswordAuthenticationToken(account + SecurityConfig.ROLE_TENANT,
                        "123123" + KeyConfig.KEY_PWD);
                authentication = authenticationManager.authenticate(upToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                userDetails = userDetailsService.loadUserByUsername(account + SecurityConfig.ROLE_TENANT);
                return "Bearer_" + JwtTokenUtil.generateToken(userDetails);
            default:
                return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
    }

    @ApiOperation("生成小程序二维码(一次性)")
    @PostMapping("public/generatedQrCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "多个参数用,号分割", required = true),
            @ApiImplicitParam(name = "pages", value = "小程序跳转页面", required = true),
    })
    @ParameterVerify(notNull = {"params", "pages"})
    public void generatedQrCode(String params, String pages, HttpServletRequest request, HttpServletResponse response) throws WxErrorException,
            IOException {
        QrCodeUtil.getQrCode(response, params, pages);
    }

    @ApiOperation("发送邮件")
    @PostMapping("public/emailRegister")
    @RequestLimiter(qps = 1D)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true),
            @ApiImplicitParam(name = "type", value = "1用户账号 2后台账号", required = true),
    })
    @ParameterVerify(notNull = {"email", "type"})
    public String emailRegister(String email, Integer type) throws Exception {
        email = RSAUtil.decryptByPrivateKey(email);
        String code = CommUtil.createRandom(false, 6);
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom("business@leepsmart.com");//发送者
        smm.setTo(email);//收件人
        smm.setSubject("【LEEPSMART.AGENCY】请查收您的验证码，有效期为10分钟");//邮件主题
        smm.setText(code);//邮件内容
        emailSender.send(smm);//发送邮件
        redisUtil.set(String.format("%s%s", type == 1 ? "USER_" : "ADMIN_", email), code, 60 * 10);
        return ReturnBody.success();
    }


}
