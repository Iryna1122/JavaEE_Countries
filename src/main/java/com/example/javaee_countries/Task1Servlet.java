package com.example.javaee_countries;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "task1Servlet", value = "/task1")
public class Task1Servlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        String url = "jdbc:postgresql://localhost:5432/Countries";
        String username = "postgres";
        String password = "postgres";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            PrintWriter out = response.getWriter();
            out.println("<h3>Create tables</h3>");
            response.setContentType("text/html; charset=UTF-8");
//            String postgreCommandCountries = "CREATE TABLE Countries (" +
//                    "CountryID SERIAL PRIMARY KEY," +
//                    "CountryName VARCHAR(255)," +
//                    "Population INT)";
//            Statement statementCountries = conn.createStatement();
//            statementCountries.executeUpdate(postgreCommandCountries);
//
//            String postgreCommandCities = "CREATE TABLE Cities (" +
//                    "CityID SERIAL PRIMARY KEY," +
//                    "CityName VARCHAR(255)," +
//                    "CountryID INT," +
//                    "IsCapital BOOLEAN)";
//            Statement statementCities = conn.createStatement();
//            statementCities.executeUpdate(postgreCommandCities);
//
//            String addForeignKey = "ALTER TABLE Cities " +
//                    "ADD FOREIGN KEY (CountryID) REFERENCES Countries(CountryID)";
//            Statement statementForeignKey = conn.createStatement();
//            statementForeignKey.executeUpdate(addForeignKey);

            //----------------------------------------------------------
//            String insertCountry = "INSERT INTO  Countries(countryname,population) VALUES" +
//                    "('Ukraine', 42000000)," +
//                    "('Italy', 1200000)," +
//                    "('Greece', 35000000)," +
//                    "('Germany', 6500000)";
//            Statement insertCountrystatement = conn.createStatement();
//           insertCountrystatement.executeUpdate(insertCountry);

//           String insertCity = "INSERT INTO cities (cityname,countryid,iscapital) VALUES" +
//                   "('Dnipro', 1, false),"+
//                   "('Rome', 2, true),"+
//                   "('Athens', 3, true),"+
//                   "('Hannover', 4, false)" ;
//
//           Statement insertCitystatement = conn.createStatement();
//           insertCitystatement.executeUpdate(insertCity);

            out.println("<h3>Відображення всіх країн</h3>");
            String command1 = "SELECT * FROM Countries";
            Statement allcountrystate = conn.createStatement();

           ResultSet set1 = allcountrystate.executeQuery(command1);

           while(set1.next())
           {
               String name = set1.getString("CountryName");
               int population = set1.getInt("Population");

               out.println("<table border='1'>");
               out.println("<tr>");
               out.println("<th>Country</th>");
               out.println("<th>Population</th>");
               out.println("</tr>");
               out.println("<tr>");
               out.println("<td>" + name + "</td>");
               out.println("<td>" + population + "</td>");
               out.println("</tr>");
               out.println("</table>");
               out.println("<br>");
           }

            out.println("<h3>Відображення всіх of Greece</h3>");
           String command2 = "SELECT cityname FROM cities WHERE countryid = 3";

            Statement statement = conn.createStatement();

            ResultSet set2 = statement.executeQuery(command2);

            while (set2.next())
            {
                String name = set2.getString("cityname");
                out.println("<p>"+name+"</p>");
            }


            out.println("<h3>Відображення всіх столиць</h3>");

            String command3 = "SELECT cityname FROM cities WHERE iscapital = true";

            Statement statement3 = conn.createStatement();

            ResultSet set3 = statement3.executeQuery(command3);

            while (set3.next())
            {
                String name = set3.getString("cityname");
                out.println("<p>"+name+"</p>");
            }


            out.println("<h3>Відображення столиці конкретної країни</h3>");
            String command4 = "SELECT cityname FROM cities WHERE iscapital = true AND countryid = 2";

            Statement statement4 = conn.createStatement();

            ResultSet set4 = statement4.executeQuery(command4);

            while (set4.next())
            {
                String name = set4.getString("cityname");
                out.println("<p>"+name+"</p>");
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
