package com.bolsafacil.corretorinteligente.controllers.dtos;

import java.math.BigDecimal;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;

/**
 * MonitoramentoDto
 */
public class MonitoramentoDto {

    public String empresa;
    public BigDecimal precoCompra;
    public BigDecimal precoVenda;
    public long id;


    public Monitoramento converterParaModelo() {
        var monitoramento = new Monitoramento(empresa, precoCompra, precoVenda, id);
        return monitoramento;
    }
}