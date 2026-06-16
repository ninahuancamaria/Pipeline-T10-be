package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.luSanchezMiranda.model.Role;
import vallegrande.luSanchezMiranda.repository.RoleRepository;
import vallegrande.luSanchezMiranda.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Role> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> listarPorEstado(String status) {
        return repository.findByStatusIgnoreCase(status);
    }

    @Transactional(readOnly = true)
    @Override
    public Role listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Role guardar(Role role) {
        role.setRoleId(null); // Asegurar creación
        if (role.getStatus() == null) role.setStatus("ACTIVO");
        return repository.save(role);
    }

    @Transactional
    @Override
    public Role actualizar(Integer id, Role role) {
        Role existing = repository.findById(id).orElse(null);
        if (existing != null) {
            if (role.getRoleName() != null) existing.setRoleName(role.getRoleName());
            if (role.getDescription() != null) existing.setDescription(role.getDescription());
            if (role.getStatus() != null) existing.setStatus(role.getStatus());
            return repository.save(existing);
        }
        return null;
    }

    @Transactional
    @Override
    public Role eliminarLogico(Integer id) {
        Role existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setStatus("INACTIVO");
            return repository.save(existing);
        }
        return null;
    }

    @Transactional
    @Override
    public Role restaurar(Integer id) {
        Role existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setStatus("ACTIVO");
            return repository.save(existing);
        }
        return null;
    }
}
