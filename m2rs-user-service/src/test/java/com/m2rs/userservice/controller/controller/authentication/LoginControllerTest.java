package com.m2rs.userservice.controller.controller.authentication;

import static com.m2rs.core.document.utils.SnippetUtils.customRequestBodyFields;
import static com.m2rs.core.document.utils.SnippetUtils.customResponseBodyFields;
import static com.m2rs.core.document.utils.SnippetUtils.getDefaultHeaders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.commons.fields.authentication.LoginRequestBodyFields;
import com.m2rs.userservice.commons.fields.authentication.LoginSuccessResponseBodyFields;
import com.m2rs.userservice.controller.CommonControllerTest;
import com.m2rs.userservice.model.api.user.UserLoginRequest;
import com.m2rs.userservice.model.entity.Role;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.model.entity.UserRoles;
import com.m2rs.userservice.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

class LoginControllerTest extends CommonControllerTest {

    private static final String LOGIN_API = "/login";

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setup() {

        // mock role
        Role mockRole =
            Role.builder()
                .id(1L)
                .rolesName(RoleType.ROLE_USER)
                .rolesDescription("USER")
                .build();

        // mock user
        User mockUser =
            User.builder()
                .id(1L)
                .email("user@user.com")
                .password(passwordEncoder.encode("12345"))
                .name("user")
                .build();

        // mock user roles
        UserRoles mockUserRoles =
            UserRoles.builder()
                .id(1L)
                .user(mockUser)
                .role(mockRole)
                .build();

        mockUser.setUserRoles(mockUserRoles);

        /*
         mock user repository
         */
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(mockUser));
    }

    @Test
    @DisplayName("login test")
    void loginTest() throws Exception {

        UserLoginRequest loginRequest = new UserLoginRequest();

        loginRequest.setEmail("user@user.com");
        loginRequest.setPassword("12345");

        mockMvc.perform(post(LOGIN_API)
                .headers(getDefaultHeaders())
                .content(toJson(loginRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customRequestBodyFields(LoginRequestBodyFields.LOGIN_REQUEST),
                customResponseBodyFields(LoginSuccessResponseBodyFields.LOGIN_SUCCESS)));

    }

}
