package controller;

import bean.Aluno;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import model.Model;

/**
 *
 * @author edson
 */
public class Pesquisar extends HttpServlet {

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
            out.println("<title>Servlet Pesquisar</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Pesquisar at " + request.getContextPath() + "</h1>");
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

        // aqui vai o método para realizar a chamada do Model para a pesquisa
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Valor a ser pesquisado
        String valorDigitado = request.getParameter("valor");

        // validação dos dados enviados
        // se o comprimento de valor for igual a zero, redireciona
        if (valorDigitado.length() == 0) {
            request.setAttribute("mensagem", "Digite um valor para pesquisar...");
            request.getRequestDispatcher("view_pesquisar.jsp").
                    forward(request, response);
        }

        // Tipo de pesquisa: ra, nome ou curso
        String tipo = request.getParameter("tipo");

        // criar um Aluno
        Aluno aluno = new Aluno();

        // atribui ao objeto "aluno" o atributo correspondente
        switch (tipo) {
            case "ra":
                aluno.setRa(valorDigitado);
                break;
            case "nome":
                aluno.setNome(valorDigitado);
                break;
            case "curso":
                aluno.setCurso(valorDigitado);
                break;

            // elaborando uma proteção mínima
            // se a opção enviada for diferente das aceitas, redireciona
            default:
                request.setAttribute("mensagem", "Opção inválida.");
                request.getRequestDispatcher("view_pesquisar.jsp").
                        forward(request, response);
        }

        try {
            // chamar o  Model (construtor)
            Model alunoModel = new Model();

            // atribuindo os valores retornados do Model para uma variável
            List<Aluno> alunosDados;

            // pesquisar tem um parâmetro: tipo que vem do formulário
            alunosDados = alunoModel.pesquisar(aluno, tipo);

            if (alunosDados.size() == 0) {
                request.setAttribute("mensagem", "Dados não localizados!");
                request.getRequestDispatcher("view_mensagem.jsp").
                        forward(request, response);
            } else {
                request.setAttribute("listaAlunos", alunosDados);
                request.getRequestDispatcher("view_listar.jsp").
                        forward(request, response);
            }

            request.setAttribute("listaAlunos", alunosDados);
            request.getRequestDispatcher("view_listar.jsp").
                    forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("mensagem", e.getMessage());
            request.getRequestDispatcher("view_mensagem.jsp").
                    forward(request, response);
        }

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
