package com.m2rs.userservice.controller.controller.department;

import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static com.m2rs.core.document.utils.SnippetUtils.customPathParamFields;
import static com.m2rs.core.document.utils.SnippetUtils.customResponseFields;
import static com.m2rs.core.document.utils.SnippetUtils.getDefaultHeaders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.commons.fields.department.DepartmentResponseField;
import com.m2rs.userservice.commons.security.annotation.WithMockCustomUser;
import com.m2rs.userservice.controller.CommonControllerTest;
import com.m2rs.userservice.model.api.department.CreateDepartmentRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.repository.company.CompanyRepository;
import com.m2rs.userservice.repository.department.DepartmentRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

class DepartmentControllerTest extends CommonControllerTest {

    private static final String DEPARTMENT_API = "/company/{comId}/department";

    @MockBean
    CompanyRepository companyRepository;

    @MockBean
    DepartmentRepository departmentRepository;

    @BeforeEach
    void setup() {
        Company mockCompany = Company.builder()
            .id(1L)
            .name("test-company")
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

        Department mockDepartment = Department.builder()
            .id(1L)
            .name("test-department")
            .cratedDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

        mockDepartment.setCompany(mockCompany);

        /*
          mock companyRepository
         */
        // mock companyRepository findById()
        when(companyRepository.findById(any())).thenReturn(Optional.of(mockCompany));

        /*
          mock departmentRepository
         */
        // mock departmentRepository search()
        when(departmentRepository.search(any()))
            .thenReturn(Collections.singletonList(mockDepartment));
        // mock departmentRepository findById()
        when(departmentRepository.findById(any())).thenReturn(Optional.of(mockDepartment));
        // mock departmentRepository save()
        when(departmentRepository.save(any())).thenReturn(mockDepartment);
    }

    @WithMockCustomUser(comId = 1, email = "manager@manager.com", roleType = RoleType.ROLE_MANAGER)
    @ParameterizedTest
    @ValueSource(longs = 1L)
    @DisplayName("create department test")
    void createDepartmentTest(long comId) throws Exception {

        CreateDepartmentRequest createRequest = new CreateDepartmentRequest();

        createRequest.setName("test-department");

        mockMvc.perform(post(DEPARTMENT_API, comId)
                .headers(getDefaultHeaders())
                .content(toJson(createRequest)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                    .description("회사 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customResponseFields(DepartmentResponseField.DEPARTMENT_RESPONSE)
            ));

    }

}
