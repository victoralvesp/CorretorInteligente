package com.bolsafacil.corretorinteligente.repositorios;

import java.util.stream.Stream;

import com.bolsafacil.corretorinteligente.domain.contas.Conta;

/**
 * ContasRepository
 */
public interface ContasRepository {

	void salvar(Conta... contasMovimentadas);

    
}