package com.tfgmicroservices.classicsort.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Game implements Comparable<Game> {

    @JsonProperty("title")
    private String title;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("rating")
    private Double rating;

    public Game(String title, Integer year, Double rating) {
        this.title = title;
        this.year = year;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public Double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Game{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                '}';
    }
 
    
    public int compareTo(Game game) {
        int res = getRating().compareTo(game.getRating());
        if(res == 0){
            res = getYear().compareTo(game.getYear());
            if(res == 0){
                res = getTitle().compareTo(game.getTitle());
            }
        }
        
        return res;
    }
}
