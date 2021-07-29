/**
 * @author Benjamin Munoz
 * email: driver396@gmail.com
 * date: Jul 29, 2021
 * purpose: Provide UI interactions for the view
 */

package com.bm.dvdlibrary.ui;

/**
 * Provides User i/o functionality
 * 
 * @author Benjamin Munoz
 */
public interface UserIO {
    /**
     * Prints text to the user
     * @param txt The text to print
     */
    public void print(String txt);
    
    /**
     * Prints informational text to the user
     * @param txt The informational text 
     */
    public void printInfo(String txt);
    
    /**
     * Prints header text to the user
     * @param txt The header text
     */
    public void printHeader(String txt);
    
    /**
     * Prints footer text to the user
     * based on the length of the basis
     * string
     * @param basis 
     */
    public void printFooter(String basis);
    
    /**
     * Prints text for solicitation of the user
     * @param txt The solicitation text
     */
    public void printSolicitation(String txt);
    
    /**
     * Prints error text to the user
     * @param txt The error text
     */
    public void printError(String txt);
    
    /**
     * Retrieves a String from the user
     * @return The aforementioned String
     */
    public String readString();
    
    /**
     * Retrieves a nonempty String from the user
     * @return The aforementioned String
     */
    public String readNonemptyString();
    
    /**
     * Retrieves an int from the user
     * @return The aforementioned int
     */
    public int readInt();
    
    /**
     * Retrieves an int from the user that lies
     * between some minimum and maximum value
     * 
     * Note: Clients are responsible for verifying
     * that the minimum value does not exceed the
     * maximum value
     * 
     * @param min
     * @param max
     * @return The aforementioned int
     */
    public int readInt(int min, int max);
    
    /**
     * Release resources required for this component
     */
    public void close();
}
