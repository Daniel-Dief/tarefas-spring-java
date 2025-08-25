package com.ifsc.tarefas.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


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
    

    // nome da coluna no banco
    @Column(name = "tarefa_id")
    // nome do atributo no back
    private Long id;

    private String titulo;
    private String descricao;
    private String responsavel;
    private LocalDate dataCriacao = LocalDate.now();
    private LocalDate dataLimite;

    // cria uma relação de muitos para muitos
    @ManyToMany
    // criar uma tabela intermediaria que tem o id da tarefa e categoria
    @JoinTable(
        // nome tabela
        name = "tarefa_categoria",
        // o atributo desse objeto que se juntara a tabela intermediaria
        joinColumns = @JoinColumn(name = "tarefa_id"),
        // o atributo do outro objeto que se juntara a tabela intermediaria
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    // List é bom para se usar para criar listas
    // lista de categorias:

    // Many to one digo que essa lista de categorias pertence 
    // a essa tarefa
    // O fetch type eager faz com que ele carregue tudo de uma vez
    // ja o lazy carrega um por um
   
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
    public List<Categoria> getCategorias() {
        return categorias;
    }
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    
}
