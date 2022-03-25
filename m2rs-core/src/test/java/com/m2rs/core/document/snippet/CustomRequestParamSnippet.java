package com.m2rs.core.document.snippet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.request.AbstractParametersSnippet;
import org.springframework.restdocs.request.ParameterDescriptor;

public class CustomRequestParamSnippet extends AbstractParametersSnippet {

  public CustomRequestParamSnippet(
      String snippetName,
      List<ParameterDescriptor> descriptors,
      Map<String, Object> attributes,
      boolean ignoreUndocumentedParameters) {
    super(snippetName + "-fields", descriptors, attributes, ignoreUndocumentedParameters);
  }

  @Override
  protected Set<String> extractActualParameters(Operation operation) {
    return operation.getAttributes().keySet();
  }

  @Override
  protected void verificationFailed(Set<String> set, Set<String> set1) {}
}
