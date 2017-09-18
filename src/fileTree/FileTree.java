package fileTree;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Iterator;

import exceptions.AlreadyExistException;
import exceptions.InvalidPathException;
import exceptions.NotDirectoryException;
import exceptions.NotExistException;
import exceptions.NotFileException;
import file.Directory;
import file.File;
import file.Directory.DirectoryIterator;

/**
 * 
 * @author Mohit Singh <mohit.singh@mail.utoronto.ca>
 * @version 1.4
 *
 */
public class FileTree {
  // This will always be the root directory
  private Directory root;
  // This will be the parent of the working directory.
  private Directory parentDir;
  // This will be the working directory
  private Directory workingDir;
  // This will store the path to the working directory.
  private String pathToWorkingDir;
  final private String PATH_TO_ROOT = "/";

  /**
   * This is the default constructor. It creates an empty FileTree object.
   */
  public FileTree() {
    // Create a new root directory
    root = new Directory("root");
    // The root directory has no parent, the workingDir is the root
    parentDir = null;
    workingDir = root;
    // The path is just the root. ('/').
    pathToWorkingDir = PATH_TO_ROOT;
  }

  /**
   * Use this constructor when the root directory has already been made.
   * 
   * @param newName
   */
  public FileTree(Directory d) {
    // Set the root and the workingDir to d. the parentDir is null, as there
    // no parent directory for the root. The path to the root is always "/".
    root = d;
    workingDir = d;
    parentDir = null;
    pathToWorkingDir = PATH_TO_ROOT;
  }

  /**
   * Returns the directory at the given path.
   * 
   * @param path The location of the directory.
   * @throws NotDirectoryException
   */
  public Directory getDirectory(String path) throws NotDirectoryException {
    // Create a temporary directory, this will be what is returned.
    Directory tempDir;
    Object tempObj;
    // Split the path into each directory
    String[] splitPath = path.split("/");
    // If the path is just /, then return the root.
    if (path.equals(PATH_TO_ROOT)) {
      tempDir = root;
    } else {
      // Determine if it is an absolute or relative path.
      if (checkIfAbsolutePath(path)) {
        // Since this path is absolute, we can start it at the root.
        tempDir = root;
        // Remove the first position of the array as it is empty
        splitPath = Arrays.copyOfRange(splitPath, 1, splitPath.length);
      } else {
        // Since this path is relative, we can start it at the working
        // directory.
        tempDir = workingDir;
      }
      // Go through all sub-directories in the path
      for (int i = 0; i < splitPath.length; i++) {
        // Attempt to get the next specified element from the list
        tempObj = tempDir.findElement(splitPath[i]);
        // Check if tempObj is a directory, if not then the path is not valid
        if (tempObj instanceof Directory)
          tempDir = (Directory) tempObj;
        else
          throw new NotDirectoryException(
              "The directory at " + path + " does not exist.");
      }
    }
    return tempDir;
  }


  /**
   * Checks if directory at the given path exists.
   * 
   * @param path The path to check
   * @return hasDir A boolean representing if the directory exists.
   */
  public boolean hasDirectory(String path) {
    // If we can get the directory then it exists.
    Directory temp;
    try {
      getDirectory(path);
      return true;
    } catch (NotDirectoryException e) {
      return false;
    }
  }

  /**
   * Returns the file at the given path.
   * 
   * @param path The location of the file.
   * @throws NotFileException
   */
  public File getFile(String path) throws NotFileException {
    // This will be the directory where the file is held
    Directory tempDir;
    Object tempObj;
    // This is the path without the file name
    String directoryPath;
    // This is the file we are looking for. It will always be the last part.
    String fileName;
    // If the index of a '/' is -1 then there are none, and the file is in the
    // working dir.
    if (path.indexOf('/') == -1) {
      tempDir = workingDir;
      fileName = path;
    }
    // If not, then it is in another directory
    else {
      // This is the path to the directory that holds the file
      directoryPath = path.substring(0, path.lastIndexOf("/"));
      if (directoryPath.equals("")) {
        directoryPath = "/";
      }
      try {
        // Temp now points to the directory that contains the file.
        tempDir = getDirectory(directoryPath);
      } catch (NotDirectoryException e) {
        // This means the file does not exist.
        throw new NotFileException("The file at " + path + " does not exist.");
      }
      // This is the file we are looking for. It will always be the last part.
      fileName = path.substring(path.lastIndexOf("/") + 1);
    }
    // Attempt to get the next specified element from the list
    tempObj = tempDir.findElement(fileName);
    // Check if tempObj is a directory, if not then the path is not valid
    if (tempObj instanceof File)
      return (File) tempObj;
    else {
      // This means the file does not exist.
      throw new NotFileException("The file at " + path + " does not exist.");
    }
  }

  /**
   * Returns the file at the given path.
   * 
   * @param path The location of the file.
   */
  public boolean hasFile(String path) {
    // Initalize variables.
    Directory tempDir = null;
    boolean hasFile = true;
    String directoryPath;
    // This is the file we are creating. It will always be the last part.
    String fileName;
    // If the index of a '/' is -1 then there are none, and the file is in the
    // working dir.
    if (path.indexOf('/') == -1) {
      tempDir = workingDir;
      fileName = path;
    }
    // If not, then it is in another directory
    else {
      // This is the path to the directory that holds the file
      directoryPath = path.substring(0, path.lastIndexOf("/"));
      // Temp now points to the directory that contains the file.
      try {
        tempDir = getDirectory(directoryPath);
      } catch (NotDirectoryException e) {
        hasFile = false;
      }
      fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
    }
    // If the directory that should contain the file exists the hasFile is true
    if (hasFile)
      hasFile = tempDir.isInDirectory(fileName);
    return hasFile;
  }

  /**
   * Creates a file at the given path.
   * 
   * @param path The path it is creating the file at including the file name.
   * @throws NotDirectoryException
   * @throws AlreadyExistException
   */
  public void makeFile(String path)
      throws NotDirectoryException, AlreadyExistException {
    // Initialize variables.
    Directory tempDir;
    File newFile;
    String directoryPath;
    // This is the file we are creating. It will always be the last part.
    String fileName;
    // If the index of a '/' is -1 then there are none, and the file is in the
    // working dir.
    if (path.indexOf('/') == -1) {
      tempDir = workingDir;
      fileName = path;
    }
    // If not, then it is in another directory
    else {
      // This is the path to the directory that holds the file
      directoryPath = path.substring(0, path.lastIndexOf("/"));
      if (directoryPath.equals("")) {
        tempDir = root;
      } else {
        // Temp now points to the directory that contains the file.
        tempDir = getDirectory(directoryPath);
      }
      fileName = path.substring(path.lastIndexOf("/") + 1);
    }
    // Check if the file already exists in this directory.
    if (hasFile(fileName)) {
      throw new AlreadyExistException(
          "A file of the name " + fileName + " already exists.");
    } else {
      // Create a new file with the given name and add it to the dirctory.
      newFile = File.createFile(fileName);
      tempDir.addElement(newFile);
    }
  }

  /**
   * Determines if the given string represents an absolute path or a relative
   * path. Does not determine if the path is valid or not.
   * 
   * @param path The path it is checking.
   * @return isAbsolute A boolean representing if the path is absolute.
   */
  private boolean checkIfAbsolutePath(String path) {
    // A path is absolute iff the first char is an /.
    // return true is the first char is a /, false otherwise.
    if (path.charAt(0) == '/')
      return true;
    return false;
  }

  /**
   * Given an absolute path in the form of a string, return the parent path. If
   * there is no possible parent return just a '/' indicating the root. This
   * does not check if the path actually exists or not.
   */
  private String getPathToParent(String path) {
    // If there is only one "/" and it starts with the dash then the parent is
    // the root
    if ((path.length() - path.replace("/", "").length() == 1)
        && path.startsWith("/")) {
      path = "/";
    } else if (path.equals("/")) {
      path = "/";
    } else if (path.indexOf("/") == -1) {
      path = pathToWorkingDir;
    } else {
      path = path.substring(0, path.lastIndexOf("/"));
    }
    return path;
  }

  public String pathToDir(Directory d) {

    return null;
  }

  /**
   * Returns the path leading to this directory
   */
  public String pwd() {
    // Return the associated string
    return pathToWorkingDir;
  }

  /**
   * Creates n directories at n given paths. The paths can be relative or
   * absolute.
   * 
   * @param paths The paths to make directories at
   * @throws NotDirectoryException
   * @throws AlreadyExistException
   */
  public void mkdir(String[] paths)
      throws NotDirectoryException, AlreadyExistException {
    // This is the parent directory of where the directory is made
    Directory tempParentDir;
    // This is the directory that will be made
    Directory childDir;
    // Directory name
    String dirName = "";
    // Go through all the paths given.
    for (int i = 0; i < paths.length; i++) {
      if (paths[i].lastIndexOf("/") == paths[i].length() - 1) {
        paths[i] = paths[i].substring(0, paths[i].length() - 1);
      }
      // If there are no / then the directory is to be made in the workingDir
      if (paths[i].indexOf("/") == -1) {
        tempParentDir = workingDir;
        dirName = paths[i];
      } else {
        // If there is a / but the path is relative make it absolute
        // so that we can use getPathToParent
        if (!checkIfAbsolutePath(paths[i]))
          // If the working directory is the root, only add a "/"
          if (workingDir.equals(root))
          paths[i] = pathToWorkingDir + paths[i];
          else
          paths[i] = pathToWorkingDir + "/" + paths[i];
        // Get the directory that will contain the new directory
        tempParentDir = getDirectory(getPathToParent(paths[i]));
        // The name of the new directory is from the last / to the end
        // of the String
        dirName = paths[i].substring(paths[i].lastIndexOf("/") + 1,
            paths[i].length());
      }
      // Check if the directory already exists in this directory.
      if (tempParentDir.isInDirectory(dirName)) {
        throw new AlreadyExistException(
            "A directory of the name " + dirName + " already exists.");
      } else {
        // Create the new directory.
        childDir = new Directory(dirName);
        // Add the new directory to the parent directory.
        tempParentDir.addElement(childDir);
      }
    }
  }

  /**
   * Given a boolean representing the r option, and a list of paths, return the
   * contents of the directories. If the r flag is true, then Recursively return
   * the contents.
   * 
   * @param r The boolean flag representing the -r option.
   * @param paths The paths to get the contents of.
   * @return The string to be printed.
   * @throws InvalidPathException
   */
  public String ls(boolean r, String[] paths) throws InvalidPathException {
    String returnString = "";
    Directory tempDir = workingDir;
    // check if any paths are given
    if (paths.length == 0) {
      returnString = "Path at " + pathToWorkingDir + ": \n";
      returnString += getContentsAsString(tempDir);
      if (r) {
        returnString += lsHelper(tempDir, pathToWorkingDir);
      }
    } else {
      // Go through each path given, add the values of each path to the string
      for (int i = 0; i < paths.length; i++) {
        // Check if the path exists
        if (hasDirectory(paths[i])) {
          // Get the indicated path
          try {
            tempDir = getDirectory(paths[i]);
          } catch (NotDirectoryException e) {
            // This will never happen.
          }
          returnString += "Path at " + paths[i] + ": \n";
          returnString += getContentsAsString(tempDir);
          if (r) {
            try {
              returnString += lsHelper(tempDir, paths[i]);
            } catch (InvalidPathException e) {
              // This will never happen.
            }
          }
        } else if (hasFile(paths[i])) {
          // This is if the path indicated a file.
          // If is it a file, we return the path to the file.
          returnString = paths[i] + "\n";
        } else {
          // If the path does not exist return an appropriate message.
          throw new InvalidPathException(
              "The path " + paths[i] + " does not exist.");
        }
      }
    }
    // Remove the last \n for formatting purposes.
    // if (! returnString.equals(""))
    // returnString = returnString.substring(0, returnString.length()-1);
    return returnString;
  }

  /**
   * Given a directory, find all the sub-directories of it, and recurse through
   * them.
   * 
   * @throws InvalidPathException
   */
  private String lsHelper(Directory d, String path)
      throws InvalidPathException {
    String returnString = "";
    List<String> tempLS;
    // Get the names of all sub-directories of the given directory.'
    tempLS = d.getAllSubDirectoryNames();
    // Convert it into an array from a list
    String[] tempStringArray = tempLS.toArray(new String[0]);
    // Format the paths
    for (int i = 0; i < tempStringArray.length; i++) {
      if (path.endsWith("/"))
        tempStringArray[i] = path + tempStringArray[i];
      else
        tempStringArray[i] = path + "/" + tempStringArray[i];
    }
    // Recurse through all the sub-directories
    if (!tempLS.isEmpty())
      returnString += ls(true, tempStringArray);
    return returnString;
  }

  /**
   * Returns the contents of this the given Directory. Return it in the format
   * required by ls.
   * 
   * @param d The directory of which the contents are being returned
   * @return Returns contents of the Directory as a String
   */
  private String getContentsAsString(Directory d) {
    // Initialize variables
    String returnString = "";
    List<String> tempLS = d.getContentNames();
    Iterator<String> tempIter = tempLS.iterator();
    // Go through all of the items of the List. Add them to the string.
    while (tempIter.hasNext())
      returnString += tempIter.next() + "\n";
    return returnString;
  }

  /**
   * Changes the working directory to the given directory
   * 
   * @throws NotDirectoryException
   */
  public void cd(String path) throws NotDirectoryException {
    String[] directoriesInPath;
    // if possible, change the workingDir to it's parent directory,
    // change the parentDir and update the path.
    if (path.equals("..")) {
      // If we are already at the root, don't do anything
      if (!workingDir.equals(root)) {
        workingDir = parentDir;
        pathToWorkingDir = getPathToParent(pathToWorkingDir);
        parentDir = getDirectory(getPathToParent(pathToWorkingDir));
      }
    } // Change the working Directory to the root. There is no parent directory
    // for the root. Also set the path to just a '/'.
    else if (path.equals("/")) {
      workingDir = root;
      parentDir = root;
      pathToWorkingDir = PATH_TO_ROOT;
    } // If the path is equal to a single dot do nothing.
    else if (path.equals(".")) {
    } // Change the working directory to a child directory.
    else if (path.indexOf('/') == -1) {
      if (hasDirectory(path)) {
        // If the working directory is the root, do not add an extra "/"
        if (workingDir.equals(root))
          pathToWorkingDir = pathToWorkingDir + path;
        else
          pathToWorkingDir = pathToWorkingDir + "/" + path;
        // Set the parent and working Directory
        parentDir = workingDir;
        workingDir = getDirectory(pathToWorkingDir);
      } else {
        throw new NotDirectoryException("the path: " + path + " is invalid.");
      }
    } else {
      if (checkIfAbsolutePath(path)) {
        // Set the working dir to the root
        cd("/");
        path = path.substring(1);
      }
      // This is the names of the directories in the path
      directoriesInPath = path.split("/");
      // Change the directory to the next directory in the path.
      for (int i = 0; i < directoriesInPath.length; i++)
        cd(directoriesInPath[i]);
    }
  }

  /**
   * Reads a file from URL and creates a new file object in the current
   * directory
   * 
   * @param inputURL URL where the file is
   * @throws NotFileException
   * @throws AlreadyExistException
   * @throws NotDirectoryException
   * @throws MalformedURLException
   * @throws NotExistException
   */

  public void getFileFromURL(String inputURL)
      throws NotFileException, NotDirectoryException, AlreadyExistException,
      MalformedURLException, NotExistException {
    String fileName;
    URL file = new URL(inputURL);
    fileName = inputURL.substring(inputURL.lastIndexOf("/") + 1);
    // Open the file at the given URL
    Scanner s;
    try {
      s = new Scanner(file.openStream());
    } catch (IOException e) {
      throw new NotExistException();
    }
    this.makeFile(fileName);
    File fileToAdd = this.getFile(fileName);
    // Read through the lines of the file and add them to the fileToAdd object
    while (s.hasNextLine()) {
      fileToAdd.addLine(s.nextLine());
    }
    // Close scanner after use
    s.close();
  }


  /**
   * Given two paths, move the object indicated by path one to the directory
   * located at path two.
   * 
   * @param oldPath The first path. This will be moved
   * @param newPath The second path. This will contain the copy.
   * @throws NotDirectoryException
   * @throws NotFileException
   * @throws InvalidPathException
   * @throws AlreadyExistException
   */
  public void copyToLocation(String oldPath, String newPath)
      throws NotDirectoryException, NotFileException, InvalidPathException,
      AlreadyExistException {
    FileTreeIterator ftIterator;
    if (!hasDirectory(newPath)) {
      throw new NotDirectoryException(
          "The Directory at " + newPath + " does not exist.");
    }
    // Get the indicated directory
    Directory newDir = getDirectory(newPath);
    // Check if the indicated path gives a file or a directory and copy it to
    // the new location.
    if (hasDirectory(oldPath)) {
      Directory oldDir = getDirectory(oldPath);
      // make sure the old path doesn't already contain new path
      ftIterator = getIteratorNotFromRoot(oldDir);
      while (ftIterator.hasNext()) {
        if (oldDir.equals((Directory) ftIterator.next()))
          throw new AlreadyExistException(
              "Can not move a directory" + "into itself.");
      }
      newDir.addElement(oldDir);
    } else if (hasFile(oldPath)) {
      File oldFile = getFile(oldPath);
      newDir.addElement(oldFile);
    } else {
      // If it is neither a directory or an object, then it is nothing.
      throw new InvalidPathException("Nothing exists at the path:" + oldPath);
    }
  }


  /**
   * This method moves old path to new path.
   * 
   * @param oldPath The String representing the old path.
   * @param newPath The String representing the new path.
   */
  public void moveToLocation(String oldPath, String newPath)
      throws NotDirectoryException, NotFileException, InvalidPathException,
      AlreadyExistException {
    FileTreeIterator ftIterator;
    String parentPath = "";
    if (!hasDirectory(newPath)) {
      throw new NotDirectoryException(
          "The Directory at " + newPath + " does not exist.");
    }
    // Get the indicated directory
    Directory newDir = getDirectory(newPath);
    // Check if the indicated path gives a file or a directory and copy it to
    // the new location.
    if (hasDirectory(oldPath)) {
      Directory oldDir = getDirectory(oldPath);
      Directory parentDir = getDirectory(getPathToParent(oldPath));
      parentDir.removeDirectory(oldDir.getName());
      // make sure the old path doesn't already contain new path
      ftIterator = getIteratorNotFromRoot(oldDir);
      while (ftIterator.hasNext()) {
        if (oldDir.equals((Directory) ftIterator.next()))
          throw new AlreadyExistException(
              "Can not move a directory" + "into itself.");
      }
      newDir.addElement(oldDir);
    } else if (hasFile(oldPath)) {
      File oldFile = getFile(oldPath);
      parentPath = getPathToParent(oldPath);
      Directory parentDir = getDirectory(parentPath);
      parentDir.removeFile(oldFile.getName());
      newDir.addElement(oldFile);
    } else {
      // If it is neither a directory or an object, then it is nothing.
      throw new InvalidPathException("Nothing exists at the path:" + oldPath);
    }
  }

  /**
   * This method do grep through the current directory.
   * 
   * @param r
   * @param regex
   * @param paths
   * @return returnString
   * @throws NotFileException
   * @throws NotDirectoryException
   */
  public String grep(boolean r, String regex, String[] paths)
      throws NotFileException, NotDirectoryException {
    String returnString = "";
    FileTreeIterator ftIterator;
    Directory tempDir;
    if (r) {
      // Go through all the file or directories given
      for (int i = 0; i < paths.length; i++) {
        if (hasDirectory(paths[i])) {
          tempDir = getDirectory(paths[i]);
          returnString += grepOneDirectory(regex, tempDir, paths[i]);
          // Check any sub-directories
          ftIterator = this.getIteratorNotFromRoot(tempDir);
          while (ftIterator.hasNext()) {
            // I can directly cast this at the file-tree iterator will only
            // ever return directories.
            tempDir = (Directory) ftIterator.next();
            returnString += grepOneDirectory(regex, tempDir, paths[i]);
          }
        } else if (hasFile(paths[i])) {
          returnString += grepOneFile(regex, getFile(paths[i]), paths[i]);
        } else {
          // throw something.
        }
      }
    } else {
      // Go through all the files given
      for (int i = 0; i < paths.length; i++) {
        returnString += grepOneFile(regex, getFile(paths[i]), paths[i]);
      }
    }
    if (!returnString.equals(""))
      returnString = returnString.substring(0, returnString.length() - 1);
    return returnString;
  }

  /**
   * This method do grep through one directly.
   * 
   * @param r
   * @param regex
   * @param paths
   * @return returnString
   * @throws NotFileException
   * @throws NotDirectoryException
   */
  private String grepOneDirectory(String regex, Directory d, String path)
      throws NotFileException {
    // Given one directory return the grep for all files in it.
    // Not recursive.
    List<File> allFiles;
    Iterator<File> fileIterator;
    String returnString = "";
    File tempF;
    String tempPath = "";
    if (!path.equals("/"))
      path = path + "/" + d.getName();
    else
      path = path + d.getName();
    allFiles = d.getFiles();
    fileIterator = allFiles.iterator();
    while (fileIterator.hasNext()) {
      tempF = fileIterator.next();
      // set the path name
      tempPath = path + "/" + tempF.getName();
      returnString += grepOneFile(regex, tempF, path);
    }
    return returnString;
  }


  /**
   * This method do grep through one file.
   * 
   * @param r
   * @param regex
   * @param paths
   * @return returnString
   * @throws NotFileException
   * @throws NotDirectoryException
   */
  private String grepOneFile(String regex, File file, String path)
      throws NotFileException {
    String returnString = "";
    String tempString = "";
    Pattern p = Pattern.compile(regex);
    ArrayList<String> data;
    Iterator<String> iterator;
    Matcher m;
    // get the data from the file
    data = file.getData();
    // Use an iterator to loop through all the lines
    iterator = data.iterator();
    while (iterator.hasNext()) {
      // If the line matches the regex, add it to the return string.
      tempString = iterator.next();
      m = p.matcher(tempString);
      if (m.matches()) {
        returnString += path + ": " + tempString + "\n";
      }
    }
    return returnString;
  }

  public FileTreeIterator getIteratorFromRoot() {
    // create a new FileTreeIterator object
    FileTreeIterator iterator = new FileTreeIterator(root);
    return iterator;
  }

  public FileTreeIterator getIteratorNotFromRoot(Directory d) {
    // create a new FileTreeIterator object
    FileTreeIterator iterator = new FileTreeIterator(d);
    return iterator;
  }

  private class FileTreeIterator implements Iterator<Object> {

    // Declare instance variables.
    private Directory currDir;
    private ArrayList<Directory> directories = new ArrayList<Directory>();
    private Iterator<Directory> directoryIterator;

    private void getAllDirectories(Directory d) {
      List<Directory> tempLS;
      Iterator<Directory> tempitr;
      Directory tempDir;
      // Get all the sub-directories for this directory
      tempLS = d.getSubDirectories();
      tempitr = tempLS.iterator();
      // recurse through all the sub-directories adding their sub-directories
      // as well.
      while (tempitr.hasNext()) {
        tempDir = tempitr.next();
        directories.add(tempDir);
        getAllDirectories(tempDir);
      }
    }

    public FileTreeIterator(Directory d) {
      getAllDirectories(d);
      directoryIterator = directories.iterator();
    }

    public boolean hasNext() {
      return directoryIterator.hasNext();
    }

    public Object next() {
      currDir = directoryIterator.next();
      return currDir;
    }

    public void remove() {

    }
  }
}
