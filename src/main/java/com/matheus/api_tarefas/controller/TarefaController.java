package com.matheus.api_tarefas.controller;

import com.matheus.api_tarefas.model.Tarefa;
import com.matheus.api_tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    // Injeção de Dependência - O Spring injeta o Repository automaticamente
    @Autowired
    private TarefaRepository tarefaRepository;

    // Metodo POST, criação de uma nova tarefa.
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        // Salva a tarefa no banco de dados
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);

        // Retorna status 201 (Created) com a tarefa criada
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaSalva);
    }

    // Metodo GET, lista todas as tarefas disponíveis.
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas() {
        // Busca todas as tarefas do banco
        List<Tarefa> tarefas = tarefaRepository.findAll();

        // Retorna status 200 com a lista de tarefas
        return ResponseEntity.ok(tarefas);
    }

    // Metodo GET, busca determinada tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        // Busca a tarefa pelo ID
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);

        // Se encontrou, retorna 200 (OK) com a tarefa
        // Se não encontrou, retorna 404 (Not Found)
        return tarefa.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Metodo PUT, atualiza determinada tarefa
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id,
                                                  @RequestBody Tarefa tarefaAtualizada) {
        // Verifica se a tarefa existe
        Optional<Tarefa> tarefaExistente = tarefaRepository.findById(id);

        if (tarefaExistente.isPresent()) {
            // Atualização dos dados
            Tarefa tarefa = tarefaExistente.get();
            tarefa.setNome(tarefaAtualizada.getNome());
            tarefa.setDataEntrega(tarefaAtualizada.getDataEntrega());
            tarefa.setResponsavel(tarefaAtualizada.getResponsavel());

            // Salva as alterações no banco de dados
            Tarefa tarefaSalva = tarefaRepository.save(tarefa);

            // Retorna 200 (OK) com a tarefa atualizada
            return ResponseEntity.ok(tarefaSalva);
        } else {
            // Retorna 404 (Not Found) se não encontrou
            return ResponseEntity.notFound().build();
        }
    }

    // Metodo DELETE, com funcionalidade de exclusão por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        // Verifica se a tarefa existe
        if (tarefaRepository.existsById(id)) {
            // Deleta a tarefa
            tarefaRepository.deleteById(id);

            // Retorna 204 (No Content) - sucesso sem conteúdo
            return ResponseEntity.noContent().build();
        } else {
            // Retorna 404 (Not Found) se não encontrou
            return ResponseEntity.notFound().build();
        }
    }
}