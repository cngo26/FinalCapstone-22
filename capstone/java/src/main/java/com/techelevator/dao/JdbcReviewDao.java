package com.techelevator.dao;

import com.techelevator.model.Reviews;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcReviewDao implements ReviewDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reviews> getAllReviews() {
        List<Reviews> review = new ArrayList<>();
        String sql = "SELECT * FROM reviews;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Reviews reviews = mapRowToReview(results);
        }
        return review;
    }

    @Override
    public Reviews getByReviewID(int reviewID) {
        String sql = "SELECT * FROM reviews WHERE review_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reviewID);
        if(results.next()){
            return mapRowToReview(results);
        }
        return null;

    }

    @Override
    public Reviews getByDoctorID(long doctorID) {
        String sql = "SELECT r.review_id, r.amount_of_stars, r.review_message, r.doctor_id, r.patient_id, r.review_response, u.user_id " +
                    "FROM reviews r " +
                    "JOIN users u " +
                    "ON r.doctor_id = u.user_id " +
                    "WHERE r.doctor_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, doctorID);
        if(results.next()){
            return mapRowToReview(results);
        }
        return null;
    }

    @Override
    public Reviews getByPatientID(long patientID) {
        String sql = "SELECT r.review_id, r.amount_of_stars, r.review_message, r.doctor_id, r.patient_id, u.user_id " +
                    "FROM reviews r " +
                    "JOIN users u " +
                    "ON r.patient_id = u.user_id " +
                    "WHERE r.patient_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, patientID);
        if(results.next()){
            return mapRowToReview(results);
        }
        return null;
    }

    @Override
    public Reviews getByOfficeID(long officeID) {
        String sql = "SELECT i.office_id, r.office_id " +
                    "FROM office_info i " +
                    "JOIN reviews r " +
                    "ON i.office_id = r.office_id " +
                    "WHERE r.office_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, officeID);
        if(results.next()){
            return mapRowToReview(results);
        }
        return null;
    }
    
    private Reviews mapRowToReview(SqlRowSet results) {
        Reviews review = new Reviews();
        review.setReviewID(results.getInt("review_id"));
        review.setAmountOfStars(results.getInt("amount_of_stars"));
        review.setReviewMessage(results.getString("review_message"));
        review.setDoctorID(results.getLong("doctor_id"));
        review.setPatientID(results.getLong("patient_id"));
        review.setOfficeID(results.getLong("office_id"));
        review.setReviewResponse(results.getString("review_response"));
        return new Reviews();
    }
}
