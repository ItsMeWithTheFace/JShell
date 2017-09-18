package errorHandling;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Validator {

  /**
   * Default contructor
   */
  public Validator() {

  }

  /**
   * Checks whether a string s contains special characters or not
   * 
   * @param s The input string
   * @return true if string contains a special char, false otherwise
   */
  private boolean containsSpecialChars(String s) {
    // Make an array to store the special characters
    String[] specialChars = new String[] {"!", "@", "$", "&", "(", ")", "?",
        "`", "'", "=", "{", "}", "<", ">", ":", "\\", "\"", "|", "?", "*", " "};
    // Check if input string contains any of the special chars in array
    // Return true if so
    for (int i = 0; i < specialChars.length; i++) {
      if (s.contains(specialChars[i]))
        return true;
    }
    // Otherwise string does not have special characters, so return false
    return false;
  }

  /**
   * Checks if a string is in valid directory path notation
   * 
   * @param path String representing an absolute or relative directory path
   * @return True if string is in a valid path syntax, false otherwise
   */
  private boolean checkIfValidPath(String path) {
    // Initialize a status variable as true (going through tests will make it
    // false
    boolean status = true;
    // Parse the path according to the character "/"
    String[] splitPath = path.split("/");

    // If any of the directory names contain special characters, immediately
    // falsify the status variable
    for (int i = 0; i < splitPath.length; i++) {
      if (containsSpecialChars(splitPath[i]))
        status = false;
    }
    // Return the status variable
    return status;
  }

  /**
   * Checks the if the file name and redirection key character are present and
   * in the right order
   * 
   * ASSUMES ARROW IS THE FIRST ELEMENT IN THE ARRAY
   * 
   * @param args Array of strings, assumes arrow is the first element
   * @return true iff the order and content of the elements comply with the
   *         redirection syntax
   */
  private boolean checkRedirectionSyntax(String[] args) {
    // For the redirection syntax, there can only be the arrow and file path
    if (args.length != 2)
      return false;
    // Check if first character is an arrow and that the second element is a
    // valid path
    else {
      if (args[0].equals(">") || args[0].equals(">>")) {
      } else
        return false;
      if (!checkIfValidPath(args[1]))
        return false;
      return true;
    }
  }

  /**
   * Checks if the array of arguments contains exactly one arrow
   * 
   * @param args Array of strings
   * @return true iff there exists exactly one arrow in the array of arguments
   */
  private boolean containsOneArrow(String[] args) {
    return ((Arrays.asList(args).contains(">"))
        ^ (Arrays.asList(args).contains(">>")));
  }

  /**
   * Parses the arrow and the elements after it into a separate array
   * 
   * ASSUMES EXACTLY ONE ARROW IS PRESENT
   * 
   * @param args Array of strings representing the general set of arguments for
   *        a command
   * @return An array of strings containing the arrow and its arguments
   */
  private String[] parseArrowCommand(String[] args) {
    // variable to hold index of the arrow
    int indexOfArrow;
    // find the index of type > or >> and assign its index to indexOfArrow
    indexOfArrow = (Arrays.asList(args).indexOf(">") != -1)
        ? Arrays.asList(args).indexOf(">") : Arrays.asList(args).indexOf(">>");
    // return the splice of arrow to the end of the array (including arrow)
    return (Arrays.copyOfRange(args, indexOfArrow, args.length));
  }

  /**
   * Parses an array of arguments from the beginning of an array to an arrow
   * operator
   * 
   * ASSUMES EXACTLY ONE ARROW IS PRESENT IN ARRAY
   * 
   * @param args Array of strings representing the general arguments of a
   *        command
   * @return An array of strings containing the arguments before the arrow
   *         operator, which are the arguments of the command
   */
  private String[] parseArgumentCommand(String[] args) {
    // variable to hold the index of the arrow operator
    int indexOfArrow;
    // find index of arrow and assign it to indexOfArrow
    indexOfArrow = (Arrays.asList(args).indexOf(">") != -1)
        ? Arrays.asList(args).indexOf(">") : Arrays.asList(args).indexOf(">>");
    // return splice from beginning of array to index of arrow (excluding arrow)
    return (Arrays.copyOfRange(args, 0, indexOfArrow));
  }

  /**
   * Determines if the list of arguments are the right syntactic format for the
   * mkdir command
   * 
   * @param arguments Array of strings taken from the shell
   * @return True iff all the arguments are valid directory paths
   */
  public boolean validateMkdir(String[] arguments) {
    // Create a variable to hold the syntactic status of the arguments
    boolean status = true;
    if (containsOneArrow(arguments)) {
      status = checkRedirectionSyntax(parseArrowCommand(arguments));
      arguments = parseArgumentCommand(arguments);
    }
    // Make sure the number of arguments is at least 1
    if (arguments.length < 1)
      status = false;
    // Loop through arguments and check if each of them are valid directory
    // paths
    for (int i = 0; i < arguments.length; i++) {
      if (checkIfValidPath(arguments[i]) == false)
        status = false;
    }
    return status;
  }

  /**
   * Checks if the right number of arguments for the command cd and if they are
   * of the proper format
   * 
   * @param arguments An array of strings representing the arguments for cd
   * @return True if the arguments are of the proper syntax for the command cd,
   *         else false
   */
  public boolean validateCd(String[] arguments) {
    // Create variable to hold validation result
    boolean status;
    // check if an arrow is present in the arguments
    if (containsOneArrow(arguments)) {
      // parse the commands and change the arguments arrays to leave out the
      // arrow and its outfile
      status = checkRedirectionSyntax(parseArrowCommand(arguments));
      arguments = parseArgumentCommand(arguments);
    }

    // If the number of arguments is not 1, then result is immediately false
    if (arguments.length != 1)
      status = false;
    // Else it should be a path, so check if it is a valid absolute or relative
    // path
    else {
      if (arguments[0] == "." || arguments[0] == "..")
        status = true;
      else
        status = checkIfValidPath(arguments[0]);
    }
    // Return the validation result
    return status;
  }

  /**
   * Checks if arguments for the command ls are of a valid form
   * 
   * @param arguments Array of strings representing the arguments taken from the
   *        shell
   * @return True iff the arguments are syntactically correct for the command ls
   */
  public boolean validateLs(String[] arguments) {
    // Initialize a status variable
    boolean status = true;
    // check if an arrow is present in the arguments
    if (containsOneArrow(arguments)) {
      // parse the commands and change the arguments arrays to leave out the
      // arrow and its outfile
      status = checkRedirectionSyntax(parseArrowCommand(arguments));
      arguments = parseArgumentCommand(arguments);
    }
    // If -r is present remove it
    if(arguments.length > 0){
      if (arguments[0].equalsIgnoreCase("-r"))
        arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
    }
    // There must be at least 0 or more arguments, if there are more than 0
    // check if arguments are valid paths
    if (arguments.length >= 0) {
      for (int i = 0; i < arguments.length; i++)
        if (checkIfValidPath(arguments[i]) == false)
          status = false;
    }
    // If not, immediately falsify status
    else
      status = false;

    return status;
  }

  /**
   * Validates the arguments for the pwd command
   * 
   * @param arguments An array of strings representing the arguments taken in by
   *        the shell
   * @return True if syntactically the arguments are valid for pwd
   */
  public boolean validatePwd(String[] arguments) {
    // initialize status variable
    boolean status = true;
    // check if an arrow is present in the arguments
    if (containsOneArrow(arguments)) {
      // parse the commands and change the arguments arrays to leave out the
      // arrow and its outfile
      status = checkRedirectionSyntax(parseArrowCommand(arguments));
      arguments = parseArgumentCommand(arguments);
    }
    // Check if the number of arguments is 0
    return ((arguments.length == 0) && status);
  }

  /**
   * Validates the arguments for the popd command
   * 
   * @param arguments An array of strings representing the arguments taken in by
   *        the shell
   * @return True if syntactically the arguments are valid for popd
   */
  public boolean validatePopD(String[] arguments) {
    // Use pwd's validation since popd also takes no arguments
    return validatePwd(arguments);
  }

  /**
   * Check if the pushd's arguments are syntactically correct
   * 
   * @param arguments An array of strings representing the arguments for pushd
   * @return True if the arguments are of the proper syntax for the command cd,
   *         else false
   */
  public boolean validatePushD(String[] arguments) {
    // Since pushd takes in a directory it is the same as cd's validation
    return validateCd(arguments);
  }

  /**
   * Checks if cat's arguments are syntactially correct
   * 
   * @param arguments An array of strings representing the arguments from the
   *        shell
   * @return True iff there exists at least one valid argument
   */
  public boolean validateCat(String[] arguments) {
    // Same as mkdir's validation, in which at least one file must be present
    // (either a relative or full path) and more than one file may optionally
    // be present
    return validateMkdir(arguments);
  }

  /**
   * Validates the arguments of echo, checks if it is of the form echo "String"
   * >/>> filename
   * 
   * @param arguments Array of strings taken from the shell
   * @return True iff the argument syntax is as outlined in the description
   */
  public boolean validateEcho(String[] arguments) {
    // If no arguments present, return false immediately
    if (arguments.length == 0)
      return false;
    boolean status = true;
    // If there is an arrow present, remove that and its args and check
    // separately
    if (containsOneArrow(arguments)) {
      status = checkRedirectionSyntax(parseArrowCommand(arguments));
      arguments = parseArgumentCommand(arguments);
    }
    // if there is more than one string, return false
    if (arguments.length != 1)
      return false;
    return status;
  }

  /**
   * Check if a file name is valid (contains no special characters)
   * 
   * @param arguments Array of strings that represent commands taken from the
   *        shell
   * @return True iff the file name contains no special characters
   */
  public boolean validateFile(String[] arguments) {
    // Make sure only one argument is passed in
    if (arguments.length != 1)
      return false;
    else
      // Just check if it contains any special characters
      return checkIfValidPath(arguments[0]);
  }

  /**
   * Checks if the arguments for man are correct
   * 
   * @param arguments An array of strings representing the arguments from the
   *        shell
   * @return True iff there is only argument and that argument is a valid
   *         command
   */
  public boolean validateMan(String[] arguments) {
    // Possible commands supported by our shell
    String[] commands = new String[] {"exit", "mkdir", "cd", "ls", "pwd",
        "pushd", "popd", "history", "cat", "echo", "man", "grep"};

    boolean status = false;
    boolean statusRedirect = true;
    if (containsOneArrow(arguments)) {
      statusRedirect = checkRedirectionSyntax(parseArrowCommand(arguments));
      arguments = parseArgumentCommand(arguments);
    }
    // If the number of arguments is not 1, return false immediately
    if (arguments.length != 1)
      return false;
    // Otherwise, check if the argument matches any of the commands above
    else {
      for (int i = 0; i < commands.length; i++) {
        if (arguments[0].equals(commands[i]))
          status = true;
      }
      // Return false if no match found
      return (status && statusRedirect);
    }
  }

  /**
   * Checks the syntax of the arguments of cp
   * 
   * @param arguments Array of strings representing the arguments taken from
   *        shell
   * @return True iff the arguments are syntactically correct
   */
  public boolean validateCp(String[] arguments) {
    boolean status = true;
    // Checker for the > and >> redirection
    if (containsOneArrow(arguments)) {
      status = checkRedirectionSyntax(parseArrowCommand(arguments));
      arguments = parseArgumentCommand(arguments);
    }
    // If the number of paths is not 2, immediately falsify
    if (arguments.length != 2)
      return false;
    else
      // Check if the two paths are valid
      return (checkIfValidPath(arguments[0]) && checkIfValidPath(arguments[1])
          && status);
  }

  /**
   * Checks if the mv command is of the correct syntax
   * 
   * @param arguments Array of strings representing the arguments taken from the
   *        shell
   * @return True iff the arguments for mv are syntactically correct
   */
  public boolean validateMv(String[] arguments) {
    // use cp's validate since they are very similar in terms of required
    // arguments
    return validateCp(arguments);
  }

  /**
   * Validates the curl command for syntactic errors
   * 
   * @param arguments Array of strings representing the commands taken from the
   *        shell
   * @return True iff the URL and redirection is valid
   */
  public boolean validateCurl(String[] arguments) {
    // Check redirection
    boolean status = true;
    if (containsOneArrow(arguments)) {
      status = checkRedirectionSyntax(parseArrowCommand(arguments));
      arguments = parseArgumentCommand(arguments);
    }
    // Check if only one URL is present
    if (arguments.length != 1)
      return false;
    try {
      // See if file at URL actually exists
      new URL(arguments[0]);
    } catch (MalformedURLException e) {
      return false;
    }
    return status;
  }

  /**
   * Checks the syntax of the arguments for the grep command
   * 
   * @param arguments Array of strings representing the arguments taken from the
   *        shell
   * @return True iff the REGEX provided is valid as well as the path names +
   *         redirection
   */
  public boolean validateGrep(String[] arguments) {
    // check redirection
    boolean status = true;
    if (containsOneArrow(arguments)) {
      status = checkRedirectionSyntax(parseArrowCommand(arguments));
      arguments = parseArgumentCommand(arguments);
    }
    // check if -R is present
    if (arguments[0].equals("-R") || arguments[0].equals("-r"))
      arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
    // at least 2 args must be present (REGEX and at least one PATH)
    if (arguments.length < 2)
      return false;
    // if Regex compiles, it's valid
    try {
      Pattern.compile(arguments[0]);
    } catch (PatternSyntaxException e) {
      status = false;
    }
    // check if the path names are valid
    for (int i = 1; i < arguments.length; i++) {
      if (checkIfValidPath(arguments[i]) == false)
        status = false;
    }
    return status;

  }

}

