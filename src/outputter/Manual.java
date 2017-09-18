package outputter;

import exceptions.InvalidManualException;


/**
 * 
 * @author Kexin Hu
 * @version 2.0
 *
 */

public class Manual {

  /**
   * Constructor for a Manual object
   */
  public Manual() {}


  /**
   * The method print the manual pages for different commands
   * 
   * @param command The name of the command.
   * @return result The manual page.
   * @throws InvalidManualException
   */
  public String command(String command) throws InvalidManualException {
    // set a variable for return
    String result = null;

    // for different command, call different methods to print the manual
    if (command.equals("exit")) {
      result = manExit();
    } else if (command.equals("mkdir")) {
      result = manMkdir();
    } else if (command.equals("cd")) {
      result = manCd();
    } else if (command.equals("ls")) {
      result = manLs() + manRedirect();
    } else if (command.equals("pwd")) {
      result = manPwd() + manRedirect();
    } else if (command.equals("cat")) {
      result = manCat() + manRedirect();
    } else if (command.equals("pushd")) {
      result = manPushd();
    } else if (command.equals("popd")) {
      result = manPopd();
    } else if (command.equals("history")) {
      result = manHistory() + manRedirect();
    } else if (command.equals("echo")) {
      result = manEcho() + manRedirect();
    } else if (command.equals("man")) {
      result = manMan() + manRedirect();
    } else if (command.equals("mv")) {
      result = manMv();
    } else if (command.equals("cp")) {
      result = manCp();
    } else if (command.equals("curl")) {
      result = manCurl();
    } else if (command.equals("!")) {
      result = manExclamation();
    } else if (command.equals("grep")) {
      result = manGrep() + manRedirect();
    }

    // if the command is not valid, throw an exception
    else {
      throw new InvalidManualException();
    }
    return result;

  }


  /**
   * The method returns a manual for exit
   * 
   * @return result The manual page for exit.
   */
  private String manExit() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- exit\n\n";

    // add the synopsis
    result = result + "Synopsis:    exit\n\n";

    // add the description
    result = result + "Description:\n    The exit command ends JShell.\n\n";

    return result;

  }


  /**
   * The method returns a manual for mkdir.
   * 
   * @return result The manual page for mkdir.
   */
  private String manMkdir() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- mkdir\n\n";

    // add the synopsis
    result = result + "Synopsis:    mkdir DIR ...\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The mkdir command creates directories. Each directory may be\n"
        + "    relative to the current directory or a full path.\n\n";

    return result;

  }


  /**
   * The method returns a manual for cd.
   * 
   * @return result The manual page for cd.
   */
  private String manCd() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- cd\n\n";

    // add the synopsis
    result = result + "Synopsis:    cd DIR\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The cd command changes the working directory to the given\n"
        + "    directory, which may be relstive to the current directory or\n"
        + "    a full path.\n\n";

    return result;

  }


  /**
   * The method returns a manual for ls.
   * 
   * @return result The manual page for ls.
   */
  private String manLs() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- ls\n\n";

    // add the synopsis
    result = result + "Synopsis:    ls [-R][PATH ...]\n\n";

    // add the description
    result = result + "Description:\n"
        + "    If no path are given, the ls command prints the contents of\n"
        + "    the current directory, including all the files and\n"
        + "    directories with a new line following each content.\n\n"
        + "    Otherwise, for each path PATH:\n"
        + "        *If PATH specifies a file, print PATH.\n"
        + "        *If PATH specifies a directory, print PATH, a colon, then\n"
        + "         all the contents in the specified directory, after that,\n"
        + "         print an extra new line.\n\n"
        + "    Each content (file or directory) will be displayed in a new\n"
        + "    line. \n\n"
        + "    If -R is present, recursively list all sub-directories.\n\n";

    return result;

  }


  /**
   * The method returns a manual for pwd.
   * 
   * @return result The manual page for pwd.
   */
  private String manPwd() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- pwd\n\n";

    // add the synopsis
    result = result + "Synopsis:    mkdir pwd\n\n";

    // add the description
    result = result + ">>  Description:\n"
        + "    The pwd command print the whole path to the current working\n"
        + "    directory.\n\n";

    return result;

  }


  /**
   * The method returns a manual for cat.
   * 
   * @return result The manual page for cat.
   */
  private String manCat() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- cat\n\n";

    // add the synopsis
    result = result + "Synopsis:    cat FILE1 [FILE2 ...]\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The cat command displays the content in the given files in\n"
        + "    the JShell. There will be three line break to separate each\n"
        + "    file's contents.\n\n";

    return result;

  }


  /**
   * The method returns a manual for pushd.
   * 
   * @return result The manual page for pushd.
   */
  private String manPushd() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- pushd\n\n";

    // add the synopsis
    result = result + "Synopsis:    pushd DIR\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The pushd command saves the current working directory by\n"
        + "    pushing onto directory stack, then changes the current working\n"
        + "    directory to the given directory DIR.\n\n";

    return result;

  }


  /**
   * The method returns a manual for popd.
   * 
   * @return result The manual page for popd.
   */
  private String manPopd() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- popd\n\n";

    // add the synopsis
    result = result + "Synopsis:    popd\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The popd command removes the top entry from the directory\n"
        + "    stack, then changes the current working directory to the poped\n"
        + "    out directory.\n\n";

    return result;

  }


  /**
   * The method returns a manual for history.
   * 
   * @return result The manual page for history.
   */
  private String manHistory() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- history\n\n";

    // add the synopsis
    result = result + "Synopsis:    history [NUMBER]\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The history command prints recent commands.\n"
        + "    If passing in a valid number NUM, then only the last\n"
        + "    at most NUMBER commands will be printed.\n"
        + "    Each command will be displayed in a new line. An index\n"
        + "    for the command will be at the front of the line, where\n"
        + "    the highest number indecates the most recent command.\n\n";

    return result;

  }


  /**
   * The method returns a manual for echo.
   * 
   * @return result The manual page for echo.
   */
  private String manEcho() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- echo\n\n";

    // add the synopsis
    result = result + "Synopsis:    echo STRING\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The echo command prints the given string in JShell.\n\n";

    return result;

  }


  /**
   * The method returns a manual for man.
   * 
   * @return result The manual page for man.
   */
  private String manMan() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- man\n\n";

    // add the synopsis
    result = result + "Synopsis:    man COMMAND\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The man command prints the munual for a given command.\n\n"
        + "    Avaliable manual includes:\n" + "        *exit\n"
        + "        *mkdir\n" + "        *cd\n" + "        *ls\n"
        + "        *pwd\n" + "        *pushd\n" + "        *popd\n"
        + "        *history\n" + "        *cat\n" + "        *echo\n"
        + "        *man\n" + "        *mv\n" + "        *cp\n"
        + "        *curl\n" + "        *grep\n" + "        *redirect\n\n";

    return result;


  }

  /**
   * The method returns a manual for mv.
   * 
   * @return result The manual page for mv.
   */
  private String manMv() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- mv\n\n";

    // add the synopsis
    result = result + "Synopsis:    mv OLDPATH NEWPATH";

    // add the description
    result = result + "Description:\n"
        + "    The mv command moves item OLDPATH to NEWPATH. Both OLDPATH\n"
        + "    and NEWPATH may be relative to the current directory or may be\n"
        + "    full paths. If NEWPATH is a directory, move the item into the\n"
        + "    directory.\n\n";

    return result;

  }


  /**
   * The method returns a manual for cp.
   * 
   * @return result The manual page for cp.
   */
  private String manCp() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- cp\n\n";

    // add the synopsis
    result = result + "Synopsis:    cp OLDPATH NEWPATH";

    // add the description
    result = result + "Description:\n"
        + "    The cp command copies item OLDPATH to NEWPATH. Both OLDPATH\n"
        + "    and NEWPATH may be relative to the current directory or may be\n"
        + "    full paths. If NEWPATH is a directory, move the item into the\n"
        + "    directory.\n\n";

    return result;

  }


  /**
   * The method returns a manual for curl.
   * 
   * @return result The manual page for curl.
   */
  private String manCurl() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- curl\n\n";

    // add the synopsis
    result = result + "Synopsis:    curl URL\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The curl command retrieves the filr at the given URL and adds\n"
        + "    it into the current working directory.\n\n";

    return result;

  }


  /**
   * The method returns a manual for !.
   * 
   * @return result The manual page for !.
   */
  private String manExclamation() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- !\n\n";

    // add the synopsis
    result = result + "Synopsis:    ! NUMBER\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The ! command recalls and execute the history with the given\n"
        + "    index NUMBER (>=1) .\n\n";

    return result;

  }


  /**
   * The method returns a manual for grep.
   * 
   * @return result The manual page for grep.
   */
  private String manGrep() {
    // set a variable for return
    String result = "";

    // add the subject
    result = result + ">>  Commands Manual -------- grep\n\n";

    // add the synopsis
    result = result + "Synopsis:    grep [-R] REGEX PATH\n\n";

    // add the description
    result = result + "Description:\n"
        + "    The grep command prints lines in files containing REGEX.\n"
        + "        *If –R is not supplied, print any lines containing REGEX\n"
        + "         in PATH, which must be a file.\n"
        + "        *If –R is supplied, and PATH is a directory, recursively\n"
        + "         traverse the directory.\n"
        + "         For all lines in all files that contain REGEX, print the\n"
        + "         path to the file (including the filename), then a colon,\n"
        + "         then the line that contained REGEX.\n\n";

    return result;

  }


  /**
   * The method returns explanation about redirect (> and >>).
   * 
   * @return result The explanation about redirect.
   */
  private String manRedirect() {
    String result = "*Redirect:    COMMAND > OUTFILE or >> OUTFILE\n";
    // add the description
    result = result
        + "    The output of the command will be captured and redirected to\n"
        + "    OUTFILE and no output will be shown to the user.\n"
        + "    All errors will not be captured.\n\n" + "        * > OUTFILE\n"
        + "         Overwrite the STRING into the given file OUTFILE.\n"
        + "         If OUTFILE already exists, erases the old contents.\n"
        + "         If OUPFILE does not exist, acreate a new file.\n"
        + "         STRING will be the only content in OUTFILE.\n\n"
        + "        * >> OUTFILE\n"
        + "         Append the STRING into the given file OUTFILE.\n"
        + "         If OUTFILE already exists, erases the old contents.\n"
        + "         If OUPFILE does not exist, acreate a new file.\n\n";

    return result;

  }
}
