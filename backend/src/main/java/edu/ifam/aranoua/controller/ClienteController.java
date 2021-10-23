package edu.ifam.aranoua.controller;

import edu.ifam.aranoua.domain.Cliente;
import edu.ifam.aranoua.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> listar(@PathVariable Integer id){
        Cliente cliente = clienteService.listar(id);

        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> listarTodos(){
        List<Cliente> clientes = clienteService.listarTodos();

        return ResponseEntity.ok().body(clientes);
    }

    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public ResponseEntity<Page<Cliente>> listarPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "3") Integer size,
            @RequestParam(value = "ord", defaultValue = "nome") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir){
        Page<Cliente> clientes = clienteService.listarPagina(page, size, ord, dir);

        return ResponseEntity.ok().body(clientes);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente){
        cliente = clienteService.inserir(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Cliente> atualizar(@PathVariable Integer id, @RequestBody Cliente cliente){
        cliente = clienteService.atualizar(id, cliente);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Cliente> excluir(@PathVariable Integer id){
        clienteService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
