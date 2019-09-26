package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.entities.ContaDataEntity;
import com.bolsafacil.corretorinteligente.entities.ContaDeAcaoDataEntity;
import com.bolsafacil.corretorinteligente.repositorios.ContasDataRepository;
import com.bolsafacil.corretorinteligente.services.ContasService;

import org.springframework.stereotype.Component;

import javassist.NotFoundException;

/**
 * ContasService
 */
@Component
public class ContasServiceImpl implements ContasService {

    private final ContasDataRepository repoContas;

    public ContasServiceImpl(ContasDataRepository repoContas) {
        this.repoContas = repoContas;
    }


    @Override
    public Collection<ContaPessoal> listar() {
        var contasEntity =  repoContas.findAll();
        var streamContasEnt = StreamSupport.stream(contasEntity.spliterator(), false);
        return streamContasEnt.map(conta -> conta.converterParaModelo())
                        .collect(Collectors.toList());
    }

    @Override
    public ContaPessoal inserir(ContaPessoal contaConvertida) {
        contaConvertida.setDataUltimaAtualizacaoSalva(DefinicoesDoServidor.getDataAtual());
        var contaEntity = ContaDataEntity.converterDe(contaConvertida);
        var contaSalva = repoContas.save(contaEntity);
        return contaSalva.converterParaModelo();
    }


	@Override
	public Collection<ContaPessoal> salvar(ContaPessoal... contas) {
        var contasEntity = Stream.of(contas).map(conta -> ContaDataEntity.converterDe(conta))
                                       .collect(Collectors.toList());
        
        // var contasDeAcaoEntity = Stream.of(contasEntity).flatMap(conta -> conta.getContasDeAcao().stream())
        //                                 .map(contaAcao -> ContaDeAcaoDataEntity.converterDe(contaAcao))
        //                                 .collect(Collectors.toList());
        // repoContasAcao.saveAll(contasDeAcaoEntity);
        var contasEntitySalvas = repoContas.saveAll(contasEntity);
        return StreamSupport.stream(contasEntitySalvas.spliterator(), false)
                        .map(ent -> ent.converterParaModelo())
                        .collect(Collectors.toList());
	}

    @Override
    public ContaPessoal buscar(long idConta) throws NotFoundException {
        var contaEntityMaybe = repoContas.findById(idConta);
        
        if(!contaEntityMaybe.isPresent())
            throw new NotFoundException("Não foi possível encontrar a conta informada");

        return contaEntityMaybe.get().converterParaModelo();
    }
}