package com.example.myapplication_rentme;

public class ReviewModel {
    private String review,user_id,product_id,review_id,review_time;
    private Float rating;
    public ReviewModel() {
    }

    public ReviewModel(String review, String user_id, String product_id, String review_id, String review_time, Float rating) {
        this.review = review;
        this.user_id = user_id;
        this.product_id = product_id;
        this.review_id = review_id;
        this.review_time = review_time;
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getReview_time() {
        return review_time;
    }

    public void setReview_time(String review_time) {
        this.review_time = review_time;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
