package com.m2rs.userservice.controller;

import static com.m2rs.core.document.utils.SnippetUtils.commonResponseBodyFields;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class CommonControllerTest {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected MockMvc mockMvc;

    protected RestDocumentationResultHandler document;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    @Qualifier("getRestLoginProcessingFilter")
    private Filter securityFilterChain;

    @BeforeEach
    void setup(WebApplicationContext context,
        RestDocumentationContextProvider restDocumentationExtension) throws Exception {

        this.document =
            document(
                "{class-name}/{method-name}",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()));

        this.mockMvc =
            MockMvcBuilders.webAppContextSetup(context)
                .apply(
                    documentationConfiguration(restDocumentationExtension)
                        .uris())
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .addFilter(securityFilterChain)
                .alwaysDo(document)
                .build();

        createCommonsResponseFields();
    }

    private void createCommonsResponseFields() throws Exception {
        this.mockMvc
            .perform(get("/docs").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentation.document(
                    "commons-response",
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    commonResponseBodyFields(
                        null,
                        subsectionWithPath("result")
                            .description("결과")
                            .type(JsonFieldType.VARIES),
                        fieldWithPath("success")
                            .description("성공 여부")
                            .type(JsonFieldType.BOOLEAN),
                        // pagination
                        subsectionWithPath("page.size")
                            .description("페이지 당 표시 항목 수")
                            .type(JsonFieldType.NUMBER)
                            .optional(),
                        subsectionWithPath("page.page")
                            .description("현재 페이지")
                            .type(JsonFieldType.NUMBER)
                            .optional(),
                        subsectionWithPath("page.totalCount")
                            .description("전체 개수")
                            .type(JsonFieldType.NUMBER)
                            .optional(),
                        subsectionWithPath("page.firstPage")
                            .description("첫 페이지 번호")
                            .type(JsonFieldType.NUMBER)
                            .optional(),
                        subsectionWithPath("page.lastPage")
                            .description("마지막 페이지 번호")
                            .type(JsonFieldType.NUMBER)
                            .optional(),
                        // error
                        subsectionWithPath("error.message")
                            .description("에러 메세지")
                            .type(JsonFieldType.STRING)
                            .optional()
                    )));
    }

    protected String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}

