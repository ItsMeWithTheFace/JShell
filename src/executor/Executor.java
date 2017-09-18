package executor;

import java.net.MalformedURLException;
import java.util.ArrayList;
import fileTree.*;
import dirStack.*;
import outputter.*;
import exceptions.*;

/**
 * Author: Jack Seebach Version: 2.0
 *
 */

public class Executor {

  // This FileTree object will hold all the files, directories and their
  // locations.
  private FileTree fileTree = new FileTree();

  // This dirStack object will hold the directory stack for pushd and popd.
  private DirStack stack = new DirStack();

  // This dirStack object will hold the directory stack for pushd and popd.
  private Manual man = new Manual();

  // This ErrorMessage object will print the error messages if they are caught.
  private ErrorMessage error = new ErrorMessage();

  // This ArrayList<String> object will hold all of the commands as they are
  // sent by the user
  private ArrayList<String> cmdHistory = new ArrayList<String>();


  public Executor() {}

  /**
   * Exits the shell
   * 
   * @param args Array of strings taken from the shell
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] exitShell(String[] args) {
    // do nothing, the shell will exit on its own
    return(new Object[] {});
  }

  /**
   * Makes a directory (or multiple) based off of the arguments given
   * 
   * @param args Array of strings taken from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] makeDirectory(String[] args) {
    // we only need the arguments after the first one
    try {
      // pass the mkdir method the paths
      fileTree.mkdir(args);
      // catch exceptions
    } catch (NotDirectoryException e) {
      error.notDir("");
    } catch (AlreadyExistException f) {
      error.alreadyExist("");
    }
    // return output
    return(new Object[] {});
  }

  /**
   * Changes the directory to the path given in the argument
   * 
   * @param args Array of strings taken from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] changeDirectory(String[] args) {
    try {
      // pass the cd method the path to change the directory to
      fileTree.cd(args[0]);
      // catch exceptions
    } catch (NotDirectoryException e) {
      error.notDir(args[0]);
    }
    // return output
    return(new Object[] {});
  }

  /**
   * prints the contents of the working directory if none given, or the
   * directory (or more) given in the arguments if provided
   * 
   * @param args Array of strings taken from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] printContents(String[] args) {
    // look for the rflag
    Object[] results = lookForRFlag(args);
    // extract the results
    args = (String[]) results[0];
    boolean rFlag = (boolean) results[1];
    String toOutput = "";
    try {
      // pass the ls method the paths
       toOutput = fileTree.ls(rFlag, args);
      
      // catch exceptions
    } catch (InvalidPathException e) {
      error.notDir("");
    }
    // return output
    return(new Object[] {toOutput.substring(0, toOutput.length() - 1),
        fileTree});
  }

  /**
   * Prints the filepath of the working directory
   * 
   * @param args Array of strings taken from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] printWorkingDirectory(String[] args) {
    // call the pwd method to get the path then return it
    return(new Object[] {fileTree.pwd(), fileTree});
  }

  /**
   * pushes the working directory to the stack, and changes the working
   * directory to the one given in the arguments
   * 
   * @param args Array of strings taken from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] pushDirectory(String[] args) {
    // push the current working directory to the stack
    stack.push(fileTree.pwd());
    // change directory to the argument
    try {
      fileTree.cd(args[0]);
      // catch exceptions
    } catch (NotDirectoryException e) {
      error.notDir(args[0]);
    }
    // return output
    return(new Object[] {});
  }

  /**
   * pops the top directory off of the stack and makes it the working directory
   * 
   * @param args Array of strings taken from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] popDirectory(String[] args) {
    // check if the stack is empty
    if (stack.isEmpty()) {
      error.emptyStack();
    }
    // if not, pop the stack and make it the current directory
    else {
      try {
        fileTree.cd(stack.pop());
        // catch exceptions
      } catch (NotDirectoryException e) {
      }
    }
    // return output
    return(new Object[] {});
  }

  /**
   * Prints the history of commands
   * 
   * @param args Array of strings taken from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] printHistory(String[] args) {
    // declare variables
    int size;
    // if there is a size, make it that size
    if (args.length > 0) {
      size = Integer.parseInt((String) args[0]);
      // cap the size if it is more than the history has
      if (size >= cmdHistory.size()) {
        size = cmdHistory.size();
      }
    } else {
      size = cmdHistory.size();
    }
    // loop through all the commands, starting at the requested size
    // minus the total size, up to the total size
    String toOutput = "";
    for (int j = cmdHistory.size() - size; j < cmdHistory.size(); j++) {
      // add up all the strings to output
      toOutput += ((j + 1) + ". " + cmdHistory.get(j) + "\n");
    }
    toOutput = toOutput.substring(0, toOutput.lastIndexOf("\n"));
    // return output
    return(new Object[] {toOutput, fileTree});
  }

  /**
   * Prints the contents of the file(s) given in the arguments
   * 
   * @param args Array of strings taken from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] printFileContents(String[] args) {
    // loop through all the file paths
    String toOutput = "";
    for (int i = 0; i < args.length - 1; i++) {
      try {
        // get and print the files
        toOutput += (fileTree.getFile(args[i]).cat());
        // catch exceptions
      } catch (NotFileException e) {
        error.notFile(args[i]);
      }
      // add 3 newlines to seperate the files
      toOutput += ("\n\n\n");
    }
    // Don't add the 3 newlines to end of last file
    try {
      // get and print the last file
      toOutput += (fileTree.getFile(args[args.length - 1]).cat());
      // catch exceptions
    } catch (NotFileException e) {
      error.notFile(args[args.length - 1]);;
    }
    // return output
    return(new Object[] {toOutput, fileTree});
  }

  /**
   * Returns the first argument it is given (the string to return)
   * 
   * @param args Array of strings taken from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] printString(String[] args) {
    // separate out the string from all arguments
    String cmdString = args[0];
    // return output
    return(new Object[] {cmdString, fileTree});
  }

  /**
   * Prints the manual of the command given in the arguments
   * 
   * @param args Array of strings taken from the shell
   */
  public Object[] printManual(String[] args) {
    String toPrint = "";
    try {
      // print the command from the man class
      toPrint = man.command(args[0]);
      // catch exceptions
    } catch (InvalidManualException e) {
      error.invalidManual(args[0]);
    }

    return(new Object[] {toPrint, fileTree});
  }

  /**
   * Gets a file from a URL and then creates a file out of it in the current
   * working directory
   * 
   * @param args String in the form of a valid URL
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] copyURL(String[] args) {
    try {
      // try to get the file given and save it
      fileTree.getFileFromURL(args[0]);
      /// catch exceptiosn
    } catch (MalformedURLException| NotFileException | NotDirectoryException
        | AlreadyExistException | NotExistException e) {
      System.out.println("File does not exist on this webpage");
    }
    // return output
    return(new Object[] {});
  }

  /**
   * finds a string from a regex expression and a file. Optional -r param can
   * be used to recursivly search for files in a given directory
   * @param args Array of strings from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] findString(String[] args) {
    // check for the rFlag
    Object[] results = lookForRFlag(args);
    /// save the results from the method call
    args = (String[]) results[0];
    boolean rFlag = (boolean) results[1];
    // get array of paths
    String[] newArgs = new String[args.length - 1];
    for (int i = 0; i < newArgs.length; i++) {
      newArgs[i] = args[i + 1];
    }
    // seperate out the regex component
    String regex = args[0], toOutput = "";
    args = newArgs;
    try {
      // call the grep command
      toOutput = fileTree.grep(rFlag, regex, args);
      // catch errors
    } catch (NotFileException e) {
      error.notFile("");
    } catch (NotDirectoryException e1){
      error.notDir("");
    }
    //return output
    return(new Object[] {toOutput, fileTree});
  }
  
  /**
   * copies 1 file/directory to another of the same type
   * @param args Array of strings from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] copy(String[] args){
    // try to call the command with path 1 and 2
    try {
      fileTree.copyToLocation(args[0], args[1]);
      // catch errors
    } catch (NotDirectoryException e) {
      error.notDir("");
    } catch (NotFileException e) {
      error.notFile("");
    } catch (InvalidPathException e) {
      error.notExist("");
    } catch (AlreadyExistException e){
      error.alreadyExist("");
    }
    // return output
    return(new Object[] {});
  }
  
  /**
   * moves 1 file/directory to another of the same type
   * @param args Array of strings from the shell
   * 
   * @return an Object array consisting of a string that the command returned
   * and a fileTree for the printer to use
   */
  public Object[] move(String[] args){
    // try to call the command
    try {
      fileTree.moveToLocation(args[0], args[1]);
      // catch errors
    } catch (NotDirectoryException e) {
      error.notDir("");
    } catch (NotFileException e) {
      error.notFile("");
    } catch (InvalidPathException e) {
      error.notExist("");
    } catch (AlreadyExistException e){
      error.alreadyExist("");
    }
    // return output
    return(new Object[] {});
  }

  /**
   * returns the working directory
   * 
   * @return a string which is a representation of the working directory
   */
  public String getWorkingDirectory() {
    // return the working directory
    return fileTree.pwd();
  }

  /**
   * Saves what it is sent to the list of commands sent
   * @param raw input to save
   * 
   */
  public void addInputToList(String input) {
    // save the input
    cmdHistory.add(input);
    
    /**
     * Searches for a arg with "-r" and returns an object with properties
     * change if the r flag exists
     * 
     * @param args Array of strings from the calling command
     * 
     * @return an Object array consisting of a boolean value indicating if the
     * flag exists and an array of arguments with the r flag removed if it
     * exists
     */
  }
  private Object[] lookForRFlag(String[] args){
 // initialize variables
    boolean rFlag = false;
    // check if arguments exist
    if (args.length > 0) {
      // if the -r argument exists, make the flag true
      if (args[0].equalsIgnoreCase("-r")) {
        rFlag = true;
        // get rid of the -r element
        String[] newArgs = new String[args.length - 1];
        for (int i = 0; i < newArgs.length; i++) {
          newArgs[i] = args[i + 1];
        }
        // overwrite the args if new ones were made
        args = newArgs;
      }
    }
    // return output
    return(new Object[] {args, rFlag});
  }
}
