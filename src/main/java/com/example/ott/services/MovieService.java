package com.example.ott.services;

import com.example.ott.models.MovieModel;
import com.example.ott.models.MovieRepository;
import com.example.ott.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public MovieModel insertMovie(MovieModel movie){
        MovieModel insertedMovie = movieRepository.insert(movie);
        return insertedMovie;
    }

    public List<MovieModel> getMovies(){
        List<MovieModel> movies = movieRepository.findByIsMovie(true);
        return movies;
    }

    public List<MovieModel> getShows(){
        List<MovieModel> shows = movieRepository.findByIsShow(true);
        return shows;
    }

    public List<MovieModel> getItemsBySearch(String queryString, String type){
        Criteria criteria = new Criteria();
        if(type.equals("movie")){
            criteria.andOperator(
                    Criteria.where("title").regex(queryString, "i"),
                    Criteria.where("isMovie").is(true)
            );
        } else if(type.equals("show")){
            criteria.andOperator(
                    Criteria.where("title").regex(queryString, "i"),
                    Criteria.where("isShow").is(true)
            );
        }
        Query query = new Query(criteria);
        List<MovieModel> movies = mongoTemplate.find(query, MovieModel.class);
        return movies;
    }

    public List<MovieModel> getFeaturedItems(String type){
        Criteria criteria = new Criteria();
        if(type.equals("movie")){
            criteria.andOperator(
                    Criteria.where("isFeatured").is(true),
                    Criteria.where("isMovie").is(true)
            );
        } else if(type.equals("show")){
            criteria.andOperator(
                    Criteria.where("isFeatured").is(true),
                    Criteria.where("isShow").is(true)
            );
        }
        Query query = new Query(criteria);
        List<MovieModel> movies = mongoTemplate.find(query, MovieModel.class);
        return movies;
    }

    public List<MovieModel> getInDemandItems(){
        List<MovieModel> movies = movieRepository.findByInDemand(true);
        return movies;
    }

    public Optional<MovieModel> getMovie(String id) throws Exception{
        Optional<MovieModel> movie = movieRepository.findById(id);
        if(!movie.isPresent()){
            throw new Exception("Movie with " + id + " is not found");
        }
        return movie;
    }

    public MovieModel editMovie(String id, MovieModel newMovieData) throws Exception{
        Optional<MovieModel> movie = movieRepository.findById(id);
        if(!movie.isPresent()){
            throw new Exception("Movie with " + id + " is not found");
        }else{
            movie.get().setTitle(newMovieData.getTitle());
            movie.get().setDetails(newMovieData.getDetails());
            movie.get().setSmallPoster(newMovieData.getSmallPoster());
            movie.get().setLargePoster(newMovieData.getLargePoster());
            movie.get().setUnitPrice(newMovieData.getUnitPrice());
            movie.get().setCostPrice(newMovieData.getCostPrice());
            movie.get().setShow(newMovieData.getShow());
            movie.get().setMovie(newMovieData.getMovie());
            movie.get().setFeatured(newMovieData.getFeatured());
            movie.get().setInDemand(newMovieData.getInDemand());
            MovieModel updatedMovie = movieRepository.save(movie.get());
            return updatedMovie;
        }
    }

    public void deleteMovie(String id) throws Exception{
        Optional<MovieModel> movie = movieRepository.findById(id);
        if(!movie.isPresent()){
            throw new Exception("Movie with " + id + " is not found");
        }else {
            movieRepository.deleteById(id);
        }
    }
}
