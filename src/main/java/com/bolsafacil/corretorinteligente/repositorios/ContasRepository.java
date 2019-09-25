package com.bolsafacil.corretorinteligente.repositorios;

import java.util.Collection;
import java.util.stream.Stream;

import com.bolsafacil.corretorinteligente.domain.contas.Conta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;

/**
 * ContasRepository
 */
public interface ContasRepository {

	void salvar(Conta... contasMovimentadas);

	Collection<ContaPessoal> listar();

	ContaPessoal buscar(String email);
    
}