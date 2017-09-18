package exceptions;

public class OutRangeException extends Exception {

  /**
   * Constructors for a OutRangeException object. This exception is thrown when
   * passing in a value that is out of range.
   */

  public OutRangeException() {
    super();
  }

  public OutRangeException(String msg) {
    super(msg);
  }
}
