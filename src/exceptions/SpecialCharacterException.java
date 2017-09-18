package exceptions;

public class SpecialCharacterException extends Exception {

  /**
   * Constructors for a NotDirectoryException object. This exception is thrown
   * when trying to name a directory or a file with special characters.
   */

  public SpecialCharacterException() {
    super();
  }

  public SpecialCharacterException(String msg) {
    super(msg);
  }
}
