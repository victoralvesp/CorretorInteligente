package com.bolsafacil.corretorinteligente.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "acoes_do_evento_de_observacao")
public class AcaoObservada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(nullable = false)
    String empresa;    
    @Column(nullable = false, name = "preco_compra")
    BigDecimal precoCompra;
    @Column(nullable = false, name = "preco_venda")
    BigDecimal precoVenda;

    public AcaoObservada(String empresa, BigDecimal precoCompra, BigDecimal precoVenda) {
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
    
}