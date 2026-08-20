package sistemaventas;

import java.time.LocalDate;

/**
 * Representa una compra realizada por un cliente.
 */
public class Factura {
    private static final int MAX_DETALLES = 50;

    private final int numero;
    private final LocalDate fecha;
    private final String nombreCliente;
    private final DetalleFactura[] detalles;
    private int cantidadDetalles;

    public Factura(int numero, LocalDate fecha, String nombreCliente) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El numero de factura debe ser positivo.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
        if (nombreCliente == null || nombreCliente.isBlank()) {
            throw new IllegalArgumentException("El cliente no puede estar vacio.");
        }

        this.numero = numero;
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.detalles = new DetalleFactura[MAX_DETALLES];
        this.cantidadDetalles = 0;
    }

    public int getNumero() {
        return numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public boolean tieneEspacioParaDetalle() {
        return cantidadDetalles < detalles.length;
    }

    /**
     * Registra el detalle en la factura para conservar la relacion
     * bidireccional Producto <-> DetalleFactura <-> Factura.
     */
    public void addDetalle(DetalleFactura detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("El detalle no puede ser nulo.");
        }
        if (detalle.getFactura() != this) {
            throw new IllegalArgumentException("El detalle pertenece a otra factura.");
        }

        // Evita guardar dos veces el mismo objeto DetalleFactura.
        for (int i = 0; i < cantidadDetalles; i++) {
            if (detalles[i] == detalle) {
                return;
            }
        }

        if (!tieneEspacioParaDetalle()) {
            throw new IllegalStateException("La factura ya no admite mas detalles.");
        }

        detalles[cantidadDetalles] = detalle;
        cantidadDetalles++;
    }

    /**
     * Suma los subtotales de todos los productos incluidos en la factura.
     */
    public double calcularTotal() {
        double total = 0;

        for (int i = 0; i < cantidadDetalles; i++) {
            if (detalles[i] != null) {
                total += detalles[i].calcularSubtotal();
            }
        }

        return total;
    }

    @Override
    public String toString() {
        return "Factura No. " + numero + " - " + nombreCliente;
    }
}