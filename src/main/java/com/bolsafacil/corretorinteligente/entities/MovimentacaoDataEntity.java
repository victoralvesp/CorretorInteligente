package com.bolsafacil.corretorinteligente.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    ContaDataEntity conta;

    @ManyToOne(fetch = FetchType.LAZY)
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
    

    public static MovimentacaoDataEntity converterDe(MovimentacaoDeConta modelo, AcaoObservada observacaoGatilho) {
        if(modelo == null) {
            return null;
        }

        var entity = new MovimentacaoDataEntity();
        entity.dataMovimentacao = modelo.getDataMovimentacao();
        entity.empresa = modelo.getEmpresaDaAcaoMovimentada();
        entity.id = modelo.getId();
        entity.valorMovimentado = modelo.getValorMovimentado();
        entity.quantidadeDeAcoesMovimentada = modelo.getQuantidadeDeAcoesMovimentada();
        entity.conta = ContaDataEntity.converterDe(modelo.getContaMovimentada());
        entity.observacaoGatilho = observacaoGatilho;

        return entity;
    }


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
        var movimentacaoDeVendaDeAcoes = new MovimentacaoDeVendaDeAcoes(valorMovimentado,
                dataMovimentacao, quantidadeDeAcoesMovimentada, empresa, contaConvertida);
        
        movimentacaoDeVendaDeAcoes.setAcaoObservada(observacaoGatilho);
        return movimentacaoDeVendaDeAcoes;
    }

    private MovimentacaoDeConta criarCompra() {
        var contaConvertida = conta.converterParaModelo();
        var modelo = new MovimentacaoDeCompraDeAcoes(valorMovimentado, dataMovimentacao, quantidadeDeAcoesMovimentada, empresa, contaConvertida);
        modelo.setAcaoObservada(observacaoGatilho);
		return modelo;
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
            public ContaPessoal getContaMovimentada() {
                return contaConvertida;
            }

            @Override
            public long getId() {
                return id;
            }

            @Override
            public AcaoObservada getAcaoObservada() {
                return observacaoGatilho;
            }

            @Override
            public void setAcaoObservada(AcaoObservada acao) {
                observacaoGatilho = acao;
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