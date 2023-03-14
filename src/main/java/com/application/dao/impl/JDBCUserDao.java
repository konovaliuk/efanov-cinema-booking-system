package com.application.dao.impl;

import com.application.dao.DataSource;
import com.application.dao.UserDao;
import com.application.dao.util.PasswordEncoder;
import com.application.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private final String GET_USERS = "select * from user_list";
    private final String GET_USER = "select * from user_list where user_id = ?";
    private final String SAVE_USER = "insert into user_list(email, password, login) VALUES(?, ?, ?)";
    private final String UPDATE_USER = "update user_list set email = ?, password = ?, login = ?, is_admin = ? where user_id = ?";
    private final String DELETE_USER = "delete from user_list where user_id = ?";
    private final String GET_BY_EMAIL = "select * from user_list where email = ?";
    private final String MAKE_ADMIN = "update user_list set is_admin = true";

    private static final Logger LOGGER = LogManager.getLogger(JDBCUserDao.class);
    private Connection connection;
    public void setConnection(Connection connection){
        this.connection = connection;
    }
    @Override
    public User get(long id) {
        try(PreparedStatement getUser = connection.prepareStatement(GET_USER)){
            getUser.setLong(1, id);
            ResultSet user = getUser.executeQuery();
            if (user.next()) {
                String name = user.getString(2);
                String password = user.getString(3);
                String login = user.getString(4);
                boolean isAdmin = user.getBoolean(5);
                return new User(id, name, login, password, isAdmin);
            }
            return null;
        } catch (SQLException e) {
            LOGGER.error("Error while getting user by id", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try(Statement getUser = connection.createStatement()){
            ResultSet user = getUser.executeQuery(GET_USERS);
            while (user.next()) {
                long userId = user.getLong(1);
                String name = user.getString(2);
                String password = (String) user.getObject(3);
                String login = user.getString(4);
                boolean isAdmin = user.getBoolean(5);
                userList.add(new User(userId, name, login, password, isAdmin));
            }
            return userList;

        } catch (SQLException e) {
            LOGGER.error("Can't get users", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        String password = user.getPassword();
        String hashedPassword = PasswordEncoder.encodePassword(password);
        user.setPassword(hashedPassword);
        try(PreparedStatement saveUser = connection.prepareStatement(SAVE_USER)){
            saveUser.setString(1, user.getEmail());
            saveUser.setString(2, user.getPassword());
            saveUser.setString(3, user.getLogin());
            saveUser.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot save user", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try(PreparedStatement updateUser = connection.prepareStatement(UPDATE_USER)){
            updateUser.setString(1, user.getEmail());
            updateUser.setString(2, user.getPassword());
            updateUser.setString(3, user.getLogin());
            updateUser.setBoolean(4, user.isAdmin());
            updateUser.setLong(5, user.getID());
            updateUser.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot update user", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement deleteUser = connection.prepareStatement(DELETE_USER)){
            deleteUser.setLong(1, id);
            deleteUser.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot delete user", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByEmail(String email) {
        try(PreparedStatement getByEmail = connection.prepareStatement(GET_BY_EMAIL)){
            getByEmail.setString(1, email);
            ResultSet userByEmail = getByEmail.executeQuery();
            if(userByEmail.next()){
                long id = userByEmail.getLong(1);
                String password = (String) userByEmail.getObject(3);
                String login = userByEmail.getString(4);
                boolean isAdmin = userByEmail.getBoolean(5);
                return new User(id, email, login, password, isAdmin);
            }
            return null;
        } catch (SQLException e) {
            LOGGER.error("Cannot get by email user", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void makeAdmin(long id)  {
        try(Statement makeAdmin = connection.createStatement()){
            makeAdmin.executeQuery(MAKE_ADMIN);
        } catch (SQLException e) {
            LOGGER.error("Cannot make admin user", e);
            throw new RuntimeException(e);
        }
    }
}
