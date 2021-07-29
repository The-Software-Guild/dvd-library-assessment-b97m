/**
 *
 * @author Benjamin Munoz
 * email: driver396@gmail.com
 * date: Jul 29, 2021
 * purpose: Act as starting point of the whole application
 */

package com.bm.dvdlibrary;

import com.bm.dvdlibrary.controller.DVDLibraryController;
import com.bm.dvdlibrary.dao.DVDLibraryDao;
import com.bm.dvdlibrary.dao.DVDLibraryDaoImpl;
import com.bm.dvdlibrary.ui.DVDLibraryView;
import com.bm.dvdlibrary.ui.UserIOImpl;
import java.util.Scanner;

/**
 * Acts as the starting point of the whole application
 * @author Benjamin Munoz
 */
public class App {
    public static void main(String[] args) {
        DVDLibraryDao dao = new DVDLibraryDaoImpl();
        DVDLibraryView view = new DVDLibraryView(
            new UserIOImpl(new Scanner(System.in))
        );
        
        DVDLibraryController controller = new DVDLibraryController(dao, view);
        
        controller.run();
    }
}
