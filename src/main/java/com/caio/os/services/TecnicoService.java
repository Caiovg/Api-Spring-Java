package com.caio.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caio.os.domain.Pessoa;
import com.caio.os.domain.Tecnico;
import com.caio.os.dtos.TecnicoDTO;
import com.caio.os.repositories.PessoaRepository;
import com.caio.os.repositories.TecnicoRepository;
import com.caio.os.services.exception.DataIntegratyViolationException;
import com.caio.os.services.exception.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	/*
	 * Busca pelo ID
	 */
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	/*
	 * Busca de todos os tecnicos
	 */
	public List<Tecnico> findAll() {
		return repository.findAll();
	}
	
	/*
	 * Cria um novo Tecnico
	 */
	public Tecnico create(TecnicoDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return repository.save(newObj);
	}

	/*
	 * Update de Tecnico */
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}

	/*
	 * Deleta um Tecnico
	 */
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui Ordens de Serviço, não pode ser deletado!");
		}
		repository.deleteById(id);
	}
	
	/*
	 * Exceção para cpf
	 */
	public Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
	}
}
