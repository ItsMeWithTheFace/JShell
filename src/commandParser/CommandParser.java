package commandParser;

import java.util.ArrayList;

public class CommandParser {

  /**
   * 
   * @author Jack Seebach <jack.seebach@mail.utoronto.ca>
   * @version 1.0
   *
   */
  
  /**
   * breaks up the string given into parts based on spaces or quotes
   * 
   * @param input the string to be broken up into parts
   * 
   * @return output an array of strings which is the broken up input
   */
  public String[] parseString(String input){
    // trip input if it has not
    input = input.trim();
    // declare and initialize variables
    ArrayList<String> args = new ArrayList<String>();
    String splitInput = input + " ";
    ArrayList<String> spaceLessArgs = new ArrayList<String>();
    // loop until the end
    while(splitInput.indexOf(" ") != -1){
      // if the first element of the arguments starts with a double quote
      if(splitInput.startsWith("\"")){
        spaceLessArgs.add(splitInput.substring(1,
            splitInput.lastIndexOf("\"")));
     // make the arguments string shorter by one argument
        splitInput = splitInput.substring(splitInput.lastIndexOf("\"") + 1,
            splitInput.length());
      }
      // if the first element of the arguments string starts with a space
      else if (!splitInput.startsWith(" ")){
        // add up until the next space to the arraylist
        spaceLessArgs.add(splitInput.substring(0, splitInput.indexOf(" ")));
        // make the arguments string shorter by one argument
        splitInput = splitInput.substring(splitInput.indexOf(" ") + 1,
            splitInput.length());
      }
      else{
     // make the arguments string shorter by one argument
        splitInput = splitInput.substring(splitInput.indexOf(" ") + 1,
            splitInput.length());
      }
      
    }
    // add all of the spaceLessArgs to the args array
      args.addAll(spaceLessArgs);
     // convert to an array of strings
    String[] output = new String[args.size()];
    for(int i = 0; i < args.size(); i++){
      output[i] = args.get(i);
    }
    // return output
    return(output);

  }
}

