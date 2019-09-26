package com.bolsafacil.corretorinteligente.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcao;
import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcaoImpl;


/**
 * ContaDeAcaoDataEntity
 */
@Entity
@Table(name = "contas_de_acao")
public class ContaDeAcaoDataEntity {

    @Id
    long id;

    @Column(nullable = false, name = "nome_empresa_acao")
    String empresa;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_conta")
    ContaDataEntity conta;

    
    @Column(nullable = false, name = "data_ultima_atualizacao")
    LocalDateTime dataUltimaAtualizacao;

    @Column(nullable = false, name = "quantidade_disponivel")
    BigDecimal quantidadeDisponivel;

    public ContaDeAcao converterParaModelo() {
        return new ContaDeAcaoImpl(quantidadeDisponivel, dataUltimaAtualizacao, empresa, id);
    }

    public static ContaDeAcaoDataEntity converterDe(ContaDeAcao conta) {
        var contaEntity = new ContaDeAcaoDataEntity();
        contaEntity.dataUltimaAtualizacao = conta.getDataUltimaAtualizacao();
        contaEntity.empresa = conta.getEmpresaDaAcao();
        contaEntity.id = conta.getId();
        contaEntity.quantidadeDisponivel = conta.getSaldoAtual();

        return contaEntity;
    }

}