package edu.ifam.aranoua.security.domain;

import java.io.Serializable;

public class Credencial implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;
    private String senha;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
