package com.m2rs.userservice.commons.fields.common;

import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import com.m2rs.core.document.generator.DocumentDefaultInputGenerator;
import com.m2rs.core.document.generator.DocumentFormatGenerator;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

public class CommonPageableRequestParamField {

    public static final ParameterDescriptor[] PAGEABLE_FIELDS = {
        parameterWithName("page")
            .description("page index")
            .attributes(generateType(JsonFieldType.NUMBER),
                DocumentDefaultInputGenerator.generate("0"))
            .optional(),
        parameterWithName("size")
            .description("page 당 표시 항목 수")
            .attributes(generateType(JsonFieldType.NUMBER),
                DocumentDefaultInputGenerator.generate("20"))
            .optional(),
        parameterWithName("sort")
            .description("정렬")
            .attributes(generateType(JsonFieldType.STRING),
                DocumentFormatGenerator.generate("[order],[asc]"),
                DocumentDefaultInputGenerator.generate("created_date,desc"))
            .optional(),
    };

}
