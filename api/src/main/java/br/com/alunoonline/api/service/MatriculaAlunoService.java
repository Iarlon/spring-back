package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.AtualizarNotasRequestDTO;
import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MatriculaAlunoService{
    private static final Double MEDIA_PARA_APROVACAO = 7.0;
    private static final Integer QTD_NOTAS = 2;

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    public void criarMatricula(MatriculaAluno matriculaAluno){
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
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

        if (atualizarNotasRequestDTO.getNota1() != null){
            matriculaAluno.setNota1(atualizarNotasRequestDTO.getNota1());
        }
        if (atualizarNotasRequestDTO.getNota2() != null){
            matriculaAluno.setNota2(atualizarNotasRequestDTO.getNota2());
        }
        calcularMediaEModificarStatus(matriculaAluno);
        matriculaAlunoRepository.save(matriculaAluno);


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
}
