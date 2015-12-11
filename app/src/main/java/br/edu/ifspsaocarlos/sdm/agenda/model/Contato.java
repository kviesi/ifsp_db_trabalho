package br.edu.ifspsaocarlos.sdm.agenda.model;

import java.io.Serializable;

/**
 * Created by viesi on 30/10/15.
 */
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String nome;
    private String fone;
    private String email;
    private String celular;
    private String aniversario;

    public Contato() {
    }

    public Contato(long id, String nome, String fone, String email) {
        this.id = id;
        this.nome = nome;
        this.fone = fone;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCelular() {
        return celular;
    }

    public void setAniversario(String aniversario) {
        this.aniversario = aniversario;
    }

    public String getAniversario() {
        return aniversario;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", fone='" + fone + '\'' +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                ", aniversario='" + aniversario + '\'' +
                '}';
    }
}
