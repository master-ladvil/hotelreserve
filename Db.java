import java.sql.*;
public class Db{
    public Connection connectdb(String dbname,String user,String pwd){
        Connection con = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,pwd);
            if(con!=null){
                System.out.println("connection estabished");
            }else{
                System.out.println("Connection failed");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return con;
    }

    public void addClient(Connection con,String tname,String fullname,String mobile){
        Statement stmt;
        try{
            String query = String.format("insert into client(fullname,mobile) values('%s','%s');",fullname,mobile);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Added user "+fullname);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public String getMobile(Connection con,String name){
        Statement stmt;
        ResultSet rs = null;
        String mobilnum = new String();
        try{
            String query = String.format("select mobile from client where fullname = '%s';",name);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
            mobilnum = rs.getString("mobile");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return mobilnum;
    }
    public void getAvailableRooms(Connection con){
        Statement stmt;
        ResultSet rs = null;
        try{
            String query = String.format("SELECT room.id,capacity,rtype,price FROM room JOIN capacity ON room.cid=capacity.id JOIN rtype ON room.tid = rtype.id WHERE isavailable = true;");
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            System.out.print("roomNo ");
            System.out.print("capacity ");
            System.out.print("roomtype ");
            System.out.println("price ");
            while(rs.next()){
                System.out.print(rs.getString("id")+"           ");
                System.out.print(rs.getString("capacity")+"   ");
                System.out.print(rs.getString("rtype")+"   ");
                System.out.println(rs.getString("price")+"   ");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void bookreservation(Connection con,String name,String mobile,String sdate,String edate,int roomno){
        Statement stmt;
        ResultSet rs = null;
        try{
            String query = String.format("select id from client where mobile = '%s';",mobile);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            int clid = rs.getInt("id");
            String rquery = String.format("insert into reservation(rid,clid,sdate,edate) values('%s','%s','%s','%s');",roomno,clid,sdate,edate);
            stmt.executeUpdate(rquery);
            System.out.println("reserved room "+roomno+" under the name "+name);

        }catch(Exception e){
            System.out.println(e);
        }
    }
}