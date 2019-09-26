package com.bolsafacil.corretorinteligente.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "observacoes_de_acao")
public class AcaoObservada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String empresa;    
    @Column(nullable = false, name = "preco_compra")
    BigDecimal precoCompra;
    @Column(nullable = false, name = "preco_venda")
    BigDecimal precoVenda;

    @Column(nullable = false, name = "data_evento")
    LocalDateTime data;


    public AcaoObservada(String empresa, BigDecimal precoCompra, BigDecimal precoVenda) {
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

    /**
     * @return obtem data do evento
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * @param data atualiza data do evento
     */
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    
}