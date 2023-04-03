package com.application.controller.command;

import com.application.model.User;
import com.application.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandRegister implements Command{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try{
            UserService userService = new UserService();
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            User userAdded = userService.register(new User(1, email, login, password, false));
            if(userAdded != null){
                req.getSession().setAttribute("user", userAdded);
                resp.sendRedirect("/html/home.jsp");
            }
            else {
                req.getRequestDispatcher("/html/signup.jsp").forward(req, resp);
            }
        }
        catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
