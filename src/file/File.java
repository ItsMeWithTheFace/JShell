package file;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Rakin Uddin <rakin.uddin@mail.utoronto.ca>
 * @version 1.2
 *
 */

public class File {
  // name of the file
  private String name;
  // list to hold data in lines
  private ArrayList<String> data;

  /**
   * Default constructor
   * 
   * @param newName
   */
  private File(String newName) {
    // set the name to be what user defines and create a new list to store
    // file's data
    this.name = newName;
    this.data = new ArrayList<String>();
  }


  /**
   * Static method to call the constructor
   * 
   * @param newName The name of the file
   */
  public static File createFile(String newName) {
    return new File(newName);
  }

  /**
   * Get the name of the file object
   * 
   * @return A string representing the name of the file
   */
  public String getName() {
    return name;
  }

  /**
   * Mutate the name of the file
   * 
   * @param setName The new name to be set for the file
   */
  public void setName(String setName) {
    // Special chars that are not allowed to be in the new name
    String[] specialChars =
        new String[] {"<", ">", ":", "\\", "\"", "|", "?", "*", " "};
    int specCharCount = 0;
    // Check if new name contains special chars above or is .. or ., which are
    // also prohibited names
    for (int i = 0; i < specialChars.length; i++) {
      if (setName.contains(specialChars[i]) || setName == ".."
          || setName == ".") {
        specCharCount++;
      }
    }
    // Mutate the old name into the new name iff there are no special chars
    if (specCharCount == 0)
      this.name = setName;
  }

  /**
   * Returns the data of a file object
   * 
   * @return An arraylist of strings representing the lines of data in file
   */
  public ArrayList<String> getData() {
    return data;
  }

  /**
   * Appends a new line to the end of the file's data
   * 
   * @param newData A string representing one line of the file's data
   */
  public void addLine(String newData) {
    this.data.add(newData);
  }

  /**
   * Returns a string consisting of file's contents with a new line for each
   * line
   * 
   * @return String representing the file's contents
   */
  public String cat() {
    String returnString = "";
    // Add contents of file to the return string along with a new line
    for (int i = 0; i < this.data.size() - 1; i++) {
      returnString += this.data.get(i) + "\n";
    }
    // No new line for last string
    if (this.data.size() != 0)
      returnString += this.data.get(this.data.size() - 1);
    return returnString;
  }

  /**
   * Deletes all the contents of file
   */
  public void clearFile() {
    // Clear the arraylist
    this.data.clear();
  }

  /**
   * Determines if two files are equal in terms of name and data
   * 
   * @param file2 Second file object to compare
   * @return True iff the data and name of file2 is equal to the current file
   */
  public boolean equals(File file2) {
    // First check if the names are equal
    if (this.getName().equals(file2.getName())) {
      // Then check if each line is equal
      // If any difference found, return false
      if (!(this.getData().equals(file2.getData())))
        return false;
      // No differences found, return true
      else
        return true;
    }
    // If names are not the same, then immediately false
    else
      return false;
  }

  public class LineIterator implements Iterator<String> {
    // counter to go through lines
    private int counter;
    
    public LineIterator () {
      counter = 0;
    }
    
    public boolean hasNext(){
      // counter should always be less than the number of lines
      return (counter < data.size());
    }
    
    public String next() {
      String returnElement = data.get(counter);
      counter++;
      return (returnElement);
    }
    
    public void remove() {}
  }
  
}
