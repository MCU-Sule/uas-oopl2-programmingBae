package UASPBO2.utils;
/**
 * @author AbednegoSteven-1972009
 */
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HibernateUtil {
    public static Session getSession(){
        SessionFactory sf;
        sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session s;
        s = sf.openSession();
        return (Session) s;

    }

    public static Connection getConnection (){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/oopl20211",
                    "root",
                    ""
            );
        }
        catch(ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }
}
