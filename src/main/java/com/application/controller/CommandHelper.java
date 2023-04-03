package com.application.controller;

import com.application.controller.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CommandHelper {
    Map<String, Command> commands;

    public CommandHelper(){
        commands = new HashMap<>();
        commands.put("login", new CommandLogin());
        commands.put("logout", new CommandLogout());
        commands.put("signup", new CommandRegister());
        commands.put("screenings", new CommandScreenings());
    }

    public void getCommand(String command, HttpServletRequest req, HttpServletResponse resp){
        commands.get(command).execute(req, resp);
    }
}
