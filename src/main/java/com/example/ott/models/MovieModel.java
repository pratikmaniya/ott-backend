package com.example.ott.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("movies")
public class MovieModel {
    private String id;
    private String title;
    private String details;
    private String smallPoster;
    private String largePoster;
    private Number unitPrice;
    private Number costPrice;
    private Boolean isMovie;
    private Boolean isShow;
    private Boolean isFeatured;
    private Boolean inDemand;

    public MovieModel(String id, String title, String details, String smallPoster, String largePoster, Number unitPrice, Number costPrice, Boolean isMovie, Boolean isShow, Boolean isFeatured, Boolean inDemand) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.smallPoster = smallPoster;
        this.largePoster = largePoster;
        this.unitPrice = unitPrice;
        this.costPrice = costPrice;
        this.isMovie = isMovie;
        this.isShow = isShow;
        this.isFeatured = isFeatured;
        this.inDemand = inDemand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSmallPoster() {
        return smallPoster;
    }

    public void setSmallPoster(String smallPoster) {
        this.smallPoster = smallPoster;
    }

    public String getLargePoster() {
        return largePoster;
    }

    public void setLargePoster(String largePoster) {
        this.largePoster = largePoster;
    }

    public Number getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Number unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Number getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Number costPrice) {
        this.costPrice = costPrice;
    }

    public Boolean getMovie() {
        return isMovie;
    }

    public void setMovie(Boolean movie) {
        isMovie = movie;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public Boolean getFeatured() {
        return isFeatured;
    }

    public void setFeatured(Boolean featured) {
        isFeatured = featured;
    }

    public Boolean getInDemand() {
        return inDemand;
    }

    public void setInDemand(Boolean inDemand) {
        this.inDemand = inDemand;
    }
}
