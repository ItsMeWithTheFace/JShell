// **********************************************************
// Assignment2:
// Student1: Rakin Uddin
// UTORID user_name: uddinrak
// UT Student #: 1002365362
// Author: Rakin Uddin
//
// Student2: Kexin Hu
// UTORID user_name: hukexin
// UT Student #: 1002095813
// Author: Kexin Hu
//
// Student3: Jack Seebach
// UTORID user_name: seebachj
// UT Student #: 1002532146
// Author: Jack Seebach
//
// Student4: Mohit Singh
// UTORID user_name: singhm78
// UT Student #: 1002243850
// Author: Mohit Singh
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

package driver;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;

import commandParser.*;
import executor.*;
import outputter.*;
import errorHandling.*;
/*
 * Author: Jack Seebach
 * Version: 3.2
 *
 */


public class JShell {

  //This Hashtable will hold all the commands and method names to call
  // for the executor class
  private static Hashtable<String, String> commands 
  = new Hashtable<String, String>();

  //This Hashtable will hold all the commands and method names to call for
  // the validator class
  private static Hashtable<String, String> validationCmds 
  = new Hashtable<String, String>();
  

  /**
   * Asks the user for input, and splits the input into command and args
   * @param String 
   * @param ArrayList 
   */
  public static void main(String[] args) {
    // Initialize variables
    Scanner userInput = new Scanner(System.in);
    String input = "";
    // to hold the history and arguments respectively
    ArrayList<String> cmdHistory = new ArrayList<String>();
    // to call methods
    Executor commandExecutor = new Executor();
    Validator cmdValidator = new Validator();
    // to fill the Hashtables
    populateTable();
    // for the validator
    boolean valid = true;
    ErrorMessage error = new ErrorMessage();
    CommandParser parser = new CommandParser();
    String[] parsedInput = {""};
    Printer printer = new Printer();
    // While the user wants to keep entering commands
    do{
      System.out.print(commandExecutor.getWorkingDirectory() +": ");
      // Read and cleanup the input
      input = userInput.nextLine();
      input = input.trim();
      
      // add the input to the historyArray
      cmdHistory.add(input);
      
   // add the input to the executor
      commandExecutor.addInputToList(input);
      
      while(input.startsWith("!")){
        input = exclamationCommand(cmdHistory, input);
        }
      

      // parse the input
      parsedInput = parser.parseString(input);
      // seperate out the first element as the printer doesnt need it
      String[] printerArray = new String[parsedInput.length - 1];
      for(int i = 0; i < printerArray.length; i++){
        printerArray[i] = parsedInput[i + 1]; 
      }
      // make a new array for the executor
      String[] argumentsArray = new String[printerArray.length];
      argumentsArray = printerArray;
      // if it is possible for the arguments to have redirection
      if(printerArray.length > 1){
        // if there is redirection
        if(printerArray[printerArray.length - 2].contains(">")){
          // remove the elements with redirection and path
          String[] tempArray = new String[printerArray.length - 2];
          for(int i = 0; i < tempArray.length; i++){
            tempArray[i] = printerArray[i];
          }
          // set the array back
          argumentsArray = tempArray;
        }
      }
      
      //declare the method
      Method validateMethod;
      // get the method with the class name of the value of the string command
      // this method call will check if the command syntax is valid
      try {
        validateMethod = cmdValidator.getClass().getDeclaredMethod(
            validationCmds.get(parsedInput[0]), printerArray.getClass());
        try {
          // call the method that was retrived above
          valid = (boolean) validateMethod.invoke(cmdValidator, 
              new Object[] {printerArray});
         // catch exceptions if the command is not valid etc.
        } catch (IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
        } 
      } catch (NoSuchMethodException | SecurityException |
          NullPointerException e) {
      }
      // if  the syntax is valid
      Object[] results = {};
      if(valid){
        Method runMethod;
        try {
          // get the method with the class name of the value of the str command
          // this method call will execute what needs to be done to accomplish
          // the requested command from the user
          runMethod = commandExecutor.getClass().getDeclaredMethod(
              commands.get(parsedInput[0]), argumentsArray.getClass());
          try {
            // call the method retrived above
            results = (Object[]) runMethod.invoke(commandExecutor,
                new Object[] {argumentsArray});
            // catch any possible exceptions
          } catch (IllegalAccessException | IllegalArgumentException
              | InvocationTargetException e) {
          }
        } catch (NoSuchMethodException | SecurityException e) {
          // catch if the method does not exist
        } catch (NullPointerException e){
          error.unknownCommand(parsedInput[0]);
        }
      }
      else
        System.out.println("JShell: Invalid syntax error");
      // if there is printing to be done
      if(results.length != 0){
        // open the returned package and save the elements
        Object[] printingPackage = new Object[results.length + 1];
        printingPackage[0] = results[0];
        printingPackage[1] = results[1];
        // give the printer the original arguments
        printingPackage[2] = printerArray;
        // send the package off to the printer
        printer.print(printingPackage);
      }
      
    }while (!parsedInput[0].equals("exit"));
  }

  
  /**
   * returns the String in the ArrayList<> at input[1];
   * 
   * @param cmdHistory An ArrayList of all commands typed in.
   * @param a vaild command of the form !<int>
   */

  private static String exclamationCommand (ArrayList<String> cmdHistory,
      String input){

    // declare variables
    String cmdNum = input.substring(1);
    String output = "";
    // get the command that was said cmdNum ago from cmdHistory and return it
        try{
          output = cmdHistory.get(Integer.parseInt(cmdNum) - 1);
        }
        catch(IndexOutOfBoundsException | NegativeArraySizeException e){
          System.out.println("That command number has not been reached");
          output = "Error";
        } catch (NumberFormatException e){
          output = "Error";
        }
        return output;

  }
  private static void populateTable(){
    // create a hashtable with all the commands
    commands.put("exit", "exitShell");
    commands.put("mkdir", "makeDirectory");
    commands.put("cd", "changeDirectory");
    commands.put("ls", "printContents");
    commands.put("pwd", "printWorkingDirectory");
    commands.put("pushd", "pushDirectory");
    commands.put("popd", "popDirectory");
    commands.put("history", "printHistory");
    commands.put("cat", "printFileContents");
    commands.put("echo", "printString");
    commands.put("man", "printManual");
    commands.put("curl", "copyURL");
    commands.put("grep", "findString");
    commands.put("cp", "copy");
    commands.put("mv", "move");

    validationCmds.put("mkdir", "validateMkdir");
    validationCmds.put("cd", "validateCd");
    validationCmds.put("ls", "validateLs");
    validationCmds.put("pwd", "validatePwd");
    validationCmds.put("pushd", "validatePushd");
    validationCmds.put("popd", "validatePopd");
    validationCmds.put("cat", "validateCat");
    validationCmds.put("man", "validateMan");
    validationCmds.put("echo", "validateEcho");
    validationCmds.put("cp", "validateCp");
    validationCmds.put("mv", "validateMv");
    validationCmds.put("curl", "validateCurl");
    validationCmds.put("grep", "validateGrep");
    
  }
}
