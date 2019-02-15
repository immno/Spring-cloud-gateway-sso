package com.github.mno.auth.exts;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.util.Optional;

/**
 * 自定义异常信息需要用到这个类
 *
 * @author mno
 * @date 2019-2-2 16:09:08
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
class CustomOauthException extends OAuth2Exception {

    private static final long serialVersionUID = -7928646565357139486L;

    private ResponseResultDTO resultDTO;

    CustomOauthException(ResponseResultDTO resultDTO) {
        super(Optional.ofNullable(resultDTO).map(ResponseResultDTO::getResultMsg).orElse(""));
        this.resultDTO = resultDTO;
    }

    public ResponseResultDTO getResultDTO() {
        return resultDTO;
    }
}
