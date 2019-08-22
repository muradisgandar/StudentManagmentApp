/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagmentapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import studentmanagmentapp.Student;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author murad_isgandar
 */
public class StudentDatabase {

    public static Connection connect() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/studentmanapp?useUnicode=true&characterEncoding=utf-8";
        String username = "root";
        String password = "root12345";
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;

    }

    public static List<Student> getAll() {
        List<Student> result = new ArrayList<>();
        try (Connection conn = connect()) {
            PreparedStatement prpm = conn.prepareStatement(
                    "select * from studentmanapp ");

            ResultSet rs = prpm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String sname = rs.getString("name");
                String ssurname = rs.getString("surname");
                Integer sage = rs.getInt("age");

                result.add(new Student(id + "", sname, ssurname, sage));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return result;
    }

    public static List<Student> getAllStudents(String name, String surname, Integer age) {
        if (name == null && surname == null && age == null) {
            return getAll();
        }

            List<Student> result = new ArrayList<>();
            try (Connection conn = connect()) {
                PreparedStatement prpm = conn.prepareStatement(
                        "select * from studentmanapp where name like ? and surname like ?");
                prpm.setString(1, "%" + name + "%");
                prpm.setString(2, "%" + surname + "%");
                

                ResultSet rs = prpm.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String sname = rs.getString("name");
                    String ssurname = rs.getString("surname");
                    Integer sage = rs.getInt("age");

                    result.add(new Student(id + "", sname, ssurname, sage));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

            return result;
    }

    public static boolean addStudent(Student s) {
        try(Connection conn = connect()) {
            PreparedStatement prpm = conn.prepareStatement("insert studentmanapp(name,surname,age) values(?,?,?)");
            prpm.setString(1, s.getName());
            prpm.setString(2, s.getSurname());
            prpm.setInt(3, s.getAge());
            
            prpm.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean updateStudent(Student s) {
        try(Connection conn = connect()) {
            PreparedStatement prpm = conn.prepareStatement("update studentmanapp set name=?, surname=?, age=? where id=?");
            prpm.setString(1, s.getName());
            prpm.setString(2, s.getSurname());
            prpm.setInt(3, s.getAge());
            prpm.setInt(4, Integer.parseInt(s.getId()));
            
            
            prpm.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean deleteStudent(String id) {
        try(Connection conn = connect()) {
            PreparedStatement prpm = conn.prepareStatement("delete from studentmanapp where id=?");
            prpm.setInt(1, Integer.parseInt(id));
            prpm.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    

}
