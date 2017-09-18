package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exceptions.AlreadyExistException;
import exceptions.InvalidPathException;
import exceptions.NotDirectoryException;
import exceptions.NotFileException;
import fileTree.FileTree;

public class FileTreeTest {

  @Before
  public void setUp() throws Exception {}

  // Tests if you can get can get a directory.
  @Test
  public void testGetDirectory() {
    FileTree myTree = new FileTree();
    String[] file = {"file1"};
    try {
      myTree.mkdir(file);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("directory1 already exists or the path is invalid.");
    }
    try {
      assertEquals(myTree.getDirectory(file[0]).getName(), "file1");
    } catch (NotDirectoryException e) {
      fail("file could not be found");
    }
  }
  
  // Tests if an exception is raised if the directory does not exist.
  @Test
  public void testGetDirectoryDoesNotExist() {
    FileTree myTree = new FileTree();
    String[] file = {"file1"};
    boolean result = false;
    try {
      String name = myTree.getDirectory(file[0]).getName();
    } catch (NotDirectoryException e) {
      result = true;
    }
    assertTrue(result);
  }
  
  // Tests if a directory exists in the file system after its creation.
  @Test
  public void testHasDirectory() {
    FileTree myTree = new FileTree();
    String[] file = {"file1"};
    try {
      myTree.mkdir(file);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("directory1 already exists or the path is invalid.");
    }
    assertTrue(myTree.hasDirectory("file1"));
  }
  
  // Tests if a directory that does not exists exists.
  @Test
  public void testHasDirectoryFalse() {
    FileTree myTree = new FileTree();
    assertTrue(!myTree.hasDirectory("file1"));
  }

  // Test if the getFile method work on a file that exists.
  @Test
  public void testGetFile() {
    FileTree myTree = new FileTree();
    String file = "file1";
    try {
      myTree.makeFile(file);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("directory1 already exists or the path is invalid.");
    }
    try {
      assertEquals(myTree.getFile(file).getName(), "file1");
    } catch (NotFileException e) {
      fail("file could not be found");
    }
  }

  // Tests if it has a file.
  @Test
  public void testHasFileTrue() {
    FileTree myTree = new FileTree();
    String file = "file1";
    try {
      myTree.makeFile(file);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("directory1 already exists or the path is invalid.");
    }
    assertTrue(myTree.hasFile(file));
  }

  // Test if the file system has a file that does not exist.
  @Test
  public void testHasFileFalse() {
    FileTree myTree = new FileTree();
    String file = "file1";
    assertTrue(!myTree.hasFile(file));
  }

  // Tests if a file can be created
  @Test
  public void testMakeFile() {
    FileTree myTree = new FileTree();
    String file = "file1";
    try {
      myTree.makeFile(file);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("directory1 already exists or the path is invalid.");
    }
    try {
      assertEquals(myTree.getFile(file).getName(), "file1");
    } catch (NotFileException e) {
      fail("file could not be found");
    }
  }
  
  // Tests if a file exists after it is created already.
  @Test
  public void testMakeFileFileExists() {
    FileTree myTree = new FileTree();
    String file = "file1";
    boolean result = false;
    try {
      myTree.makeFile(file);
      myTree.makeFile(file);
    } catch (NotDirectoryException | AlreadyExistException e) {
      result = true;
    }
    assertTrue(result);

  }

  // Tests if the printing the path to the working directory
  @Test
  public void testPwd() {
    FileTree myTree = new FileTree();
    String result = myTree.pwd();
    assertEquals(result, "/");
  }

  // Check if mkdir works with a new directory from an absolute path that is
  // one deep.
  @Test
  public void testMkdirOneDirectoryAbsolutePathOneDeep() {
    FileTree myTree = new FileTree();
    String[] path = {"/directory1"};
    try {
      myTree.mkdir(path);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("directory1 already exists or the path is invalid.");
    }
    assertEquals(myTree.hasDirectory("/directory1"), true);
  }

  // Check if mkdir works with a new directory from an relative path that is
  // one deep.
  @Test
  public void testMkdirOneDirectoryRelativePathOneDeep() {
    FileTree myTree = new FileTree();
    String[] path = {"directory1"};
    try {
      myTree.mkdir(path);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("directory1 already exists or the path is invalid.");
    }
    assertEquals(myTree.hasDirectory("/directory1"), true);
  }

  // Check if mkdir works with multiple new directories from absolute
  // paths that are one deep.
  @Test
  public void testMkdirMultipleDirectoriesAbsolutePathOneDeep() {
    FileTree myTree = new FileTree();
    String[] path = {"/directory1", "/directory2"};
    try {
      myTree.mkdir(path);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("directory1 or directory2 already exists or the path is invalid.");
    }
    assertEquals(myTree.hasDirectory("/directory1"), true);
    assertEquals(myTree.hasDirectory("/directory2"), true);
  }

  // Check if mkdir works with multiple new directories from  
  // relative paths that are one deep.
  @Test
  public void testMkdirMultipleDirectoriesRelativePathOneDeep() {
    FileTree myTree = new FileTree();
    String[] path = {"directory1", "directory2"};
    try {
      myTree.mkdir(path);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("directory1 or directory2 already exists or the path is invalid.");
    }
    assertEquals(myTree.hasDirectory("/directory1"), true);
    assertEquals(myTree.hasDirectory("/directory2"), true);
  }

  // Check if mkdir works with multiple new directories from absolute or 
  // relative paths that are one deep.
  @Test
  public void testMkdirMultipleDirectoriesOneDeep() {
    FileTree myTree = new FileTree();
    String[] path = {"directory1", "/directory2", "directory3"};
    try {
      myTree.mkdir(path);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("Directory 1 or 2 already exists or the path is invalid.");
    }
    assertEquals(myTree.hasDirectory("/directory1"), true);
    assertEquals(myTree.hasDirectory("/directory2"), true);
    assertEquals(myTree.hasDirectory("/directory3"), true);
  }

  // Check if mkdir works with a new directory from an absolute path that is
  // multiple directories deep.
  @Test
  public void testMkdirOneDirectoryAbsolutePathMultipleDeep() {
    FileTree myTree = new FileTree();
    String[] path = {"/directory1", "/directory1/subDirectory1"};
    try {
      myTree.mkdir(path);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("A already exists or a path is invalid.");
    }
    assertEquals(myTree.hasDirectory("/directory1/subDirectory1"), true);
  }

  // Check if mkdir works with a new directory from an relative path that is
  // multiple directories deep.
  @Test
  public void testMkdirOneDirectoryRelativePathMultipleDeep() {
    FileTree myTree = new FileTree();
    String[] path = {"directory1", "directory1/subDirectory1"};
    try {
      myTree.mkdir(path);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("A already exists or a path is invalid.");
    }
    assertEquals(myTree.hasDirectory("/directory1/subDirectory1"), true);
  }

  // Check if mkdir works with multiple new directories from absolute
  // paths that are multiple directories deep.
  @Test
  public void testMkdirMultipleDirectoriesAbsolutePathMultipleDeep() {
    FileTree myTree = new FileTree();
    String[] path1 = {"/directory1", "/directory2"};
    String[] path2 = {"/directory1/subDirectory1", "/directory2/subDirectory2"};
    try {
      myTree.mkdir(path1);
      myTree.mkdir(path2);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    assertEquals(myTree.hasDirectory("/directory1/subDirectory1"), true);
    assertEquals(myTree.hasDirectory("/directory2/subDirectory2"), true);
  }

  // Check if mkdir works with multiple new directories from  
  // relative paths that are multiple directories deep.
  @Test
  public void testMkdirMultipleDirectoriesRelativePathMultipleDeep() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory1", "directory2"};
    String[] path2 = {"directory1/subDirectory1", "directory2/subDirectory2"};
    try {
      myTree.mkdir(path1);
      myTree.mkdir(path2);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    assertEquals(myTree.hasDirectory("/directory1/subDirectory1"), true);
    assertEquals(myTree.hasDirectory("/directory2/subDirectory2"), true);
  }

  // Check if mkdir works with multiple new directories from absolute or 
  // relative paths that are multiple directories deep.
  @Test
  public void testMkdirMultipleDirectoriesMultipleDeep() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory1", "/directory2"};
    String[] path2 = {"directory1/subDirectory1", "/directory2/subDirectory2"};
    try {
      myTree.mkdir(path1);
      myTree.mkdir(path2);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    assertEquals(myTree.hasDirectory("/directory1/subDirectory1"), true);
    assertEquals(myTree.hasDirectory("/directory2/subDirectory2"), true);
  }

  // Test ls when no path is given.
  @Test
  public void testLsNoPath() {
    FileTree myTree = new FileTree();
    String[] paths = {"directory1"};
    String[] empty_path = {};
    try{
      myTree.mkdir(paths);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    String result = "";
    try {
      result = myTree.ls(false, empty_path);
    } catch (InvalidPathException e1) {
      fail("Could not find the directories");
    }

    assertEquals(result, "Path at /: \ndirectory1\n");
  }

  // Tests ls on a file
  @Test
  public void testLsFile() {
    FileTree myTree = new FileTree();
    String[] paths = {};
    try {
      myTree.makeFile("/file");
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("Could not create file");
    }
    String result = "";
    try {
      result = myTree.ls(false, paths);
    } catch (InvalidPathException e1) {
      fail("Could not find the directories");
    }

    assertEquals(result, "Path at /: \nfile\n");
  }

  // Tests ls on a directory
  @Test
  public void testLsDirectory() {
    FileTree myTree = new FileTree();
    String[] paths = {"file"};
    try {
      myTree.mkdir(paths);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("Could not create file");
    }
    String result = "";
    try {
      result = myTree.ls(false, paths);
    } catch (InvalidPathException e1) {
      fail("Could not find the directories");
    }

    assertEquals(result, "Path at file: \n");
  }
  
  // test ls when the given directory does not exist.
  @Test
  public void testLsDirectoryDoesNotExist() {
    FileTree myTree = new FileTree();
    String[] paths = {"file"};
    String result = "";
    try {
      result = myTree.ls(false, paths);
    } catch (InvalidPathException e1) {
      assertTrue(true);
    }

  }
  
  // test ls on a directory and file
  @Test
  public void testLsDirectoryAndFile() {
    FileTree myTree = new FileTree();
    String[] paths = {"file"};
    try {
      myTree.mkdir(paths);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("Could not create directory");
    }
    String result = "";
    try {
      myTree.makeFile("file1");
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("could not create file");
    }
    String[] empty_path = {};
    try {
      result = myTree.ls(false, empty_path);
    } catch (InvalidPathException e1) {
      fail("Could not find the directories");
    }

    assertEquals(result, "Path at /: \nfile\nfile1\n");
  }
  // test ls on a directory and file with recursion but no subfiles
  @Test
  public void testLsDirectoryAndFileR() {
    FileTree myTree = new FileTree();
    String[] paths = {"file"};
    try {
      myTree.mkdir(paths);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("Could not create directory");
    }
    String result = "";
    try {
      myTree.makeFile("file1");
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("could not create file");
    }
    String[] empty_path = {};
    try {
      result = myTree.ls(true, empty_path);
    } catch (InvalidPathException e1) {
      fail("Could not find the directories");
    }

    assertEquals(result, "Path at /: \nfile\nfile1\nPath at /file: \n");
  }
  
  // test ls on a directory and file with r and subfiles
  @Test
  public void testLsDirectoryAndFileRSub() {
    FileTree myTree = new FileTree();
    String[] paths = {"file", "file/file1", "file/file2", "file/file1/file3"};
    try {
      myTree.mkdir(paths);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("Could not create directory");
    }
    String result = "";
    try {
      myTree.makeFile("fileA");
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("could not create file");
    }
      try {
        myTree.cd("/file/file1");
      } catch (NotDirectoryException e) {
        fail("could not cd into directory");
      }
      try {
        myTree.makeFile("fileB");
      } catch (NotDirectoryException | AlreadyExistException e) {
        fail("could not create file");
      }
      try {
        myTree.cd("/file/file1/file3");
      } catch (NotDirectoryException e) {
        fail("could not cd into directory");
      }
      try {
        myTree.makeFile("fileC");
      } catch (NotDirectoryException | AlreadyExistException e) {
        fail("could not create file");
      }
      try {
        myTree.cd("/");
      } catch (NotDirectoryException e) {
        fail("could not cd into directory");
      }
    String[] empty_path = {};
    try {
      result = myTree.ls(true, empty_path);
    } catch (InvalidPathException e1) {
      fail("Could not find the directories");
    }

    assertEquals(result, "Path at /: \nfile\nfileA\nPath at /file: "
        + "\nfile1\nfile2\nPath at /file/file1: \nfile3\nfileB\nPath at "
        + "/file/file1/file3: \nfileC\nPath at /file/file2: \n");
  }
  

  // Test changing directory to a directory that does not exist.
  @Test
  public void testCdDoesNotExsist() {
    FileTree myTree = new FileTree();
    boolean result = false;
    try{
      myTree.cd("/test");
    }
    catch (NotDirectoryException e){
      result = true;
    }
    assertTrue(result);
  }
  
  // Tests change directory on an absolute path that is one away.
  @Test
  public void testCdAbsolutePathOneAway() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory2"};
    try {
      myTree.mkdir(path1);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    try {
      myTree.cd("/directory2");
    } catch (NotDirectoryException e) {
      fail("Could not change to the directory");
    }
    assertEquals("/directory2", myTree.pwd());
  }
  
  // Tests change directory on a relative path that is one away.
  @Test
  public void testCdRelativePathOneAway() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory2"};
    try {
      myTree.mkdir(path1);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    try {
      myTree.cd("directory2");
    } catch (NotDirectoryException e) {
      fail("Could not change to the directory");
    }
    assertEquals("/directory2", myTree.pwd());
  }
  
  // Tests cd on an absolute path that is multiple directories deep
  @Test
  public void testCdAbsolutePathMultiAway() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory2" , "/directory2/directory3", 
    "/directory2/directory3/directory4"};
    try {
      myTree.mkdir(path1);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    try {
      myTree.cd("/directory2/directory3/directory4");
    } catch (NotDirectoryException e) {
      fail("Could not change to the directory");
    }
    assertEquals("/directory2/directory3/directory4", myTree.pwd());
  }
  
  // Tests cd on an relative path that is multiple directories deep
  @Test
  public void testCdRelativePathMultiAway() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory2" , "/directory2/directory3", 
    "/directory2/directory3/directory4"};
    try {
      myTree.mkdir(path1);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    try {
      myTree.cd("directory2/directory3/directory4");
    } catch (NotDirectoryException e) {
      fail("Could not change to the directory");
    }
    assertEquals("/directory2/directory3/directory4", myTree.pwd());
  }
  
  // Tests cd from an absolute path from one deep to many deep
  @Test
  public void testCdAbsolutePathOneDeepToMultiDeep() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory2" , "/directory2/directory3", 
    "/directory2/directory3/directory4"};
    try {
      myTree.mkdir(path1);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    try {
      myTree.cd("directory2");
      myTree.cd("/directory2/directory3/directory4");
    } catch (NotDirectoryException e) {
      fail("Could not change to the directory");
    }
    assertEquals("/directory2/directory3/directory4", myTree.pwd());
  }
  
  // Tests cd from an relative path from one deep to many deep
  @Test
  public void testCdRelativePathOneDeepToMultiDeep() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory2" , "/directory2/directory3", 
    "/directory2/directory3/directory4"};
    try {
      myTree.mkdir(path1);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    try {
      myTree.cd("directory2");
      myTree.cd("directory3/directory4");
    } catch (NotDirectoryException e) {
      fail("Could not change to the directory");
    }
    assertEquals("/directory2/directory3/directory4", myTree.pwd());
  }
  

  // Tests cd from an absolute path from many deep to one deep
  @Test
  public void testCdAbsolutePathMultiDeepToOneDeep() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory2" , "/directory2/directory3", 
    "/directory2/directory3/directory4"};
    try {
      myTree.mkdir(path1);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    try {
      myTree.cd("/directory2/directory3/directory4");
      myTree.cd("/directory2/directory3");
    } catch (NotDirectoryException e) {
      fail("Could not change to the directory");
    }
    assertEquals("/directory2/directory3", myTree.pwd());
  }
  
  // Tests cd from an relative path from many deep to one deep
  @Test
  public void testCdRelativePathMultiDeepToOneDeeper() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory2" , "/directory2/directory3", 
    "/directory2/directory3/directory4"};
    try {
      myTree.mkdir(path1);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    try {
      myTree.cd("/directory2/directory3");
      myTree.cd("directory4");
    } catch (NotDirectoryException e) {
      fail("Could not change to the directory");
    }
    assertEquals("/directory2/directory3/directory4", myTree.pwd());
  }
  
  // Tests cd to the parent directory.
  @Test
  public void testCdUpOneWithDotDot() {
    FileTree myTree = new FileTree();
    String[] path1 = {"directory2" , "/directory2/directory3", 
    "/directory2/directory3/directory4"};
    try {
      myTree.mkdir(path1);
    } catch (NotDirectoryException | AlreadyExistException e) {
      fail("The directories could not be made.");
    }
    try {
      myTree.cd("/directory2/directory3/directory4");
      myTree.cd("..");
    } catch (NotDirectoryException e) {
      fail("Could not change to the directory");
    }
    assertEquals("/directory2/directory3", myTree.pwd());
  }

}

