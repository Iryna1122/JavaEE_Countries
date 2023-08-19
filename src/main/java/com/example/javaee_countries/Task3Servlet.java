package com.example.javaee_countries;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "task3Servlet", value = "/task3")
public class Task3Servlet extends HttpServlet {

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
            out.println("<h3>Показати три країни з самою більшою кількістю міст</h3>");

            String command = "SELECT c.countryname, COUNT(city.cityid) AS CountOfCity FROM Countries c " +
                    "JOIN cities city ON c.countryid = city.countryid " +
                    "GROUP BY c.countryname " +
                    "ORDER BY CountOfCity DESC "+
                    "LIMIT 3";
            Statement stat = conn.createStatement();
            ResultSet set = stat.executeQuery(command);

            while(set.next())
            {
                String name = set.getString("countryname");
                out.println("<p>"+name+"</p>");
            }


            out.println("<h3>Показати три країни з самою більшою кількістю жителів</h3>");
            String command2 = "SELECT c.countryname FROM Countries c " +
                              "ORDER BY c.population DESC "+
                              "LIMIT 3";
            Statement stat2 = conn.createStatement();
            ResultSet set2 = stat2.executeQuery(command2);

            while(set2.next())
            {
                String name = set2.getString("countryname");
                out.println("<p>"+name+"</p>");
            }

            out.println("<h3>Показати три країни з самою меншою кількістю жителів</h3>");
            String command3 = "SELECT c.countryname FROM Countries c "+
                    "ORDER BY c.population ASC "+
                    "LIMIT 3";

            Statement stat3 = conn.createStatement();
            ResultSet set3 = stat3.executeQuery(command3);

            while(set3.next())
            {
                String name = set3.getString("countryname");
                out.println("<p>"+name+"</p>");
            }

            out.println("<h3>Показати середню кількість жителів у місті для вказаної країни</h3>");

            String targetCountry = "Ukraine"; // Замініть на назву країни, яку ви розглядаєте
            String command4 = "SELECT AVG(c.Population) AS AveragePopulation " +
                    "FROM Countries c " +
                    "JOIN Cities ct ON c.CountryID = ct.CountryID " +
                    "WHERE c.CountryName = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(command4);
            preparedStatement.setString(1, targetCountry);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double averagePopulation = resultSet.getDouble("AveragePopulation");
                out.println("Average population in " + targetCountry + ": " + averagePopulation);
            } else {
                out.println("No data found for " + targetCountry);
            }

            out.println("<h3>Показати кількість міст з однаковою назвою в різних країнах</h3>");

            String command5 = "SELECT CityName, COUNT(*) AS NumberOfCities " +
                    "FROM Cities " +
                    "GROUP BY CityName " +
                    "HAVING COUNT(*) > 1;";

            Statement stat5 = conn.createStatement();
            ResultSet set5 = stat5.executeQuery(command5);

            while(set5.next())
            {
                String name = set5.getString("cityname");
                int count = set5.getInt("NumberOfCities");
                out.println("<p>City "+name+ " - "+ count+"</p>");
            }



            out.println("<h3>Показати унікальні назви міст із різних країн</h3>");



//            String command6 = "SELECT DISTINCT ct.CityName, c.CountryName FROM Cities ct " +
//                    "JOIN Country c ON ct.CountryID = c.CountryID ";
            String command6 = "SELECT DISTINCT ct.CityName FROM Cities ct " ;

            Statement stat6 = conn.createStatement();
            ResultSet set6 = stat6.executeQuery(command6);

            while(set6.next())
            {
                String name = set6.getString("countryname");
                String name2 = set6.getString("cityname");

                out.println("<p>Country: "+name+ " City "+ name2+"</p>");
            }



            out.println("<h3>Показати країни з кількістю міст в указаному діапазоні</h3>");

            String command7 = "SELECT c.CountryName, COUNT(ct.CityID) AS NumberOfCities\n" +
                    "FROM Countries c\n" +
                    "JOIN Cities ct ON c.CountryID = ct.CountryID\n" +
                    "GROUP BY c.CountryName\n" +
                    "HAVING COUNT(ct.CityID) BETWEEN 2 AND 3 \n ";

            Statement stat7 = conn.createStatement();
            ResultSet set7 = stat7.executeQuery(command7);

            while(set7.next())
            {
                String name = set7.getString("countryname");
                String name2 = set7.getString("cityname");

                out.println("<p>Country: "+name+ " City "+ name2+"</p>");
            }


            //-----------------
//DELETE
            String deleteCommand = "DELETE FROM Countries WHERE countryid = 1";

            Statement statement = conn.createStatement();
            statement.executeUpdate(deleteCommand);
//UPDATE
            String updateCommand = "UPDATE Countries SET countryname = 'Italy' WHERE countryname = 'Zimbabve'";

            Statement statement2 = conn.createStatement();
            statement2.executeUpdate(updateCommand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
