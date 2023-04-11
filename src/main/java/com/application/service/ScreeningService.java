package com.application.service;

import com.application.dao.DataSource;
import com.application.dao.ScreeningDao;
import com.application.dao.impl.JDBCDaoFactory;
import com.application.model.Screening;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ScreeningService {

    public List<Screening> getAllScreenings(){
        try (Connection connection = DataSource.getConnection();){
            ScreeningDao screeningDao = new JDBCDaoFactory(connection).createScreeningDao();
            return screeningDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteScreening(Screening screening){
        try(Connection connection = DataSource.getConnection()) {
            ScreeningDao screeningDao = new JDBCDaoFactory(connection).createScreeningDao();
            screeningDao.delete(screening.getID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addScreening(Screening screening){
        try(Connection connection = DataSource.getConnection()) {
            ScreeningDao screeningDao = new JDBCDaoFactory(connection).createScreeningDao();
            screeningDao.save(screening);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
