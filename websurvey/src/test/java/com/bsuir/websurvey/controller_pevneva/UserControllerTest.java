package com.websurvey.websurvey_pevneva.controller_pevneva;

import com.websurvey.websurvey_pevneva.WebsurveyApplication;
import com.websurvey.websurvey_pevneva.enums_pevneva.UserRole_Pevneva;
import org.apache.tomcat.util.net.jsse.JSSEImplementation;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    String adminLogin;
    String adminPassword;
    String adminCodePhrase;
    String adminSession;

    String userLogin;
    String userPassword;
    String userCodePhrase;
    String userSession;

    @Test
    @Order(0)
    void GetAllUsers() throws Exception {
        mvc.perform(get("/user/all").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(1)
    void RegisterAdmin() throws Exception {
        adminLogin = GenerateString();
        adminPassword = GenerateString();
        adminCodePhrase = GenerateString();

        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("password", adminPassword);
        json.put("role", UserRole_Pevneva.Admin.value);
        json.put("code_phrase", adminCodePhrase);

        TestPostOk("/user/register", json);
    }

    @Test
    @Order(1)
    void RegisterUser() throws Exception {
        userLogin = GenerateString();
        userPassword = GenerateString();
        userCodePhrase = GenerateString();

        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("password", userPassword);
        json.put("role", UserRole_Pevneva.User.value);
        json.put("code_phrase", userCodePhrase);

        TestPostOk("/user/register", json);
    }

    @Test
    @Order(2)
    void LoginRandomNotExist() throws Exception {
        mvc.perform(get("/user/exist/" + GenerateString())).andExpect(status().isOk()).andExpect(content().string("false"));
    }

    @Test
    @Order(2)
    void LoginAdminExist() throws Exception {
        mvc.perform(get("/user/exist/" + adminLogin)).andExpect(status().isOk()).andExpect(content().string("true"));
    }

    @Test
    @Order(2)
    void LoginUserExist() throws Exception {
        mvc.perform(get("/user/exist/" + userLogin)).andExpect(status().isOk()).andExpect(content().string("true"));
    }

    @Test
    @Order(2)
    void RecoveryAdmin() throws Exception {
        adminPassword = GenerateString();

        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("password", adminPassword);
        json.put("code_phrase", adminCodePhrase);
        TestPostOk("/user/recovery", json);
    }

    @Test
    @Order(2)
    void RecoveryUser() throws Exception {
        userPassword = GenerateString();

        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("password", userPassword);
        json.put("code_phrase", userCodePhrase);
        TestPostOk("/user/recovery", json);
    }

    @Test
    @Order(3)
    void LoginUser() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("password", userPassword);
        userSession = TestPostOk("/user/login", json).getResponse().getContentAsString();
    }

    @Test
    @Order(3)
    void LoginAdmin() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("password", adminPassword);
        adminSession = TestPostOk("/user/login", json).getResponse().getContentAsString();
    }

    @Test
    @Order(4)
    void VerifyAdminSession() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        TestPostOk("/user/session/verify", json);
    }

    @Test
    @Order(4)
    void VerifyUserSession() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("session_id", userSession);
        TestPostOk("/user/session/verify", json);
    }

    @Test
    @Order(5)
    void Ban() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        json.put("user", userLogin);
        TestPostOk("/user/ban", json);
    }

    @Test
    @Order(6)
    void Unban() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        json.put("user", userLogin);
        TestPostOk("/user/ban", json);
    }

    @Test
    @Order(7)
    void GetAdminUncompletedSurveys() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        TestPostOk("/user/surveys/uncompleted", json);
    }

    @Test
    @Order(7)
    void GetUserUncompletedSurveys() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("session_id", userSession);
        TestPostOk("/user/surveys/uncompleted", json);
    }

    @Test
    @Order(7)
    void GetAllSurveys() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        TestPostOk("/user/surveys/my", json);
    }

    @Test
    @Order(8)
    void removeAdminSession() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", adminLogin);
        json.put("session_id", adminSession);
        TestPostOk("/user/session/remove", json);
    }

    @Test
    @Order(8)
    void removeUserSession() throws Exception {
        JSONObject json = new JSONObject();
        json.put("login", userLogin);
        json.put("session_id", userSession);
        TestPostOk("/user/session/remove", json);
    }

    MvcResult TestPostOk(String url, JSONObject body) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toString());

        return mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    String GenerateString() {
        Random random = new Random();

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}