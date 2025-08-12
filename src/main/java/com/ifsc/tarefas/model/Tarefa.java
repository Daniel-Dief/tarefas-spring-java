package com.ifsc.tarefas.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


// entidade do banco, cria uma tabela em nosso banco h2 a tabela tarefa e 
// os atributos dentro dela
@Entity
public class Tarefa {
    // Informa que nosso atributo "id" é uma chave primária e a cada novo registro ele vai 
    // ser auto incrementado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private = não posso acessar esse atributo fora da classe, outros tipos também usados: public e protected
    // estrutura java: primeiro o tipo da variavel, depois o nome da variavel
    private Long id;
    private String titulo;
    private String descricao;
    private String responsavel;
    private LocalDate dataCriacao = LocalDate.now();
    private LocalDate dataLimite;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Prioridade getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public LocalDate getDataLimite() {
        return dataLimite;
    }
    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }




    
}
