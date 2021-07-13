package com.caio.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.caio.os.domain.Cliente;
import com.caio.os.domain.OS;
import com.caio.os.domain.Tecnico;
import com.caio.os.domain.enums.Prioridade;
import com.caio.os.domain.enums.Status;
import com.caio.os.repositories.ClienteRepository;
import com.caio.os.repositories.OSRepository;
import com.caio.os.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;
	
	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "Caio Vinicius", "085.638.910-22", "(88) 98888-8888");
		Tecnico t2 = new Tecnico(null, "Linus Torvalds", "665.910.830-06", "(11) 95689-8441");
		Cliente c1 = new Cliente(null, "Bia Lopes", "338.210.760-03", "(88) 98888-2222");
		Cliente c2 = new Cliente(null, "Betina Campos", "994.881.370-70", "(88) 98888-1111");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OS", Status.ANDAMENTO, t1, c1);
		
		t1.getList().add(os1);
		c1.getList().add(os1);
		
		tecnicoRepository.saveAll(Arrays.asList(t1, t2));
		clienteRepository.saveAll(Arrays.asList(c1, c2));
		osRepository.saveAll(Arrays.asList(os1));
	}
}
