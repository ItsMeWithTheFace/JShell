package exceptions;

public class InvalidPathException extends Exception {

  /**
   * Constructors for a InvalidPathException object. This exception is thrown
   * when passing in a invalid path.
   */

  public InvalidPathException() {
    super();
  }

  public InvalidPathException(String msg) {
    super(msg);
  }
}
