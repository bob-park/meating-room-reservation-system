package com.m2rs.userservice.controller.controller.admin;

import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static com.m2rs.core.document.utils.SnippetUtils.customPathParamFields;
import static com.m2rs.core.document.utils.SnippetUtils.customRequestFields;
import static com.m2rs.core.document.utils.SnippetUtils.customRequestParam;
import static com.m2rs.core.document.utils.SnippetUtils.customResponseFields;
import static com.m2rs.core.document.utils.SnippetUtils.getDefaultHeaders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.commons.fields.common.CommonPageableRequestParamField;
import com.m2rs.userservice.commons.fields.company.CompanyResponseField;
import com.m2rs.userservice.commons.security.annotation.WithMockCustomUser;
import com.m2rs.userservice.controller.CommonControllerTest;
import com.m2rs.userservice.model.api.company.CreateCompanyRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.repository.company.CompanyRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

class AdminCompanyControllerTest extends CommonControllerTest {

    private static final String ADMIN_COMPANY_API = "/admin/company";

    @MockBean
    CompanyRepository companyRepository;

    @BeforeEach
    void setup() {
        Company mockCompany = Company.builder()
            .id(1L)
            .name("test-company")
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

        Page<Company> mockCompanies = new PageImpl<>(Collections.singletonList(mockCompany));

        when(companyRepository.search(any(), any())).thenReturn(mockCompanies);
        when(companyRepository.findById(any())).thenReturn(Optional.of(mockCompany));
        when(companyRepository.save(any())).thenReturn(mockCompany);
    }

    @WithMockCustomUser(email = "admin@admin.com", roleType = RoleType.ROLE_ADMIN)
    @Test
    @DisplayName("search company test")
    void searchCompanyTest() throws Exception {

        List<ParameterDescriptor> requestParamFields = new ArrayList<>();

        requestParamFields.add(parameterWithName("id")
            .description("회사 아이디")
            .attributes(generateType(JsonFieldType.NUMBER))
            .optional());

        requestParamFields.add(parameterWithName("name")
            .description("회사 이름")
            .attributes(generateType(JsonFieldType.STRING))
            .optional());

        requestParamFields.addAll(Arrays.asList(CommonPageableRequestParamField.PAGEABLE_FIELDS));

        mockMvc.perform(get(ADMIN_COMPANY_API + "/list"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customRequestParam(requestParamFields.toArray(new ParameterDescriptor[]{})),
                customResponseFields(CompanyResponseField.COMPANY_RESPONSE)
            ));
    }

    @WithMockCustomUser(email = "admin@admin.com", roleType = RoleType.ROLE_ADMIN)
    @Test
    @DisplayName("create company test")
    void createCompanyTest() throws Exception {

        CreateCompanyRequest createRequest = new CreateCompanyRequest();
        createRequest.setName("test-company");

        mockMvc.perform(post(ADMIN_COMPANY_API)
                .headers(getDefaultHeaders())
                .content(toJson(createRequest)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document.document(customRequestFields(fieldWithPath("name")
                    .type(JsonFieldType.STRING)
                    .description("회사 이름")),
                customResponseFields(CompanyResponseField.COMPANY_RESPONSE)));
    }


    @WithMockCustomUser(email = "admin@admin.com", roleType = RoleType.ROLE_ADMIN)
    @ParameterizedTest
    @ValueSource(longs = 1L)
    @DisplayName("remove company test")
    void removeCompanyTest(long comId) throws Exception {

        mockMvc.perform(delete(ADMIN_COMPANY_API + "/{comId}", comId))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document.document(customPathParamFields(parameterWithName("comId")
                .description("회사 아이디")
                .attributes(generateType(JsonFieldType.NUMBER)))));

    }

}
