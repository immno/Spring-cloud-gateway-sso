package com.github.mno.auth.exts;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * oauth2
 *
 * @author mno
 * @date 2019/2/2 15:51
 * @see org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator
 */
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {

        if (e instanceof OAuth2Exception) {
            OAuth2Exception exception = (OAuth2Exception) e;
            ResponseResultDTO dto = ResponseResultDTO.failure(RequestIdUtil.get(), "1002", exception.getMessage(), null);
            return ResponseEntity.status(exception.getHttpErrorCode()).body(new CustomOauthException(dto));
        }
        ResponseResultDTO dto = ResponseResultDTO.failure(RequestIdUtil.get(), "1002", e.getMessage(), null);
        return ResponseEntity.ok(new CustomOauthException(dto));
    }

}
