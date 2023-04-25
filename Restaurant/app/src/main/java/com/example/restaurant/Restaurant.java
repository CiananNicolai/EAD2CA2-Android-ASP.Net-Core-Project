package com.example.restaurant;

import java.util.ArrayList;

public class Restaurant {
    private int id;
    private String name;
    private String type;
    private String address;
    private String phone;
    private String description;
    private int stars;
    private ArrayList<Review> reviews;
    private String pictureUrl;

    public Restaurant(int id, String name, String type, String address, String phone, String description, int stars, ArrayList<Review> reviews, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.stars = stars;
        this.reviews = reviews;
        this.pictureUrl = pictureUrl;
    }

    public Restaurant(int id, String name, String address, String type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public int getStars() {
        return stars;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public static class Review {
        private String author;
        private String body;
        private float rating;

        public Review(String author, String body, float rating) {
            this.author = author;
            this.body = body;
            this.rating = rating;
        }

        public String getAuthor() {
            return author;
        }

        public String getBody() {
            return body;
        }

        public float getRating() {
            return rating;
        }
    }
}
