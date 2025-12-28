package com.dnc.school.service;

import com.dnc.school.dto.ViaCepResponse;
import com.dnc.school.exception.CpfDuplicadoException;
import com.dnc.school.exception.RecursoNaoEncontradoException;
import com.dnc.school.model.Aluno;
import com.dnc.school.model.Endereco;
import com.dnc.school.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ViaCepService viaCepService;

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()) {
            return aluno.get();
        }
        throw new RecursoNaoEncontradoException("Aluno com ID: " + id + " não encontrado");
    }

    public Aluno buscarPorCpf(String cpf) {
        Optional<Aluno> aluno = alunoRepository.findByCpf(cpf);
        if (aluno.isPresent()) {
            return aluno.get();
        }
        throw new RecursoNaoEncontradoException("Aluno com CPF: " + cpf + " não encontrado");
    }

    public Aluno buscarPorMatricula(String matricula) {
        Optional<Aluno> aluno = alunoRepository.findByMatricula(matricula);
        if (aluno.isPresent()) {
            return aluno.get();
        }
        throw new RecursoNaoEncontradoException("Aluno com matricula: " + matricula + " não encontrado");
    }

    public Aluno salvar(Aluno aluno) {
        Optional<Aluno> alunoExistente = alunoRepository.findByCpf(aluno.getCpf());
        if (alunoExistente.isPresent()) {
            throw new CpfDuplicadoException("CPF já cadastrado no sistema: " + aluno.getCpf());
        }
        Endereco endereco = aluno.getEndereco();
        if (endereco != null && endereco.getCep() != null && !endereco.getCep().isEmpty()) {
            try {
                ViaCepResponse dadosCep = viaCepService.buscarEnderecoPorCep(endereco.getCep());
                endereco.setLogradouro(dadosCep.getLogradouro());
                endereco.setBairro(dadosCep.getBairro());
                endereco.setCidade(dadosCep.getCidade());
                endereco.setEstado(dadosCep.getEstado());
            } catch (Exception e) {
                throw new RuntimeException("Falha ao integrar com API de CEP");
            }
        }
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(long id, Aluno alunoAtualizado) {
        // 1° valida se o existe aluno com o ID desejado
        Optional<Aluno> alunoExistente = alunoRepository.findById(id);
        if (alunoExistente.isEmpty()) {
            throw new RecursoNaoEncontradoException("Aluno com ID: " + id + " não encontrado");
        }
        alunoAtualizado.setId(id);

        // 2° valida se o cpf já foi cadastrado para outro aluno
        Optional<Aluno> alunoComMesmoCpf = alunoRepository.findByCpf(alunoAtualizado.getCpf());
        if (alunoComMesmoCpf.isPresent() && !alunoComMesmoCpf.get().getId().equals(alunoAtualizado.getId())) {
            throw new CpfDuplicadoException("CPF já cadastrado no sistema: " + alunoAtualizado.getCpf());
        }
        return alunoRepository.save(alunoAtualizado);
    }

    public boolean deletar(long id) {
        Optional<Aluno> alunoExistente = alunoRepository.findById(id);
        if (alunoExistente.isPresent()) {
            alunoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
