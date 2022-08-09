package com.techelevator.dao;

import com.techelevator.model.Reviews;

import java.util.List;

public interface ReviewDao {

    List<Reviews> getAllReviews();

    Reviews getByReviewID(long reviewID);

    Reviews getByDoctorID(long doctorID);

    Reviews getByPatientID(long patientID);

    List<Reviews> getByOfficeID(long officeID);

    Reviews createReview(Reviews reviews);

}
