package com.ifsc.tarefas.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsc.tarefas.model.Anexo;
import com.ifsc.tarefas.repository.TarefaRepository;

@Controller
@RequestMapping("/templates/anexos")
public class AnexoController {

    @Autowired
    private AnexoService anexoService;
    
    @Autowired
    private TarefaRepository tarefaRepository;
    
    @PostMapping("/{tarefaId}/upload")
    public String uploadAnexo(@PathVariable Long tarefaId, 
                             @RequestParam("arquivo") MultipartFile arquivo,
                             RedirectAttributes redirectAttributes) {
        try {
            anexoService.salvarAnexo(arquivo, tarefaId);
            redirectAttributes.addFlashAttribute("mensagem", "Arquivo anexado com sucesso!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao anexar arquivo: " + e.getMessage());
        }
        
        return "redirect:/templates/" + tarefaId + "/editar";
    }
    
    @GetMapping("/{tarefaId}/listar")
    public String listarAnexos(@PathVariable Long tarefaId, Model model) {
        List<Anexo> anexos = anexoService.getAnexosByTarefaId(tarefaId);
        var tarefa = tarefaRepository.findById(tarefaId).orElse(null);
        
        if (tarefa == null) {
            return "redirect:/templates/listar";
        }
        
        model.addAttribute("anexos", anexos);
        model.addAttribute("tarefa", tarefa);
        
        return "anexos";
    }
    
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadAnexo(@PathVariable Long id) {
        Anexo anexo = anexoService.getAnexo(id);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(anexo.getTipo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + anexo.getNome() + "\"")
                .body(new ByteArrayResource(anexo.getDados()));
    }
    
    @PostMapping("/{id}/excluir")
    public String excluirAnexo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Anexo anexo = anexoService.getAnexo(id);
        Long tarefaId = anexo.getTarefa().getId();
        
        anexoService.deleteAnexo(id);
        redirectAttributes.addFlashAttribute("mensagem", "Anexo excluído com sucesso!");
        
        return "redirect:/templates/anexos/" + tarefaId + "/listar";
    }
}