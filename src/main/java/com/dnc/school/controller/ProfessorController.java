package com.dnc.school.controller;

import com.dnc.school.exception.RecursoNaoEncontradoException;
import com.dnc.school.model.Aluno;
import com.dnc.school.model.Professor;
import com.dnc.school.service.AlunoService;
import com.dnc.school.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<Professor>> listarTodos() {
        List<Professor> alunos = professorService.listarTodos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable Long id) {
        try {
            Professor professor = professorService.buscarPorId(id);
            return ResponseEntity.ok(professor);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Professor> buscarPorCpf(@PathVariable String cpf) {
        try {
            Professor professor = professorService.buscarPorCpf(cpf);
            return ResponseEntity.ok(professor);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<Professor>> buscarPorDepartamento(@PathVariable String departamento) {
        try {
            List<Professor> professores = professorService.buscarPorDepartamento(departamento);
            return ResponseEntity.ok(professores);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Professor> criar(@RequestBody Professor professor) {
        try {
            Professor professorSalvo = professorService.salvar(professor);
            return ResponseEntity.ok(professorSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Professor> atualizar(@PathVariable Long id, @RequestBody Professor professor) {
        try {
            Professor professorAtualizado = professorService.atualizar(id, professor);
            return ResponseEntity.ok(professorAtualizado);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Professor> deletar(@PathVariable Long id) {
        boolean resultado = professorService.deletar(id);
        if (resultado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
