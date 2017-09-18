package exceptions;

public class EmptyStackException extends Exception {

  /**
   * Constructors for a EmptyStackException object. This exception is thrown
   * when trying to call popd methods when the directory stack is empty.
   */

  public EmptyStackException() {
    super();
  }

  public EmptyStackException(String msg) {
    super(msg);
  }
}
