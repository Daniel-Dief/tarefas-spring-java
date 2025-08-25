package com.ifsc.tarefas.services;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsc.tarefas.model.Prioridade;
import com.ifsc.tarefas.model.Status;
import com.ifsc.tarefas.model.Tarefa;
import com.ifsc.tarefas.repository.TarefaRepository;


// anotação pra dizer que é um controller
@Controller
@RequestMapping("/templates")
public class TemplateServices {
    private final TarefaRepository tarefaRepository;
    
    // colocar o nome dessa função o mesmo nome do arquivo que estamos atualmente:
    public TemplateServices(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    // pagina de listagem de tarefas
    @GetMapping("/listar")
    // O model para adicionar atributos para minha view 
    // o html no caso  
    String listarTarefas(Model model){
        // o primeiro argumento é para como esse objeto sera chamado dentro da view
        // o segundo argumento é o objeto(s) que irei passar
        model.addAttribute("tarefas", tarefaRepository.findAll());
        // Vai dizer qual template eu quero usar
        return "lista";
    }


    // pagina de criar nova tarefa
    @GetMapping("/nova-tarefa")
    String novaTarefa(Model model){
        // quem vamos salvar no banco
        model.addAttribute("tarefa", new Tarefa());
        // pegamos os valores e prioridade
        model.addAttribute("prioridades", Prioridade.values());
        // e dos status
        model.addAttribute("listaStatus", Status.values());
        return "tarefa";
    }

    // Api que vai salvar o formulario
    @PostMapping("/salvar")
    // modelAttribute vai pegar os dados do objeto do nome que passamos
    // e que veio na view
    String salvar(@ModelAttribute("tarefa") Tarefa tarefa){
        tarefaRepository.save(tarefa);
        // redireciona depois de salvar direto pra listagem
        return "redirect:/templates/listar";
    }

    // deletar uma terefa com thymelaf
    @PostMapping("/{id}/excluir")
    String excluir(@PathVariable Long id) {
        tarefaRepository.deleteById(id);
        return "redirect:/templates/listar";
    }

    // pagina de editar
    @GetMapping("/{id}/editar")
    String editar(@PathVariable Long id, Model model) {
        // vai procurar tarefas pelo id, se n achar
        var tarefa = tarefaRepository.findById(id).orElse(null);
        if(tarefa == null) {
            // retornar para pagina inicial
            return "redirect:/templates/listar";
        }
        // adiciona a tarefa que vai ser editada e todo o resto ao template do formulario
        model.addAttribute("tarefa", tarefa);
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("listaStatus", Status.values());
        return "tarefa";
    }

}
