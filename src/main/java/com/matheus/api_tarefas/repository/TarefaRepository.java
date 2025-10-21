package com.matheus.api_tarefas.repository;

import com.matheus.api_tarefas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Arquivo que instancia os métodos, gerenciamento de métodos!
@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {


}
