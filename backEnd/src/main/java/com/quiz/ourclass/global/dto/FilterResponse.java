package com.quiz.ourclass.global.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;


@Component
public class FilterResponse {

    private final ObjectMapper mapper = new ObjectMapper();

    // Filter 정상 종료에 따른 Response 설정
    public <T> HttpServletResponse ok(HttpServletResponse response, T data)
        throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(data));
        response.setStatus(HttpServletResponse.SC_OK);

        return response;
    }

    public void error(HttpServletResponse response, String msg) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);
    }

}
