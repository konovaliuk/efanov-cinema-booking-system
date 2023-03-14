package com.application.dao;

import com.application.dao.util.PasswordEncoder;
import com.application.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public interface UserDao extends Dao<User>{
    User getByEmail(String email);
    void makeAdmin(long id);
}
