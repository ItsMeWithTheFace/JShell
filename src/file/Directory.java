package file;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Kexin Hu 1002095813
 * @version 2.0
 * 
 */

import file.File;

public class Directory {
  // a list contains all the content in the file
  private List<Object> content;
  // instantiate a file class
  private String dirName;

  /**
   * constructor of directory
   * 
   * @param name The name of the directory wanted to create.
   */
  public Directory(String name) {

    dirName = name;
    this.content = new ArrayList<Object>();

  }


  /**
   * Retrieves the name of the directory
   * 
   * @return Name Name of the directory
   */
  public String getName() {
    return dirName;
  }

  /**
   * Set the name of the directory object
   * 
   * @param Name Potential name of the directory
   */
  public void setName(String name) {
    this.dirName = name;
  }


  /**
   * The method returns the content list of the directory.
   * 
   * @return content The content list of the directory.
   * 
   */
  public List<Object> getContent() {
    return content;
  }


  /**
   * This method returns whether a file or directory with the given name is
   * already exist in the directory.
   * 
   * @param name The name of the file or directory wanted to find.
   * @return result Whether there is a element in the wanted name.
   * 
   */
  public boolean isInDirectory(String name) {
    // set a variable to trace the result
    boolean result = false;
    // loop through all the element in the list
    for (int i = 0; i < content.size() && result == false; i++) {
      // reach the element
      Object currentObject = content.get(i);
      // get the name of the element
      // if the type if the element is directory
      String objectName;
      if (currentObject instanceof Directory) {
        objectName = ((Directory) currentObject).getName();
      } else {
        objectName = ((File) currentObject).getName();
      }
      // if the name of the element we are checking equals to the name we want,
      // change result to true
      if (objectName.equals(name)) {
        result = true;
      }
    }
    return result;
  }


  /**
   * This method returns the element having given name in the directory. If the
   * element is not found, return null.
   * 
   * @param name The name of the file or directory wanted to find.
   * @return element The wanted element in the directory.
   * 
   */
  public Object findElement(String name) {
    // set a variable for return
    Object element = null;

    // loop through all the element in the list
    for (int i = 0; i < content.size() && element == null; i++) {
      // reach the element
      Object currentObject = content.get(i);
      // get the name of the element
      // if the type if the element is directory
      String objectName;
      if (currentObject instanceof Directory) {
        objectName = ((Directory) currentObject).getName();
      } else {
        objectName = ((File) currentObject).getName();
      }
      // if the name of the element we are checking equals to the name we want,
      // set result element to current element
      if (objectName.equals(name)) {
        element = currentObject;
      }
    }
    return element;
  }


  /**
   * This method returns a list which contains names of all the file and
   * directory in the current directory.
   * 
   * @return contentName The list containing names of all contents.
   * 
   */
  public List<String> getContentNames() {
    // create a list to store the names
    List<String> contentName = new ArrayList<String>();
    // loop through all the elements in the directory
    for (int i = 0; i < content.size(); i++) {
      // reach the element
      Object currentObject = content.get(i);
      // copy the name into the result list
      if (currentObject instanceof Directory) {
        contentName.add(((Directory) currentObject).getName());
      } else {
        contentName.add(((File) currentObject).getName());
      }
    }
    return contentName;
  }


  /**
   * This method returns a list which contains names of all sub-directories in
   * the current directory
   * 
   * @return contentName The list containing names of all sub-directory
   * 
   */
  public List<String> getAllSubDirectoryNames() {
    // create a list to store the names
    List<String> nameList = new ArrayList<String>();
    // loop through all the elements in the directory
    for (int i = 0; i < content.size(); i++) {
      // reach the element
      Object currentObject = content.get(i);
      // check the type of the element, if it is a directory, then copy its name
      // and get into the directory
      if (currentObject instanceof Directory) {
        String subDirName = ((Directory) currentObject).getName();
        nameList.add(subDirName);
      }
    }
    return nameList;
  }


  /**
   * This method add the given file or directory into the current directory.
   * 
   * @param newElement The element wanted to add into the directory.
   * 
   */
  public void addElement(Object newElement) {
    // add the new element into the content list
    content.add(newElement);
  }


  /**
   * This method get into a subclass with the given name in current directory.
   * 
   * @param dirName The name of the sub-directory wanted to get into.
   * @return subDirectory The wanted sub-directory.
   * 
   */
  public Directory toSubDirectory(String dirName) {
    // create a local variable for the sub-directory
    Directory subDirectory = null;
    // loop through all contents in the directory
    for (int i = 0; i < content.size(); i++) {
      // reach the element
      Object currentObject = content.get(i);
      // check the type of the element
      // if it is a directory, then check whether the name is same as dirName
      if (currentObject instanceof Directory) {
        if (((Directory) currentObject).getName() == dirName) {
          // then we find the wanted sub-directory
          subDirectory = (Directory) currentObject;
        }
      }
    }
    return (Directory) subDirectory;
  }


  /**
   * This method returns a array, where the first entry shows the type of the
   * element (can be "Directory" or "File"), the second entry shows the name of
   * the element.
   * 
   * @param element The element wanted to check.
   * @return result The array containing wanted information.
   * 
   */
  private String[] getTypeAndName(Object element) {
    // set variable to store type and name
    String type;
    String name;

    // case 1: it is a Directory
    if (element instanceof Directory) {
      type = "Directory";
      name = ((Directory) element).getName();
    }
    // case 2: it is a File
    else {
      type = "File";
      name = ((File) element).getName();
    }

    // create the return array and return
    String[] result = {type, name};
    return result;
  }


  /**
   * This method returns whether the given directory has equal content to the
   * current directory.
   * 
   * @param d2 The directory wanted to compare with.
   * @return result Whether this directory equals to the passed in directory.
   * 
   */
  public boolean equals(Directory d2) {
    // set a variable for the result boolean
    boolean result = true;

    // get the content list of the passed in directory
    List<Object> d2Content = d2.getContent();

    // first check if the number of the elements are same
    result = result && (content.size() == d2Content.size());

    // If the size is same, then check the actual contents.
    // Loop through all the elements in this directory and try to find the same
    // content in d2.
    for (int i = 0; result == true && i < content.size(); i++) {
      // step 1: get one element in this directory
      Object elementThis = content.get(i);

      // step 2: get the type and name of the element
      String[] thisTypeAndName = getTypeAndName(elementThis);
      String thisType = thisTypeAndName[0];
      String thisName = thisTypeAndName[1];

      // step 3: try to fine the element with same name in d2
      Object d2Element = d2.findElement(thisName);

      // step 4: if we found the element, check if the type is same
      if (d2Element != null) {
        // case 1: should be directory (else a file)
        if (thisType == "Directory") {
          // check type, then the content
          if (d2Element instanceof File) {
            result = false;
          } else {
            ;
            result = result
                && ((Directory) elementThis).equals(((Directory) d2Element));
          }
        } else {
          // check type, then the content
          if (d2Element instanceof Directory) {
            result = false;
          } else {
            result = result && ((File) elementThis).equals(((File) d2Element));
          }
        }
      } else {
        result = false;
      }
    }
    return result;
  }


  /**
   * This method removes element in the current directory with a given index.
   * 
   */
  public void remove(int i) {
    if (i < content.size()) {
      content.remove(i);
    }
  }


  /**
   * This method removes a sub-directory in the current directory with a given
   * name.
   * 
   */
  public void removeDirectory(String name) {
    // loop through all the elements in the directory
    for (int i = 0; i < content.size(); i++) {
      // reach the element
      Object currentObject = content.get(i);
      // check the type of the element
      if (currentObject instanceof Directory) {
        // if it is a directory, then check its name
        if (((Directory) currentObject).getName() == name) {
          content.remove(i);
        }
      }
    }
  }


  /**
   * This method removes a file in the current directory with a given name.
   * 
   */
  public void removeFile(String name) {
    // loop through all the elements in the directory
    for (int i = 0; i < content.size(); i++) {
      // reach the element
      Object currentObject = content.get(i);
      // check the type of the element
      if (currentObject instanceof File) {
        // if it is a directory, then check its name
        if (((File) currentObject).getName() == name) {
          content.remove(i);
        }
      }
    }
  }


  /**
   * This method returns a list contains all the sub-directory objects in the
   * current directory. Not recursively.
   * 
   * @return directories The list contains all the sub-directories.
   * 
   */
  public List<Directory> getSubDirectories() {
    // Initialize temp. variables needed for this.
    List<Object> content = this.getContent();
    List<Directory> directories = new ArrayList<Directory>();
    Object tempObject = new Object();
    Iterator<Object> contentIterator = content.iterator();
    // Go through all the items in this directory
    while (contentIterator.hasNext()) {
      tempObject = contentIterator.next();
      // If it is a directory add it to the array list
      if (tempObject instanceof Directory) {
        directories.add((Directory) tempObject);
      }
    }
    return directories;
  }


  /**
   * This method returns a list contains all the File objects in the current
   * directory. Not recursively.
   * 
   * @return files The list contains all the files.
   * 
   */
  public List<File> getFiles() {
    // Initialize temp. variables needed for this.
    List<Object> content = this.getContent();
    List<File> files = new ArrayList<File>();
    Object tempObject = new Object();
    Iterator<Object> contentIterator = content.iterator();
    // Go through all the items in this directory
    while (contentIterator.hasNext()) {
      tempObject = contentIterator.next();
      // If it is a file add it to the array list
      if (tempObject instanceof File) {
        files.add((File) tempObject);
      }
    }
    return files;
  }


  /**
   * This method returns whether the current directories contained a
   * sub-directory. Not recursively.
   * 
   * @return result Whether the current directories contained a sub-directory.
   * 
   */
  public boolean hasSubDirectories() {
    // Initialize temp. variables needed for this.
    List<Object> content = this.getContent();
    Object tempObject = new Object();
    Iterator<Object> contentIterator = content.iterator();
    // Go through all the items in this directory
    while (contentIterator.hasNext()) {
      tempObject = contentIterator.next();
      // If it is a directory return True.
      if (tempObject instanceof Directory) {
        return true;
      }
    }
    return false;
  }


  /**
   * This method returns a DirectoryIterator.
   * 
   * @return iterator A new DirectoryIterator object.
   * 
   */
  public DirectoryIterator createIterator() {
    // create a new DirectoryIterator object
    DirectoryIterator iterator = new DirectoryIterator();
    return iterator;
  }


  public class DirectoryIterator implements Iterator<Object> {

    // set a pointer for the position of next element in the Directory
    private int pointer;

    public DirectoryIterator() {
      pointer = 0;
    }

    public boolean hasNext() {
      // pointer should smaller than the last index in the content list
      return (pointer < content.size());
    }

    public Object next() {
      Object nextElement = content.get(pointer);
      pointer++;
      return nextElement;
    }
    
    public void remove() {}

  }

}
