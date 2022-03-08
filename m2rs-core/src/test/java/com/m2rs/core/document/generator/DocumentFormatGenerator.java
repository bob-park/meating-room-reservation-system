package com.m2rs.core.document.generator;

import org.springframework.restdocs.snippet.Attributes;

public interface DocumentFormatGenerator {

  String KEY = "format";

  static Attributes.Attribute generate(Object... inputs) {

    StringBuilder builder = new StringBuilder();

    for (Object input : inputs) {
      builder.append(input).append(" / ");
    }

    builder.deleteCharAt(builder.lastIndexOf("/"));

    return Attributes.key(KEY).value(builder.toString());
  }
}
