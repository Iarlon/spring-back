package br.com.alunoonline.api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class HistoricoAlunoResponseDTO {
    private String nomeAluno;
    private String EmailAluno;
    private String cpfAluno;
    private List<DisciplinasAlunoResponseDTO> disciplinas;
}