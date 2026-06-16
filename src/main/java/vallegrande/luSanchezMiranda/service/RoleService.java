package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.Role;
import java.util.List;

public interface RoleService {
    List<Role> listar();
    List<Role> listarPorEstado(String status);
    Role listarPorId(Integer id);
    Role guardar(Role role);
    Role actualizar(Integer id, Role role);
    Role eliminarLogico(Integer id);
    Role restaurar(Integer id);
}
