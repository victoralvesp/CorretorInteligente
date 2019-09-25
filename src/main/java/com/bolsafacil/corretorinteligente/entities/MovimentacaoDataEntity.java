package com.bolsafacil.corretorinteligente.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.Conta;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeCompraDeAcoes;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeVendaDeAcoes;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.TipoMovimentacao;


/**
 * MovimentacaoDataEntity
 */
@Entity
@Table(name = "movimentacoes_de_conta")
public class MovimentacaoDataEntity {

    @Id
    long id;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    ContaDataEntity conta;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gatilho_observacao")
    AcaoObservada observacaoGatilho;

    @Column(nullable = false, name = "empresa_da_acao")
    String empresa;

    @Column(nullable = false, name = "quantidade_de_acoes_movimentada")
    BigDecimal quantidadeDeAcoesMovimentada;

    @Column(nullable = false, name = "valor_movimentado")
    BigDecimal valorMovimentado;

    @Column(name = "data_movimentacao")
    LocalDateTime dataMovimentacao;
    

    public MovimentacaoDeConta converterParaModelo() {
        var tipoDeMovimentacao = definirTipoMovimentacao();
        switch (tipoDeMovimentacao) {
            case VENDA:
                return criarVenda();
            case COMPRA:
                return criarCompra();
            default:
                return criarNeutro();
        }
    }

    private MovimentacaoDeConta criarVenda() {
        var contaConvertida = conta.converterParaModelo();
        return new MovimentacaoDeVendaDeAcoes(valorMovimentado, dataMovimentacao, quantidadeDeAcoesMovimentada, empresa, contaConvertida);
    }

    private MovimentacaoDeConta criarCompra() {
        var contaConvertida = conta.converterParaModelo();
		return new MovimentacaoDeCompraDeAcoes(valorMovimentado, dataMovimentacao, quantidadeDeAcoesMovimentada, empresa, contaConvertida);
	}

	private MovimentacaoDeConta criarNeutro() {
        var contaConvertida = conta.converterParaModelo();
        return new MovimentacaoDeConta(){
        
            @Override
            public BigDecimal getValorMovimentado() {
                return valorMovimentado;
            }
        
            @Override
            public TipoMovimentacao getTipoMovimentacao() {
                return null;
            }
        
            @Override
            public BigDecimal getQuantidadeDeAcoesMovimentada() {
                return quantidadeDeAcoesMovimentada;
            }
        
            @Override
            public String getEmpresaDaAcaoMovimentada() {
                return empresa;
            }
        
            @Override
            public LocalDateTime getDataMovimentacao() {
                return dataMovimentacao;
            }
        
            @Override
            public Conta getContaMovimentada() {
                return conta.converterParaModelo();
            }
        };
    }

    private TipoMovimentacao definirTipoMovimentacao() {
        int valorMovimentadoCompareToZero = valorMovimentado.compareTo(BigDecimal.ZERO);
		if(valorMovimentadoCompareToZero > 0) {
            return TipoMovimentacao.VENDA;
        } else if (valorMovimentadoCompareToZero < 0) {
            return TipoMovimentacao.COMPRA;
        }
        return null;
    }
}