package edu.ifam.aranoua.service;

import edu.ifam.aranoua.domain.Categoria;
import edu.ifam.aranoua.repository.CategoriaRepository;
import edu.ifam.aranoua.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria listar(Integer id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        return categoria.orElseThrow(() -> new ObjectNotFoundException
                ("Categoria não encontrada! ID: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public List<Categoria> listarTodos(){
        return categoriaRepository.findAll();
    }

    public Page<Categoria> listarPagina(Integer page, Integer size, String ord, String dir){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(dir), ord);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria inserir(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Integer id, Categoria categoria){
        categoria.setId(id);
        return categoriaRepository.save(categoria);
    }

    public void excluir(Integer id){
        listar(id);
        categoriaRepository.deleteById(id);
    }
}
