package test;

import static org.junit.Assert.*;
import org.junit.Test;

import exceptions.InvalidManualException;
import outputter.Manual;

public class ManualTest {

  // start by creating a manual object
  public Manual manual = new Manual();

  // check whether the command method prints a manual page properly, when
  // passing in a valid command name

  // the method have to work with command:
  // exit, mkdir, cd, ls, pwd, cat, pushd, popd, history, echo, man, mv, cp,
  // curl, !, grep

  @Test
  public void testCommandExit() throws InvalidManualException {
    assertNotEquals("", manual.command("exit"));
  }

  @Test
  public void testCommandMkdir() throws InvalidManualException {
    assertNotEquals("", manual.command("mkdir"));
  }

  @Test
  public void testCommandCd() throws InvalidManualException {
    assertNotEquals("", manual.command("cd"));
  }

  @Test
  public void testCommandLs() throws InvalidManualException {
    assertNotEquals("", manual.command("ls"));
  }

  @Test
  public void testCommandPwd() throws InvalidManualException {
    assertNotEquals("", manual.command("pwd"));
  }

  @Test
  public void testCommandPushd() throws InvalidManualException {
    assertNotEquals("", manual.command("pushd"));
  }

  @Test
  public void testCommandPopd() throws InvalidManualException {
    assertNotEquals("", manual.command("popd"));
  }

  @Test
  public void testCommandHistory() throws InvalidManualException {
    assertNotEquals("", manual.command("history"));
  }

  @Test
  public void testCommandCat() throws InvalidManualException {
    assertNotEquals("", manual.command("cat"));
  }

  @Test
  public void testCommandEcho() throws InvalidManualException {
    assertNotEquals("", manual.command("echo"));
  }

  @Test
  public void testCommandMan() throws InvalidManualException {
    assertNotEquals("", manual.command("man"));
  }

  @Test
  public void testCommandMv() throws InvalidManualException {
    assertNotEquals("", manual.command("mv"));
  }

  @Test
  public void testCommandCp() throws InvalidManualException {
    assertNotEquals("", manual.command("cp"));
  }

  @Test
  public void testCommandCurl() throws InvalidManualException {
    assertNotEquals("", manual.command("curl"));
  }

  @Test
  public void testCommandExclamation() throws InvalidManualException {
    assertNotEquals("", manual.command("!"));
  }

  @Test
  public void testCommandGrep() throws InvalidManualException {
    assertNotEquals("", manual.command("grep"));
  }

  
  // need to check a InvalidManualException is thrown when passing in an invalid
  // string
  @Test
  public void testInvalidCommand() throws InvalidManualException {
    boolean result = false;

    try {
      manual.command("54656");
    } catch (InvalidManualException e) {
      result = true;
    }
    assertTrue(result);

    try {
      manual.command("");
    } catch (InvalidManualException e) {
      result = true;
    }
    assertTrue(result);

    try {
      manual.command("man man");
    } catch (InvalidManualException e) {
      result = true;
    }
    assertTrue(result);

  }

}
