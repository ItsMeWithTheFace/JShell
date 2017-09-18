package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import errorHandling.Validator;

public class ValidatorTest {
  Validator test;
  // Array intended for use with one argument
  String[] testargs1;
  // Array intended for use with 3 arguments
  String[] testargs3;

  @Before
  public void setUp() throws Exception {
    test = new Validator();
    testargs1 = new String[1];
    testargs3 = new String[3];
  }

  @Test
  public void testValidateMkdirWithRedirection() {
    String[] testargs3 = {"/dir2/dir3", ">", "file.txt"};
    assertTrue(test.validateMkdir(testargs3));
  }

  @Test
  public void testValidateMkdirOneAbsolutePath() {
    String[] testargs1 = {"/dir1/dir2"};
    assertTrue(test.validateMkdir(testargs1));
  }

  @Test
  public void testValidateMkdirThreeAbsolutePaths() {
    String[] testargs3 = {"/dir1/dir2", "/dir4/dir5/dir3", "/dir2"};
    assertTrue(test.validateMkdir(testargs3));
  }

  @Test
  public void testValidateMkdirOneRelativePath() {
    String[] testargs1 = {"dir1/dir2"};
    assertTrue(test.validateMkdir(testargs1));
  }

  @Test
  public void testValidateMkdirThreeRelativePaths() {
    String[] testargs3 = {"dir1/dir2", "dir4/dir5/dir3", "dir2"};
    assertTrue(test.validateMkdir(testargs3));
  }

  @Test
  public void testValidateMkdirOneAbsolutePathSpecialChars() {
    String[] testargs1 = {"/di\"r1/di*>r2"};
    assertFalse(test.validateMkdir(testargs1));
  }

  @Test
  public void testValidateMkdirThreeAbsolutePathsSpecialChars() {
    String[] testargs3 = {"/d*>ir1/dir2", "/di\\r4/dir5/di\"r3", "/dir*2"};
    assertFalse(test.validateMkdir(testargs3));
  }

  @Test
  public void testValidateMkdirThreeAbsolutePathsSomeSpecialChars() {
    String[] testargs3 = {"/dir1/dir2", "/dir4/dir5/dir3", "/dir\"2"};
    assertFalse(test.validateMkdir(testargs3));
  }

  @Test
  public void testValidateMkdirOneRelativePathSpecialChars() {
    String[] testargs1 = {"di\"r1/di*>r2"};
    assertFalse(test.validateMkdir(testargs1));
  }

  @Test
  public void testValidateMkdirThreeRelativePathsSpecialChars() {
    String[] testargs3 = {"d*>ir1/dir2", "di\\r4/dir5/di\"r3", "dir*2"};
    assertFalse(test.validateMkdir(testargs3));
  }

  @Test
  public void testValidateMkdirThreeRelativePathsSomeSpecialChars() {
    String[] testargs3 = {"dir1/dir2", "dir4/dir5/dir3", "dir\"2"};
    assertFalse(test.validateMkdir(testargs3));
  }

  @Test
  public void testValidateMkdirSomeRelativeSomeAbsolutePaths() {
    String[] testargs3 = {"dir1/dir2", "/dir4/dir5/dir3", "dir2/"};
    assertTrue(test.validateMkdir(testargs3));
  }

  @Test
  public void testValidateMkdirSomeRelativeSomeAbsolutePathsSpecialChars() {
    String[] testargs3 = {"di<>r1/dir2", "/dir4/dir*\\5/dir3", "dir2/"};
    assertFalse(test.validateMkdir(testargs3));
  }

  @Test
  public void testValidateCdMultipleArgs() {
    String[] testargs3 = {"/dir234/asd", "/sdasd"};
    assertFalse(test.validateCd(testargs3));
  }

  @Test
  public void testValidateCdWithRedirection() {
    String[] testargs3 = {"/dir234/asd", ">>", "/sdasd"};
    assertTrue(test.validateCd(testargs3));
  }

  @Test
  public void testValidateCdAbsolutePath() {
    String[] testargs1 = {"/dir234/asd"};
    assertTrue(test.validateCd(testargs1));
  }

  @Test
  public void testValidateCdRelativePath() {
    String[] testargs1 = {"dir234/asd"};
    assertTrue(test.validateCd(testargs1));
  }

  @Test
  public void testValidateCdAbsolutePathSpecialChars() {
    String[] testargs1 = {"/dir2:<34/asd"};
    assertFalse(test.validateCd(testargs1));
  }

  @Test
  public void testValidateCdRelativePathSpecialChars() {
    String[] testargs1 = {"dir23*\"4"};
    assertFalse(test.validateCd(testargs1));
  }

  @Test
  public void testValidateLsNoArgs() {
    String[] testargs1 = {""};
    assertTrue(test.validateLs(testargs1));
  }

  @Test
  public void testValidateLsNoArgsRedirection() {
    String[] testargs3 = {"", ">", "file.txt"};
    assertTrue(test.validateLs(testargs3));
  }

  @Test
  public void testValidateLsOneArgRedirection() {
    String[] testargs3 = {"asd", ">>", "/file.txt"};
    assertTrue(test.validateLs(testargs3));
  }

  @Test
  public void testValidateLsOneAbsolutePath() {
    String[] testargs1 = {"/dirasd/asdlk"};
    assertTrue(test.validateLs(testargs1));
  }

  @Test
  public void testValidateLsThreeAbsolutePaths() {
    String[] testargs3 = {"/asdqwe/afgdgf", "/adasd34asd", "/aljh32/dir2/354g"};
    assertTrue(test.validateLs(testargs3));
  }

  @Test
  public void testValidateLsOneRelativePath() {
    String[] testargs1 = {"dirasd/asdlk"};
    assertTrue(test.validateLs(testargs1));
  }

  @Test
  public void testValidateLsThreeRelativePaths() {
    String[] testargs3 = {"asdqwe/afgdgf", "adasd34asd/", "aljh32/dir2/354g"};
    assertTrue(test.validateLs(testargs3));
  }

  @Test
  public void testValidatePwdOneArg() {
    String[] testargs1 = {"asdka;l"};
    assertFalse(test.validatePwd(testargs1));
  }

  @Test
  public void testValidatePwdNoArgs() {
    String[] testargs1 = {};
    assertTrue(test.validatePwd(testargs1));
  }

  @Test
  public void testValidatePwdNoArgsRedirection() {
    String[] testargs3 = {">", "file"};
    assertTrue(test.validatePwd(testargs3));
  }

  @Test
  public void testValidatePopDOneArg() {
    String[] testargs1 = {"asdka;l"};
    assertFalse(test.validatePopD(testargs1));
  }

  @Test
  public void testValidatePopDNoArgs() {
    String[] testargs1 = {};
    assertTrue(test.validatePopD(testargs1));
  }

  @Test
  public void testValidatePopDNoArgsRedirection() {
    String[] testargs3 = {">", "file"};
    assertTrue(test.validatePopD(testargs3));
  }

  @Test
  public void testValidatePushDMultipleArgs() {
    String[] testargs3 = {"/dir234/asd", "/sdasd"};
    assertFalse(test.validatePushD(testargs3));
  }

  @Test
  public void testValidatePushDAbsolutePath() {
    String[] testargs1 = {"/dir234/asd"};
    assertTrue(test.validatePushD(testargs1));
  }

  @Test
  public void testValidatePushDRelativePath() {
    String[] testargs1 = {"dir234/asd"};
    assertTrue(test.validatePushD(testargs1));
  }

  @Test
  public void testValidatePushDAbsolutePathSpecialChars() {
    String[] testargs1 = {"/dir2:<34/asd"};
    assertFalse(test.validatePushD(testargs1));
  }

  @Test
  public void testValidatePushDRelativePathSpecialChars() {
    String[] testargs1 = {"dir23*\"4"};
    assertFalse(test.validatePushD(testargs1));
  }

  @Test
  public void testValidatePushDRelativePathRedirection() {
    String[] testargs3 = {"dir234", ">>", "fas"};
    assertTrue(test.validatePushD(testargs3));
  }

  @Test
  public void testValidateCatOneAbsolutePath() {
    String[] testargs1 = {"/dir1/dir2"};
    assertTrue(test.validateCat(testargs1));
  }

  @Test
  public void testValidateCatThreeAbsolutePaths() {
    String[] testargs3 = {"/dir1/dir2", "/dir4/dir5/dir3", "/dir2"};
    assertTrue(test.validateCat(testargs3));
  }

  @Test
  public void testValidateCatOneRelativePath() {
    String[] testargs1 = {"dir1/dir2"};
    assertTrue(test.validateCat(testargs1));
  }

  @Test
  public void testValidateCatThreeRelativePaths() {
    String[] testargs3 = {"dir1/dir2", "dir4/dir5/dir3", "dir2"};
    assertTrue(test.validateCat(testargs3));
  }

  @Test
  public void testValidateCatOneAbsolutePathSpecialChars() {
    String[] testargs1 = {"/di\"r1/di*>r2"};
    assertFalse(test.validateCat(testargs1));
  }

  @Test
  public void testValidateCatThreeAbsolutePathsSpecialChars() {
    String[] testargs3 = {"/d*>ir1/dir2", "/di\\r4/dir5/di\"r3", "/dir*2"};
    assertFalse(test.validateCat(testargs3));
  }

  @Test
  public void testValidateCatThreeAbsolutePathsSomeSpecialChars() {
    String[] testargs3 = {"/dir1/dir2", "/dir4/dir5/dir3", "/dir\"2"};
    assertFalse(test.validateCat(testargs3));
  }

  @Test
  public void testValidateCatOneRelativePathSpecialChars() {
    String[] testargs1 = {"di\"r1/di*>r2"};
    assertFalse(test.validateCat(testargs1));
  }

  @Test
  public void testValidateCatThreeRelativePathsSpecialChars() {
    String[] testargs3 = {"d*>ir1/dir2", "di\\r4/dir5/di\"r3", "dir*2"};
    assertFalse(test.validateCat(testargs3));
  }

  @Test
  public void testValidateCatThreeRelativePathsSomeSpecialChars() {
    String[] testargs3 = {"dir1/dir2", "dir4/dir5/dir3", "dir\"2"};
    assertFalse(test.validateCat(testargs3));
  }

  @Test
  public void testValidateCatSomeRelativeSomeAbsolutePaths() {
    String[] testargs3 = {"dir1/dir2", "/dir4/dir5/dir3", "dir2/"};
    assertTrue(test.validateCat(testargs3));
  }

  @Test
  public void testValidateCatSomeRelativeSomeAbsolutePathsSpecialChars() {
    String[] testargs3 = {"di<>r1/dir2", "/dir4/dir*\\5/dir3", "dir2/"};
    assertFalse(test.validateCat(testargs3));
  }

  @Test
  public void testValidateCatWithRedirection() {
    String[] testargs3 = {"asda/asda", ">", "asdasd"};
    assertTrue(test.validateCat(testargs3));
  }

  @Test
  public void testValidateFileMultipleArgs() {
    String[] testargs3 = {"/dir234/asd", "/sdasd"};
    assertFalse(test.validateFile(testargs3));
  }

  @Test
  public void testValidateFileAbsolutePath() {
    String[] testargs1 = {"/dir234/asd/"};
    assertTrue(test.validateFile(testargs1));
  }

  @Test
  public void testValidateFileRelativePath() {
    String[] testargs1 = {"dir234/asd"};
    assertTrue(test.validateFile(testargs1));
  }

  @Test
  public void testValidateFileAbsolutePathSpecialChars() {
    String[] testargs1 = {"/dir2:<34/asd"};
    assertFalse(test.validateFile(testargs1));
  }

  @Test
  public void testValidateFileRelativePathSpecialChars() {
    String[] testargs1 = {"dir23*\"4"};
    assertFalse(test.validateFile(testargs1));
  }

  @Test
  public void testValidateManNotOneArg() {
    String[] testargs3 = {"helo", "asdas", "exit"};
    assertFalse(test.validateMan(testargs3));
  }

  @Test
  public void testValidateManKnownCommand() {
    String[] testargs1 = {"cd"};
    assertTrue(test.validateMan(testargs1));
  }

  @Test
  public void testValidateManUnknownCommand() {
    String[] testargs1 = {"yolo"};
    assertFalse(test.validateMan(testargs1));
  }

  @Test
  public void testValidateManKnownAndUnknownCommands() {
    String[] testargs3 = {"cd", "yolo", "ls"};
    assertFalse(test.validateMan(testargs3));
  }

  @Test
  public void testValidateManRedirection() {
    String[] testargs3 = {"cd", ">", "yolo"};
    assertTrue(test.validateMan(testargs3));
  }

  @Test
  public void testValidateEchoWithOneCommand() {
    String[] testargs1 = {"\"asd as\""};
    assertTrue(test.validateEcho(testargs1));
  }

  @Test
  public void testValidateEchoWithIrregularNumCommands() {
    String[] testargs1 = {"sup", ">"};
    assertFalse(test.validateEcho(testargs1));
  }

  @Test
  public void testValidateEchoWith3CommandsButBadSyntax() {
    String[] testargs3 = {"hey", "what's up", "hello"};
    assertFalse(test.validateEcho(testargs3));
  }

  @Test
  public void testValidateEchoWithNoQuotes() {
    String[] testargs3 = {"Hello", ">", "file"};
    assertTrue(test.validateEcho(testargs3));
  }

  @Test
  public void testValidateEchoWithQuotes() {
    String[] testargs3 = {"\"Hello\"", ">", "Hey"};
    assertTrue(test.validateEcho(testargs3));
  }

  @Test
  public void testValidateEchoWithOneArrow() {
    String[] testargs3 = {"\"Hello\"", ">", "Hey"};
    assertTrue(test.validateEcho(testargs3));
  }

  @Test
  public void testValidateEchoWithTwoArrows() {
    String[] testargs3 = {"\"Hello\"", ">>", "file"};
    assertTrue(test.validateEcho(testargs3));
  }

  @Test
  public void testValidateEchoWithInvalidFileName() {
    String[] testargs3 = {"\"Hello\"", ">>", "fil>e:"};
    assertFalse(test.validateEcho(testargs3));
  }

  @Test
  public void testValidateEchoWithValidPathName() {
    String[] testargs3 = {"\"Hello\"", ">>", "/root/right/"};
    assertTrue(test.validateEcho(testargs3));
  }

  @Test
  public void testValidateLsWithROption() {
    String[] testargs3 = {"-r", "asdasd", ">", "/asd/qwe"};
    assertTrue(test.validateLs(testargs3));
  }

  @Test
  public void testValidateMvWithOneArg() {
    String[] testargs1 = {"/asd"};
    assertFalse(test.validateMv(testargs1));
  }

  @Test
  public void testValidateMvWithTwoArgs() {
    String[] testargs3 = {"/asdasd", "/asdqw"};
    assertTrue(test.validateMv(testargs3));
  }

  @Test
  public void testValidateMvWithEqualArgs() {
    String[] testargs3 = {"/arsd", "/arsd"};
    assertTrue(test.validateMv(testargs3));
  }

  @Test
  public void testValidateMvWithRedirection() {
    String[] testargs3 = {"lol", "asda", ">", "file"};
    assertTrue(test.validateMv(testargs3));
  }

  @Test
  public void testValidateCpWithOneArg() {
    String[] testargs1 = {"/asd"};
    assertFalse(test.validateCp(testargs1));
  }

  @Test
  public void testValidateCpWithTwoArgs() {
    String[] testargs3 = {"/asdasd", "/asdqw"};
    assertTrue(test.validateCp(testargs3));
  }

  @Test
  public void testValidateCpWithEqualArgs() {
    String[] testargs3 = {"/arsd", "/arsd"};
    assertTrue(test.validateCp(testargs3));
  }

  @Test
  public void testValidateCpWithRedirection() {
    String[] testargs3 = {"lol", "yol", ">", "file"};
    assertTrue(test.validateCp(testargs3));
  }

  @Test
  public void testValidateCurlWithValidURL() {
    String[] testargs1 = {"http://www.cs.cmu.edu/~spok/grimmtmp/073.txt"};
    assertTrue(test.validateCurl(testargs1));
  }

  @Test
  public void testValidateCurlWithInvalidURL() {
    String[] testargs1 = {"asdasd"};
    assertFalse(test.validateCurl(testargs1));
  }

  @Test
  public void testValidateCurlWithRedirection() {
    String[] testargs3 =
        {"http://www.cs.cmu.edu/~spok/grimmtmp/073.txt", ">", "yolo"};
    assertTrue(test.validateCurl(testargs3));
  }
  
  @Test
  public void testValidateGrepWithValidRegex() {
    String[] testargs3 = {"[a-z]*", "/asdas/asdad"};
    assertTrue(test.validateGrep(testargs3));
  }
  
  @Test
  public void testValidateGrepWithROption() {
    String[] testargs3 = {"-r", "[a-z]*", "asd/asd"};
    assertTrue(test.validateGrep(testargs3));
  }
  
  @Test
  public void testValidateGrepWithRedirection() {
    String[] testargs3 = {"[a-z]*", "/asd/qwe", ">>", "file"};
    assertTrue(test.validateGrep(testargs3));
  }
}
