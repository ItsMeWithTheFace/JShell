package exceptions;

public class UnknownCommandException extends Exception {

  /**
   * Constructors for a UnknownCommandException object. This exception is thrown
   * when passing an invalid command into Jshell.
   */

  public UnknownCommandException() {
    super();
  }

  public UnknownCommandException(String msg) {
    super(msg);
  }
}
