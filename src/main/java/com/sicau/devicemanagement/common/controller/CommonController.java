package com.sicau.devicemanagement.common.controller;

import com.google.code.kaptcha.Producer;
import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.redis.RedisCache;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.xin
 * @date 2022/1/30 20:50
 * @Description
 */
@RestController()
public class CommonController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Autowired
    private RedisCache redisCache;

    @GetMapping("/captchaImage")
    public AjaxResult getCaptchaCode(HttpServletResponse response){
        AjaxResult ajax = AjaxResult.success();
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY+ uuid;
        String capStr = null, code = null;
        BufferedImage image = null;

        //生成验证码
        String capText = captchaProducerMath.createText();
        capStr = capText.substring(0,capText.lastIndexOf("@"));
        code = capText.substring(capText.lastIndexOf("@")+1);
        image = captchaProducer.createImage(capStr);

        //缓存
        redisCache.setCacheObject(verifyKey,code,Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try{
            ImageIO.write(image,"jpg",os);
        }catch (IOException e){
            return AjaxResult.error(e.getMessage());
        }
        ajax.put("uuid",uuid);
        ajax.put("img", new BASE64Encoder().encode(os.toByteArray()));
        return ajax;
    }
}
