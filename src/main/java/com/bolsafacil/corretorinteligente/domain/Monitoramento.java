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
    Long id;

    @Column(nullable = false)
    String empresa;    
    @Column(nullable = false, name = "preco_compra")
    BigDecimal precoCompra;
    @Column(nullable = false, name = "preco_venda")
    BigDecimal precoVenda;

    @Column
    Boolean excluido;

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || obj.getClass() != this.getClass())
            return false;

        var other = (Monitoramento) obj;
        return empresa.equals(other.getEmpresa());
    }

    public Monitoramento(String empresa, BigDecimal precoCompra, BigDecimal precoVenda) {
        this.empresa = empresa;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda; 
    }
    /**
     * @return o id
     */
    public Long getId() {
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
}