package com.bartash.dice;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copy a file in place, but for each line that contains "numFiles",
 * copy the line again with numFiles replaced with numFilesErasureCoded
 */
public class FixNumFilesLine {

  private static final boolean debug = false;

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
      try (Scanner s = new Scanner(newFile).useDelimiter(
          "(?<=\n)|(?!\n)(?<=\r)"); FileWriter out = new FileWriter(file)) {
        while (s.hasNext()) {
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
    String newLine = line.replaceFirst("numFiles {12}", "numFilesErasureCoded");
    if (!newLine.contains("numFilesErasureCoded")) {
      // if the numbers weren't all lined up
      newLine = line.replaceFirst("numFiles", "numFilesErasureCoded");
    }
    Matcher matcher = Pattern.compile("\\d+")
        .matcher(newLine);
    boolean found = matcher.find();
    if (!found) {
      throw new RuntimeException("could not find integer in  " + newLine);
    }
    StringBuilder paddedZero = new StringBuilder("0");
    int oldNumLength = matcher.end() - matcher.start();
    assert oldNumLength < 1 : "backwards";
    if (debug) {
      System.out.println("oldNumLength = " + oldNumLength);
    }
    for (int i = 1; i < oldNumLength; i++) {
      paddedZero.append(" ");
    }
    if (debug) {
      System.out.println("paddedZero = '" + paddedZero + "'");
    }
    StringBuilder sb = new StringBuilder(newLine);
    sb.replace(matcher.start(), matcher.end(), paddedZero.toString());
    newLine = sb.toString();

    return line + newLine;
  }
}


