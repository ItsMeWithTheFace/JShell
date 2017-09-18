package exceptions;

public class InvalidObjectException extends Exception {

  /**
   * Constructors for a invalidObjectException object. This exception is thrown
   * when passing in a invalid object for the current argument.
   */

  public InvalidObjectException() {
    super();
  }

  public InvalidObjectException(String msg) {
    super(msg);
  }
}
