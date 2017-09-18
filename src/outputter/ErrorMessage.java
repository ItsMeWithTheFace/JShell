package outputter;


/**
 * 
 * @author Kexin Hu
 * @version 1.0
 *
 */

public class ErrorMessage {

  /**
   * Constructor for a ErrorMessage
   */
  public ErrorMessage() {}


  /**
   * The method print an error message indicating the error of passing in an
   * invalid command.
   * 
   * @param errorCommand The string that is not valid command.
   */
  public void unknownCommand(String errorCommand) {
    // print out the invalid command and explanation sentence
    System.out.println("Jshell: " + errorCommand + ": Command not found");

  }


  /**
   * The method print an error message indicating the error of accessing to a
   * nonexistent file or directory.
   * 
   * @param fileOrDir The name of a nonexistent file or directory.
   */
  public void notExist(String fileOrDir) {
    // print out names of a nonexistent file or directory and explanation
    // sentence
    System.out.println("Jshell: " + fileOrDir + ": No such file or directory");

  }


  /**
   * The method print an error message indicating the error of trying to create
   * a file or directory having the same name with a existent file or directory
   * 
   * @param name The repetitive name of the file or directory.
   */
  public void alreadyExist(String name) {
    // print out names of a nonexistent file or directory and explanation
    // sentence
    System.out.println("Jshell: " + name + ": File or directory exists");

  }


  /**
   * The method print an error message indicating the error of passing in a
   * value that is not a file name, when requiring a file name.
   * 
   * @param notFileName The string that is not a file name.
   */
  public void notFile(String notFileName) {
    // print out names of a nonexistent file or directory and explanation
    // sentence
    System.out.println("Jshell: " + notFileName + ": Not a file");

  }

  /**
   * The method print an error message indicating the error of passing in a
   * value that is not a directory name, when requiring a directory name.
   * 
   * @param notDirName The string that is not a file name.
   */
  public void notDir(String notDirName) {
    // print out names of a nonexistent file or directory and explanation
    // sentence
    System.out.println("Jshell: " + notDirName + ": Not a directory");

  }


  /**
   * The method print an error message indicating the error of passing in a
   * value that is out of range.
   */
  public void outRange() {
    // print out the error message
    System.out.println("Jshell: " + "Value out of range");

  }


  /**
   * The method print an error message indicating the error of trying to call
   * popd methods when the directory stack is empty.
   * 
   */
  public void emptyStack() {
    // print out the error message
    System.out.println("Jshell: " + "The directory stack is empty");

  }


  /**
   * The method print an error message indicating the error of trying to name a
   * directory or a file with special characters.
   */
  public void specialCharacter() {
    // print out the error message
    System.out.println("Jshell: " + "Special characters are not allowed");

  }


  /**
   * The method print an error message indicating the error of trying trying to
   * get the manual for a non-existent command.
   * 
   * @param command The command that does not has a manual entry.
   */
  public void invalidManual(String command) {
    // print out the error message
    System.out.println("Jshell: " + "No manual entry for " + command);

  }


  /**
   * The method print an error message indicating the error of passing in a
   * invalid object for the argument.
   * 
   * @param object The name of the passed-in invalid object.
   */
  public void invalidObject(String object) {
    // print out the error message
    System.out.println("Jshell: " + object + ": Not a valid object");

  }


}

