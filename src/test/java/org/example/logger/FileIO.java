package org.example.logger;

import org.example.entities.User;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileIO {
    private static File fileErrors = new File("errors.log");
    private static File userCard = new File("user.html");
    private static StringBuilder builderUser = new StringBuilder();

    public static void logUser(User user){
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy ' | ' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        builderUser
                .append("<html>")
                .append("<head>")
                .append("<title> User info </title>")
                .append("</head>")
                .append("<body>")
                .append("<b>").append(formatter.format(date)).append("</b>")
                .append("<table border='1'>")
                .append("<tr>").append("<td>").append("Username ").append("</td>").append("<td>").append(user.getUsername()).append("</td>").append("</tr>")
                .append("<tr>").append("<td>").append("Email ").append("</td>").append("<td>").append(user.getEmail()).append("</td>").append("</tr>")
                .append("<tr>").append("<td>").append("Password ").append("</td>").append("<td>").append(user.getPassword()).append("</td>").append("</tr>")
                .append("<tr>").append("<td>").append("Age ").append("</td>").append("<td>").append(user.getAge()).append("</td>").append("</tr>")
                .append("</table>")
                .append("</body>")
                .append("</html>");
        try{
           FileWriter fileWriter = new FileWriter(userCard);
           fileWriter.write(builderUser.toString());
           fileWriter.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    //public void writeError()
}
