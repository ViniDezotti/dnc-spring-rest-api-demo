package com.dnc.school.service;

import com.dnc.school.exception.CpfDuplicadoException;
import com.dnc.school.exception.RecursoNaoEncontradoException;
import com.dnc.school.model.Professor;
import com.dnc.school.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    public Professor buscarPorId(Long id) {
        Optional<Professor> professor = professorRepository.findById(id);
        if (professor.isPresent()) {
            return professor.get();
        }
        throw new RecursoNaoEncontradoException("Professor com ID: " + id + " não encontrado");
    }

    public Professor buscarPorCpf(String cpf) {
        Optional<Professor> professor = professorRepository.findByCpf(cpf);
        if (professor.isPresent()) {
            return professor.get();
        }
        throw new RecursoNaoEncontradoException("Professor com CPF: " + cpf + " não encontrado");
    }

    public List<Professor> buscarPorDepartamento(String departamento) {
        Optional<List<Professor>> professores = professorRepository.findAllByDepartamento(departamento);
        if (professores.isPresent()) {
            return professores.get();
        }
        throw new RecursoNaoEncontradoException("Não foram encontrados professores com departamento " + departamento);
    }

    public Professor salvar(Professor professor) {
        Optional<Professor> professorExistente = professorRepository.findByCpf(professor.getCpf());
        if (professorExistente.isPresent()) {
            throw new CpfDuplicadoException("CPF já cadastrado no sistema: " + professor.getCpf());
        }
        return professorRepository.save(professor);
    }

    public Professor atualizar(long id, Professor professorAtualizado) {
        // 1° valida se o existe professor com o ID desejado
        Optional<Professor> professorExistente = professorRepository.findById(id);
        if (professorExistente.isEmpty()) {
            throw new RecursoNaoEncontradoException("Aluno com ID: " + id + " não encontrado");
        }
        professorAtualizado.setId(id);

        // 2° valida se o cpf já foi cadastrado para outro professor
        Optional<Professor> professorComMesmoCpf = professorRepository.findByCpf(professorAtualizado.getCpf());
        if (professorComMesmoCpf.isPresent() && !professorComMesmoCpf.get().getId().equals(professorAtualizado.getId())) {
            throw new CpfDuplicadoException("CPF já cadastrado no sistema: " + professorAtualizado.getCpf());
        }
        return professorRepository.save(professorAtualizado);
    }

    public boolean deletar(long id) {
        Optional<Professor> professorExistente = professorRepository.findById(id);
        if (professorExistente.isPresent()) {
            professorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
