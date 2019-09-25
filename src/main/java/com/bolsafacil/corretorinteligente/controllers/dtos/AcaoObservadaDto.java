package com.bolsafacil.corretorinteligente.controllers.dtos;

import java.math.BigDecimal;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;

/**
 * AcaoObservadaDto
 */
public class AcaoObservadaDto {

    public String empresa;
    public BigDecimal precoCompra;
    public BigDecimal precoVenda;

    public AcaoObservada converterParaModelo() {
        var acao = new AcaoObservada(empresa, precoCompra, precoVenda);
        return acao;
    }
}