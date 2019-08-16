package com.example;

import java.util.ArrayList;

public class Professor {
    private String nome;
    private int idade;
    private String universidade;
    private ArrayList<String> cadeiras;
    
    public Professor() {
        
    }
    
    public Professor(String nome, int idade, String universidade, ArrayList<String> cadeiras) {
        this.nome = nome;
        this.idade = idade;
        this.universidade = universidade;
        this.cadeiras = cadeiras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getUniversidade() {
        return universidade;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public ArrayList<String> getCadeiras() {
        return cadeiras;
    }

    public void setCadeiras(ArrayList<String> cadeiras) {
        this.cadeiras = cadeiras;
    }
    
    
}
