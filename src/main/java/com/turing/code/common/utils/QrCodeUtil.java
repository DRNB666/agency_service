package com.turing.code.common.utils;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import com.github.binarywang.utils.qrcode.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.turing.code.common.utils.encrypt.RSAUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class QrCodeUtil {

    @Resource
    private WxMaService wxMaService;

    private static WxMaService staticWxMaService;

    @PostConstruct
    public void init() {
        staticWxMaService = wxMaService;
    }


    /**
     * 普通二维码
     *
     * @param params 二维码携带的参数
     * @param name   文件名称
     */
    public static String createParamsQrCode(String params,String name) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(RSAUtil.encryptByPublicKey(params), BarcodeFormat.QR_CODE, 500, 500);

        String filePath = name + ".png";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        MatrixToImageWriter.writeToFile(bitMatrix, "png", file);
//        String qrCode = FileUtil.QnUploadFile(file, "QrCode");
        file.delete();
        return "";
    }

    /**
     * 获取微信小程序二维码，响应给客户端
     *
     * @param params   二维码携带的参数,为null则无参数
     * @param pagePath 扫码二维码需要跳转的微信页面
     */
    public static void getQrCode(HttpServletResponse response, String params, String pagePath) throws WxErrorException, IOException {
        // 获取小程序二维码生成实例
        WxMaQrcodeService wxMaQrcodeService = staticWxMaService.getQrcodeService();
        // 设置小程序二维码线条颜色为黑色
        WxMaCodeLineColor lineColor = new WxMaCodeLineColor("0", "0", "0");
        byte[] qrCodeBytes=null;
        if (CommUtil.checkNull(params)) {
            qrCodeBytes = wxMaQrcodeService.createWxaCodeUnlimitBytes(params,pagePath, false,"trial",430, true, null, true);
        } else {
            //该方式生成二维码总共只有10w，仅供测试调用
//            qrCodeBytes = wxMaQrcodeService.createWxaCodeBytes(pagePath, 430, false, lineColor, false);
        }
        // 设置contentType
        response.setContentType("image/png");
        // 写入response的输出流中
        OutputStream stream = response.getOutputStream();
        stream.write(qrCodeBytes);
        stream.flush();
        stream.close();
    }

    /**
     * 获取微信小程序二维码并保存到本地
     *
     * @param params   二维码携带的参数,为null则无参数
     * @param pagePath 扫码二维码需要跳转的微信页面
     */
    public static String getQrCodeSave(String params, String pagePath, HttpServletRequest request) throws WxErrorException, IOException,
            IOException {
        // 获取小程序二维码生成实例
        WxMaQrcodeService wxMaQrcodeService = staticWxMaService.getQrcodeService();
        // 设置小程序二维码线条颜色为黑色
//        WxMaCodeLineColor lineColor = new WxMaCodeLineColor("254", "237", "150");
        File wxaCode=null;
        if (CommUtil.checkNull(params)) {
            wxaCode = wxMaQrcodeService.createWxaCodeUnlimit(params,pagePath, "",false,"trial",430, true, null, true);
        } else {
            //该方式生成二维码总共只有10w，仅供测试调用
//            wxaCode = wxMaQrcodeService.createWxaCode(pagePath, 430, false, lineColor, false);
        }
        return FileUtil.upload("/test/", wxaCode,"image",request);
    }

    public static void main(String[] args) throws Exception {

    }
}