package com.AppRH.AppRH.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "vaga")
public class Vaga implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento no BD
    private Long codigo;

    @NotEmpty
    @Column(nullable = false, length = 100)
    private String nome;

    @NotEmpty
    @Column(nullable = false, length = 255)
    private String descricao;

    @NotEmpty
    @Column(nullable = false, length = 10)
    private String data;

    @NotEmpty
    @Column(nullable = false, length = 20)
    private String salario;

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Candidato> candidatos;

    // Getters e Setters
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(List<Candidato> candidatos) {
        this.candidatos = candidatos;
    }
}
