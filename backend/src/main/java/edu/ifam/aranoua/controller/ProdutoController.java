package edu.ifam.aranoua.controller;

import edu.ifam.aranoua.domain.Produto;
import edu.ifam.aranoua.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> listar(@PathVariable Integer id){
        Produto produto = produtoService.listar(id);

        return ResponseEntity.ok().body(produto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listarTodos(){
        List<Produto> produtos = produtoService.listarTodos();

        return ResponseEntity.ok().body(produtos);
    }

    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public ResponseEntity<?> listarPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "3") Integer size,
            @RequestParam(value = "ord", defaultValue = "descricao") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir){
        Page<Produto> produtos = produtoService.listarPagina(page, size, ord, dir);

        return ResponseEntity.ok().body(produtos);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> inserir(@RequestBody Produto produto){
        produto = produtoService.inserir(produto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produto.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Produto produto){
        produto = produtoService.atualizar(id, produto);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public  ResponseEntity<?> excluir(@PathVariable Integer id){
        produtoService.excluir(id);

        return ResponseEntity.noContent().build();
    }















}
