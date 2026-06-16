package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.SaleDetail;
import java.util.List;

/**
 * Interfaz que define las operaciones de negocio para la gestión individual de Detalles de Venta.
 */
public interface SaleDetailService {

    /**
     * Obtiene el listado completo de todos los detalles de venta registrados.
     * @return Lista de entidades SaleDetail.
     */
    List<SaleDetail> listar();

    /**
     * Busca el detalle de una venta por su ID único.
     * @param id Identificador del detalle de venta.
     * @return El objeto SaleDetail, o null si no se encuentra.
     */
    SaleDetail listarPorId(Integer id);

    /**
     * Guarda individualmente un detalle de venta.
     * @param saleDetail Entidad SaleDetail a guardar.
     * @return El detalle guardado.
     */
    SaleDetail guardar(SaleDetail saleDetail);

    /**
     * Actualiza los datos de un detalle de venta específico.
     * @param id Identificador del detalle a actualizar.
     * @param saleDetail Datos nuevos del detalle.
     * @return El detalle actualizado.
     */
    SaleDetail actualizar(Integer id, SaleDetail saleDetail);

    /**
     * Elimina físicamente un detalle de venta de la base de datos por su ID.
     * @param id Identificador del detalle a eliminar.
     */
    void eliminar(Integer id);
}
