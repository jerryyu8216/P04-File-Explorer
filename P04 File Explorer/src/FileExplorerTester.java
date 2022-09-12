//////////////// P04 File Explorer //////////////////////////
//
// Title: FileExplorerTester
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
import java.util.List;
import java.util.Arrays;

public class FileExplorerTester {
  /**
   * Main method to call the test methods
   * 
   * @param args - input arguments if any
   */
  public static void main(String[] args) {
    File f = new File("cs300");
    System.out.println("testListContents: " + testListContents(f));
    System.out.println("testDeepListBaseCase: " + testDeepListBaseCase(f));
    System.out.println("testDeepListRecursiveCase: " + testDeepListRecursiveCase(f));
    System.out.println("testSearchByKeyBaseCase: " + testSearchByKeyBaseCase(f));
    System.out.println("testSearchByKeyRecursiveCase: " + testSearchByKeyRecursiveCase(f));
    System.out.println("testSearchByFileName: " + testSearchByFileName(f));
  }

  /**
   * Checks whether the the FileExplorer.listContents() method outputs the correct ArrayLists and
   * throws NotDirectoryExceptions when it is supposed to
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testListContents(File folder) {
    try {
      // Scenario 1
      // list the basic contents of the cs300 folder
      ArrayList<String> listContent = FileExplorer.listContents(folder);
      // expected output content
      String[] contents = new String[] {"grades", "lecture notes", "programs",
          "quizzes preparation", "reading notes", "syllabus.txt", "todo.txt"};
      List<String> expectedList = Arrays.asList(contents);
      // check the size of the output
      if (listContent.size() != 7) {
        System.out.println("Problem detected: cs300 folder must contain 7 elements.");
        return false;
      }
      // Checks the contents of the output
      for (int i = 0; i < expectedList.size(); i++) {
        if (!listContent.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
              + " is missing from the output of the list contents of cs300 folder.");
          return false;
        }
      }

      // Scenario 2 - list the contents of the grades folder
      File f = new File(folder.getPath() + File.separator + "grades");
      listContent = FileExplorer.listContents(f);
      if (listContent.size() != 0) {
        System.out.println("Problem detected: grades folder must be empty.");
        return false;
      }
      // Scenario 3 - list the contents of the p02 folder
      f = new File(folder.getPath() + File.separator + "programs" + File.separator + "p02");
      listContent = FileExplorer.listContents(f);
      if (listContent.size() != 1 || !listContent.contains("WisconsinPrairie.java")) {
        System.out.println("Problem detected: p02 folder must contain only one file named "
            + "WisconsinPrairie.java.");
        return false;
      }
      // Scenario 4 - Try to list the contents of a file
      f = new File(folder.getPath() + File.separator + "todo.txt");
      try {
        listContent = FileExplorer.listContents(f);
        System.out.println("Problem detected: Your FileExplorer.listContents() must "
            + "throw a NotDirectoryException if it is provided an input which is not"
            + "a directory.");
        return false;
      } catch (NotDirectoryException e) { // catch only the expected exception
        // Expected behavior -- no problem detected
      }
      // Scenario 5 - Try to list the contents of not found directory/file
      f = new File(folder.getPath() + File.separator + "music.txt");
      try {
        listContent = FileExplorer.listContents(f);
        System.out.println("Problem detected: Your FileExplorer.listContents() must "
            + "throw a NotDirectoryException if the provided File does not exist.");
        return false;
      } catch (NotDirectoryException e) {
        // catch only the expected exception to be thrown -- no problem detected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.listContents() has thrown"
          + " a non expected exception.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Checks whether the the FileExplorer.deepListContents() method outputs the correct ArrayLists
   * and throws NotDirectoryExceptions when it is supposed to for a situation that does not require
   * any recursive calls
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testDeepListBaseCase(File folder) {
    try {
      // Scenario 1 - Try to list the contents of the Reading Notes Folder - no recursive cases
      File f = new File(folder.getPath() + File.separator + "reading notes");
      // List all of the contents of the reading notes folder
      ArrayList<String> deepListContent = FileExplorer.deepListContents(f);
      // Expected contents of the reading notes folder
      String[] contents =
          new String[] {"zyBooksCh1.txt", "zyBooksCh2.txt", "zyBooksCh3.txt", "zyBooksCh4.txt"};
      // Puts expected contents into a list
      List<String> expectedList = Arrays.asList(contents);
      // Checks the size of the output
      if (deepListContent.size() != 4) {
        System.out.println("Problem detected: reading notes folder must contain 4 elements.");
        return false;
      }
      // Checks the contents of the output
      for (int i = 0; i < expectedList.size(); i++) {
        if (!deepListContent.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
              + " is missing from the output of the list contents of reading notes folder.");
          return false;
        }
      }
      // Scenario 2 - Try to list the contents of a file that is not a directory
      f = new File(folder.getPath() + File.separator + "todo.txt");
      try {
        deepListContent = FileExplorer.deepListContents(f);
        System.out.println("Problem detected: Your FileExplorer.deepListContents() must "
            + "throw a NotDirectoryException if it is provided an input which is not"
            + "a directory.");
        return false;
      } catch (NotDirectoryException e) { // catch only the expected exception
        // Expected behavior -- no problem detected
      }
      // Scenario 3 - Try to list the contents of a file that does not exist
      f = new File(folder.getPath() + File.separator + "music.txt");
      try {
        deepListContent = FileExplorer.deepListContents(f);
        System.out.println("Problem detected: Your FileExplorer.deepListContents() must "
            + "throw a NotDirectoryException if the provided File does not exist.");
        return false;
      } catch (NotDirectoryException e) {
        // catch only the expected exception to be thrown -- no problem detected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.deepListContents() has thrown"
          + "a non expected exception");
    }
    return true;
  }

  /**
   * Checks whether the the FileExplorer.deepListContents() method outputs the correct ArrayLists
   * and throws NotDirectoryExceptions when it is supposed to for situations that does require
   * recursive calls
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testDeepListRecursiveCase(File folder) {
    try {
      // Scenario 1 - Try to list all of the deep contents of the cs300 folder
      // List all of the deep contents of the cs300 folder
      ArrayList<String> deepListContent = FileExplorer.deepListContents(folder);
      // Expected contents of the cs300 folder
      String[] contents = new String[] {"AlgorithmAnalysis.txt", "Benchmarker.java",
          "COVIDTracker.java", "COVIDTrackerTester.java", "ComparisonMethods.java",
          "ExceptionHandling.txt", "Program01.pdf", "Program02.pdf", "Program03.pdf",
          "Recursion.txt", "UsingObjectsAndArrayLists.txt", "WisconsinPrairie.java",
          "codeSamples.java", "outline.txt", "proceduralProgramming.txt", "syllabus.txt",
          "todo.txt", "zyBooksCh1.txt", "zyBooksCh2.txt", "zyBooksCh3.txt", "zyBooksCh4.txt"};
      // Puts expected contents into a list
      List<String> expectedList = Arrays.asList(contents);
      // Check the size of the output
      if (deepListContent.size() != 21) {
        System.out.println("Problem detected: cs300 folder must contain 21 elements.");
        return false;
      }
      // Check the contents of the output
      for (int i = 0; i < expectedList.size(); i++) {
        if (!deepListContent.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
              + " is missing from the output of the list contents of cs300 folder.");
          return false;
        }
      }
      // Scenario 2 - Try to list the contents of a File that is not a directory
      File f = new File(folder.getPath() + File.separator + "todo.txt");
      try {
        deepListContent = FileExplorer.listContents(f);
        System.out.println("Problem detected: Your FileExplorer.deepListContents() must "
            + "throw a NotDirectoryException if it is provided an input which is not"
            + "a directory.");
        return false;
      } catch (NotDirectoryException e) { // catch only the expected exception
        // Expected behavior -- no problem detected
      }
      // Scenario 3 - Try to list the contents of a File that does not exist
      f = new File(folder.getPath() + File.separator + "music.txt");
      try {
        deepListContent = FileExplorer.listContents(f);
        System.out.println("Problem detected: Your FileExplorer.deepListContents() must "
            + "throw a NotDirectoryException if the provided File does not exist.");
        return false;
      } catch (NotDirectoryException e) {
        // catch only the expected exception to be thrown -- no problem detected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.deepListContents() has thrown"
          + "a non expected exception");
    }
    return true;
  }

  /**
   * Checks whether the recursive method FileExplorer.searchByFileName() method outputs the correct
   * path and throws NoSuchElementExceptions when it is supposed to
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSearchByFileName(File folder) {
    // Scenario 1 - Try to find the file "syllabus.txt"
    // Search for the file: "syllabus.txt"
    String testPath = FileExplorer.searchByName(folder, "syllabus.txt");
    // Expected path for "syllabus.txt"
    String expected = "cs300/syllabus.txt";
    // Checks if the path that is outputted is correct
    if (!testPath.equals(expected)) {
      System.out.println("Problem detected: Expected output did not match actual output");
      return false;
    }
    // Scenario 2 - Try to search for a file with null as the fileName
    try {
      FileExplorer.searchByName(folder, null);
      System.out.println("Problem detected: Your FileExplorer.searchByName() must "
          + "throw a NoSuchElementException if the fileName is null");
      return false;
    } catch (NoSuchElementException e) {// catch only the expected exception
      // Expected behavior -- no problem detected
    }
    // Scenario 3 - Try to search for a file within a file that is not a directory
    File f = new File(folder.getPath() + File.separator + "todo.txt");
    try {
      FileExplorer.searchByName(f, "syllabus.txt");
      System.out.println("Problem detected: Your FileExplorer.searchByName() must "
          + "throw a NoSuchElementException if it is provided an input which is not"
          + "a directory.");
      return false;
    } catch (NoSuchElementException e) {// catch only the expected exception
      // Expected behavior -- no problem detected
    }
    // Scenario 4 - Try to search for a file that does not exist
    try {
      FileExplorer.searchByName(folder, "stuff");
      System.out.println("Problem detected: Your FileExplorer.searchByName() must "
          + "throw a NoSuchElementException if the file name is not found in the directory");
      return false;
    } catch (NoSuchElementException e) {// catch only the expected exception
      // Expected behavior -- no problem detected
    }
    return true;
  }

  /**
   * Checks whether the method FileExplorer.searchByKey() method outputs the correct ArrayList for
   * situations with no recursive calls
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSearchByKeyBaseCase(File folder) {
    // Scenario 1 - Search for files with ".txt" in the reading notes folder
    // Searches for files with ".txt" in the reading notes folder
    File f = new File(folder.getPath() + File.separator + "reading notes");
    ArrayList<String> deepListContent = FileExplorer.searchByKey(f, ".txt");
    // Expected contents with ".txt" in the reading notes folder
    String[] contents =
        new String[] {"zyBooksCh1.txt", "zyBooksCh2.txt", "zyBooksCh3.txt", "zyBooksCh4.txt"};
    // Puts expected contents into a list
    List<String> expectedList = Arrays.asList(contents);
    // Check the size of the output
    if (deepListContent.size() != 4) {
      System.out.println("Problem detected: reading notes folder must contain 4 elements.");
      return false;
    }
    // Check the contents of the output
    for (int i = 0; i < expectedList.size(); i++) {
      if (!deepListContent.contains(expectedList.get(i))) {
        System.out.println("Problem detected: " + expectedList.get(i)
            + " is missing from the output of the list contents of reading notes folder.");
        return false;
      }
    }
    return true;
  }

  /**
   * Checks whether the method FileExplorer.searchByKey() method outputs the correct ArrayList for
   * situations with recursive calls
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSearchByKeyRecursiveCase(File folder) {
    // Scenario 1 - Search for files with".txt" in the cs300 folder
    // Searches for files with ".txt" in the cs300 folder
    ArrayList<String> searchByKeyList = FileExplorer.searchByKey(folder, ".txt");
    // Searches for files with "stuff" in the cs300 folder
    ArrayList<String> empty = FileExplorer.searchByKey(folder, "stuff");
    // Expected contents with ".txt" in the cs300 folder
    String[] contents = new String[] {"todo.txt", "AlgorithmAnalysis.txt", "Recursion.txt",
        "proceduralProgramming.txt", "UsingObjectsAndArrayLists.txt", "ExceptionHandling.txt",
        "outline.txt", "zyBooksCh3.txt", "zyBooksCh2.txt", "zyBooksCh1.txt", "zyBooksCh4.txt",
        "syllabus.txt"};
    // Puts expected contents into a list
    List<String> expectedList = Arrays.asList(contents);
    // Check the size of the output
    if (searchByKeyList.size() != 12) {
      System.out.println(
          "Problem detected: cs300 folder must contain 12 elements with " + "the specified key.");
      return false;
    }
    // Check the size of the output
    if (empty.size() > 0) {
      System.out.println(
          "Problem detected: cs300 folder should not contain any elements " + "with specified key");
      return false;
    }
    // Check the contents of the output
    for (int i = 0; i < expectedList.size(); i++) {
      if (!searchByKeyList.contains(expectedList.get(i))) {
        System.out.println("Problem detected: " + expectedList.get(i)
            + " is missing from the output of the list contents of cs300 folder.");
        return false;
      }
    }
    return true;
  }
}

