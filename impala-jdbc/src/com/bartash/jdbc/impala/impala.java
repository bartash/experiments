
package com.bartash.jdbc.impala;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class impala {

  public static void main(String[] args) {
    System.out.println("hello world");


    Driver d = new com.cloudera.impala.jdbc.Driver();

    String driverName = "com.cloudera.impala.jdbc.Driver";
    try {
      // Register driver and create driver instance
      Class.forName(driverName);
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    System.out.println("before trying to connect");
    // jdbc:impala://coordinator-asherman-run-run-run.env-sxmzbh.dwx-dev-public.cloudera.com:443/default;AuthMech=3;transportMode=http;httpPath=cliservice;ssl=1
//    String url = "jdbc:impala://coordinator-asherman.env-gj7xn5.dw.xcu2-8y8x.local.dwx.dev.cldr.work:443/default;AuthMech=3;transportMode=http;httpPath=cliservice;ssl=1";
    String url = "jdbc:impala://localhost:28000/default;AuthMech=3;transportMode=http;httpPath=cliservice";

    try {
      Driver driver = DriverManager.getDriver(url);
      System.out.println("Driver class=" + driver.getClass().getName() +
      " major=" + driver.getMajorVersion() +
      " minor=" + driver.getMinorVersion());
    } catch (SQLException sqlException) {
      System.out.println("caught " + sqlException);
    }
    long start = System.currentTimeMillis();
    String connect = url
            + ";LogLevel=6;LogPath=/tmp" // Simba logs to /tmp/Impala_connection_0.log /tmp/ImpalaJDBC_driver.log
            + ";UID=;PWD="; // No credentials
    try (
         Connection con = DriverManager.getConnection(connect)) {
      System.out.println("connected after " + (System.currentTimeMillis() - start) + " ms" );
      // create statement
      Statement stmt = con.createStatement();
      // execute statement
      ResultSet resultSet = stmt.executeQuery("show databases");
      while (resultSet.next()) {
        String name = resultSet.getString(1);
        System.out.println("db = " + name);
      }
      ResultSet resultSet2 = stmt.executeQuery("show tables");
      while (resultSet2.next()) {
        String name = resultSet2.getString(1);
        System.out.println("tbl = " + name);
      }
    } catch (Exception e) {
      System.out.println("caught " + e);
      System.out.println("time since start  " + (System.currentTimeMillis() - start) + " ms" );
      e.printStackTrace();
    }
  }
}