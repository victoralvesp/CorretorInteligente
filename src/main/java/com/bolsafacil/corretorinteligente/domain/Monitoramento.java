package com.bolsafacil.corretorinteligente.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Monitoramento
 */
public class Monitoramento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(nullable = false)
    String empresa;    
    @Column(nullable = false, name = "preco_compra")
    BigDecimal precoCompra;
    @Column(nullable = false, name = "preco_venda")
    BigDecimal precoVenda;

    @Column(nullable = false)
    boolean excluido;

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || obj.getClass() != this.getClass())
            return false;

        var other = (Monitoramento) obj;
        return empresa.equals(other.getEmpresa());
    }

    public Monitoramento(String empresa, BigDecimal precoCompra, BigDecimal precoVenda, long id) {
        this.empresa = empresa;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda; 
        this.id = id;
    }
    
    public Monitoramento(String empresa, BigDecimal precoCompra, BigDecimal precoVenda) {
        this.empresa = empresa;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda; 
    }

    /**
     * @return o id
     */
    public long getId() {
        return id;
    }
    /**
     * @return a empresa da acao
     */
    public String getEmpresa() {
        return empresa;
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
}