/**
 *
 * @author Benjamin Munoz
 * email: driver396@gmail.com
 * date: Jul 29, 2021
 * purpose: Provides DAO services
 */

package com.bm.dvdlibrary.dao;

import com.bm.dvdlibrary.dto.DVD;
import java.util.List;
import java.util.Optional;

/**
 * Provides DAO services, handling the retrieval, storage,
 * and modification of DVD information
 * 
 * @author Benjamin Munoz
 */
public interface DVDLibraryDao {
    /**
     * Loads DVDs from an external source, such as a text file or database,
     * into memory. If this cannot be done, the below exception may be thrown
     * @throws DVDLibraryDaoException
     */
    public void loadDVDsFromExternal() throws DVDLibraryDaoException;
    
    /**
     * Attempts to add the DVD into the collection.
     * 
     * If it already exists in the collection, no modifications to the
     * collection will be made, and an empty instance will be 
     * returned.
     * 
     * Otherwise, an instance containing the added in DVD will be returned.
     * 
     * @param subject
     * @return The aforementioned instances
     */
    public Optional<DVD> addDVD(DVD subject);
    
    /**
     * Attempts to remove the DVD corresponding to this title
     * in the collection
     * 
     * If such a DVD exists, then an instance containing this
     * DVD is returned. Otherwise, and empty instance will be
     * returned
     * 
     * @param title
     * @return The aforementioned instance
     */
    public Optional<DVD> removeDVD(String title);
    
    /**
     * Modifies a DVD in the collection according to the modified
     * DVD input.
     * 
     * If there is no DVD in the collection whose title matches that of the 
     * modified DVD, no modifications to the collection will be made, and an
     * empty instance will be returned.
     * 
     * Otherwise, the DVD in the collection whose title matches that of the
     * modified DVD will acquire the same information as contained in the 
     * modified DVD, and an instance containing the modified DVD will be
     * returned
     * 
     * @param modifiedDVD
     * @return 
     */
    public Optional<DVD> saveDVD(DVD modifiedDVD);
    
    /**
     * Attempts to find a DVD in the collection with this title
     * 
     * If no such DVD exists, an empty instance will be returned.
     * 
     * Otherwise, an instance containing the matching DVD will be returned.
     * 
     * @param title
     * @return 
     */
    public Optional<DVD> getDVDByTitle(String title);
    
    /**
     * Obtains a List of all the DVDs in this collection
     * 
     * @return The aforementioned List
     */
    public List<DVD> getAllDVDs();
    
    /**
     * Saves DVDs in this collection to an external source, such as a file or a
     * database. If this cannot be done, the below exception may be thrown.
     * 
     * @throws DVDLibraryDaoException 
     */
    public void saveDVDsToExternal() throws DVDLibraryDaoException;
}
