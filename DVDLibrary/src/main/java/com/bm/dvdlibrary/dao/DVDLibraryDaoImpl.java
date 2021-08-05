/**
 *
 * @author Benjamin Munoz
 * email: driver396@gmail.com
 * date: Jul 29, 2021
 * purpose: (TODO)
 */

package com.bm.dvdlibrary.dao;

import com.bm.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class DVDLibraryDaoImpl implements DVDLibraryDao {
    private Map<String, DVD> titleDVDMap;
    private static final String SRC_FILENAME = "dvds.txt";
    private static final String DELIMITER = "::";
    
    public DVDLibraryDaoImpl() {
        this.titleDVDMap = new HashMap<>();
    }
    
    @Override
    public void loadDVDsFromExternal() throws DVDLibraryDaoException {
        Scanner fileReader;
        
        try {
            fileReader = new Scanner(new BufferedReader(
                new FileReader(SRC_FILENAME)
            ));
        } catch (FileNotFoundException ex) {
            throw new DVDLibraryDaoException("Unable to load dvds from file", ex);
        }
        while (fileReader.hasNextLine()) {
            String[] record = fileReader.nextLine().split(DELIMITER);
            
            String title = record[0];
            DVD dvdToAdd = new DVD(title);
            
            LocalDate releaseDate = null;
            int year = Integer.parseInt(record[1]);
            int month = Integer.parseInt(record[2]);
            int day = Integer.parseInt(record[3]);            
            try {
                releaseDate = LocalDate.of(year, month, day);
            } catch (Exception ex) {
                throw new DVDLibraryDaoException(
                    "A record appears to have an invalid date", 
                    ex
                );
            }
            dvdToAdd.setReleaseDate(releaseDate);
            
            String mpaaRating = record[4];
            dvdToAdd.setMpaaRating(mpaaRating);
            
            String directorName = record[5];
            dvdToAdd.setDirectorName(directorName);
            
            String studio = record[6];
            dvdToAdd.setStudio(studio);
            
            int rating = Integer.parseInt(record[7]);
            dvdToAdd.setRating(rating);
            
            String note = ""; // notes in record may be left empty
            if (record.length == 9) {
                note = record[8];
            }
            dvdToAdd.setNote(note);

            titleDVDMap.put(title, dvdToAdd);
        }
        
        fileReader.close();
    }

    @Override
    public Optional<DVD> addDVD(DVD subject) {
        if (titleDVDMap.containsKey(subject.getTitle())) {
            return Optional.empty();
        }
        titleDVDMap.put(subject.getTitle(), subject);
        return Optional.of(subject);
    }
    
    @Override
    public Optional<DVD> removeDVD(String title) {
        DVD receivedDVD = titleDVDMap.remove(title);
        if (receivedDVD == null) {
            return Optional.empty();
        }
        return Optional.of(receivedDVD);
    }

    @Override
    public Optional<DVD> saveDVD(DVD modifiedDVD) {
        if (!titleDVDMap.containsKey(modifiedDVD.getTitle())) {
            return Optional.empty();
        }
        titleDVDMap.put(modifiedDVD.getTitle(), modifiedDVD);
        return Optional.of(modifiedDVD);
    }

    @Override
    public Optional<DVD> getDVDByTitle(String title) {
        DVD receivedDVD = titleDVDMap.get(title);
        if (receivedDVD == null) {
            return Optional.empty();
        }
        return Optional.of(receivedDVD);
    }

    @Override
    public void saveDVDsToExternal() throws DVDLibraryDaoException {
        PrintWriter writer;
        try {
            writer = new PrintWriter(new FileWriter(SRC_FILENAME));
        } catch (IOException ex) {
            throw new DVDLibraryDaoException("Unable to save dvds to file", ex);
        }
        
        for (DVD dvd : titleDVDMap.values()) {
            writer.format(
                "%s::%d::%d::%d::%s::%s::%s::%d::%s%n",
                dvd.getTitle(),
                dvd.getReleaseDate().getYear(),
                dvd.getReleaseDate().getMonthValue(),
                dvd.getReleaseDate().getDayOfMonth(),
                dvd.getMpaaRating(),
                dvd.getDirectorName(),
                dvd.getStudio(),
                dvd.getRating(),
                dvd.getNote()
            );
        }
        
        writer.close();
    }

    @Override
    public List<DVD> getAllDVDs() {
        return new ArrayList<>(titleDVDMap.values());
    }
}