package com.example.ott.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<MovieModel, String> {
    List<MovieModel> findByIsMovie(Boolean isMovie);
    List<MovieModel> findByIsShow(Boolean isShow);
    List<MovieModel> findByInDemand(Boolean inDemand);
}
