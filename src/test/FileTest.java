package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.junit.*;

import file.File;

public class FileTest {
  // Test if file is properly created
  @Test
  public void testCreateFile() {
    File testFile = File.createFile("test.txt");
    assertTrue(testFile instanceof File);
  }

  // Test if file name is retrieved
  @Test
  public void testGetName() {
    File testFile = File.createFile("test.txt");
    assertEquals(testFile.getName(), "test.txt");
  }

  // Check if setName works with a string with no special chars (i.e., " ", ".",
  // "..", "/", etc.)
  @Test
  public void testSetNameWithString() {
    File testFile = File.createFile("test.txt");
    testFile.setName("helloworld");
    assertEquals(testFile.getName(), "helloworld");
  }

  // Test that setName doesn't change the name into on with special chars
  @Test
  public void testSetNameWithSpecialChars() {
    File testFile = File.createFile("test.txt");
    testFile.setName("Hello World");
    assertEquals(testFile.getName(), "test.txt");
  }

  // Check that line of data is added to file
  @Test
  public void testAddLine() {
    File testFile = File.createFile("test.txt");
    testFile.addLine("A test line");
    ArrayList<String> data = testFile.getData();
    assertEquals("[A test line]", data.toString());
  }

  // Check that cat prints contents of file in order
  @Test
  public void testCat() {
    File testFile = File.createFile("test.txt");
    testFile.addLine("This is a test line");
    assertEquals(testFile.cat(), "This is a test line");
  }

  // Test to see if file's data is properly cleared
  @Test
  public void testClearFile() {
    File testFile = File.createFile("test.txt");
    testFile.addLine("Hello world");
    testFile.clearFile();
    assertEquals(testFile.cat(), "");
  }

  // Test to see if equals returns false for files with same name but different
  // data
  @Test
  public void testEqualsForSameNameDifferentData() {
    File testFile1 = File.createFile("test.txt");
    File testFile2 = File.createFile("test.txt");

    testFile1.addLine("This is a line");
    testFile2.addLine("This is a different line");

    assertFalse(testFile1.equals(testFile2));
  }

  // Test to see if equals returns false for files with different name but same
  // data
  @Test
  public void testEqualsForDifferentNameSameData() {
    File testFile1 = File.createFile("test1.txt");
    File testFile2 = File.createFile("test2.txt");

    testFile1.addLine("This is a line");
    testFile2.addLine("This is a line");

    assertFalse(testFile1.equals(testFile2));
  }

  // Test to see if equals returns true for files with same name and data
  @Test
  public void testEqualsForSameNameSameData() {
    File testFile1 = File.createFile("test.txt");
    File testFile2 = File.createFile("test.txt");

    testFile1.addLine("This is a line");
    testFile2.addLine("This is a line");

    assertTrue(testFile1.equals(testFile2));
  }

  // Test to see if equals returns false for files with different names and data
  @Test
  public void testEqualsForDifferentNameDifferentData() {
    File testFile1 = File.createFile("test1.txt");
    File testFile2 = File.createFile("test2.txt");

    testFile1.addLine("This is a line");
    testFile2.addLine("This is a different line");

    assertFalse(testFile1.equals(testFile2));
  }
}
