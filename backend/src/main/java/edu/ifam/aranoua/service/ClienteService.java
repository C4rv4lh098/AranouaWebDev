package edu.ifam.aranoua.service;

import edu.ifam.aranoua.domain.Cliente;
import edu.ifam.aranoua.repository.ClienteRepository;
import edu.ifam.aranoua.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente listar(Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);

        return cliente.orElseThrow(() -> new ObjectNotFoundException
                ("Cliente não cadastrado! ID " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public List<Cliente> listarTodos(){
       return clienteRepository.findAll();
    }

    public Page<Cliente> listarPagina(Integer page, Integer size, String ord, String dir){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(dir), ord);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente inserir(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Integer id, Cliente cliente){
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    public void excluir(Integer id){
        listar(id);
        clienteRepository.deleteById(id);
    }
}
