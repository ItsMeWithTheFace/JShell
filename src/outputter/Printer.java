package outputter;

import exceptions.AlreadyExistException;
import exceptions.NotDirectoryException;
import exceptions.NotFileException;
import fileTree.FileTree;

public class Printer {
	
	private ErrorMessage error = new ErrorMessage();
	
	public void print(Object[] printingPackage){
	    FileTree fileTree = (FileTree) printingPackage[1];
	    String[] args = (String[]) printingPackage[2];
	    String lines = (String) printingPackage[0];
		String operand;
	    try{
	    operand = args[args.length - 2];
	    }
	    catch(IndexOutOfBoundsException e){
	      operand = "";
	    }
	    // if the operator is a >>
	    if (operand.contains(">>")) {
	      // check if the file exists
	      if (fileTree.hasFile(args[args.length - 1])) {
	        try {
	          // if it does, add the text to the end
	          fileTree.getFile(args[args.length - 1]).addLine(lines);
	          // catch exceptions
	        } catch (NotFileException e) {
	          error.notFile(args[args.length - 1]);
	        }
	        // if the file does not exist
	      } else {
	        try {
	          // make a new file
	          fileTree.makeFile(args[args.length - 1]);
	          // catch exceptions
	        } catch (NotDirectoryException e1) {
	          error.notDir(args[args.length -1]);  
	        }
	        catch (AlreadyExistException e2){
	          error.alreadyExist(args[args.length - 1]);
	        }
	        try {
	          // add the text to the end
	          fileTree.getFile(args[args.length - 1]).addLine(lines);
	          // catch exceptions
	        } catch (NotFileException e) {
	          error.notFile(args[args.length -1]);
	        }
	      }
	    }
	    // if the operator is a >
	    else if (operand.contains(">")) {
	      // if the file exists
	      if (fileTree.hasFile(args[args.length - 1])) {
	        try {
	          // clear it and add the text to it
	          fileTree.getFile(args[args.length - 1]).clearFile();
	          fileTree.getFile(args[args.length - 1]).addLine(lines);
	          // catch exceptions
	        } catch (NotFileException e) {
	          error.notFile(args[args.length - 1]);
	        }
	        // if the file does not exist
	      } else {
	        try {
	          // make a new one
	          fileTree.makeFile(args[args.length - 1]);
	          // catch exceptions
	        } catch (NotDirectoryException e1) {
	          error.notDir(args[args.length - 1]);
	        } catch (AlreadyExistException e2) {
	          error.alreadyExist(args[args.length - 1]);
	        }
	        
	        try {
	          // add the text to the end of  the new file
	          fileTree.getFile(args[args.length - 1]).addLine(lines);
	        } catch (NotFileException e) {
	          error.notFile(args[args.length - 1]);
	        }
	      }
	      // if there is no operator, just print the text
	    }
	    else{
	    	System.out.println(lines);
	    }
	}

}
