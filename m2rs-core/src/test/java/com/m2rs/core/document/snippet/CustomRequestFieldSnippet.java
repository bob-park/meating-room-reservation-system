package com.m2rs.core.document.snippet;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

public class CustomRequestFieldSnippet extends AbstractFieldsSnippet {

  public CustomRequestFieldSnippet(
      String type,
      PayloadSubsectionExtractor<?> subsectionExtractor,
      List<FieldDescriptor> descriptors,
      Map<String, Object> attributes,
      boolean ignoreUndocumentedFields) {
    super(type, descriptors, attributes, ignoreUndocumentedFields, subsectionExtractor);
  }

  @Override
  protected MediaType getContentType(Operation operation) {
    return operation.getRequest().getHeaders().getContentType();
  }

  @Override
  protected byte[] getContent(Operation operation) {
    return operation.getRequest().getContent();
  }
}
