package br.com.alunoonline.api.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RealizarMatriculaRequest {
    private Long alunoId;
    private Long disciplinaId;
}
