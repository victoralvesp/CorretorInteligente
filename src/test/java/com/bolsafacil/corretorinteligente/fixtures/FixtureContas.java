package com.bolsafacil.corretorinteligente.fixtures;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeCompraDeAcoes;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeVendaDeAcoes;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.TipoMovimentacao;

/**
 * FixtureContas
 */
public class FixtureContas {

    
    public MovimentacaoDeConta criarVendaComValorDe(String val) {
        var empresa = "Intel";
        return criarVendaComValorDe(val, empresa);
    }

    public MovimentacaoDeConta criarVendaComValorDe(String val, String empresa) {
        var valorVendido = new BigDecimal(val);
        var data = LocalDateTime.now();
        var quantidade = new BigDecimal("10");
        var movimentacao = new MovimentacaoDeVendaDeAcoes(valorVendido, data, quantidade, empresa);
        return movimentacao;
    }

    public MovimentacaoDeConta criarCompraComValorDe(String val) {
        var empresa = "Intel";
        return criarCompraComValorDe(val, empresa);
    }

    public MovimentacaoDeConta criarCompraComValorDe(String val, String empresa) {
        var valorVendido = new BigDecimal(val);
        var data = LocalDateTime.now();
        var quantidade = new BigDecimal("10");
        var movimentacao = new MovimentacaoDeCompraDeAcoes(valorVendido, data, quantidade, empresa);
        return movimentacao;
    }
    public MovimentacaoDeConta criarMovimentacaoGenerica(String valor, LocalDateTime dataDaMovimentacao) {
        return new MovimentacaoDeConta(){
        
            @Override
            public BigDecimal getValorMovimentado() {
                return new BigDecimal(valor);
            }
        
            @Override
            public TipoMovimentacao getTipoMovimentacao() {
                return null;
            }
        
            @Override
            public BigDecimal getQuantidadeDeAcoesMovimentada() {
                return new BigDecimal("10");
            }
        
            @Override
            public String getEmpresaDaAcaoMovimentada() {
                return "Intel";
            }
        
            @Override
            public LocalDateTime getDataMovimentacao() {
                return dataDaMovimentacao;
            }
        };
    }

}