package com.application.service;

import com.application.dao.DaoFactory;
import com.application.dao.DataSource;
import com.application.dao.UserDao;
import com.application.dao.impl.JDBCDaoFactory;
import com.application.dao.util.PasswordEncoder;
import com.application.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    public User register(User user){
        try (Connection connection = DataSource.getConnection()){
            UserDao userDao = new JDBCDaoFactory(connection).createUserDao();
            userDao.save(user);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeUserAdmin(User user){
        try (Connection connection = DataSource.getConnection()){
            UserDao userDao = new JDBCDaoFactory(connection).createUserDao();
            userDao.makeAdmin(user.getID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByEmail(User user){
        try (Connection connection = DataSource.getConnection()){
            UserDao userDao = new JDBCDaoFactory(connection).createUserDao();
            return userDao.getByEmail(user.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User login(String email, String password){
        try (Connection connection = DataSource.getConnection()){
            UserDao userDao = new JDBCDaoFactory(connection).createUserDao();
            User userToLogin = userDao.getByEmail(email);
            if(userToLogin != null && PasswordEncoder.encodePassword(email, password).equals(userToLogin.getPassword()))
                return userToLogin;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateUser(User user){
        try(Connection connection = DataSource.getConnection()) {
            try{
                connection.setAutoCommit(false);
                UserDao userDao = new JDBCDaoFactory(connection).createUserDao();
                user.setPassword(PasswordEncoder.encodePassword(user.getLogin(), user.getPassword()));
                userDao.update(user);
                connection.commit();
            }
            catch(Exception e){
                connection.rollback();
            }
            finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
