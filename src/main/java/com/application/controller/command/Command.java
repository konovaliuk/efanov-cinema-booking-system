package com.application.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response);
}
