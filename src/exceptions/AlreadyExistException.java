package exceptions;

public class AlreadyExistException extends Exception {

  /**
   * Constructors for a AlreadyExistException object. This exception is thrown
   * when trying to create a file or directory having the same name with a
   * existent file or directory.
   */

  public AlreadyExistException() {
    super();
  }

  public AlreadyExistException(String msg) {
    super(msg);
  }
}
