package com.application.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/home", "/html/login", "/html/signup", "/html/logout", "/index", "/"})
public class CinemaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = new String("screenings");
        CommandHelper commandHelper = new CommandHelper();
        commandHelper.getCommand(command, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        CommandHelper commandHelper = new CommandHelper();
        commandHelper.getCommand(command, req, resp);
    }
}
