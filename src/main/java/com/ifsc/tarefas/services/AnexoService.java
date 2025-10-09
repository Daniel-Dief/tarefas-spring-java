package com.ifsc.tarefas.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ifsc.tarefas.model.Anexo;
import com.ifsc.tarefas.model.Tarefa;
import com.ifsc.tarefas.repository.AnexoRepository;
import com.ifsc.tarefas.repository.TarefaRepository;

@Service
public class AnexoService {

    @Autowired
    private AnexoRepository anexoRepository;
    
    @Autowired
    private TarefaRepository tarefaRepository;
    
    public Anexo salvarAnexo(MultipartFile arquivo, Long tarefaId) throws IOException {
        Optional<Tarefa> tarefaOpt = tarefaRepository.findById(tarefaId);
        if (!tarefaOpt.isPresent()) {
            throw new RuntimeException("Tarefa não encontrada com ID: " + tarefaId);
        }
        
        Tarefa tarefa = tarefaOpt.get();
        
        String fileName = StringUtils.cleanPath(arquivo.getOriginalFilename());
        
        Anexo anexo = new Anexo();
        anexo.setNome(fileName);
        anexo.setTipo(arquivo.getContentType());
        anexo.setDados(arquivo.getBytes());
        anexo.setTarefa(tarefa);
        
        return anexoRepository.save(anexo);
    }
    
    public Anexo getAnexo(Long id) {
        return anexoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado com ID: " + id));
    }
    
    public List<Anexo> getAnexosByTarefaId(Long tarefaId) {
        return anexoRepository.findByTarefaId(tarefaId);
    }
    
    public void deleteAnexo(Long id) {
        anexoRepository.deleteById(id);
    }
}