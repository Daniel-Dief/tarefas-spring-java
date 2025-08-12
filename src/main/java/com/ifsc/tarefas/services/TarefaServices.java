package com.ifsc.tarefas.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsc.tarefas.model.Tarefa;
import com.ifsc.tarefas.repository.TarefaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    // anotação pra post --> /tarefas/inserir: 
    // preciso informar a anotação @RequestBody pra informar que vou enviar um body
    @PostMapping("/inserir")
    public ResponseEntity<Tarefa> criarNovaTarefa(@RequestBody Tarefa tarefa){
        return ResponseEntity.ok(tarefaRepository.save(tarefa));
    }

}
