package exceptions;

public class NotDirectoryException extends Exception {

  /**
   * Constructors for a NotDirectoryException object. This exception is thrown
   * when passing in a value that is not a directory name, when requiring a
   * directory name.
   */

  public NotDirectoryException() {
    super();
  }

  public NotDirectoryException(String msg) {
    super(msg);
  }
}
