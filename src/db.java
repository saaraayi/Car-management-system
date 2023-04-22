/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saara_sufbl2b
 */
public class db {

    public int userId = 2;

    public void selectAll() {
        String sql = "SELECT * FROM Cars";

        try (Connection conn = db.Connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("Name") + "\t"
                        + rs.getString("Category"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean editCarOwn(String id, String carId) {

        String sql = "UPDATE own SET carId = ?  " + "WHERE id = ?";
        System.out.println(sql);
        try (Connection conn = this.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, carId);
            pstmt.setString(2, id);
            // update 
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean editCarWish(String id, String carId) {

        String sql = "UPDATE wish SET carId = ?  " + "WHERE id = ?";
        System.out.println(sql);
        try (Connection conn = this.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, carId);
            pstmt.setString(2, id);
            // update 
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean editCar(int id, String Name, String Category) {
        System.out.println(id);
        System.out.println(Name);
        System.out.println(Category);
        String sql = "UPDATE cars SET Name = ? , "
                + "Category = ? "
                + "WHERE id = ?";
        System.out.println("sql");
        try (Connection conn = this.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, Name);
            pstmt.setString(2, Category);
            pstmt.setInt(3, id);
            // update 
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean delOwn(int id) {

        String sql = "DELETE FROM own WHERE carId = ? and userId = ?";

        try (Connection conn = this.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            pstmt.setInt(2, Integer.parseInt(fileToString()));
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("Deleted Successfully");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean delWish(int id) {

        String sql = "DELETE FROM wish WHERE carId = ? and userId = ?";
        System.out.println("id");
        System.out.println(id);
        try (Connection conn = this.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            pstmt.setInt(2, Integer.parseInt(fileToString()));
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("Deleted Successfully");
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

    public boolean delCar(int id) {

        String sql = "DELETE FROM cars WHERE id = ?";

        try (Connection conn = this.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("Deleted Successfully");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String getSingleCar(int id) {
        String sql = "SELECT * FROM cars WHERE id = ?";
        String myCar = "";
        try (Connection conn = this.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setInt(1, id);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                myCar = myCar + rs.getString("id") + "!" + rs.getString("Name") + "!" + rs.getString("Category");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return myCar;
    }

    public boolean insertToOwn(String carId) {

        String sql = "INSERT INTO own(carId, userId) VALUES(?,?)";
        System.out.println(sql);
        try (Connection conn = db.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            //pstmt.setString(1, Id);
            pstmt.setInt(1, Integer.parseInt(carId));
            pstmt.setInt(2, Integer.parseInt(fileToString()));
            //pstmt.setString(2, Name);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean insertToWish(String carId) {

        String sql = "INSERT INTO wish(carId, userId) VALUES(?,?)";
        System.out.println(sql);
        try (Connection conn = db.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            //pstmt.setString(1, Id);
            pstmt.setInt(1, Integer.parseInt(carId));
            pstmt.setInt(2, Integer.parseInt(fileToString()));
            //pstmt.setString(2, Name);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String getAllWish() {
        String sql = "SELECT * FROM wish";
        String myCars = "";
        try (Connection conn = db.Connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            int i = 0;
            int j = 0;
            while (rs.next()) {
                //System.out.println(rs.getString("carId"));
                //System.out.println(this.getSingleCar(Integer.parseInt(rs.getString("carId"))));
                System.out.println(userId);
                if (rs.getInt("userId") == Integer.parseInt(fileToString())) {
                    String x = this.getSingleCar(Integer.parseInt(rs.getString("carId")));
                    myCars = myCars + "@" + x + "!" + rs.getInt("id");
                }

                //myCars[i][0] = rs.getString("id");
                //myCars[i][1] = rs.getString("Name");
                //myCars[i][2] = rs.getString("Category");
                //System.out.println(rs.getString("id"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return myCars;
    }

    public String getAllOwn() {
        String sql = "SELECT * FROM own";
        String myCars = "";
        try (Connection conn = db.Connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            int i = 0;
            int j = 0;
            while (rs.next()) {
                //System.out.println(rs.getString("carId"));
                //System.out.println(this.getSingleCar(Integer.parseInt(rs.getString("carId"))));
                if (rs.getInt("userId") == Integer.parseInt(fileToString())) {
                    String x = this.getSingleCar(Integer.parseInt(rs.getString("carId")));
                    myCars = myCars + "@" + x + "!" + rs.getInt("id");
                }

                //myCars[i][0] = rs.getString("id");
                //myCars[i][1] = rs.getString("Name");
                //myCars[i][2] = rs.getString("Category");
                //System.out.println(rs.getString("id"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return myCars;
    }

    public String getAllCars() {
        String sql = "SELECT * FROM Cars";
        String myCars = "";
        try (Connection conn = db.Connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            int i = 0;
            int j = 0;
            while (rs.next()) {
                myCars = myCars + "@" + rs.getString("id") + "!" + rs.getString("Name") + "!" + rs.getString("Category");
                //myCars[i][0] = rs.getString("id");
                //myCars[i][1] = rs.getString("Name");
                //myCars[i][2] = rs.getString("Category");
                //System.out.println(rs.getString("id"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return myCars;
    }

    public String getAllCategory() {
        String sql = "SELECT * FROM category";
        String myCars = "";
        try (Connection conn = db.Connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            int i = 0;
            int j = 0;
            while (rs.next()) {
                myCars = myCars + "@" + rs.getString("id") + "!" + rs.getString("name");
                //myCars[i][0] = rs.getString("id");
                //myCars[i][1] = rs.getString("Name");
                //myCars[i][2] = rs.getString("Category");
                //System.out.println(rs.getString("id"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return myCars;
    }

    public static String fileToString() {
        try {
            String input = null;
            Scanner sc = new Scanner(new File("userId.txt"));
            StringBuffer sb = new StringBuffer();
            while (sc.hasNextLine()) {
                input = sc.nextLine();
                sb.append(input);
            }
            return sb.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public boolean Login(String name, String pass) {

        System.out.println(fileToString());

        String sql = "SELECT * FROM users";
        try (Connection conn = db.Connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                if (name.equals(rs.getString("name")) && pass.equals(rs.getString("pass"))) {
                    //userId = rs.getInt("id");
                    try {
                        FileWriter fw = new FileWriter("userId.txt");
                        BufferedWriter out = new BufferedWriter(fw);
                        out.write(Integer.toString(rs.getInt("id")));
                        out.close();
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;

                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean insertData(String Name, String Category) {
        //System.out.println(Id);
        System.out.println(Name);
        System.out.println(Category);
        String sql = "INSERT INTO Cars(Name,Category) VALUES(?,?)";
        System.out.println(sql);
        try (Connection conn = db.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            //pstmt.setString(1, Id);
            pstmt.setString(1, Name);
            pstmt.setString(2, Category);
            //pstmt.setString(2, Name);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static Connection Connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\2102891\\Desktop\\Application to submit(edited)\\Application\\cars.db");
            System.out.println("successfull");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }

    }
}
