package modelo;

/**
 * Entidad METODO_PAGO (HU-07). El patrón Strategy real vive en la capa de
 * servicio (ver backend/README): esta clase modela únicamente el registro
 * persistente del método elegido para un pedido.
 */
public class MetodoPago {
    private int idMetodoPago;
    private int idPedido;
    private String tipoTarjeta; // "PagoEfectivo", "PagoTarjeta", "PagoNequi", "PagoDaviplata"

    public MetodoPago() {}

    public MetodoPago(int idMetodoPago, int idPedido, String tipoTarjeta) {
        this.idMetodoPago = idMetodoPago;
        this.idPedido = idPedido;
        this.tipoTarjeta = tipoTarjeta;
    }

    public int getIdMetodoPago() { return idMetodoPago; }
    public void setIdMetodoPago(int idMetodoPago) { this.idMetodoPago = idMetodoPago; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public String getTipoTarjeta() { return tipoTarjeta; }
    public void setTipoTarjeta(String tipoTarjeta) { this.tipoTarjeta = tipoTarjeta; }

    /** Simula el procesamiento del pago (aquí se integraría la pasarela real). */
    public boolean procesarPago() {
        return tipoTarjeta != null && !tipoTarjeta.isEmpty();
    }

    public String generarRecibo() {
        return "Recibo del pedido #" + idPedido + " pagado con " + tipoTarjeta;
    }
}
