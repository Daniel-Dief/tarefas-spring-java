package com.ifsc.tarefas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Anexo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anexo_id")
    private Long id;
    
    private String nome;
    
    private String tipo;
    
    @Lob
    @Column(length = 20971520) // 20MB max
    private byte[] dados;
    
    @ManyToOne
    @JoinColumn(name = "tarefa_id")
    private Tarefa tarefa;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public byte[] getDados() {
        return dados;
    }
    
    public void setDados(byte[] dados) {
        this.dados = dados;
    }
    
    public Tarefa getTarefa() {
        return tarefa;
    }
    
    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }
}