package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service

public class AlunoService {
    @Autowired
    AlunoRepository alunoRepository;

    public void criarAluno(Aluno aluno){
        alunoRepository.save(aluno);
    }
    public List<Aluno> listarTodosAlunos(){return alunoRepository.findAll();}
    public Optional<Aluno> buscarAlunoPorId(Long id){
        return alunoRepository.findById(id);
    }
    public void deletarAlunoPorId(Long id){
        alunoRepository.deleteById(id);
    }
    public void atualizarAlunoPorId(Long id, Aluno aluno){
        // PRIMEIRO PASSO: VER SE O ALUNO EXISTE NO BD
        Optional<Aluno> alunoDoBancoDeDados = buscarAlunoPorId(id);

        // E SE NÃO EXISTIR O ALUNO
        if (alunoDoBancoDeDados.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado.");
        }

        // SE HOUVER ALUNO COM ESSE ID
        // VOU ARMAZENÁ-LO EM UMA VARIÁVEL PARA DEPOIS EDITA-LO
            Aluno alunoParaEditar = alunoDoBancoDeDados.get();

        // PARA EDITAR O ALUNO ACIMA IREI UTILIZAR DOS SETS NECESSÁRIOS PARA ATUALIZAR OS ATRIBUTOS DELE
        alunoParaEditar.setNome(aluno.getNome());
        alunoParaEditar.setCpf(aluno.getCpf());
        alunoParaEditar.setEmail(aluno.getEmail());

        // LINHA QUE DEVOLVE O ALUNO QUE FOI EDITADO PARA O BANCO DE DADOS
        alunoRepository.save(alunoParaEditar);

    }
}
