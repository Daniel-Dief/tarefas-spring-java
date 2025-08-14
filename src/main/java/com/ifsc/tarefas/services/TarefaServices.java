package com.ifsc.tarefas.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsc.tarefas.model.Tarefa;
import com.ifsc.tarefas.repository.TarefaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController // anotação que indica que essa classe é um service
@RequestMapping("/tarefas") // anotação que define padrão url exemplo: /tarefas/inserir
public class TarefaServices {
   
    // Injetando o repositorio de tarefa pra usar no service e buscar coisas do banco
    private final TarefaRepository tarefaRepository;
    
    public TarefaServices(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    // anotação pra GET
    // pra chamar minha api de buscar todas eu uso --> /tarefas/buscar-todos <--
    @GetMapping("/buscar-todos")
    public ResponseEntity<?> buscarTodas(){
        // uso o repository pra buscar todas as tarefas
        return ResponseEntity.ok(tarefaRepository.findAll());
    }
    
    // api para criar uma nova tarefa
    // link da api para o post --> /tarefas/inserir: 
    // preciso informar a anotação @RequestBody pra informar que vou enviar um body
    @PostMapping("/inserir")
    public ResponseEntity<Tarefa> criarNovaTarefa(@RequestBody Tarefa tarefa){
        return ResponseEntity.ok(tarefaRepository.save(tarefa));
    }


    // api para editar uma tarefa
    // link do put
    // preciso informar a anotação @RequestBody pra informar que vou enviar um body
    // e informar o id para saber qual tarefa eu quero editar
    @PutMapping("editar/{id}")
    // Path variable para informar qual id eu quero editar
    public ResponseEntity<Tarefa> editarTarefa(@PathVariable Long id, @RequestBody Tarefa novaTarefa) {
        // recebi um id
        // quero procurar no banco esse id
        return tarefaRepository.findById(id).map(
            // procuro pelo id e o que eu achar eu altero os atributos
            tarefa -> {
                // pego o atributo antigo e coloco o que veio no novo
                tarefa.setTitulo(novaTarefa.getTitulo());
                // coloca todo o resto que veio novo 
                // nova descrição
                tarefa.setDescricao(novaTarefa.getDescricao());
                // novo responsavel e etc
                tarefa.setResponsavel(novaTarefa.getResponsavel());
                tarefa.setDataLimite(novaTarefa.getDataLimite());
                tarefa.setStatus(novaTarefa.getStatus());
                tarefa.setPrioridade(novaTarefa.getPrioridade());
                // gato 200
                return ResponseEntity.ok(tarefaRepository.save(tarefa));
            }
        // se não achar a tarefa retorna que não encontrou nada
        // erro gato 404
        ).orElse(ResponseEntity.notFound().build());
        
    }


}
