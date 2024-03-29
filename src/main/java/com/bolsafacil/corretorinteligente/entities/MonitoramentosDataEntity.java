package com.bolsafacil.corretorinteligente.entities;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;

/**
 * MonitoramentosDataEntity
 */
@Entity
@Table(name = "monitoramentos")
public class MonitoramentosDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    Long id;

    @Column(nullable = false)
    String empresa;    
    @Column(nullable = false, name = "preco_compra")
    BigDecimal precoCompra;
    @Column(nullable = false, name = "preco_venda")
    BigDecimal precoVenda;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_conta")
    ContaDataEntity conta;

    @Column(nullable = false)
    boolean excluido;

    public Monitoramento converterParaModelo() {
        var contaConvertida = conta.converterParaModelo();
        var monitoramento = new Monitoramento(empresa, precoCompra, precoVenda, id);
        monitoramento.setConta(contaConvertida);
        monitoramento.setExcluido(excluido);
        return monitoramento;
    }

    public static MonitoramentosDataEntity converterDe(Monitoramento monitoramento) {
        var monitoramentoEntity = new MonitoramentosDataEntity();

        monitoramentoEntity.conta = ContaDataEntity.converterDe(monitoramento.getConta());
        monitoramentoEntity.empresa = monitoramento.getEmpresa();
        monitoramentoEntity.excluido = monitoramento.getExcluido();
        monitoramentoEntity.precoCompra = monitoramento.getPrecoCompra();
        monitoramentoEntity.precoVenda = monitoramento.getPrecoVenda();
        monitoramentoEntity.id = monitoramento.getId();

        return monitoramentoEntity;
    }

    /**
     * @return a empresa da acao
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @return a conta do monitoramento
     */
    public ContaDataEntity getConta() {
        return conta;
    }
    /**
     * @param conta conta do monitoramento
     */
    public void setConta(ContaDataEntity conta) {
        this.conta = conta;
    }
    
    /**
     * @return o preco de compra da acao registrado no monitoramento
     */
    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }
    
    /**
     * @return o preco de venda da acao registrado no monitoramento
     */
    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    /**
     * @return valor indicando se o monitoramento foi excluido
     */
    public boolean getExcluido() {
        return excluido;
    }

    /**
     * @param excluido valor indicando se o monitoramento foi excluido
     */
    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

	public Long getId() {
		return id;
	}


  
}