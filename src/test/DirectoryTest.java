package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import file.Directory;
import file.Directory.DirectoryIterator;
import file.File;

public class DirectoryTest {

  private Directory testDir;


  // test if Directory is properly created
  @Test
  public void testCreateDirectory() {
    Directory newDir = new Directory("dir");
    assertTrue(newDir instanceof Directory);
  }


  // for each following test case, we need to initialize a Directory object
  @Before
  public void setUp() {
    testDir = new Directory("dir");
  }


  // test if name of directory is retrieved
  @Test
  public void testGetName() {
    assertEquals("dir", testDir.getName());
  }


  // test if a new name for the directory can be set properly
  @Test
  public void testSetName() {
    testDir.setName("newDir");
    assertEquals("newDir", testDir.getName());
  }


  // test whether the content of the directory is returned properly
  @Test
  public void testAddElementAndGetContent() {
    // check in an empty directory
    List<Object> expect = new ArrayList<Object>();
    assertEquals(expect, testDir.getContent());

    // check in a non-empty directory
    // create and add two sub-directory into the parent directory
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    testDir.addElement(subD1);
    testDir.addElement(subD2);
    // create a proper expected content list
    expect.add(subD1);
    expect.add(subD2);

    assertEquals(expect, testDir.getContent());

  }


  // test whether isInDirectory method works well
  @Test
  public void testIsInDirectory() {
    // check in an empty directory
    assertEquals(false, testDir.isInDirectory("noFile"));

    // add some elements in the directory and check
    File f1 = File.createFile("f1");
    File f2 = File.createFile("f2");
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    testDir.addElement(f1);
    testDir.addElement(f2);
    testDir.addElement(subD1);
    testDir.addElement(subD2);

    // check if the method works with file and directory
    assertEquals(false, testDir.isInDirectory("noFile"));
    assertEquals(true, testDir.isInDirectory("f1"));
    assertEquals(true, testDir.isInDirectory("d2"));

  }


  // test whether the method findElement returns the correct element in the
  // directory
  @Test
  public void testFindElement() {
    // check in an empty directory
    assertEquals(null, testDir.findElement("noFile"));

    // add some files and sub-directories
    File f1 = File.createFile("f1");
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    testDir.addElement(f1);
    testDir.addElement(subD1);
    testDir.addElement(subD2);

    // check in an non-empty directory
    assertEquals(f1, testDir.findElement("f1"));
    assertEquals(subD2, testDir.findElement("d2"));

  }


  // test whether the method getContentNames returns the correct list of names
  // of all the contents in the current directory
  @Test
  public void tesGetContentNames() {
    // check in an empty directory
    List<Object> expect = new ArrayList<Object>();
    assertEquals(expect, testDir.getContentNames());

    // check in a non-empty directory
    // create and add two sub-directory into the parent directory
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    File f1 = File.createFile("f1");
    testDir.addElement(subD1);
    testDir.addElement(subD2);
    testDir.addElement(f1);
    // create a proper expected content list
    expect.add("d1");
    expect.add("d2");
    expect.add("f1");

    assertEquals(expect, testDir.getContentNames());

  }


  // test whether the method getAllSubDirectoryNames returns the correct list of
  // names of all the sub-directories, including sub-directories in
  // sub-directory.
  @Test
  public void testGetAllSubDirectoryNames() {
    // check in an empty directory
    List<Object> expect = new ArrayList<Object>();
    assertEquals(expect, testDir.getContentNames());

    // check in a non-empty directory
    // create and add two sub-directory into the parent directory
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    Directory subD3 = new Directory("d3");
    Directory subD4 = new Directory("d4");
    File f1 = File.createFile("f1");
    File f2 = File.createFile("f2");
    testDir.addElement(subD1);
    testDir.addElement(subD2);
    testDir.addElement(f1);
    subD1.addElement(subD3);
    subD1.addElement(f2);
    subD3.addElement(subD4);

    // create a proper expected content list
    expect.add("d1");
    expect.add("d2");
    assertEquals(expect, testDir.getAllSubDirectoryNames());

  }


  // test whether the toSubDirectory method returns the wanted sub-directory
  @Test
  public void testToSubDirectory() {
    // add some elements in the directory and check
    File f1 = File.createFile("f1");
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    testDir.addElement(f1);
    testDir.addElement(subD1);
    testDir.addElement(subD2);

    // try to find a exist sub-directory
    assertEquals(subD1, testDir.toSubDirectory("d1"));

    // try to find a sub-directory, but there is only a file with the same name
    // in the directory, should get null
    assertEquals(null, testDir.toSubDirectory("f1"));

    // try to find a sub-directory with a non-existent name, should get null
    assertEquals(null, testDir.toSubDirectory("noDir"));
  }


  // test whether the method equals can correctly check whether the content of
  // two directory are equal
  @Test
  public void testEquals() {
    // create two directories
    Directory d1 = new Directory("d1");
    Directory d2 = new Directory("d2");

    // check if the method equals works well in empty directory
    assertEquals(true, d1.equals(d2));


    // add same files into d1 and d2
    File file1 = File.createFile("file");
    File file2 = File.createFile("file");
    d1.addElement(file1);
    d2.addElement(file2);

    // check if the method work well with two directories containing same file
    assertEquals(true, d1.equals(d2));


    // create some file and sub-directories and add them into d1
    File f1 = File.createFile("f1");
    File f2 = File.createFile("f2");
    f1.addLine("some words");
    Directory subD1 = new Directory("subD1");
    Directory subD2 = new Directory("subD2");

    subD1.addElement(f1);
    subD1.addElement(f2);
    d1.addElement(subD1);
    d1.addElement(subD2);

    // check if the method works well with different directories
    assertEquals(false, d1.equals(d2));


    // create equal file and sub-directories (same name and contents) then add
    // them into d2
    File f1Copy = File.createFile("f1");
    File f2Copy = File.createFile("f2");
    f1Copy.addLine("some words");
    Directory subD1Copy = new Directory("subD1");
    Directory subD2Copy = new Directory("subD2");

    subD1Copy.addElement(f1Copy);
    subD1Copy.addElement(f2Copy);
    d2.addElement(subD1Copy);
    d2.addElement(subD2Copy);

    // check if the method equals works well with two non-empty and equal
    // directories with complex structures
    assertEquals(true, d1.equals(d2));

    // change the content of file, check if the methods works well with files
    // have the same file name but different content
    f1.addLine("other words");
    assertEquals(false, d1.equals(d2));

  }


  // test remove method
  @Test
  public void testRemove() {
    // add some elements in the directory and check
    File f1 = File.createFile("f1");
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    Directory subD3 = new Directory("d3");
    Directory subD4 = new Directory("d4");
    testDir.addElement(f1);
    testDir.addElement(subD1);
    testDir.addElement(subD2);
    subD1.addElement(subD3);
    subD1.addElement(subD4);

    // try removing a non-exist element and assert
    // create a list for expected result
    List<Object> expected = new ArrayList<Object>();
    expected.add(f1);
    expected.add(subD1);
    expected.add(subD2);

    testDir.remove(5);
    assertEquals(expected, testDir.getContent());

    // try removing a exist element and assert
    // create a list for expected result
    expected.clear();
    expected.add(f1);
    expected.add(subD1);

    testDir.remove(2);
    assertEquals(expected, testDir.getContent());
  }

  // test removeDirectory method
  @Test
  public void testRemoveDirectory() {
    // add some elements in the directory and check
    File f1 = File.createFile("f1");
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    Directory subD3 = new Directory("d3");
    Directory subD4 = new Directory("d4");
    testDir.addElement(f1);
    testDir.addElement(subD1);
    testDir.addElement(subD2);
    subD1.addElement(subD3);
    subD1.addElement(subD4);

    // try removing a non-exist directory and assert
    // create a list for expected result
    List<Object> expected = new ArrayList<Object>();
    expected.add(f1);
    expected.add(subD1);
    expected.add(subD2);

    testDir.removeDirectory("f1");
    assertEquals(expected, testDir.getContent());

    // try removing a exist directory and assert
    // create a list for expected result
    expected.clear();
    expected.add(f1);
    expected.add(subD1);

    testDir.removeDirectory("d2");
    assertEquals(expected, testDir.getContent());
  }

  // test removeFile method
  @Test
  public void testRemoveFile() {
    // add some elements in the directory and check
    File f1 = File.createFile("f1");
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    Directory subD3 = new Directory("d3");
    Directory subD4 = new Directory("d4");
    testDir.addElement(f1);
    testDir.addElement(subD1);
    testDir.addElement(subD2);
    subD1.addElement(subD3);
    subD1.addElement(subD4);

    // try removing a non-exist file and assert
    // create a list for expected result
    List<Object> expected = new ArrayList<Object>();
    expected.add(f1);
    expected.add(subD1);
    expected.add(subD2);

    testDir.removeFile("d1");
    assertEquals(expected, testDir.getContent());

    // try removing a exist file and assert
    // create a list for expected result
    expected.clear();
    expected.add(subD1);
    expected.add(subD2);

    testDir.removeFile("f1");
    assertEquals(expected, testDir.getContent());
  }


  // test whether the getSubDirectories method returns the list contains all
  // the sub-directory objects in the current directory.
  @Test
  public void testGetSubDirectories() {
    // add some elements in the directory and check
    File f1 = File.createFile("f1");
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    Directory subD3 = new Directory("d3");
    Directory subD4 = new Directory("d4");
    testDir.addElement(f1);
    testDir.addElement(subD1);
    testDir.addElement(subD2);
    subD1.addElement(subD3);
    subD1.addElement(subD4);

    // create a list for expected result
    List<Directory> expected = new ArrayList<Directory>();
    expected.add(subD1);
    expected.add(subD2);

    assertEquals(expected, testDir.getSubDirectories());

  }


  // test whether the getSubDirectories method returns the list contains all
  // the Files in the current directory.
  @Test
  public void testGetFiles() {
    // add some elements in the directory and check
    File f1 = File.createFile("f1");
    File f2 = File.createFile("f2");
    File f3 = File.createFile("f3");
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    testDir.addElement(f1);
    testDir.addElement(f2);
    testDir.addElement(subD1);
    testDir.addElement(subD2);
    subD1.addElement(f3);

    // create a list for expected result
    List<File> expected = new ArrayList<File>();
    expected.add(f1);
    expected.add(f2);

    assertEquals(expected, testDir.getFiles());

  }


  // test whether the hasSubDirectories method works well.
  @Test
  public void testHasSubDirectories() {
    // check in a empty directory
    assertFalse(testDir.hasSubDirectories());

    // add some elements in the directory and check
    File f1 = File.createFile("f1");
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    Directory subD3 = new Directory("d3");
    Directory subD4 = new Directory("d4");
    testDir.addElement(f1);
    testDir.addElement(subD1);
    testDir.addElement(subD2);
    subD1.addElement(subD3);
    subD1.addElement(subD4);

    // check in a non-empty directory
    assertTrue(testDir.hasSubDirectories());

  }


  // test whether the hasSubDirectories method works well.
  @Test
  public void testCreateIterator() {
    // add some elements in the directory and check
    File f1 = File.createFile("f1");
    Directory subD1 = new Directory("d1");
    Directory subD2 = new Directory("d2");
    Directory subD3 = new Directory("d3");
    Directory subD4 = new Directory("d4");
    testDir.addElement(f1);
    testDir.addElement(subD1);
    testDir.addElement(subD2);
    subD1.addElement(subD3);
    subD1.addElement(subD4);

    // create a iterator and check hasNext method
    DirectoryIterator iterator = testDir.createIterator();

    // check hasNext
    assertTrue(iterator.hasNext());
    iterator.next();
    assertTrue(iterator.hasNext());
    iterator.next();
    assertTrue(iterator.hasNext());
    iterator.next();
    assertFalse(iterator.hasNext());

    // create a iterator and check next method
    iterator = testDir.createIterator();

    // check next method
    boolean catchException = false;
    assertEquals(f1, iterator.next());
    assertEquals(subD1, iterator.next());
    assertEquals(subD2, iterator.next());
    try {
      iterator.next();
    } catch (IndexOutOfBoundsException e) {
      catchException = true;
      assertTrue(catchException);
    }
  }

}
