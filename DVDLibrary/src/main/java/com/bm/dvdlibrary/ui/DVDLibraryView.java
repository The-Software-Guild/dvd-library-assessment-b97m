/**
 * @author Benjamin Munoz
 * email: driver396@gmail.com
 * date: Jul 29, 2021
 * purpose: The view portion of this application
 */

package com.bm.dvdlibrary.ui;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The View component for this application
 * 
 * @author Benjamin Munoz
 */
@Component
public class DVDLibraryView {
    private UserIO userIO;
    
    /**
     * Creates a new instance of this View
     * based on a UserIO component
     * 
     * @param userIO 
     */
    @Autowired
    public DVDLibraryView(UserIO userIO) {
        this.userIO = userIO;
    }

    /**
     * Send text to the user
     * 
     * @param text 
     */
    public void displayText(String text) {
        userIO.print(text);
    }

    /**
     * Send informational text to the user
     * @param text 
     */
    public void displayInfo(String text) {
        userIO.printInfo(text);
    }

    /**
     * Send header text to the user
     * @param text 
     */
    public void displayHeader(String text) {
        userIO.printHeader(text);
    }

    /**
     * Send footer text to the user
     * @param text 
     */
    public void displayFooter(String text) {
        userIO.printFooter(text);
    }
    
    /**
     * Display the possible menu options that the user
     * can choose from when they're at the main menu
     */
    public void displayMenuOptions() {
        userIO.printHeader("MAIN MENU");
        userIO.print("1: Add a DVD from the collection");
        userIO.print("2: View information for a DVD");
        userIO.print("3: Edit an existing DVD in the collection");
        userIO.print("4: Remove a DVD from the collection");
        userIO.print("5: List all DVDs in collection");
        userIO.print("6: Exit Application");
        userIO.printFooter("MAIN MENU");
    }
    
    /**
     * Prompt the user to give a String
     * @param prompt
     * @return The String entered by the user
     */
    public String queryString(String prompt) {
        userIO.printSolicitation(prompt);
        return userIO.readString();
    }
    
    public String queryNonemptyString(String prompt) {
        userIO.printSolicitation(prompt);
        return userIO.readNonemptyString();
    }
    
    /**
     * Prompt the user to give an int
     * @param prompt
     * @return The int entered by the user
     */
    public int queryInt(String prompt) {
        userIO.printSolicitation(prompt);
        return userIO.readInt();
    }
    
    /**
     * Prompt the user to give an int that lies 
     * between some minimum and maximum value
     * @param min
     * @param max
     * @param prompt
     * @return The aforementioned int
     */
    public int queryInt(int min, int max, String prompt) {
        userIO.printSolicitation(prompt);
        return userIO.readInt(min, max);
    }

    /**
     * Prompt the user to give a LocalDate
     * @param prompt
     * @return The LocalDate entered by the user
     */
    public LocalDate queryDate(String prompt) {
        userIO.printSolicitation(prompt);
        boolean dateNotValid = true;
        LocalDate receivedDate = null;
        int year;
        int month;
        int day;
        while (dateNotValid) {
            year = queryInt("Provide a year");
            month = queryInt("Provide a month");
            day = queryInt("Provide a day");
            try {
                receivedDate = LocalDate.of(year, month, day);
                dateNotValid = false;
            } catch (Exception ex) {
                displayError("The date entered was invalid");
            }
        }
        return receivedDate;
    }
    
    /**
     * Prompt the user to give an MPAA Rating
     * The possible MPAA Ratings are
     * - G
     * - PG
     * - PG-13
     * - R
     * - NC-17
     * @return A String representing a valid MPAA rating
     */
    public String queryMpaaRating() {
        userIO.printSolicitation("Enter an MPAA Rating");
        String receivedRating;
        boolean ratingIsInvalid = true;
        do {
            receivedRating = userIO.readNonemptyString();
            switch (receivedRating) {
                case "G" :
                case "PG" :
                case "PG-13" :
                case "R":
                case "NC-17":
                    ratingIsInvalid = false;
                    break;
                default:
                    userIO.printError("Please enter a valid MPAA rating");
            }
        } while (ratingIsInvalid);
        return receivedRating;
    }
    
    /**
     * Send error text to the user
     * @param err 
     */
    public void displayError(String err) {
        userIO.printError(err);
    }
}
