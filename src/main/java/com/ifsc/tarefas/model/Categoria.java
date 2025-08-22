package com.ifsc.tarefas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// categoria tabela
@Entity
public class Categoria {

   @Column(name = "categoria_id")
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String nome;


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
   


   
}
