package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.contas.Conta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;

/**
 * ContasService
 */
public interface ContasService {

	Collection<ContaPessoal> listar();

	ContaPessoal inserir(ContaPessoal contaConvertida);

	void salvar(Conta... contas);
}