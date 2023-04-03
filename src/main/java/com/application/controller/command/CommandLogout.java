package com.application.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandLogout implements Command{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getSession().setAttribute("user", null);
            resp.sendRedirect("/html/login.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
