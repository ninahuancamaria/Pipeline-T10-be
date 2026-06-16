package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.dto.StockMovementRequest;
import vallegrande.luSanchezMiranda.dto.StockMovementResponse;

import java.util.List;

public interface StockMovementService {
    List<StockMovementResponse> listar(Integer productsSaleId, String startDate, String endDate, String status);
    StockMovementResponse listarPorId(Integer id);
    StockMovementResponse guardar(StockMovementRequest request);
    StockMovementResponse actualizar(Integer id, StockMovementRequest request);
    StockMovementResponse eliminarLogico(Integer id);
    StockMovementResponse restaurar(Integer id);
}
