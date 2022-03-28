package com.m2rs.core.document.utils;

import com.m2rs.core.document.snippet.CustomCodeFieldSnippet;
import com.m2rs.core.document.snippet.CustomRequestParamSnippet;
import java.util.Arrays;
import java.util.Collections;

import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.restdocs.request.ParameterDescriptor;

import com.m2rs.core.document.snippet.CustomPathParamFieldSnippet;
import com.m2rs.core.document.snippet.CustomRequestFieldSnippet;
import com.m2rs.core.document.snippet.CustomResponseFieldsSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;

public interface SnippetUtils {

    static CustomCodeFieldSnippet customCodeFields(
        PayloadSubsectionExtractor<?> subsectionExtractor,
        Map<String, Object> attributes,
        FieldDescriptor... descriptors) {
        return new CustomCodeFieldSnippet(
            "custom-code", subsectionExtractor, Arrays.asList(descriptors), attributes, true);
    }

    static CustomRequestParamSnippet customRequestParamFields(
        ParameterDescriptor... descriptors) {
        return new CustomRequestParamSnippet(
            "custom-request-param", Arrays.asList(descriptors), null, false);
    }

    static CustomRequestFieldSnippet commonRequestBodyFields(
        PayloadSubsectionExtractor<?> extractor, FieldDescriptor... descriptors) {
        return new CustomRequestFieldSnippet(
            "custom-request", extractor, Arrays.asList(descriptors), null, true);
    }

    static CustomResponseFieldsSnippet commonResponseBodyFields(
        PayloadSubsectionExtractor<?> extractor, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(
            "custom-response", extractor, Arrays.asList(descriptors), null, true);
    }

    static CustomPathParamFieldSnippet customPathParamFields(
        ParameterDescriptor... descriptors) {

        return new CustomPathParamFieldSnippet(
            "custom-path-param", Arrays.asList(descriptors), null, false);
    }

    static CustomRequestFieldSnippet customRequestBodyFields(FieldDescriptor... descriptors) {
        return commonRequestBodyFields(null, descriptors);
    }

    static CustomResponseFieldsSnippet customResponseBodyFields(
        FieldDescriptor... descriptors) {

        return commonResponseBodyFields(beneathPath("result").withSubsectionId("result"), descriptors);
    }

    static HttpHeaders getDefaultHeaders() {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }

}
