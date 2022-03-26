package com.zhejianglab.spring3web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhejianglab.spring3dao.entity.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author chenze
 * @date 2022/3/24
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Resource
    private MockMvc mockMvc;

    @Resource
    private ObjectMapper objectMapper;

    @Test
    public void should_check_person_value() throws Exception {
        User user = User.builder().userName("spring3").realName("test3").roleType(1).password("123456").build();
        mockMvc.perform(post("/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

    }
}
