package exceptions;

public class NotExistException extends Exception {

  /**
   * Constructors for a NotExistException object. This exception is thrown
   * when accessing to a nonexistent file or directory.
   */

  public NotExistException() {
    super();
  }

  public NotExistException(String msg) {
    super(msg);
  }
}
