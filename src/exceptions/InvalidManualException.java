package exceptions;

public class InvalidManualException extends Exception {

  /**
   * Constructors for a invalidManual object. This exception is thrown when
   * trying to get the manual for a non-existent command.
   */

  public InvalidManualException() {
    super();
  }

  public InvalidManualException(String msg) {
    super(msg);
  }
}
