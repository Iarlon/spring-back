package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.dtos.AtualizarNotasRequestDTO;
import br.com.alunoonline.api.dtos.HistoricoAlunoResponseDTO;
import br.com.alunoonline.api.dtos.RealizarMatriculaRequest;
import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.service.MatriculaAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")

public class MatriculaAlunoController {
    @Autowired
    MatriculaAlunoService matriculaAlunoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> criarMatricula(@RequestBody RealizarMatriculaRequest request){
        try {
            matriculaAlunoService.criarMatricula(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Matrícula criada com sucesso.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/trancar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void trancarMatricula(@PathVariable Long id){
        matriculaAlunoService.trancarMatricula(id);
    }

    @PatchMapping("/atualizar-notas/{id}")
    public ResponseEntity<?> atualizarNotas(@PathVariable Long id, @RequestBody AtualizarNotasRequestDTO atualizarNotasRequestDTO){
        try {
            matriculaAlunoService.atualizarNota(id, atualizarNotasRequestDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/historico/{alunoId}")
    @ResponseStatus(HttpStatus.OK)
    public HistoricoAlunoResponseDTO emitirHistorico(@PathVariable Long alunoId){
        return matriculaAlunoService.emitirHistorico(alunoId);
    }
}
