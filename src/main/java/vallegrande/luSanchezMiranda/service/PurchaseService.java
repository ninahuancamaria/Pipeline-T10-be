package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.Purchase;

import java.util.List;

public interface PurchaseService {

    List<Purchase> listar();
    Purchase listarPorId(Integer id);
    List<Purchase> listarPorEstado(String status);

    Purchase guardar(Purchase purchase);
    Purchase actualizar(Integer id, Purchase purchase);

    Purchase eliminarLogico(Integer id); // Cambiar estado a CANCELADO o similar
}
