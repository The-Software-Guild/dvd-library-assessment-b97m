/**
 *
 * @author Benjamin Munoz
 * email: driver396@gmail.com
 * date: Jul 29, 2021
 * purpose: The Data Transfer Object for the whole application
 */

package com.bm.dvdlibrary.dto;

import java.time.LocalDate;

/**
 * The Data Transfer Object for the whole application.
 * It represents DVDs, each of which are assumed to be 
 * identified by their title
 * 
 * @author Benjamin Munoz
 */
public class DVD {
    private String title;
    private LocalDate releaseDate;
    
    private String mpaaRating;
    private String directorName;
    private String studio;
    
    private int rating;
    private String note;

    public DVD(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }
    
    public String getTitle() {
        return title;
    }
}