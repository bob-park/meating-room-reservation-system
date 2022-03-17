package com.m2rs.meetingroomservice.feign.error;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.commons.model.api.response.ApiResult;
import feign.Response;
import feign.Response.Body;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {

        String errorMsg = null;

        try {
            Body body = response.body();

            ApiResult<?> apiResult = objectMapper.readValue(body.asInputStream(), ApiResult.class);

            if (isNotEmpty(apiResult.getError())) {
                errorMsg = apiResult.getError().getMessage();
            }

        } catch (IOException e) {
            log.warn("Response parse error - {}", e.getMessage());
        }

        switch (response.status()) {
            case 400:
                break;

            case 404:
                return new NotFoundException(errorMsg);

            default:
                return new Exception(response.reason());
        }

        return null;
    }
}
