package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.entities.ContaDataEntity;
import com.bolsafacil.corretorinteligente.repositorios.ContasDataRepository;
import com.bolsafacil.corretorinteligente.services.ContasService;

import org.springframework.stereotype.Component;

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
        var contaEntity = ContaDataEntity.converterDe(contaConvertida);
        var contaSalva = repoContas.save(contaEntity);
        return contaSalva.converterParaModelo();
    }


	@Override
	public void salvar(ContaPessoal... contas) {
        var contasEntity = Stream.of(contas).map(conta -> ContaDataEntity.converterDe(conta))
                                       .collect(Collectors.toList()) ;
		repoContas.saveAll(contasEntity);
	}
}