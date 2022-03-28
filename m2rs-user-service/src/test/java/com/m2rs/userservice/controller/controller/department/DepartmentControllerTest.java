package com.m2rs.userservice.controller.controller.department;

import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static com.m2rs.core.document.utils.SnippetUtils.customPathParamFields;
import static com.m2rs.core.document.utils.SnippetUtils.customRequestParamFields;
import static com.m2rs.core.document.utils.SnippetUtils.customResponseBodyFields;
import static com.m2rs.core.document.utils.SnippetUtils.getDefaultHeaders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.commons.fields.department.DepartmentRequestParamField;
import com.m2rs.userservice.commons.fields.department.DepartmentResponseField;
import com.m2rs.userservice.commons.security.annotation.WithMockCustomUser;
import com.m2rs.userservice.controller.CommonControllerTest;
import com.m2rs.userservice.model.api.department.CreateDepartmentRequest;
import com.m2rs.userservice.model.api.department.ModifyDepartmentRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.repository.company.CompanyRepository;
import com.m2rs.userservice.repository.department.DepartmentRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
        // findById()
        when(companyRepository.findById(any())).thenReturn(Optional.of(mockCompany));

        /*
          mock departmentRepository
         */
        // search()
        when(departmentRepository.search(any()))
            .thenReturn(Collections.singletonList(mockDepartment));
        // findById()
        when(departmentRepository.findById(any())).thenReturn(Optional.of(mockDepartment));
        //  getDepartment()
        when(departmentRepository.getDepartment(any(), any())).thenReturn(
            Optional.of(mockDepartment));
        // save()
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
                customResponseBodyFields(DepartmentResponseField.DEPARTMENT_RESPONSE)
            ));

    }

    @WithMockCustomUser(comId = 1, email = "manager@manager.com", roleType = RoleType.ROLE_MANAGER)
    @ParameterizedTest
    @MethodSource("mockDepartmentTestPathVariables")
    @DisplayName("get department test")
    void getDepartmentTest(long comId, long departmentId) throws Exception {

        mockMvc.perform(get(DEPARTMENT_API + "/{departmentId}", comId, departmentId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                        .description("회사 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER)),
                    parameterWithName("departmentId")
                        .description("부서 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER))),
                customResponseBodyFields(DepartmentResponseField.DEPARTMENT_RESPONSE)));
    }

    @WithMockCustomUser(comId = 1, email = "manager@manager.com", roleType = RoleType.ROLE_MANAGER)
    @ParameterizedTest
    @MethodSource("mockDepartmentTestPathVariables")
    @DisplayName("modify department test")
    void modifyDepartmentTest(long comId, long departmentId) throws Exception {

        ModifyDepartmentRequest modifyRequest = new ModifyDepartmentRequest();

        modifyRequest.setName("test-department");

        mockMvc.perform(put(DEPARTMENT_API + "/{departmentId}", comId, departmentId)
                .headers(getDefaultHeaders())
                .content(toJson(modifyRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                        .description("회사 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER)),
                    parameterWithName("departmentId")
                        .description("부서 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER))),
                customResponseBodyFields(DepartmentResponseField.DEPARTMENT_RESPONSE)));
    }

    @WithMockCustomUser(comId = 1, email = "manager@manager.com", roleType = RoleType.ROLE_MANAGER)
    @ParameterizedTest
    @MethodSource("mockDepartmentTestPathVariables")
    @DisplayName("remove department")
    void removeDepartmentTest(long comId, long departmentId) throws Exception {

        mockMvc.perform(delete(DEPARTMENT_API + "/{departmentId}", comId, departmentId))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                        .description("회사 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER)),
                    parameterWithName("departmentId")
                        .description("부서 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER))
                )));

    }

    @WithMockCustomUser(comId = 1, email = "manager@manager.com", roleType = RoleType.ROLE_MANAGER)
    @ParameterizedTest
    @ValueSource(longs = 1)
    @DisplayName("get department list")
    void getListTest(long comId) throws Exception {

        mockMvc.perform(get(DEPARTMENT_API + "/list", comId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(
                    parameterWithName("comId")
                        .description("회사 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER))),
                customRequestParamFields(DepartmentRequestParamField.GET_DEPARTMENT_LIST),
                customResponseBodyFields(DepartmentResponseField.DEPARTMENT_RESPONSE)));

    }

    private static Stream<Arguments> mockDepartmentTestPathVariables() {
        return Stream.of(
            Arguments.of(1, 1)
        );
    }

}
