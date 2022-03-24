package com.m2rs.userservice.controller.controller.company;

import java.time.LocalDateTime;
import java.util.Collections;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.m2rs.userservice.commons.fields.company.CompanyResponseField;
import com.m2rs.userservice.controller.CommonController;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.repository.company.CompanyRepository;
import org.springframework.restdocs.request.ParameterDescriptor;

import static com.m2rs.core.document.utils.SnippetUtils.customPathParamFields;
import static com.m2rs.core.document.utils.SnippetUtils.customResponseFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompanyControllerTest extends CommonController {

    private static final String COMPANY_API = "/company";

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
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    @DisplayName("get-company")
    void getCompanyTest(long comId) throws Exception {
        mockMvc.perform(get(COMPANY_API + "/{comId}", comId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(customPathParamFields(),
                customResponseFields(CompanyResponseField.COMPANY_RESPONSE)));
    }

}
