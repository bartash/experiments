
package com.bartash.dice;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class FixNumFilesLine {
  public static void main(String[] args) {

    try {
      String fileName = args[0];

      System.out.println("file = " + fileName);
      File file = new File(fileName);
      if (!file.exists()) {
        throw new RuntimeException("file does not exist " + file);
      }

      // rename old file using
      // https://stackoverflow.com/questions/1158777/rename-a-file-using-java
      File newFile = new File(fileName + ".old");
      boolean ok = file.renameTo(newFile);
      if (!ok) {
        throw new RuntimeException("rename failed on " + file);
      }

      // https://stackoverflow.com/questions/25640805/how-to-copy-a-file-line-by-line-keeping-its-original-line-breaks
      try(Scanner s=new Scanner(newFile).useDelimiter("(?<=\n)|(?!\n)(?<=\r)");
          FileWriter out= new FileWriter(file)) {

        while(s.hasNext()){
          String line=s.next();
//          String updatedLine = replaceKeys(line);
          String updatedLine = line;
              out.write(updatedLine);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Caught " + e);
    }

  }
}


