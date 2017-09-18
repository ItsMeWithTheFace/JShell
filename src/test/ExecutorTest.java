package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import executor.Executor;

public class ExecutorTest {

  private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private Executor executor;

  @Before
  public void setUp() {
    executor = new Executor();
    System.setOut(new PrintStream(outContent));
  }

  // test the method exitShell
  @Test
  public void testExitShell() {
    boolean result = true;
    try {
      executor.exitShell(null);
    } catch (Exception e) {
      result = false;
    }
    assertTrue(result);
  }

  // test makeDirectory and printContents together
  @Test
  public void testMakeOneDirectory() {
    String[] args1 = {"a"};
    String[] args2 = {};
    executor.makeDirectory(args1);
    Object[] output = executor.printContents(args2);
    assertEquals("Path at /: \na", output[0]);
  }

  @Test
  public void testMakeMultipleAndParallelDirectories() {
    String[] args1 = {"b", "c/"};
    String[] args2 = {};
    executor.makeDirectory(args1);
    Object[] output = executor.printContents(args2);
    assertEquals("Path at /: \nb\nc", output[0]);
  }

  @Test
  public void testMakeMultipleAndNonParallelDirectories() {
    String[] args1 = {"d/", "e", "e/f"};
    String[] args2 = {};
    executor.makeDirectory(args1);
    Object[] output = executor.printContents(args2);
    assertEquals("Path at /: \nd\ne", output[0]);
  }

  @Test
  public void testMakeDirectoriesCausingNotDirectoryException() {
    // test on arguments that will cause NotDirectoryException
    String[] args1 = {"eeee/f"};
    executor.makeDirectory(args1);
    assertEquals("Jshell: : Not a directory\n", outContent.toString());
  }

  @Test
  public void testMakeDirectoriesCausingAlreadyExistException() {
    // test on arguments that will cause AlreadyExistException
    String[] args1 = {"a", "a"};
    executor.makeDirectory(args1);
    assertEquals("Jshell: : File or directory exists\n", outContent.toString());
  }


  // test changeDirectory and printWorkingDirectory together
  @Test
  public void testGetIntoSubDirectory() {
    String[] args1 = {"a", "a/b", "a/b/c", "a/b/c/d"};
    String[] args2 = {"a/b"};
    String[] args3 = {"c"};
    String[] args4 = {};
    executor.makeDirectory(args1);
    executor.changeDirectory(args2);
    executor.changeDirectory(args3);
    Object[] output = executor.printWorkingDirectory(args4);
    assertEquals("/a/b/c", output[0]);
  }

  @Test
  public void testGetIntoParentDirectory() {
    String[] args1 = {"a", "a/b", "a/b/c", "a/b/c/d"};
    String[] args2 = {"a/b/c"};
    String[] args3 = {".."};
    String[] args4 = {};
    executor.makeDirectory(args1);
    executor.changeDirectory(args2);
    executor.changeDirectory(args3);
    Object[] output = executor.printWorkingDirectory(args4);
    assertEquals("/a/b", output[0]);
  }

  @Test
  public void testGetIntoRootDirectory() {
    String[] args1 = {"a", "a/b", "a/b/c", "a/b/c/d"};
    String[] args2 = {"a/b/c"};
    String[] args3 = {"/"};
    String[] args4 = {};
    executor.makeDirectory(args1);
    executor.changeDirectory(args2);
    executor.changeDirectory(args3);
    Object[] output = executor.printWorkingDirectory(args4);
    assertEquals("/", output[0]);
  }

  @Test
  public void testChangeDirectoryWithFullPath() {
    String[] args1 = {"a", "a/b", "a/b/c", "a/b/c/d"};
    String[] args2 = {"a/b/c/d"};
    String[] args3 = {"/a/b"};
    String[] args4 = {};
    executor.makeDirectory(args1);
    executor.changeDirectory(args2);
    executor.changeDirectory(args3);
    Object[] output = executor.printWorkingDirectory(args4);
    assertEquals("/a/b", output[0]);
  }


  // test pushDirectory and popDirectory together
  @Test
  public void testPushAndPopDirectory() {
    String[] args1 = {"a", "a/aa", "b", "c"};
    String[] args2 = {"b"};
    String[] args3 = {"/a/aa"};
    String[] args4 = {};
    String[] args5 = {};
    executor.makeDirectory(args1);
    executor.pushDirectory(args2);
    executor.pushDirectory(args3);
    executor.popDirectory(args4);
    Object[] output = executor.printWorkingDirectory(args5);
    assertEquals("/b", output[0]);
  }

  @Test
  public void testPopdFromEmptyStack() {
    String[] args1 = {"a", "a/b", "a/b/c", "a/b/c/d", "B", "B/C"};
    String[] args2 = {};
    executor.makeDirectory(args1);
    executor.popDirectory(args2);
    assertEquals("Jshell: The directory stack is empty\n",
        outContent.toString());
  }


  // test addInputToList and printHistory
  @Test
  public void testHistory() {
    String args1 = "a";
    String args2 = "b";
    String args3 = "c";
    String args4 = "d";
    executor.addInputToList(args1);
    executor.addInputToList(args2);
    executor.addInputToList(args3);
    executor.addInputToList(args4);

    // no number passed in
    String[] args5 = {};
    Object[] output = executor.printHistory(args5);
    assertEquals("1. a\n2. b\n3. c\n4. d", output[0]);

    // a valid number
    String[] args6 = {"2"};
    output = executor.printHistory(args6);
    assertEquals("3. c\n4. d", output[0]);

    // a larger number
    String[] args7 = {"8"};
    output = executor.printHistory(args7);
    assertEquals("1. a\n2. b\n3. c\n4. d", output[0]);

  }

  // test printString and printFileContents together
  @Test
  public void testPrintInShell() {
    String[] args1 = {"new line"};
    Object[] output = executor.printString(args1);
    assertEquals("new line", output[0]);
  }


  @Test
  public void testPrintMultipleFiles() {
    String[] args1 =
        {"http://www.utsc.utoronto.ca/~nick/cscA48/161/checkout1.py"};
    String[] args2 =
        {"http://www.utsc.utoronto.ca/~nick/cscA48/161/checkout2.py"};
    executor.copyURL(args1);
    executor.copyURL(args2);

    String[] args3 = {"checkout1.py", "checkout2.py"};
    Object[] output = executor.printFileContents(args3);
    assertEquals(
        "# A class for a checkout line\n" + "# Uses a list for the line.\n"
            + "# WARNING: contains poor code.\n\n" + "class CheckoutLine():\n"
            + "    def __init__(self: 'CheckoutLine') -> None:\n"
            + "        self.line = []\n\n" +

            "    def arrive(self: 'CheckoutLine', customer: str) -> None:\n"
            + "        self.line.append(customer)\n\n" +

            "    def depart(self: 'CheckoutLine') -> str:\n"
            + "        customer = self.line[0]\n" + "        del self.line[0]\n"
            + "        return customer\n\n\n\n"
            + "# Another class for a checkout line\n"
            + "# Uses a dictionary for the line.\n"
            + "# WARNING: contains poor code.\n\n" + "class CheckoutLine():\n"
            + "    def __init__(self: 'CheckoutLine') -> None:\n"
            + "        self.line = {}\n" + "        self.first_index = 0\n"
            + "        self.next_index = 0\n\n"
            + "    def arrive(self: 'CheckoutLine', customer: str) -> None:\n"
            + "        self.line[self.next_index] = customer\n"
            + "        self.next_index = self.next_index + 1\n\n"
            + "    def depart(self: 'CheckoutLine') -> str:\n"
            + "        customer = self.line[self.first_index]\n"
            + "        del self.line[self.first_index]\n"
            + "        self.first_index = self.first_index + 1\n"
            + "        return customer\n",
        output[0]);
  }


  // test the method printManual
  @Test
  public void testPrintManual() {
    String[] args1 = {"exit"};
    Object[] output = executor.printManual(args1);
    assertNotEquals("", output[0]);
  }

  @Test
  public void testPrintInvalidManual() {
    String[] args1 = {"sgersg"};
    executor.printManual(args1);
    assertEquals("Jshell: No manual entry for sgersg\n", outContent.toString());
  }


  // test copyURL
  @Test
  public void testCopyURL() {
    String[] args1 =
        {"http://www.utsc.utoronto.ca/~nick/cscA48/161/checkout1.py"};
    String[] args2 = {"checkout1.py"};
    executor.copyURL(args1);
    Object[] output = executor.printFileContents(args2);
    assertEquals(
        "# A class for a checkout line\n" + "# Uses a list for the line.\n"
            + "# WARNING: contains poor code.\n\n" + "class CheckoutLine():\n"
            + "    def __init__(self: 'CheckoutLine') -> None:\n"
            + "        self.line = []\n\n" +

            "    def arrive(self: 'CheckoutLine', customer: str) -> None:\n"
            + "        self.line.append(customer)\n\n" +

            "    def depart(self: 'CheckoutLine') -> str:\n"
            + "        customer = self.line[0]\n" + "        del self.line[0]\n"
            + "        return customer\n",
        output[0]);
  }

  @Test
  public void testCopyURLCauseException() {
    String[] args1 = {"fgjdfgsfnfgnsfgn"};
    executor.copyURL(args1);
    assertEquals("File does not exist on this webpage\n",
        outContent.toString());
  }


  // test copy method
  @Test
  public void testCopy() {
    // initialize fileTree and copy
    String[] args1 = {"a", "a/b", "A"};
    String[] args2 = {"a"};
    String[] args3 =
        {"http://www.utsc.utoronto.ca/~nick/cscA48/161/checkout1.py"};
    String[] args4 = {"/a", "/A"};

    executor.makeDirectory(args1);
    executor.changeDirectory(args2);
    executor.copyURL(args3);
    executor.copy(args4);

    // print content in A
    String[] args5 = {"/A"};
    Object[] output = executor.printContents(args5);
    assertEquals("Path at /A: \na", output[0]);

    // print content in A/a
    String[] args6 = {"/A/a"};
    output = executor.printContents(args6);
    assertEquals("Path at /A/a: \n" + "b\n" + "checkout1.py", output[0]);

    String[] args7 = {"/a"};
    output = executor.printContents(args7);
    assertEquals("Path at /a: \n" + "b\n" + "checkout1.py", output[0]);
  }


  // test move method
  @Test
  public void testMove() {
    // initialize fileTree and move
    String[] args1 = {"a", "a/b", "A"};
    String[] args2 = {"a"};
    String[] args3 =
        {"http://www.utsc.utoronto.ca/~nick/cscA48/161/checkout1.py"};
    String[] args4 = {"/a", "/A"};

    executor.makeDirectory(args1);
    executor.changeDirectory(args2);
    executor.copyURL(args3);
    executor.move(args4);

    // print content in /A
    String[] args5 = {"/A"};
    Object[] output = executor.printContents(args5);
    assertEquals("Path at /A: \na", output[0]);

    // print content in /A/a
    String[] args6 = {"/A/a"};
    output = executor.printContents(args6);
    assertEquals("Path at /A/a: \n" + "b\n" + "checkout1.py", output[0]);

    // went to root and check
    String[] args7 = {"/"};
    executor.changeDirectory(args7);
    String[] args8 = {};
    output = executor.printContents(args8);
    assertEquals("Path at /: \nA", output[0]);
  }


  @After
  public void cleanUpStreams() {
    System.setOut(null);
  }
}
