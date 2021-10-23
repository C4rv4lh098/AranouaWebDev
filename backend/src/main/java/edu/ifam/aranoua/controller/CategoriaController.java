package edu.ifam.aranoua.controller;

import edu.ifam.aranoua.domain.Categoria;
import edu.ifam.aranoua.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> listar(@PathVariable Integer id){
        Categoria categoria = categoriaService.listar(id);

        return ResponseEntity.ok().body(categoria);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Categoria>> listarTodos(){
        List<Categoria> categorias = categoriaService.listarTodos();

        return ResponseEntity.ok().body(categorias);
    }

    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public ResponseEntity<Page<Categoria>> listarPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "3") Integer size,
            @RequestParam(value = "ord", defaultValue = "descricao") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir) {
        Page<Categoria> categorias = categoriaService.listarPagina(page, size, ord, dir);

        return ResponseEntity.ok().body(categorias);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Categoria> inserir(@RequestBody Categoria categoria){
     categoria = categoriaService.inserir(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Categoria> atualizar(@PathVariable Integer id, @RequestBody Categoria categoria){
        categoria = categoriaService.atualizar(id, categoria);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Categoria> excluir(@PathVariable Integer id){
        categoriaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
