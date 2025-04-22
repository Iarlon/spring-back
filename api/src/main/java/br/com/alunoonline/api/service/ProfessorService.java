package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service

public class ProfessorService {
    @Autowired
    ProfessorRepository professorRepository;

    public void criarProfessor(Professor professor){
        professorRepository.save(professor);
    }
    public List<Professor> listarTodosProfessores(){return professorRepository.findAll();}
    public Optional<Professor> buscarProfessorPorId(Long id){
        return professorRepository.findById(id);
    }
    public void deletarProfessorPorId(Long id){
        professorRepository.deleteById(id);
    }
    public void atualizarProfessorPorId(Long id, Professor professor){
        // PRIMEIRO PASSO: VER SE O PROFESSOR EXISTE NO BD
        Optional<Professor> professorDoBancoDeDados = buscarProfessorPorId(id);

        // E SE NÃO EXISTIR O PROFESSOR
        if (professorDoBancoDeDados.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "professor não encontrado.");
        }

        // SE HOUVER PROFESSOR COM ESSE ID
        // VOU ARMAZENÁ-LO EM UMA VARIÁVEL PARA DEPOIS EDITA-LO
        Professor professorParaEditar = professorDoBancoDeDados.get();

        // PARA EDITAR O PROFESSOR ACIMA IREI UTILIZAR DOS SETS NECESSÁRIOS PARA ATUALIZAR OS ATRIBUTOS DELE
        professorParaEditar.setNome(professor.getNome());
        professorParaEditar.setCpf(professor.getCpf());
        professorParaEditar.setEmail(professor.getEmail());

        // LINHA QUE DEVOLVE O PROFESSOR QUE FOI EDITADO PARA O BANCO DE DADOS
        professorRepository.save(professorParaEditar);

    }
}
