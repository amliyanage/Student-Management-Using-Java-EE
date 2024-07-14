package lk.ijse.gdse68.student_management;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.student_management.Dto.StudentDto;
import lk.ijse.gdse68.student_management.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.UID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(urlPatterns = "/student")
public class Student extends HttpServlet {

    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO, Get Student
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO Save Student
        if ( req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        Jsonb jsonb = JsonbBuilder.create();
        StudentDto studentDto = jsonb.fromJson(req.getReader(), StudentDto.class);
        studentDto.setId(Utils.generateId());

        resp.setContentType("application/json");
        jsonb.toJson(studentDto,resp.getWriter());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: Update Student
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO , Delete Student
    }

    @Override
    public void init() throws ServletException {
        //TODO, Initialize
        try {
            var dbClass = getServletContext().getInitParameter("com.mysql.cj.jdbc.Driver");
            var dbUrl = getServletContext().getInitParameter("jdbc:mysql://localhost:3306/student_management");
            var dbUsername = getServletContext().getInitParameter("root");
            var dbPassword = getServletContext().getInitParameter("Ijse@2024");
            Class.forName(dbClass);
            this. connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
