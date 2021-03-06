package com.caio.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caio.os.domain.Cliente;
import com.caio.os.domain.OS;
import com.caio.os.domain.Tecnico;
import com.caio.os.domain.enums.Prioridade;
import com.caio.os.domain.enums.Status;
import com.caio.os.dtos.OSDTO;
import com.caio.os.repositories.OSRepository;
import com.caio.os.services.exception.ObjectNotFoundException;

@Service
public class OsService {

	@Autowired
	private OSRepository repository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	/*
	 * Busca a ordem de serviço pelo id*/
	public OS findById(Integer id) {
		Optional<OS> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!" + id + ", Tipo: " + OS.class.getName()));
	}
	
	/*
	 * Retorna todas as ordens de serviço 
	 */
	public List<OS> findAll(){
		return repository.findAll();
	}

	public OS create(@Valid OSDTO obj) {
		return fromDTO(obj);
	}
	
	public OS update(@Valid OSDTO obj) {
		findById(obj.getId());
		return fromDTO(obj);
	}
	
	private OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		newObj.setStatus(Status.toEnum(obj.getStatus()));
	
		Tecnico tec = tecnicoService.findById(obj.getTecnico());
		Cliente cli = clienteService.findById(obj.getCliente());
		
		newObj.setTecnico(tec);
		newObj.setCliente(cli);
		
		/*Validação para que quando se encerre a ordem de serviço pegue a hora e data atual*/
		if(newObj.getStatus().getCod().equals(2)){
			newObj.setDataFechamento(LocalDateTime.now());
		}
		
		return repository.save(newObj);
	}
}
