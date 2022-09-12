//////////////// P04 File Explorer //////////////////////////
//
// Title: FileExplorer
// Course: CS 300 Fall 2020
//
// Author: Jerry Yu
// Email: jcyu4@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////
import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FileExplorer {
  /**
   * Public method that lists the contents of the file given
   * 
   * @param currentFolder - a directory whose contents is to be listed
   * @return an ArrayList of Strings of the names of the contents of the file
   * @throws NotDirectoryException if the file given is not a directory or does not exist
   */
  public static ArrayList<String> listContents(File currentFolder) throws NotDirectoryException {
    // Lists the files from the folder given
    String[] names = currentFolder.list();
    // Creates new ArrayList to store the names of the files
    ArrayList<String> namesList = new ArrayList<String>();
    // Throws NotDirectoryException if the file given does not exist or is not a directory
    if (currentFolder.isDirectory() == false) {
      throw new NotDirectoryException("Does not exist or is not a directory");
    }
    // Adds the contents of the string array into the ArrayList
    for (int i = 0; i < names.length; i++) {
      namesList.add(names[i]);
    }
    // Returns the ArrayList with all of the names of the files within the given file
    return namesList;
  }

  /**
   * Public recursive method that searches through the directory and all sub directories and lists
   * all the names of the files
   * 
   * @param currentFolder - a directory whose non directory files is to be listed
   * @return an ArrayList of Strings of the names of all of the files
   * @throws NotDirectoryException if the file given is not a directory or does not exist
   */
  public static ArrayList<String> deepListContents(File currentFolder)
      throws NotDirectoryException {
    // Creates an ArrayList to store all of the names
    ArrayList<String> filesList = new ArrayList<String>();
    // Creates a File array with all of the files within the given directory
    File[] files = currentFolder.listFiles();
    // Throws NotDirectoryException if the file given is not a directory or does not exist
    if (currentFolder.isDirectory() == false) {
      throw new NotDirectoryException("Does not exist or is not a directory");
    }
    // Iterates through a for loop and adds all non directories to the ArrayList
    for (int i = 0; i < files.length; i++) {
      if (files[i].isDirectory() == false) {
        filesList.add(files[i].getName());
      }
      // Recursively calls the method if a directory is found to search the sub directories
      else {
        File f = new File(currentFolder + File.separator + files[i].getName());
        // Creates an ArrayList that contains all of the contents from the recursive calls
        ArrayList<String> add = new ArrayList<String>(deepListContents(f));
        // Adds all of the contents from the recursive calls to the filesList ArrayList
        filesList.addAll(add);
      }
    }
    // Returns an ArrayList with all of the non directory files within the given directory
    return filesList;
  }

  /**
   * Public method that searches the directory for a file that matches the given file name
   * 
   * @param currentFolder - a directory that is to be searched
   * @param fileName      - the name of the file that the method is searching for
   * @return the path of the file if the correct file is found
   * @throws NoSuchElementException if the file name is null,the file was not found, or if
   *                                currentFolder is not a directory
   */
  public static String searchByName(File currentFolder, String fileName) {
    // Check that the file name is not null and throws a NoSuchElementException if it is
    if (fileName == null) {
      throw new NoSuchElementException("Error: File name was null");
    }
    // Check that currentFolder is a directory
    if (currentFolder.isDirectory()) {
      // Calls the recursive private method search to search the directory for the given file name
      String path = search(currentFolder, fileName);
      // Returns the path to the file if the file was found
      if (path != null) {
        return path;
      }
      // Throws a NoSucheElementException if the file was not found
      else {
        throw new NoSuchElementException("Error: File was not found");
      }
    }
    // Throws a NoSuchElementException if currentFolder is not a directory
    else {
      throw new NoSuchElementException("Error: File was not a directory");
    }
  }

  /**
   * Private recursive method that searches the given directory for a file with the given file name
   * 
   * @param currentFolder - a directory that is to be searched
   * @param fileName      - the name of the file that is being searched for
   * @return a string with the path if the file was found or null if the file was not found
   */
  private static String search(File currentFolder, String fileName) {
    // Creates a file array that lists the files of the directory currentFolder
    File[] files = currentFolder.listFiles();
    // Iterates through the file array to find a file with the same name as the given file name
    for (int i = 0; i < files.length; i++) {
      if (files[i].getName().equals(fileName)) {
        // Returns the path to the file if the file is found
        return files[i].getPath();
      }
      // Recursively calls the method if there are sub directories within the given directory
      File f = new File(currentFolder + File.separator + files[i].getName());
      if (files[i].isDirectory() == true) {
        if (search(f, fileName) != null) {
          // returns the results of the search
          return search(f, fileName);
        }
      }
    }
    // returns null if the file was not found
    return null;
  }

  /**
   * Public recursive method that searches the given directory for any files that have the given key
   * and puts them into an ArrayList to be returned
   * 
   * @param currentFolder - a directory that is to be searched
   * @param key           - the string that you are searching for
   * @return an ArrayList of Strings of the names of the files that contain the key
   */
  public static ArrayList<String> searchByKey(File currentFolder, String key) {
    // Creates the ArrayList that will hold all of the file names with the key in it
    ArrayList<String> filesList = new ArrayList<String>();
    // Creates a File array that lists all of the contents of the directory
    File[] files = currentFolder.listFiles();
    // Iterates through the File array to see if any of the names contain the key
    for (int i = 0; i < files.length; i++) {
      // If the file name contains the key then it is added to the ArrayList filesList
      if (files[i].getName().contains(key)) {
        filesList.add(files[i].getName());
      }
      // Recursively calls the method if a directory is found to search all of the sub directories
      if (files[i].isDirectory() == true) {
        File f = new File(currentFolder + File.separator + files[i].getName());
        // Creates a new ArrayList with the results of the recursive calls
        ArrayList<String> add = new ArrayList<String>(searchByKey(f, key));
        // Adds all of the contents of the recursive calls to the ArrayList filesList
        filesList.addAll(add);
      }
    }
    // returns an ArrayList of Strings of the names of the files that contain the key
    return filesList;
  }

  /**
   * Public recursive method that searches the given directory for any files whose size is within
   * the given parameters
   * 
   * @param currentFolder - a directory that is to be searched
   * @param sizeMin       - the lower boundary of bytes the file can contain
   * @param sizeMax       - the upper boundary of bytes the file can contain
   * @return an ArrayList of Strings of the names of the files whose size is within the given
   *         parameters
   */
  public static ArrayList<String> searchBySize(File currentFolder, long sizeMin, long sizeMax) {
    // Creates an ArrayList that will hold all of the file names whose size is within the range
    ArrayList<String> filesList = new ArrayList<String>();
    // Creates a file array of all the files from the given directory
    File[] files = currentFolder.listFiles();
    // Iterates through the file array to find files whose size is within the range
    for (int i = 0; i < files.length; i++) {
      // Recursively calls the method if a directory is found to search all of the sub directories
      if (files[i].isDirectory() == true) {
        File f = new File(currentFolder + File.separator + files[i].getName());
        // Creates a new ArrayList with the results of the recursive callse
        ArrayList<String> add = new ArrayList<String>(searchBySize(f, sizeMin, sizeMax));
        // Adds all of the contents of the recursive calls to the ArrayList filesList
        filesList.addAll(add);
      }
      // Checks if the file size is within the range and adds them to the ArrayList if they are
      else if (files[i].length() > sizeMin && files[i].length() < sizeMax) {
        filesList.add(files[i].getName());
      }
    }
    // returns an ArrayList of Strings of the names of the files that are within the range
    return filesList;
  }
}
