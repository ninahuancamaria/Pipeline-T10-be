package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.Sale;
import java.util.List;

/**
 * Interfaz que define las operaciones de negocio para la gestión de Ventas.
 */
public interface SaleService {

    /**
     * Obtiene el listado completo de ventas registradas.
     * @return Lista de entidades Sale.
     */
    List<Sale> listar();

    /**
     * Busca una venta específica por su identificador único.
     * @param id Identificador de la venta.
     * @return Entidad Sale encontrada, o null si no existe.
     */
    Sale listarPorId(Integer id);

    /**
     * Registra una nueva venta junto con sus detalles asociados.
     * @param sale Entidad Sale con los detalles cargados.
     * @return La venta registrada con sus IDs generados.
     */
    Sale guardar(Sale sale);

    /**
     * Actualiza la información y/o los detalles de una venta existente.
     * @param id Identificador de la venta a actualizar.
     * @param sale Entidad Sale con los nuevos datos y detalles.
     * @return La venta modificada y guardada.
     */
    Sale actualizar(Integer id, Sale sale);

    /**
     * Realiza una eliminación lógica (anulación) de una venta cambiando su estado a "anulada".
     * @param id Identificador de la venta a anular.
     * @return La venta con el estado actualizado a "anulada".
     */
    Sale eliminar(Integer id);
}
