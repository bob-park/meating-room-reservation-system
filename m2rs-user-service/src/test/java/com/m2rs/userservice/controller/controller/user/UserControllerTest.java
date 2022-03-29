package com.m2rs.userservice.controller.controller.user;

import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static com.m2rs.core.document.utils.SnippetUtils.customPathParamFields;
import static com.m2rs.core.document.utils.SnippetUtils.customRequestBodyFields;
import static com.m2rs.core.document.utils.SnippetUtils.customRequestParamFields;
import static com.m2rs.core.document.utils.SnippetUtils.customResponseBodyFields;
import static com.m2rs.core.document.utils.SnippetUtils.getDefaultHeaders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.commons.fields.user.UserRequestBodyFields;
import com.m2rs.userservice.commons.fields.user.UserRequestParamField;
import com.m2rs.userservice.commons.fields.user.UserResponseBodyFields;
import com.m2rs.userservice.commons.security.annotation.WithMockCustomUser;
import com.m2rs.userservice.controller.CommonControllerTest;
import com.m2rs.userservice.model.api.user.CreateUserRequest;
import com.m2rs.userservice.model.api.user.ModifyUserRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.model.entity.Role;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.model.entity.UserRoles;
import com.m2rs.userservice.repository.company.CompanyRepository;
import com.m2rs.userservice.repository.department.DepartmentRepository;
import com.m2rs.userservice.repository.user.UserRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserControllerTest extends CommonControllerTest {

    private static final String USER_API = "/company/{comId}/user";

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    CompanyRepository companyRepository;

    @MockBean
    DepartmentRepository departmentRepository;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        // mock company
        Company mockCompany = Company.builder()
            .id(1L)
            .name("test-company")
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

        // mock department
        Department mockDepartment = Department.builder()
            .id(1L)
            .name("test-department")
            .cratedDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

        mockDepartment.setCompany(mockCompany);

        // mock user
        User mockUser = User.builder()
            .id(1L)
            .name("test-user")
            .email("test@test.com")
            .password(passwordEncoder.encode("12345"))
            .build();

        // mock role
        Role managerRole = Role.builder()
            .id(1L)
            .rolesName(RoleType.ROLE_MANAGER)
            .rolesDescription("MANAGER")
            .build();

        Role userRole = Role.builder()
            .id(2L)
            .rolesName(RoleType.ROLE_USER)
            .rolesDescription("USER")
            .build();

        userRole.setParent(managerRole);

        // mock user roles
        UserRoles mockUserRoles = UserRoles.builder()
            .id(1L)
            .role(userRole)
            .user(mockUser)
            .build();

        mockUser.setDepartment(mockDepartment);
        mockUser.setUserRoles(mockUserRoles);


        /*
         mock company repository
         */

        /*
         mock department repository
         */
        when(departmentRepository.findById(any())).thenReturn(Optional.of(mockDepartment));

        /*
         mock user repository
         */
        when(userRepository.findById(any())).thenReturn(Optional.of(mockUser));
        when(userRepository.getUser(any(), any())).thenReturn(Optional.of(mockUser));
        when(userRepository.search(any(), any()))
            .thenReturn(new PageImpl<>(Collections.singletonList(mockUser)));
        when(userRepository.save(any())).thenReturn(mockUser);
        when(userRepository.isExistEmail(any(), any())).thenReturn(false);

    }

    @WithMockCustomUser(comId = 1, email = "test@test.com")
    @ParameterizedTest
    @MethodSource("mockUserTestPathVariables")
    @DisplayName("get user test")
    void getUserTest(long comId, long userId) throws Exception {

        mockMvc.perform(get(USER_API + "/{id}", comId, userId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId").description("회사 아이디"),
                    parameterWithName("id").description("사용자 아이디")),
                customResponseBodyFields(UserResponseBodyFields.USER_RESPONSE)));
    }

    @WithMockCustomUser(comId = 1, email = "manager@manager.com", roleType = RoleType.ROLE_MANAGER)
    @ParameterizedTest
    @ValueSource(longs = 1)
    @DisplayName("get user list test")
    void getUserListTest(long comId) throws Exception {
        mockMvc.perform(get(USER_API + "/list", comId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                    .description("회사 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customRequestParamFields(UserRequestParamField.SEARCH_REQUEST_PARAMETERS),
                customResponseBodyFields(UserResponseBodyFields.USER_RESPONSE)
            ));
    }

    @WithMockCustomUser(comId = 1, email = "manager@manager.com", roleType = RoleType.ROLE_MANAGER)
    @ParameterizedTest
    @ValueSource(longs = 1L)
    @DisplayName("create user test")
    void createUserTest(long comId) throws Exception {

        CreateUserRequest createRequest =
            CreateUserRequest.builder()
                .departmentId(1L)
                .email("test@test.com")
                .password("12345")
                .name("test")
                .build();

        mockMvc.perform(post(USER_API, comId)
                .headers(getDefaultHeaders())
                .content(toJson(createRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                    .description("회사 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customRequestBodyFields(UserRequestBodyFields.CREATE_USER_REQUEST),
                customResponseBodyFields(UserResponseBodyFields.USER_RESPONSE)
            ));

    }

    @WithMockCustomUser(comId = 1, departmentId = 1, email = "user@user.com")
    @ParameterizedTest
    @MethodSource("mockUserTestPathVariables")
    @DisplayName("modify user test")
    void modifyUserTest(long comId, long userId) throws Exception {
        ModifyUserRequest modifyRequest =
            ModifyUserRequest.builder()
                .name("user")
                .password("12345")
                .build();

        mockMvc.perform(put(USER_API + "/{userId}", comId, userId)
                .headers(getDefaultHeaders())
                .content(toJson(modifyRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                        .description("회사 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER)),
                    parameterWithName("userId")
                        .description("사용자 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER))),
                customRequestBodyFields(UserRequestBodyFields.MODIFY_USER_REQUEST),
                customResponseBodyFields(UserResponseBodyFields.USER_RESPONSE)))
        ;
    }

    @WithMockCustomUser(comId = 1, email = "manager@manager.com", roleType = RoleType.ROLE_MANAGER)
    @ParameterizedTest
    @MethodSource("mockUserTestPathVariables")
    @DisplayName("remove user test")
    void removeUserTest(long comId, long userId) throws Exception {

        mockMvc.perform(delete(USER_API + "/{userId}", comId, userId))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                        .description("회사 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER)),
                    parameterWithName("userId")
                        .description("사용자 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER)))));

    }

    @WithMockCustomUser(comId = 1, email = "manager@manager.com", roleType = RoleType.ROLE_MANAGER)
    @ParameterizedTest
    @ValueSource(longs = 1L)
    @DisplayName("exist email test")
    void existEmailTest(long comId) throws Exception {
        mockMvc.perform(get(USER_API + "/exist/email", comId)
                .param("email", "user@user.com"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                    .description("회사 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customRequestParamFields(parameterWithName("email")
                    .description("사용자 이메일")
                    .attributes(generateType(JsonFieldType.STRING))),
                customResponseBodyFields(fieldWithPath("exist")
                    .description("존재 여부")
                    .type(JsonFieldType.BOOLEAN))));
    }

    private static Stream<Arguments> mockUserTestPathVariables() {
        return Stream.of(
            Arguments.of(1, 1)
        );
    }


}
