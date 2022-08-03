package com.example.ott.controllers;

import com.example.ott.CustomizedResponse;
import com.example.ott.models.MovieModel;
import com.example.ott.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping(value = "/movies", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity addMovie(@RequestBody MovieModel movie){
        var response = new CustomizedResponse("Movie added successfully", Collections.singletonList(movieService.insertMovie(movie)));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/movies")
    public ResponseEntity getMovies(){
        CustomizedResponse customizedResponse = new CustomizedResponse("Movies retrieved successfully", movieService.getMovies());
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/shows")
    public ResponseEntity getShows(){
        CustomizedResponse customizedResponse = new CustomizedResponse("Shows retrieved successfully", movieService.getShows());
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity getSearchResult(@RequestParam String query){
        CustomizedResponse customizedResponse = new CustomizedResponse("Movies/Shows retrieved successfully", movieService.getItemsBySearch(query));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/featured")
    public ResponseEntity getFeatured(@RequestParam String type){
        CustomizedResponse customizedResponse = new CustomizedResponse("Movies retrieved successfully", movieService.getFeaturedItems(type));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity getMovie(@PathVariable("id") String id){
        try {
            CustomizedResponse customizedResponse = new CustomizedResponse(" Movie with id " + id , Collections.singletonList(movieService.getMovie(id)));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch (Exception e) {
            CustomizedResponse customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(value = "/movies/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity editMovie(@PathVariable("id") String id, @RequestBody MovieModel newMovie ) {
        try {
            CustomizedResponse customizedResponse = new CustomizedResponse(" Movie with ID:  " + id + " is updated successfully " , Collections.singletonList(movieService.editMovie(id, newMovie)));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch (Exception e) {
            CustomizedResponse customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity deleteAMovie(@PathVariable("id") String id)
    {
        try {
            movieService.deleteMovie(id);
            CustomizedResponse customizedResponse = new CustomizedResponse(" Movie with ID:  " + id + " is deleted successfully " , null);
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch (Exception e) {
            CustomizedResponse customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }
    }

}
