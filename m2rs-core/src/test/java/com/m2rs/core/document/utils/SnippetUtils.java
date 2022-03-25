package com.m2rs.core.document.utils;

import java.util.Arrays;
import java.util.Collections;

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

    static CustomRequestFieldSnippet commonRequestFields(
        PayloadSubsectionExtractor<?> extractor, FieldDescriptor... descriptors) {
        return new CustomRequestFieldSnippet(
            "custom-request", extractor, Arrays.asList(descriptors), null, true);
    }

    static CustomResponseFieldsSnippet commonResponseFields(
        PayloadSubsectionExtractor<?> extractor, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(
            "custom-response", extractor, Arrays.asList(descriptors), null, true);
    }

    static CustomPathParamFieldSnippet customPathParamFields(
        ParameterDescriptor... descriptors) {

        return new CustomPathParamFieldSnippet(
            "custom-path-param", Arrays.asList(descriptors), null, false);
    }

    static CustomRequestFieldSnippet customRequestFields(FieldDescriptor... descriptors) {
        return commonRequestFields(null, descriptors);
    }

    static CustomResponseFieldsSnippet customResponseFields(
        FieldDescriptor... descriptors) {

        return commonResponseFields(beneathPath("result").withSubsectionId("result"), descriptors);
    }

    static HttpHeaders getDefaultHeaders() {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }

}
