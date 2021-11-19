package net.ariflaksito.tmdbapp_new;

public class MovieModels {
    private String title;
    private String overview;
    private String release_date;
    private String poster_path;

    public MovieModels(String title, String overview, String release_date, String poster_path) {
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = "https://image.tmdb.org/t/p/w500" + poster_path;
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

    public String getPoster_path(){
        return poster_path;
    }

    public void setPoster_path(String poster_path){
        this.poster_path = poster_path;
    }
}
