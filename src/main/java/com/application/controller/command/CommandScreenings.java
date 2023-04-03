package com.application.controller.command;

import com.application.model.Screening;
import com.application.service.ScreeningService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CommandScreenings implements Command{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ScreeningService screeningService = new ScreeningService();
            List<Screening> screenings = screeningService.getAllScreenings();
            req.getSession().setAttribute("screenings", screenings);
            req.getRequestDispatcher("/html/home.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
