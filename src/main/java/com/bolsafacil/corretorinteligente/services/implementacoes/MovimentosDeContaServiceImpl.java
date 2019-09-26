package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;
import static java.util.stream.Collectors.toList;
import java.util.stream.StreamSupport;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.entities.MovimentacaoDataEntity;
import com.bolsafacil.corretorinteligente.repositorios.MovimentosDataRepository;
import com.bolsafacil.corretorinteligente.services.MovimentosDeContaService;

import org.springframework.stereotype.Component;

/**
 * MovimentosDeContaServiceImpl
 */
@Component
public class MovimentosDeContaServiceImpl implements MovimentosDeContaService {

    private final MovimentosDataRepository repo;


    public MovimentosDeContaServiceImpl(MovimentosDataRepository repo) {
        this.repo = repo;
    }
    

    @Override
    public Collection<? extends MovimentacaoDeConta> salvar(
            Collection<? extends MovimentacaoDeConta> movimentacoesDeConta, AcaoObservada observacaoGatilho) {
        var movimentacoesEntity = movimentacoesDeConta.stream()
                                        .map(mov -> MovimentacaoDataEntity.converterDe(mov, observacaoGatilho))
                                        .collect(toList());
        var movimentacoesEntitySalvas = repo.saveAll(movimentacoesEntity);

        return StreamSupport.stream(movimentacoesEntitySalvas.spliterator(), false)
                            .map(mov -> mov.converterParaModelo())
                            .collect(toList());
    }

    @Override
    public Collection<? extends MovimentacaoDeConta> listar() {
        var movimentacoesEntitySalvas = repo.findAll();
        return StreamSupport.stream(movimentacoesEntitySalvas.spliterator(),false)
                            .map(mov -> mov.converterParaModelo())
                            .collect(toList());
    }

    
}