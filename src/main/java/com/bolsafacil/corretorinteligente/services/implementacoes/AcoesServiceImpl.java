package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.repositorios.AcoesObservadasRepository;
import com.bolsafacil.corretorinteligente.services.AcoesService;

import org.springframework.stereotype.Component;

import javassist.NotFoundException;

/**
 * MonitorServiceImpl
 */
@Component
public class AcoesServiceImpl implements AcoesService {

    private final AcoesObservadasRepository repository;

    public AcoesServiceImpl(AcoesObservadasRepository repository) {
        this.repository = repository;
    }

    @Override
    public AcaoObservada salvar(AcaoObservada observacao) {
        var dataAtual = DefinicoesDoServidor.getDataAtual();
        observacao.setData(dataAtual);

        var observacaoSalvo = repository.save(observacao);

        return observacaoSalvo;
    }

    @Override
    public Collection<AcaoObservada> listarObservacoesRealizadas() {
        
        var observacoesSalvas = repository.findAll();

        return StreamSupport.stream(observacoesSalvas.spliterator(), false)
                            .collect(Collectors.toList());
    }

    @Override
    public AcaoObservada buscar(long id) throws NotFoundException{
        var observacao = repository.findById(id);

        if(!observacao.isPresent())
            throw new NotFoundException("Não foi possível encontrar o monitoramento buscado");

        return observacao.get();
    }
}