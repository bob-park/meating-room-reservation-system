package com.m2rs.core.document.generator;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;

public interface DocumentParamTypeGenerator {

  String KEY = "paramType";

  static Attributes.Attribute generateType(JsonFieldType type) {
    return Attributes.key(KEY).value(type.toString());
  }
}
