package com.soybean.uaa.controller.common;

import com.soybean.framework.security.client.annotation.IgnoreAuthorize;
import com.soybean.uaa.service.VerificationService;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 验证码
 *
 * @author wenxina
 * @date 2022/07/12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    private final VerificationService verificationService;

    /**
     * 创建验证码
     *
     * @param key      关键
     * @param response 响应
     */
    @SneakyThrows
    @IgnoreAuthorize
    @GetMapping(produces = "image/png")
    public void create(@RequestParam(value = "key") String key, HttpServletResponse response) {
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
        final Captcha captcha = verificationService.create(key);
        captcha.out(response.getOutputStream());
    }


}
