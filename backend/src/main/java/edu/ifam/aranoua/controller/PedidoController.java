package edu.ifam.aranoua.controller;

import edu.ifam.aranoua.domain.Pedido;
import edu.ifam.aranoua.service.PedidoSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "pedidos")
public class PedidoController {

    @Autowired
    private PedidoSevice pedidoSevice;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> listar(@PathVariable Integer id){
        Pedido pedido = pedidoSevice.listar(id);

        return ResponseEntity.ok().body(pedido);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listarTodos(){
        List<Pedido> pedidos = pedidoSevice.listarTodos();

        return ResponseEntity.ok().body(pedidos);
    }

    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public ResponseEntity<?> listarPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "3") Integer size,
            @RequestParam(value = "ord", defaultValue = "data") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir){
        Page<Pedido> pedidos = pedidoSevice.listarPagina(page, size, ord, dir);

        return ResponseEntity.ok().body(pedidos);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> inserir(@RequestBody Pedido pedido){
        pedido = pedidoSevice.inserir(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Pedido pedido){
        pedido = pedidoSevice.atualizar(id,pedido);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> excluir(@PathVariable Integer id){
        pedidoSevice.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
