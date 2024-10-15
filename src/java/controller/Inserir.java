package controller;

import bean.Aluno;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Model;

/**
 *
 * @author edson
 */
public class Inserir extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Inserir</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Inserir at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // como vamos usar o Model, então temos que tratar os erros de SQL
        try {
            // passar os valores recebidos do formulário para o objeto Aluno
            Aluno aluno = new Aluno();
            // atribui os valores, mas antes vamos tratar os dados
            // ra, nome e curso

            // capturando os dados do formulário
            String ra = request.getParameter("ra");
            String nome = request.getParameter("nome");
            String curso = request.getParameter("curso");

            // Armazena os erros capturados
            ArrayList<String> erros = new ArrayList();

            // Verifica se os campos estão vazios
            //if (ra.isEmpty()) {
            //    erros.add("Preencha o campo RA");
            //}
            // trim retira todos os espaços do início e do fim da string
            if (ra == null || ra.trim().isEmpty()) {
                erros.add("Preencha o campo RA");
            }

            if (nome.isEmpty()) {
                erros.add("Preencha o campo NOME");
            }

            if (curso.isEmpty()) {
                erros.add("Preencha o campo CURSO");
            }

            if (!erros.isEmpty()) {
                request.setAttribute("mensagem", erros);
                request.getRequestDispatcher("view_cadastrar.jsp").forward(request, response);
            }

            // Se chegou até aqui, é porque podemos gravar
            // Passar os valores recebidos para o objeto
            aluno.setRa(ra);
            aluno.setNome(nome);
            aluno.setCurso(curso);

            // Instancia o Model
            Model alunoModel = new Model();

            // invoca o método inserir() passando o aluno como parâmetro
            alunoModel.inserir(aluno);

            // mensagem de aviso: deu bom!
            request.setAttribute("mensagem", alunoModel.toString());

        } catch (SQLException ex) {
            // retornando a mensagem de erro ao usuário (view)
            request.setAttribute("mensagem", ex.getMessage());
        }

        // se chegou até aqui, é porque deu tudo certo, então....
        request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
