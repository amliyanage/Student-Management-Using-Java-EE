package lk.ijse.gdse68.student_management;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.student_management.Dto.StudentDto;
import lk.ijse.gdse68.student_management.util.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/student")
public class Student extends HttpServlet {

    private Connection connection;

    private static final String SAVE_STUDENT = "INSERT INTO STUDENT (Stu_Id, Name, email, level) VALUES (?, ?, ?, ?)";
    private static final String GET_STUDENT = "SELECT * FROM STUDENT WHERE Stu_Id = ?";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            var dbClass = getServletContext().getInitParameter("db-class");
            var dbUrl = getServletContext().getInitParameter("db-url");
            var dbUsername = getServletContext().getInitParameter("db-username");
            var dbPassword = getServletContext().getInitParameter("db-password");
            Class.forName(dbClass);
            this.connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try (var reader = req.getReader(); var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            StudentDto studentDto = jsonb.fromJson(reader, StudentDto.class);
            studentDto.setId(Utils.generateId());

            try (PreparedStatement pstm = connection.prepareStatement(SAVE_STUDENT)) {
                pstm.setString(1, studentDto.getId());
                pstm.setString(2, studentDto.getName());
                pstm.setString(3, studentDto.getEmail());
                pstm.setString(4, studentDto.getLevel());

                boolean success = pstm.executeUpdate() > 0;

                if (success) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    writer.write("Save Student Success");
                } else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    writer.write("Save Student Failed");
                }
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: Get Student
        try (var writer = resp.getWriter()) {
        System.out.println("invoked");

            StudentDto studentDto = new StudentDto();
            Jsonb jsonb = JsonbBuilder.create();
            var stuId = req.getParameter("Stu_Id");
            PreparedStatement pstm = connection.prepareStatement(GET_STUDENT);
            pstm.setString(1, stuId);
            var resultSet = pstm.executeQuery();
            while (resultSet.next()){
                studentDto.setId(resultSet.getString(1));
                studentDto.setName(resultSet.getString(2));
                studentDto.setEmail(resultSet.getString(3));
                studentDto.setLevel(resultSet.getString(4));
            }
            resp.setContentType("application/json");
            jsonb.toJson(studentDto, writer);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: Update Student
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: Delete Student
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
