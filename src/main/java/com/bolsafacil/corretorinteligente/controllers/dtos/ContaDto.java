package com.bolsafacil.corretorinteligente.controllers.dtos;

import java.math.BigDecimal;

import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;

/**
 * ContaDto
 */
public class ContaDto {

    public String email;
    public BigDecimal saldoDisponivel;
	public ContaPessoal converterParaModelo() {
		return new ContaPessoal(email, saldoDisponivel);
	}
}