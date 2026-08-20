package sistemaventas;

/**
 * Clase asociativa que resuelve la relacion muchos a muchos entre
 * Producto y Factura.
 */
public class DetalleFactura {
    private final Producto producto;
    private final Factura factura;
    private final int cantidad;

    public DetalleFactura(Producto producto, Factura factura, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (factura == null) {
            throw new IllegalArgumentException("La factura no puede ser nula.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (!producto.tieneEspacioParaDetalle()) {
            throw new IllegalStateException("El producto ya no admite mas detalles.");
        }
        if (!factura.tieneEspacioParaDetalle()) {
            throw new IllegalStateException("La factura ya no admite mas detalles.");
        }

        this.producto = producto;
        this.factura = factura;
        this.cantidad = cantidad;

        // Asociacion bidireccional fuerte: el mismo detalle queda registrado
        // tanto en el producto como en la factura.
        producto.addDetalle(this);
        factura.addDetalle(this);
    }

    public Producto getProducto() {
        return producto;
    }

    public Factura getFactura() {
        return factura;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {
        return cantidad * producto.getPrecioBase();
    }
}