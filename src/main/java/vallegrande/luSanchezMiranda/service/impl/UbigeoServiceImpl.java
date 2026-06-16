package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vallegrande.luSanchezMiranda.model.Ubigeo;
import vallegrande.luSanchezMiranda.repository.UbigeoRepository;
import vallegrande.luSanchezMiranda.service.UbigeoService;

import java.util.List;
import java.util.Optional;

@Service
public class UbigeoServiceImpl implements UbigeoService {

    @Autowired
    private UbigeoRepository repository;

    @Override
    public List<Ubigeo> listar() {
        return repository.findAll();
    }

    @Override
    public Ubigeo listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Ubigeo guardar(Ubigeo ubigeo) {
        return repository.save(ubigeo);
    }

    @Override
    public Ubigeo actualizar(Integer id, Ubigeo ubigeo) {
        Optional<Ubigeo> existente = repository.findById(id);
        if (existente.isPresent()) {
            Ubigeo u = existente.get();
            u.setDepartment(ubigeo.getDepartment());
            u.setProvince(ubigeo.getProvince());
            u.setDistrict(ubigeo.getDistrict());
            return repository.save(u);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}