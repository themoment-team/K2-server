package com.moment.the.exceptionAdvice.handler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnswerAlreadyExistsHandler {
    public void handle(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.sendRedirect("/exception/answer-already-exists");
    }
}
