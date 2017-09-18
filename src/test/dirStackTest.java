package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import dirStack.*;

public class dirStackTest {
  DirStack testStack;

  @Before
  public void setUp() throws Exception {
    testStack = new DirStack();
  }

  @Test
  public void testpushd() {
    testStack.push("test");
    assertEquals(testStack.pop(), "test");
  }
  
  @Test
  public void testpopd() {
    testStack.push("test");
    assertEquals(testStack.pop(), "test");
  }
  
  @Test
  public void testisEmptyFalse() {
    testStack.push("test");
    assertEquals(testStack.isEmpty(), false);
  }
  
  @Test
  public void testisEmptyTrue() {
    assertEquals(testStack.isEmpty(), true);
  }
  
  @Test
  public void testLIFOprincipal() {
    testStack.push("test0");
    testStack.push("test1");
    testStack.push("test2");
    testStack.push("test3");
    assertEquals(testStack.pop(), "test3");
    assertEquals(testStack.pop(), "test2");
    assertEquals(testStack.pop(), "test1");
    assertEquals(testStack.pop(), "test0");
  }

}
