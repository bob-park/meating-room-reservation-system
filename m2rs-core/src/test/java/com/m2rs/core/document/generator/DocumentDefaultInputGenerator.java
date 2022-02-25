package com.m2rs.core.document.generator;

import org.springframework.restdocs.snippet.Attributes;

public interface DocumentDefaultInputGenerator {

  String KEY = "defaultInput";

  static Attributes.Attribute generate(String defaultInput) {
    return Attributes.key(KEY).value(defaultInput);
  }
}
