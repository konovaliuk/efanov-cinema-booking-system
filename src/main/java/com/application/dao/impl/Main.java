package com.application.dao.impl;

import com.application.dao.DataSource;
import com.application.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        try(Connection connection = DataSource.getConnection()){
            JDBCUserDao userDao = new JDBCUserDao();
            userDao.setConnection(connection);
            User userToAdd = new User(9, "Bleach@gmail.com", "Bleach", "20e1ecc339b44ae045fca1ebd4e90aad958401de0f8e1cda2e53d40be1e3c5f77743076b14705c9801cd8fb05554b1417549a4d30b1bd1d361e2f1508a19b8ed", false);
            //userDao.save(userToAdd);
            List<User> users = userDao.getAll();
            System.out.println();
            for(User user : users){
                System.out.println(user);
            }
            JDBCMovieDao movieDao = new JDBCMovieDao();
            movieDao.setConnection(connection);
            Movie movieToAdd = new Movie(7, "Babylon", "Shazel", "Film about death of silent movie", 150);
            //movieDao.save(movieToAdd);
            List<Movie> movies = movieDao.getAll();
            System.out.println();
            for(Movie movie : movies){
                System.out.println(movie);
            }
            Movie movieByID = movieDao.get(6);
            if(movieByID != null)
                System.out.println(movieByID);
            else
                System.out.println("No such movie");
            movieDao.delete(8);
            List<Movie> movies2 = movieDao.getAll();
            System.out.println();
            for(Movie movie : movies2){
                System.out.println(movie);
            }
            JDBCSeatDao seatDao = new JDBCSeatDao();
            seatDao.setConnection(connection);
            Seat seatToAdd1 = new Seat(3, 1, 2);
            //Seat seatToAdd2 = new Seat(1, 1, 1);
            //seatDao.save(seatToAdd1);
            //seatDao.save(seatToAdd2);
            System.out.println();
            for(Seat seat : seatDao.getAll()){
                System.out.println(seat);
            }
            JDBCActorDao actorDao = new JDBCActorDao();
            actorDao.setConnection(connection);
//            Actor actorToAdd1 = new Actor(1, "Brad", "Pitt", "Jack Conrad");
//            Actor actorToAdd2 = new Actor(2, "Tobey", "Maguire", "James McKay");
//            Actor actorToAdd3 = new Actor(3, "Brendan", "Fraser", "Charlie");
//            actorDao.save(actorToAdd1);
//            actorDao.save(actorToAdd2);
//            actorDao.save(actorToAdd3);
//            for(Actor actor : actorDao.getAll()){
//                System.out.println(actor);
//            }
            JDBCMovieActorDao movieActorDao = new JDBCMovieActorDao();
            movieActorDao.setConnection(connection);
//            MovieActor movieActor1 = new MovieActor(1, 7);
//            MovieActor movieActor2 = new MovieActor(2, 7);
//            MovieActor movieActor3 = new MovieActor(3, 1);
//            movieActorDao.save(movieActor1);
//            movieActorDao.save(movieActor2);
//            movieActorDao.save(movieActor3);
            System.out.println();
            for(Actor actor : movieDao.getFilmActors(7)){
                System.out.println(actor);
            }
            JDBCScreeningDao screeningDao = new JDBCScreeningDao();
            screeningDao.setConnection(connection);
            Screening screeningToAdd = new Screening(1, movieToAdd, new Timestamp(new Date().getTime()));
            //screeningDao.save(screening);
            System.out.println();
            for(Screening screening: screeningDao.getAll()){
                System.out.println(screening);
            }
            JDBCReservationDao reservationDao = new JDBCReservationDao();
            reservationDao.setConnection(connection);
            Reservation reservation = new Reservation(1, userToAdd, screeningToAdd, true, false, false);
            //reservationDao.save(reservation);
            System.out.println();
            for(Reservation reservation1 : reservationDao.getAll()){
                System.out.println(reservation1);
            }
            JDBCSeatReservedDao seatReservedDao = new JDBCSeatReservedDao();
            seatReservedDao.setConnection(connection);
            //seatReservedDao.save(new SeatReserved(1, seatToAdd1, reservation, screeningToAdd));
            System.out.println();
            for(SeatReserved seatReserved: seatReservedDao.getAll()){
                System.out.println(seatReserved);
            }
            System.out.println();
            List<Seat> userSeatReserved = seatReservedDao.getReservedSeats(screeningToAdd);
            for(Seat seatReserved: userSeatReserved){
                System.out.println(seatReserved);
            }
            System.out.println();
            for(Seat seatReserved : seatReservedDao.getReservedSeatsByUser(screeningToAdd, userToAdd)){
                System.out.println(seatReserved);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
