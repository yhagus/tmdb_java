package net.ariflaksito.tmdbapp_new;

public class Movie {
    int id;
    String title,overview,release_date,poster_path,backdrop_path;
    int vote_average;

    public Movie(){};

    public Movie(int id, String title, String overview, String release_date, String poster_path, int vote_average, String backdrop_path){
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = "https://image.tmdb.org/t/p/w500" + poster_path;
        this.vote_average = vote_average;
        this.backdrop_path = backdrop_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
}
