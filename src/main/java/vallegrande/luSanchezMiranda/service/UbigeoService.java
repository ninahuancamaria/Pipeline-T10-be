package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.Ubigeo;
import java.util.List;

public interface UbigeoService {
    List<Ubigeo> listar();
    Ubigeo listarPorId(Integer id);
    Ubigeo guardar(Ubigeo ubigeo);
    Ubigeo actualizar(Integer id, Ubigeo ubigeo);
    void eliminar(Integer id);
}