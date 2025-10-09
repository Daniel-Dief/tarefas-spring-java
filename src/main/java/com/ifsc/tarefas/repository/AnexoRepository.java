package com.ifsc.tarefas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsc.tarefas.model.Anexo;

@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long> {
    List<Anexo> findByTarefaId(Long tarefaId);
}