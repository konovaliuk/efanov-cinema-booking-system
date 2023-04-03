package com.application.controller.command;

import com.application.model.User;
import com.application.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandLogin implements Command{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try{
            UserService userService = new UserService();
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            User user = userService.login(login, password);
            if (user != null){
                req.getSession().setAttribute("user", user);
                resp.sendRedirect("/html/home.jsp");
            }
            else {
                req.getRequestDispatcher("/html/login.jsp").forward(req, resp);
            }
        }
        catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
