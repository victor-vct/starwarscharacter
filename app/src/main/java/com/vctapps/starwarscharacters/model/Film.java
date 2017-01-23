package com.vctapps.starwarscharacters.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Victor on 22/01/2017.
 */

public class Film {

    private String title;
    @SerializedName("episode_id")
    private int episodeId;
    @SerializedName("opening_crawl")
    private String openingCrawl;
    private String director;
    private String producer;
    @SerializedName("release_date")
    private String releaseDate;
    private String created;
    private String edited;
    private String url;
    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
    private String urlPoster;

    public String getUrlPoster() {
        return urlPoster;
    }

    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public String getOpeningCrawl() {
        return openingCrawl;
    }

    public void setOpeningCrawl(String openingCrawl) {
        this.openingCrawl = openingCrawl;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public List<String> getPlanets() {
        return planets;
    }

    public void setPlanets(List<String> planets) {
        this.planets = planets;
    }

    public List<String> getStarships() {
        return starships;
    }

    public void setStarships(List<String> starships) {
        this.starships = starships;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }

    public List<String> getSpecies() {
        return species;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }
}
