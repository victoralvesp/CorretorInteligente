package com.bolsafacil.corretorinteligente.repositorios;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.contas.Conta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;

/**
 * ContasRepository
 */
public interface ContasRepository {

	void salvar(ContaPessoal... contasMovimentadas);

	Collection<ContaPessoal> listar();

	ContaPessoal buscar(String email);
    
}