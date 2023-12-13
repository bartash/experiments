
import java.sql.*;

class jdbc {

  private static final String CONNECTION_URL = "jdbc:hive2://hs2-asherman-hive.dw-env-pwttjn.xcu2-8y8x.local.dwx.dev.cldr.work:443/default;transportMode=http;httpPath=cliservice;socketTimeout=60;ssl=true;retries=3;" ;
//  private static final String CONNECTION_URL = "jdbc:impala://localhost:21050" ;
  private static final String sqlStatementCreate = "CREATE TABLE if not exists helloworld (message String) STORED AS PARQUET";
  private static final String sqlStatementInsert = "INSERT INTO helloworld VALUES (\"helloworld\")";

  private static final String sqlCompiledQuery = "INSERT INTO foo (a)" + " VALUES(?)";
  private static final String oldsqlCompiledQuery = "INSERT INTO tbl_mindarray (source_ip,destination_ip,protocol_number," +
      "source_port,destination_port,packet,volume,duration,pps,bps,bpp,source_latitude,source_longitude," +
      "source_city,source_country,destination_latitude,destination_longitude ,destination_city ,destination_country ," +
      "ingress_volume ,egress_volume ,ingress_packet ,egress_packet ,source_if_index ,destination_if_index," +
      "source_host,event_date,event_time,_time,flow,year)" + " VALUES" +
      "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  ;
  //,event_date,event_time
  //,?,?,?,?,?,?,?
  //source_longitude,source_city," +
  //            "source_country,destination_latitude,destination_longitude ,destination_city ,destination_country

  public static void main(String[] args) {
    System.out.println("Impala using Java");


    Connection connection = null;
    try {
      connection = connectViaDS();
      Statement statement = connection.createStatement();
      statement.execute("drop table foo");
      statement.execute("create table   foo(a varchar(2048))");
      statement.executeBatch();
//      statement.execute("insert into foo values('str')");
//      statement.execute("insert into foo values('str')");
    } catch (Exception e) {
      e.printStackTrace();
    }
    writeInABatchWithCompiledQuery(100);

    System.out.println("Done");

  }


  private static Connection connectViaDS() throws Exception {
    Connection connection = null;

//    Class.forName("com.cloudera.impala.jdbc41.Driver");
    Class.forName("org.apache.hive.jdbc.HiveDriver");


    connection = DriverManager.getConnection(CONNECTION_URL);

    return connection;

  }

  private static void writeInABatchWithCompiledQuery(int records) {

    int protocol_no = 233,s_port=20,d_port=34,packet=46,volume=58,duration=39,pps=76,
        bps=65,bpp=89,i_vol=465,e_vol=345,i_pkt=5,e_pkt=54,s_i_ix=654,d_i_ix=444,_time=1000,flow=989;

    int yr = 1951;

    int flag = 0;

    String s_city = "Mumbai",s_country = "India", s_latt = "12.165.34c", s_long = "39.56.32d",
        s_host="motadata",d_latt="29.25.43c",d_long="49.15.26c",d_city="Damouli",d_country="Nepal";

    long e_date= 1275822966, e_time= 1370517366;

    PreparedStatement preparedStatement;

    // int total = 1000000*1000;

    int total = 100*1000;

    int counter =0;

    Connection connection = null;
    try {
      connection = connectViaDS();

      connection.setAutoCommit(false);

      preparedStatement = connection.prepareStatement(sqlCompiledQuery);


      Timestamp ed = new Timestamp(e_date);
      Timestamp et = new Timestamp(e_time);

      while(counter <total) {

        for (int index = 1; index <= records; index++) {

          counter++;

          preparedStatement.setString(1, "s_ip" + String.valueOf(index));
      /*    //   System.out.println(1);
          preparedStatement.setString(2, "d_ip" + String.valueOf(index));
          //   System.out.println(2);
          preparedStatement.setInt(3, protocol_no + index);
          //   System.out.println(3);
          preparedStatement.setInt(4, s_port + index);
          //  System.out.println(4);
          preparedStatement.setInt(5, d_port + index);
          //  System.out.println(5);
          preparedStatement.setInt(6, packet + index);
          //  System.out.println(6);
          preparedStatement.setInt(7, volume + index);
          //  System.out.println(7);
          preparedStatement.setInt(8, duration + index);
          //   System.out.println(8);
          preparedStatement.setInt(9, pps + index);
          //  System.out.println(9);
          preparedStatement.setInt(10, bps + index);
          //  System.out.println(10);
          preparedStatement.setInt(11, bpp + index);
          //   System.out.println(11);
          preparedStatement.setString(12, s_latt + String.valueOf(index));
          //  System.out.println(12);
          preparedStatement.setString(13, s_long + String.valueOf(index));
          //  System.out.println(13);
          preparedStatement.setString(14, s_city + String.valueOf(index));
          //  System.out.println(14);
          preparedStatement.setString(15, s_country + String.valueOf(index));
          //  System.out.println(15);
          preparedStatement.setString(16, d_latt + String.valueOf(index));
          //   System.out.println(16);
          preparedStatement.setString(17, d_long + String.valueOf(index));
          //   System.out.println(17);
          preparedStatement.setString(18, d_city + String.valueOf(index));
          //   System.out.println(18);
          preparedStatement.setString(19, d_country + String.valueOf(index));
          //   System.out.println(19);
          preparedStatement.setInt(20, i_vol + index);
          //   System.out.println(20);
          preparedStatement.setInt(21, e_vol + index);
          //   System.out.println(21);
          preparedStatement.setInt(22, i_pkt + index);
          //   System.out.println(22);
          preparedStatement.setInt(23, e_pkt + index);
          //   System.out.println(23);
          preparedStatement.setInt(24, s_i_ix + index);
          //   System.out.println(24);
          preparedStatement.setInt(25, d_i_ix + index);
          //    System.out.println(25);
          preparedStatement.setString(26, s_host + String.valueOf(index));
          //   System.out.println(26);
          preparedStatement.setTimestamp(27, ed);
          //   System.out.println(27);
          preparedStatement.setTimestamp(28, et);
          //   System.out.println(28);
          preparedStatement.setInt(29, _time);
          //   System.out.println(29);
          preparedStatement.setInt(30, flow + index);

          preparedStatement.setInt(31, yr + flag);
          //   System.out.println(30);
          // System.out.println(index);*/
          preparedStatement.addBatch();

        }
        preparedStatement.executeBatch();

        preparedStatement.clearBatch();

        //connection.commit();

        System.out.println("Counter = "+counter);

        //flag++;


      }





    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

  }

}
