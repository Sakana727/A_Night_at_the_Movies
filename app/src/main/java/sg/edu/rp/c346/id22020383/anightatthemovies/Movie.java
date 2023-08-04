package sg.edu.rp.c346.id22020383.anightatthemovies;

import android.os.Parcel;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title;
    private String genre;
    private int year;
    private String rating;

    public Movie(int id, String title, String genre, int year, String rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @NonNull
    @Override
    public String toString() {
        return title + " - " + genre + " (" + year + ")";
    }


    public int describeContents() {
        return 0;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(genre);
        dest.writeInt(year);
        dest.writeString(rating);
    }

}
