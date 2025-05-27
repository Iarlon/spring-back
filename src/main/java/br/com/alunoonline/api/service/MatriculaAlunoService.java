package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.AtualizarNotasRequestDTO;
import br.com.alunoonline.api.dtos.DisciplinasAlunoResponseDTO;
import br.com.alunoonline.api.dtos.HistoricoAlunoResponseDTO;
import br.com.alunoonline.api.dtos.RealizarMatriculaRequest;
import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.model.Disciplina;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import br.com.alunoonline.api.repository.DisciplinaRepository;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaAlunoService{
    private static final Double MEDIA_PARA_APROVACAO = 7.0;
    private static final Integer QTD_NOTAS = 2;

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;
    @Autowired
    AlunoRepository alunoRepository;
    @Autowired
    DisciplinaRepository disciplinaRepository;

    public void criarMatricula(RealizarMatriculaRequest matriculaAlunoRequest){
        MatriculaAluno matricula = new MatriculaAluno();
        validarMatriculaUnica(matriculaAlunoRequest.getAlunoId(), matriculaAlunoRequest.getDisciplinaId());

        Aluno aluno = alunoRepository.findById(matriculaAlunoRequest.getAlunoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        Disciplina disciplina = disciplinaRepository.findById(matriculaAlunoRequest.getDisciplinaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));


        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matricula);
    }
    public void trancarMatricula(Long id){
        MatriculaAluno matricula = matriculaAlunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula aluno não encontrado."));
        // Só tranca se houver o id e o seu STATUS for MATRICULADO
        if(matricula.getStatus().equals(MatriculaAlunoStatusEnum.MATRICULADO)){
            matricula.setStatus(MatriculaAlunoStatusEnum.TRANCADO);
            matriculaAlunoRepository.save(matricula);
    }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível trancar com status MATRICULADO");
        }
    }
    public void atualizarNota(Long matriculaAlunoId, AtualizarNotasRequestDTO atualizarNotasRequestDTO){
        MatriculaAluno matriculaAluno = buscarMatriculaLancarExcecao(matriculaAlunoId);
        validarStatusMatricula(matriculaAlunoId);

        if (atualizarNotasRequestDTO.getNota1() != null){
            matriculaAluno.setNota1(atualizarNotasRequestDTO.getNota1());
        }
        if (atualizarNotasRequestDTO.getNota2() != null){
            matriculaAluno.setNota2(atualizarNotasRequestDTO.getNota2());
        }
        calcularMediaEModificarStatus(matriculaAluno);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public HistoricoAlunoResponseDTO emitirHistorico(Long alunoId){
        List<MatriculaAluno> matriculaAlunos = matriculaAlunoRepository.findByAlunoId(alunoId);
        if(matriculaAlunos.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse aluno não possui matriculas");
        }

        HistoricoAlunoResponseDTO historicoAluno = new HistoricoAlunoResponseDTO();
        historicoAluno.setNomeAluno(matriculaAlunos.get(0).getAluno().getNome());
        historicoAluno.setEmailAluno(matriculaAlunos.get(0).getAluno().getEmail());
        historicoAluno.setCpfAluno(matriculaAlunos.get(0).getAluno().getCpf());

        List<DisciplinasAlunoResponseDTO> disciplinas = matriculaAlunos.stream()
                .map(this::mapearParaDisciplinasAlunoResponseDTO)
                .toList();
        historicoAluno.setDisciplinas(disciplinas);
        return historicoAluno;
    }

    private MatriculaAluno buscarMatriculaLancarExcecao(Long matriculaAlunoId){
        return matriculaAlunoRepository.findById(matriculaAlunoId)
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Matricula do aluno não encontrada"));
    }

    private void calcularMediaEModificarStatus(MatriculaAluno matAluno){
        Double nota1 = matAluno.getNota1();
        Double nota2 = matAluno.getNota2();
        if (nota1 != null && nota2 != null){
            Double media = (nota1 + nota2) / QTD_NOTAS;
            matAluno.setStatus(media >= MEDIA_PARA_APROVACAO ?
                    MatriculaAlunoStatusEnum.APROVADO : MatriculaAlunoStatusEnum.REPROVADO);
        }
    }

    private DisciplinasAlunoResponseDTO mapearParaDisciplinasAlunoResponseDTO(MatriculaAluno matriculaAluno){
        DisciplinasAlunoResponseDTO response = new DisciplinasAlunoResponseDTO();

        response.setNomeDisciplina(matriculaAluno.getDisciplina().getNome());
        response.setNomeProfessor(matriculaAluno.getDisciplina().getProfessor().getNome());
        response.setNota1(matriculaAluno.getNota1());
        response.setNota2(matriculaAluno.getNota2());
        response.setMedia(calcularMedia(matriculaAluno.getNota1(), matriculaAluno.getNota2()));
        response.setStatus(matriculaAluno.getStatus());

        return response;
    }

    private Double calcularMedia(Double nota1, Double nota2){
        return (nota1 != null && nota2 != null ? (nota1 + nota2) / QTD_NOTAS : null);
    }

    private void validarMatriculaUnica(Long alunoId, Long disciplinaId) {
        List<MatriculaAluno> matriculas = matriculaAlunoRepository.findByAlunoIdAndDisciplinaId(alunoId, disciplinaId);
        boolean jaMatriculadoOuAprovado = matriculas.stream()
                .anyMatch(m -> m.getStatus() == MatriculaAlunoStatusEnum.MATRICULADO || m.getStatus() == MatriculaAlunoStatusEnum.APROVADO);

        if (jaMatriculadoOuAprovado) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno já está matriculado ou aprovado nesta disciplina.");
        }
    }
    private void validarStatusMatricula(Long matricula){
        MatriculaAluno matriculaAluno = matriculaAlunoRepository.findById(matricula)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula aluno não encontrado."));
        if (!matriculaAluno.getStatus().equals(MatriculaAlunoStatusEnum.MATRICULADO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível atualizar notas com status MATRICULADO");
        }
    }

}
