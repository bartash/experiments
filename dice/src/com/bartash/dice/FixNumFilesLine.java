package com.bartash.dice;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Copy a file in place, but for each line that contains "numFiles",
 * copy the line aagin with numFiles replaced with numFilesErasureCoded
 */
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
      try(Scanner s = new Scanner(newFile).useDelimiter("(?<=\n)|(?!\n)(?<=\r)");
          FileWriter out = new FileWriter(file)) {
        while(s.hasNext()){
          String updatedLine = replaceKeys(s.next());
          out.write(updatedLine);
        }
      }
      boolean delete = newFile.delete();
      if (!delete) {
        throw new RuntimeException("could not delete " + file);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Caught " + e);
    }

  }

  private static String replaceKeys(String line) {
    if (!line.contains("numFiles")) {
      return line;
    }
    // sometimes the output has all the numbers lined up
    String newLine = line.replaceFirst("numFiles {11}", "numFilesErasureCoded");
    if (!newLine.contains("numFilesErasureCoded")) {
      // if the numbers weren't all lined up
      newLine = line.replaceFirst("numFiles", "numFilesErasureCoded");
    }
    newLine = newLine.replaceFirst("\\d+", "0");

    return line + newLine;
  }
}


