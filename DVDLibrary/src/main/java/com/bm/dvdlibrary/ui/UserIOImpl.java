/**
 * @author Benjamin Munoz
 * email: driver396@gmail.com
 * date: Jul 29, 2021
 * purpose: Console implementation of the UserIO interface
 */

package com.bm.dvdlibrary.ui;

import java.util.Scanner;

/**
 * An implementation of the UserIO interface
 * based on console interactions
 * 
 * @author Benjamin Munoz
 */
public class UserIOImpl implements UserIO {

    private Scanner uInput;

    /**
     * Constructs a new instance of this class
     * using a Scanner to the console
     * 
     * @param uInput 
     */
    public UserIOImpl(Scanner uInput) {
        this.uInput = uInput;
    }
        
    @Override
    public void print(String txt) {
        System.out.println(txt);
    }

    @Override
    public void printInfo(String txt) {
        System.out.format("|| %s ||%n", txt);
    }

    @Override
    public void printHeader(String txt) {
        System.out.format("/| %s |\\%n", txt);
    }

    @Override
    public void printFooter(String basis) {
        System.out.format("\\| %s |/%n", "-".repeat(basis.length()));
    }

    @Override
    public void printSolicitation(String txt) {
        System.out.format("-- %s --%n", txt);
    }

    @Override
    public void printError(String txt) {
        System.out.format("!! %s !!%n", txt);
    }

    @Override
    public String readString() {
        return uInput.nextLine();
    }

    @Override
    public String readNonemptyString() {
        String receivedString = uInput.nextLine();
        while (receivedString.length() <= 0) {
            printError("The input must not be empty");
            printSolicitation("Please provide nonempty input");
            receivedString = uInput.nextLine();
        }
        return receivedString;
    }

    @Override
    public int readInt() {
        boolean notAnInt = true;
        int receivedInt = -1;
        while (notAnInt) {
            try {
                receivedInt = Integer.parseInt(uInput.nextLine());
                notAnInt = false;
            } catch (NumberFormatException ex) {
                printError("The input could not be converted");
                printError("to a suitable integer           ");
                printSolicitation("Please provide an integer");
            }
        }
        return receivedInt;
    }

    @Override
    public int readInt(int min, int max) {
        boolean notValidInt = true;
        int receivedInt = -1;
        while (notValidInt) {
            try {
                receivedInt = Integer.parseInt(uInput.nextLine());
                notValidInt = !(min <= receivedInt && receivedInt <= max);
                if (notValidInt) {
                    printError("The input must be between " + min + " and " + max);
                    printSolicitation("Please provide such input");
                }
            } catch (NumberFormatException ex) {
                printError("The input could not be converted");
                printError("to a suitable integer           ");
                printSolicitation("Please provide an integer");
            }
        }
        return receivedInt;
    }

    @Override
    public void close() {
        uInput.close();
    }
}
