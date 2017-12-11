package com.example.asus.pikachise.model;

import com.example.asus.pikachise.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 05/12/2017.
 */

public class ReviewRating {
    @SerializedName("franchisee_id")
    @Expose
    private String franchiseeId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("franchise_id")
    @Expose
    private String franchiseId;
    @SerializedName("review_rating_id")
    @Expose
    private Integer reviewRatingId;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return apiUtils.getUrlImage() + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    public Integer getReviewRatingId() {
        return reviewRatingId;
    }

    public void setReviewRatingId(Integer reviewRatingId) {
        this.reviewRatingId = reviewRatingId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
