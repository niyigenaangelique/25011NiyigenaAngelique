package main;

import dao.UserDAO;
import model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private static final Logger LOGGER = Logger.getLogger(UserServlet.class.getName());

    public void init() {
        try {
            LogManager.getLogManager().readConfiguration(
                getClass().getClassLoader().getResourceAsStream("logging.properties")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        userDAO = new UserDAO();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in doGet method", e);
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<User> listUser = userDAO.selectAllUsers();
            request.setAttribute("listUsers", listUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("list-users.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in listUser method", e);
            throw new ServletException(e);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in showNewForm method", e);
            throw new ServletException(e);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            User existingUser = userDAO.selectUser(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            request.setAttribute("user", existingUser);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in showEditForm method", e);
            throw new ServletException(e);
        }
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            User newUser = new User(name, email);
            userDAO.insertUser(newUser);
            response.sendRedirect("UserServlet?action=list");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in insertUser method", e);
            throw new IOException(e);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            User user = new User(id, name, email);
            userDAO.updateUser(user);
            response.sendRedirect("UserServlet?action=list");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in updateUser method", e);
            throw new IOException(e);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String id = request.getParameter("id");
            userDAO.deleteUser(id);
            response.sendRedirect("UserServlet?action=list");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in deleteUser method", e);
            throw new IOException(e);
        }
    }
    
    
}
