// Essa classe modela um objeto Aluno
// Conhecida pelo nome POJO (Plain Old Java Objects ou Objetos Clássicos do Java)
package bean;

// Vamos usar uma interface, pois objetos trafegados na web precisam estar
// serializados em um fluxo de bytes (arquivo).
import java.io.Serializable;

public class Aluno implements Serializable {

    private int id;
    private String ra;
    private String nome;
    private String curso;

    // método construtor vazio
    public Aluno() {
    }

    // construtor valorado (sobrecarga)
    public Aluno(String ra, String nome, String curso) {
        this.ra = ra;
        this.nome = nome;
        this.curso = curso;
    }

    // gets and sets
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

}
