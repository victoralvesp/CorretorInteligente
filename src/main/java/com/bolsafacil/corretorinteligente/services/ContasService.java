package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;

import javassist.NotFoundException;

/**
 * ContasService
 */
public interface ContasService {

	Collection<ContaPessoal> listar();

	ContaPessoal inserir(ContaPessoal contaConvertida);

	Collection<ContaPessoal> salvar(ContaPessoal... contas);

	ContaPessoal buscar(long idConta) throws NotFoundException;
}