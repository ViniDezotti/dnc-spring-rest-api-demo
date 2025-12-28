package com.dnc.school.controller;

import com.dnc.school.exception.RecursoNaoEncontradoException;
import com.dnc.school.model.Aluno;
import com.dnc.school.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos() {
        List<Aluno> alunos = alunoService.listarTodos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        try {
            Aluno aluno = alunoService.buscarPorId(id);
            return ResponseEntity.ok(aluno);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Aluno> buscarPorCpf(@PathVariable String cpf) {
        try {
            Aluno aluno = alunoService.buscarPorCpf(cpf);
            return ResponseEntity.ok(aluno);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Aluno> buscarPorMatricula(@PathVariable String matricula) {
        try {
            Aluno aluno = alunoService.buscarPorMatricula(matricula);
            return ResponseEntity.ok(aluno);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody Aluno aluno) {
        try {
            Aluno alunoSalvo = alunoService.salvar(aluno);
            return ResponseEntity.ok(alunoSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
        try {
            Aluno alunoAtualizado = alunoService.atualizar(id, aluno);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Aluno> deletar(@PathVariable Long id) {
        boolean resultado = alunoService.deletar(id);
        if (resultado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
