package com.bolsafacil.corretorinteligente.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "monitoramentos")
public class Monitoramento {

    @Id
    Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_monitoramento")
    Set<AcaoDoMonitoramento> acoesMonitoradas;
    
    @Column(nullable = false, name = "data_evento")
    LocalDateTime data;

    @Column(nullable = false, name = "data_registro")
    LocalDateTime dataRegistro;


    public Monitoramento(Set<AcaoDoMonitoramento> acoesMonitoradas, LocalDateTime data) {
        this.acoesMonitoradas = acoesMonitoradas;
        this.data = data;
    }        


    /**
     * @return as acoes monitoradas nesse monitoramento
     */
    public Set<AcaoDoMonitoramento> getAcoesMonitoradas() {
        return acoesMonitoradas;
    }
    /**
     * @return a data do evento de monitoramento
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * @return data do registro em banco do monitoramento
     */
    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }
    /**
     * @param dataRegistro a data e horo em que o monitoramento foi registrado
     */
    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    /**
     * @return o id
     */
    public Long getId() {
        return id;
    }
    
}