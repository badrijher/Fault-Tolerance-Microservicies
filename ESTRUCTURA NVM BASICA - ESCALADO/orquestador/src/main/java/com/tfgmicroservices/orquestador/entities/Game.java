package com.tfgmicroservices.orquestador.entities;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        if (rating == null) {
            if (other.rating != null)
                return false;
        } else if (!rating.equals(other.rating))
            return false;
        return true;
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
