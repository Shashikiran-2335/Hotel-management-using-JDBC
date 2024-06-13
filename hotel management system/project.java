import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
class hms
{
    Scanner sc=new Scanner(System.in);
    void login()
    {
        try 
        {
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shashikiran","root", "Shashi@2335");
                Statement stmt=con.createStatement();
                System.out.println("enter username ");
                String name=sc.next();
                System.out.println("enter password");
                int password=sc.nextInt();
                String qu="select * from account where username="+"'"+name+"'"+" and password="+password;
                ResultSet rs=stmt.executeQuery(qu);
                if(rs.next()==false)
                System.out.println("Access denied");
                else
                {
                  //  Runtime.getRuntime().exec("cls");
                    System.out.println("Acess granted");
                    ResultSet res=stmt.executeQuery(" select Distinct room.roomno,room.roomprice from room,c where c.roomno not in(room.roomno)");
                    System.out.println("Roomno          price");
                    while(res.next())
                    {
                        System.out.println(res.getString(1)+"          "+res.getString(2));
                    }
                System.out.println("enter selected room no");
                int roomno=sc.nextInt();
                ResultSet r=stmt.executeQuery("select * from room where roomno="+roomno);
                if(r.next()==false)
                System.out.println("not a valid roomno");
                else
                {

                    String q="insert into c values(?,?,?,?,sysdate())";  
                    System.out.println("enter phone number");
                    long number=sc.nextLong();
                    System.out.println("enter addhar number");
                    long addharnumber=sc.nextLong();
                    PreparedStatement pstmt=con.prepareStatement(q);
                    pstmt.setString(1, name);
                    pstmt.setLong(2, number);
                    pstmt.setLong(3, addharnumber);
                    pstmt.setInt(4, roomno);
                    pstmt.executeUpdate();
                    System.out.println("room booked successfully");
                }
        } }catch (Exception e) {

            System.out.println(e);
        }
        
    }
    void create()
    {
        try {
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shashikiran","root", "Shashi@2335");
            //Statement stmt=con.createStatement();
            String q1="insert into account values(?,?)";
            PreparedStatement pstmt=con.prepareStatement(q1);
            System.out.println("enter username");
            String username=sc.next();
            System.out.println("enter password");
            int password=sc.nextInt();
            pstmt.setString(1, username);
            pstmt.setInt(2, password);
            pstmt.executeUpdate();
            System.out.println("Account created successfully");
        } catch (Exception e) {
            
            System.out.println(e);
        }
    }
    void checkdetails()
    {
        try {
            System.out.println("enter username");
            String check=sc.next();
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shashikiran","root", "Shashi@2335");
            Statement s=con.createStatement();
            ResultSet re=s.executeQuery("select * from c where username="+"'"+check+"'");
            if(re.next()==false)
            System.out.println("enter correct username or you have not booked any room");
            else
            {
                ResultSet r=s.executeQuery("select * from c where username="+"'"+check+"'");
            while(r.next())
            {
                System.out.println("user name="+r.getString(1));
                System.out.println("phonenumber="+r.getString(2));
                System.out.println("addhar number="+r.getString(3));
                System.out.println("roomno="+r.getString(4));
                System.out.println("date="+r.getString(5));
            }}
        } catch (Exception e) {
            System.out.println(e);
        }


    }
    
    
}
public class App {
    public static void main(String[] args) throws Exception {

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shashikiran","root", "Shashi@2335");
            Scanner sc=new Scanner(System.in);
            while(true)
            {
            System.out.println("1.LOGIN\n2.create account\n3.checkbooking details\n4.exit");
            int choice=sc.nextInt();
            hms o=new hms();
            if(choice==1)
            o.login();
            else if(choice==2)
            o.create();
            else if(choice==3)
            o.checkdetails();
            else
            {
                con.close();
                sc.close();
                break;
            }
        }
        }
        catch (Exception e) {
        System.out.println(e);
       }
    }
}