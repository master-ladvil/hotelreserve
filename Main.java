import java.sql.Connection;
import java.util.Scanner;

public class Main{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        Db db = new Db();
        Connection con = db.connectdb("hotelreserve1_0","postgres","pwd");       
        String name;
        String mobile;
        System.out.println("Welcome to automated room reservation system....");
        System.out.println("please choose a choice... 1.login 2.register to proceed with reservation");
        int ch = sc.nextInt();
        switch(ch){
            case 1 :                
                name = login(con,db);               
                if(name != null){
                    mobile = db.getMobile(con,name);
                    
                    reservation(con,db,name,mobile);
                }else{
                    System.out.println("wrong credentials");
                }
                break;
            case 2 : 
                name = regUser(con,db);
                mobile = db.getMobile(con,name);
                
            default :
            System.out.println("enter 1 or 2");
        }


        
        //regUser(con,db);

        
    
    }
    public static String regUser(Connection con,Db db){
        String fullname;
        String mobile;
        Scanner sc = new Scanner(System.in);
        fullname = getval("fullname :");
        mobile = getval("mobile no :");
        db.addClient(con,"client",fullname,mobile);
        return fullname;

    }
    public static String login(Connection con,Db db){
        String getmob ;
        String name = getval("name");
        String mobile = getval("mobile");
        getmob = db.getMobile(con,name);
        
            if(getmob.equals(mobile)){
                return name;
            }
            else{
                return null;
            }
        
    }
    public static String getval(String name){
        System.out.println("Enter the "+name);
        Scanner sc = new Scanner(System.in);
        String value = sc.next();
        return value;
    }
    public static int getvalInt(String name){
        System.out.println("Enter the "+name);
        Scanner sc = new Scanner(System.in);
        int value = sc.nextInt();
        return value;
    }

    public static void reservation(Connection con, Db db,String name,String mobile){
            int roomno = showAvailableRooms(con,db);
            System.out.println(roomno+"picked");
            String sdate = getval("starting date(yyyy-mm-dd) : ");
            String edate = getval("end date(yyyy-mm-dd : ");
            reservation(con,db,name,mobile,sdate,edate,roomno);
            //changeIsAvailable()
    }
    public static int showAvailableRooms(Connection con, Db db){
        db.getAvailableRooms(con);
        int roomno = getvalInt("room number : ");
        return roomno;
    }
    public static void reservation(Connection con, Db db,String name,String mobile,String sdate,String edate,int roomno){
        db.bookreservation(con,name,mobile,sdate,edate,roomno);
    }
}