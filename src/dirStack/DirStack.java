package dirStack;

import java.util.ArrayList;

public class DirStack {
  
  /**
   * 
   * @author Jack Seebach <jack.seebach@mail.utoronto.ca>
   * @version 1.0
   *
   */
  
  // This will store the whole stack
  private ArrayList<String> stack;
  
  /**
   * Default constructor.
   * It creates a new dirStack object.
   */
  public DirStack(){
    stack = new ArrayList<String>();
  }
  
  /**
   * Use this method to add elements to the stack
   * 
   * @param path The path to be added to the end
   */
  public void push(String path){
    // add the path to the end
    stack.add(path);
  }
  
  /**
   * Use this method to remove and retrieve the last item in the stack
   */
  public String pop(){
    // get the last element at the end
    String path = stack.get(stack.size()-1);
    // remove the last element
    stack.remove(stack.size()-1);
    // return the path
    return path;
    
  }
  
  /**
   * Use this method to determine if the stack is empty
   */
  public Boolean isEmpty(){
    // initialize variable
    Boolean result = false;
    // if there are no elements it is true
    if(stack.size() == 0){
      result = true;
    }
    // return result
    return result;
  }
}
