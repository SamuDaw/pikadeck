package com.savants.Pokemon.Services;

import com.savants.Pokemon.Models.Pedido;
import com.savants.Pokemon.Models.Sobre;
import com.savants.Pokemon.Models.User;
import com.savants.Pokemon.Repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
    public List<Pedido> obtenerPedidos(){
        return pedidoRepository.findAll();
    }
    public Pedido obtenerPedidoPorId(Long id){
        return pedidoRepository.findById(id).orElse(null);
    }
    public void guardarPedido(Pedido pedido){pedidoRepository.save(pedido);}
    public void  eliminarPedido(Pedido pedido) { pedidoRepository.delete(pedido); }
    public Pedido actualizarPedido(Pedido pedido, Long id)
    {
        Optional<Pedido> existingPedidoOptional = pedidoRepository.findById(id);
        if (existingPedidoOptional.isPresent()) {
            Pedido existingPedido = existingPedidoOptional.get();
            existingPedido.setSobres(pedido.getSobres());
            existingPedido.setFecha(pedido.getFecha());
            //Logica para sumar el precio de todos los sobres.
            List<Sobre> sobres = existingPedido.getSobres();
            existingPedido.setPrecio(0.0);
            for (Sobre sobre : sobres)
            {
                existingPedido.setPrecio(existingPedido.getPrecio() + sobre.getPrecio());
            }
            return pedidoRepository.save(existingPedido);
        } else {
            return null;
        }
    }

    public List<Pedido> obtenerPedidosPorUsuario(String nombreUsuario) {
        return pedidoRepository.findByUserUsername(nombreUsuario);
    }


}
