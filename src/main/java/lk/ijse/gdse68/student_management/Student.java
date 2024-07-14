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
import java.util.UUID;

@WebServlet(urlPatterns = "/student")
public class Student extends HttpServlet {
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
//        BufferedReader reader = req.getReader();
//        StringBuilder stringBuilder = new StringBuilder();
//        reader.lines().forEach(
//                line -> stringBuilder.append(line).append("\n")
//        );
//        System.out.println(stringBuilder);

//        JsonReader reader = Json.createReader(req.getReader());
//        JsonObject jsonObject = reader.readObject();
//        String id = jsonObject.getString("id");
//        System.out.println("Student id : " + id);

//        //Data to Client
//        var writer = resp.getWriter();
//        writer.write(id);

        //json array processing
//        JsonArray jsonValues = reader.readArray();
//        for (int i = 0; i < jsonValues.size(); i++) {
//            var jsonObject = jsonValues.getJsonObject(i);
//            System.out.println(jsonObject.getString("id"));
//            resp.getWriter().write(jsonObject.getString("id")+"\n");
//        }

//        JsonReader reader = Json.createReader(req.getReader());
//        var students = new StudentDto();

        Jsonb jsonb = JsonbBuilder.create();
//        PrintWriter writer = resp.getWriter();
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


}
