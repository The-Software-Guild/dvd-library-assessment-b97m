/**
 *
 * @author Benjamin Munoz
 * email: driver396@gmail.com
 * date: Jul 29, 2021
 * purpose: Acts as the controller for the whole application
 */

package com.bm.dvdlibrary.controller;

import com.bm.dvdlibrary.dao.DVDLibraryDao;
import com.bm.dvdlibrary.dao.DVDLibraryDaoException;
import com.bm.dvdlibrary.dto.DVD;
import com.bm.dvdlibrary.ui.DVDLibraryView;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Acts as the controller for this whole application
 * 
 * @author Benjamin Munoz
 */
@Component("dvdLibraryController")
public class DVDLibraryController {
    private DVDLibraryDao dao;
    private DVDLibraryView view;

    /**
     * Creates a new instance of this class that is initialized
     * with a given DAO and VIEW
     * @param dao
     * @param view 
     */
    @Autowired
    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view) {
        this.dao = dao;
        this.view = view;
    }
    
    /**
     * Executes a run of this application
     */
    public void run() {
        // attempt to load the DVDs into memory, if possible
        try {
            dao.loadDVDsFromExternal();
        } catch (DVDLibraryDaoException ex) {
            view.displayError(ex.getMessage());
        }
        
        boolean active = true;
        int choice;
        while (active) {
            view.displayMenuOptions();
            choice = view.queryInt(1, 6, "Select an option");
            switch (choice) {
                case 1:
                    addDVD();
                    break;
                case 2:
                    viewDVD();
                    break;
                case 3:
                    editDVD();
                    break;
                case 4:
                    removeDVD();
                    break;
                case 5:
                    listDVDs();
                    break;
                case 6:
                    active = false;
                    break;
                default:
                    view.displayError("UNKNOWN COMMAND");
                    break;
            }
        }
        
        view.displayText("Thank you for using this application");
        
        // attempt to save DVDs to an external source for future usage by this
        // application
        try {
            dao.saveDVDsToExternal();
        } catch (DVDLibraryDaoException ex) {
            view.displayError(ex.getMessage());
        }
    }
    
    /**
     * Handles the addition of a DVD into the collection
     */
    private void addDVD() {
        view.displayHeader("DVD ADDITION MENU");
        
        DVD newDVD = produceDVD();
        
        dao.addDVD(newDVD).ifPresentOrElse(
            dvd -> {
                view.displayInfo("DVD added to records");
            }, () -> {
                view.displayError(
                    "It appears there's a DVD in the collection that has "
                    + "already has that title"
                );
                view.displayInfo("DVD not added to records");
            }
        );
        view.displayFooter("DVD ADDITION MENU");
        pauseBeforeAllowedContinuation();
    }
    
    /**
     * Handles the viewing of a DVD in the collection
     */
    private void viewDVD() {
        view.displayHeader("DVD INFO MENU");
        String title = view.queryNonemptyString("Enter the name of the DVD to view");
        dao.getDVDByTitle(title).ifPresentOrElse(
            dvd -> {
                view.displayInfo("DVD INFO");
                displayDVDInfo(dvd);
            }, () -> view.displayError("There is no DVD with that title")
        );
        view.displayFooter("DVD INFO MENU");
        pauseBeforeAllowedContinuation();
    }

    /**
     * Handles the editing of a DVD in the collection
     */
    private void editDVD() {
        view.displayHeader("DVD EDIT MENU");
        
        String title = view.queryNonemptyString("Enter the name of the DVD to edit");
        dao.getDVDByTitle(title).ifPresentOrElse(
            original -> {
                view.displayInfo("CURRENT DVD INFO");
                displayDVDInfo(original);
                DVD modifiedDVD = getModifiedDVDOf(original);
                dao.saveDVD(modifiedDVD);
                view.displayInfo("Changes saved");
            }, () -> {
                view.displayError("There's no DVD in the collection with this title");
            }
        );
        
        view.displayFooter("DVD EDIT MENU");
        pauseBeforeAllowedContinuation();
    }
    
    /**
     * Handles the removal of a DVD in the collection
     */
    private void removeDVD() {
        view.displayHeader("DVD REMOVAL MENU");
        String title = view.queryNonemptyString("Enter the title of the DVD to remove");
        dao.removeDVD(title).ifPresentOrElse(
            removedDvd -> view.displayInfo("DVD removed succesfully"),
            () -> view.displayInfo("There is no DVD with that title")
        );
        view.displayFooter("DVD REMOVAL MENU");
        pauseBeforeAllowedContinuation();
    }
    
    /**
     * Handles the listing of DVDs in the collection
     */
    private void listDVDs() {
        view.displayHeader("DVDS IN COLLECTION");
        for (DVD dvd : dao.getAllDVDs()) {
            view.displayHeader("---".repeat(10));
            displayDVDInfo(dvd);
            view.displayFooter("---".repeat(10));
        }
        view.displayFooter("DVDS IN COLLECTION");
        pauseBeforeAllowedContinuation();
    }
    
    /**
     * Handler for taking an existing DVD and
     * allowing the user to modify its information
     * 
     * @param original The original DVD
     * @return The aforementioned DVD after modification
     */
    private DVD getModifiedDVDOf(DVD original) {
        String director = view.queryNonemptyString("Enter the film's director");
        LocalDate releaseDate = view.queryDate("Enter the film's release date");
        String mpaaRating = view.queryMpaaRating();
        String studio = view.queryNonemptyString("Enter the film's studio(s)");
        
        int rating = view.queryInt(0, 10, "Give your rating for this film");
        String note = view.queryString("Enter a short note for this film, if any");
        
        original.setDirectorName(director);
        original.setMpaaRating(mpaaRating);
        original.setNote(note);
        original.setRating(rating);
        original.setReleaseDate(releaseDate);
        original.setStudio(studio);
        
        return original;
    }
    
    /**
     * Handler for allowing the user to enter a new DVD
     * 
     * @return The aforementioned DVD
     */
    private DVD produceDVD() {
        String title = view.queryNonemptyString("Enter the film title");
        return getModifiedDVDOf(new DVD(title));
    }
        
    /**
     * Handler for showing the user information about a given DVD
     * 
     * @param dvd 
     */
    private void displayDVDInfo(DVD dvd) {
        view.displayText(dvd.getTitle());
        view.displayText("Director: " + dvd.getDirectorName());
        view.displayText("Studio(s): " + dvd.getStudio());
        view.displayText("Released: " + dvd.getReleaseDate().toString());
        view.displayText("MPAA Rating: " + dvd.getMpaaRating());
        view.displayText("User Rating: " + dvd.getRating());
        view.displayText("Notes: " + dvd.getNote());
    }

    /**
     * Handler for pausing the whole application before the user
     * allows it to continue
     */
    private void pauseBeforeAllowedContinuation() {
        view.queryString("Press ENTER to continue");
    }
}
