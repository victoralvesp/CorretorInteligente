package com.bolsafacil.corretorinteligente.entities;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcao;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;

/**
 * ContaDataEntity
 */
@Entity
@Table(name = "contas_pessoais")
public class ContaDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(nullable = false)
    String email;

    @Column(nullable = false, name = "data_ultima_atualizacao")
    LocalDateTime dataUltimaAtualizacao;

    @Column(nullable = false, name = "saldo_disponivel")
    BigDecimal saldoDisponivel;

    @OneToMany
    Set<ContaDeAcaoDataEntity> contasDeAcao;


    public ContaPessoal converterParaModelo() {
        List<ContaDeAcao> contasDeAcaoConvertidas = new ArrayList<ContaDeAcao>();
        if(contasDeAcao != null)
        {
            contasDeAcaoConvertidas = contasDeAcao.stream().map(ent -> ent.converterParaModelo())
                                                     .collect(Collectors.toList());
        }   

        return new ContaPessoal(email, saldoDisponivel, dataUltimaAtualizacao, contasDeAcaoConvertidas, id);
    }


}