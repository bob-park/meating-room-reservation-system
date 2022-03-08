package com.m2rs.core.document.snippet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.request.AbstractParametersSnippet;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.util.Assert;

public class CustomPathParamFieldSnippet extends AbstractParametersSnippet {

  public CustomPathParamFieldSnippet(
      String snippetName,
      List<ParameterDescriptor> descriptors,
      Map<String, Object> attributes,
      boolean ignoreUndocumentedParameters) {
    super(snippetName + "-fields", descriptors, attributes, ignoreUndocumentedParameters);
  }

  @Override
  protected Map<String, Object> createModel(Operation operation) {
    Map<String, Object> model = super.createModel(operation);
    model.put("path", this.removeQueryStringIfPresent(this.extractUrlTemplate(operation)));
    return model;
  }

  @Override
  protected Set<String> extractActualParameters(Operation operation) {
    return operation.getAttributes().keySet();
  }

  @Override
  protected void verificationFailed(Set<String> set, Set<String> set1) {}

  private String extractUrlTemplate(Operation operation) {
    String urlTemplate =
        (String) operation.getAttributes().get("org.springframework.restdocs.urlTemplate");
    Assert.notNull(
        urlTemplate,
        "urlTemplate not found. If you are using MockMvc did you use RestDocumentationRequestBuilders to build the request?");
    return urlTemplate;
  }

  private String removeQueryStringIfPresent(String urlTemplate) {
    int index = urlTemplate.indexOf(63);
    return index == -1 ? urlTemplate : urlTemplate.substring(0, index);
  }
}
