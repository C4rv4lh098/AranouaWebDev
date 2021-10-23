package edu.ifam.aranoua.service;

import edu.ifam.aranoua.domain.Pedido;
import edu.ifam.aranoua.repository.ItemPedidoRepository;
import edu.ifam.aranoua.repository.PedidoRepository;
import edu.ifam.aranoua.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoSevice {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido listar(Integer id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        return pedido.orElseThrow(() -> new ObjectNotFoundException
                ("Pedido não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public List<Pedido> listarTodos(){
        return pedidoRepository.findAll();
    }

    public Page<Pedido> listarPagina(Integer page, Integer size, String ord, String dir){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(dir), ord);
        return pedidoRepository.findAll(pageRequest);
    }

    public Pedido inserir(Pedido pedido){
         Pedido pedidoSaved = pedidoRepository.save(pedido);

         pedido.getItensPedido().forEach(itemPedido -> {
             itemPedido.setPedido(pedidoSaved);
         });

        itemPedidoRepository.saveAll(pedido.getItensPedido());

        return pedidoRepository.save(pedido);
    }

    public Pedido atualizar(Integer id, Pedido pedido){
        pedido.setId(id);
        return pedidoRepository.save(pedido);
    }

    public void excluir(Integer id){
        listar(id);
        pedidoRepository.deleteById(id);
    }
}
