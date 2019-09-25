package com.bolsafacil.corretorinteligente.repositorios.implementacoes;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.ObservacaoDeAcoes;
import com.bolsafacil.corretorinteligente.repositorios.ObservacoesDeAcaoRepository;

import org.springframework.stereotype.Component;

/**
 * ObservacoesDeAcoesRepositoryImpl
 */
@Component
public class ObservacoesDeAcoesRepositoryImpl implements ObservacoesDeAcaoRepository {

    @Override
    public ObservacaoDeAcoes salvar(ObservacaoDeAcoes monitoramento) {
        return null;
    }

    @Override
    public Collection<ObservacaoDeAcoes> listar() {
        return null;
    }

    @Override
    public ObservacaoDeAcoes buscar(long id) {
        return null;
    }

    
}