package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 05/12/2017.
 */

public class ReviewRatingResponse {

    @SerializedName("review_rating")
    @Expose
    private List<ReviewRating> reviewRating = null;

    public List<ReviewRating> getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(List<ReviewRating> reviewRating) {
        this.reviewRating = reviewRating;
    }

}
