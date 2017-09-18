package exceptions;

public class NotFileException extends Exception {

  /**
   * Constructors for a NotFileException object. This exception is thrown when
   * passing in a value that is not a file name, when requiring a file name.
   */

  public NotFileException() {
    super();
  }

  public NotFileException(String msg) {
    super(msg);
  }
}
