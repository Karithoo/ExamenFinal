package crontrolador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clinica.Usuario;

@WebServlet(name = "ValidadorUsuario", urlPatterns = {"/ValidadorUsuario"})
public class ValidadorUsuario<EntityManagerFactory, EntityManager> extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String usuario, password, msg;
        usuario = request.getParameter("usuario");
        password = request.getParameter("password");

        // Validar el usuario utilizando la lógica de negocio adecuada
        if (validarUsuario(usuario, password)) {
            msg = "Usuario y contraseña correcta";
        } else {
            msg = "Revise su usuario y contraseña";
        }

        msg = msg.toUpperCase();

        RequestDispatcher despachador = request.getRequestDispatcher("/mensaje.jsp");
        request.setAttribute("mensaje", msg);
        despachador.forward(request, response);
    }

    private boolean validarUsuario(String usuario, String password) {
        // Aquí deberías implementar la lógica de negocio para validar el usuario
        // Puedes utilizar Hibernate para acceder a la base de datos y realizar la validación
        // Ejemplo ficticio:
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("nombreUnidadPersistencia");
        EntityManager em = emf.createEntityManager();

        try {
            Usuario usuarioBD = em.find(Usuario.class, usuario);
            return usuarioBD != null && usuarioBD.getPassword().equals(password);
        } finally {
            em.close();
            emf.close();
        }
    }
}
