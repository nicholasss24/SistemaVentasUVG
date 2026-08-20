package sistemaventas;

/**
 * Representa un producto disponible en el catalogo del supermercado.
 */
public class Producto {
    private static final int MAX_DETALLES = 50;

    private final String codigo;
    private final String nombre;
    private final double precioBase;
    private final DetalleFactura[] detalles;
    private int cantidadDetalles;

    public Producto(String codigo, String nombre, double precioBase) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if (precioBase < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.detalles = new DetalleFactura[MAX_DETALLES];
        this.cantidadDetalles = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public boolean tieneEspacioParaDetalle() {
        return cantidadDetalles < detalles.length;
    }

    /**
     * Registra el detalle en el producto para conservar la relacion
     * bidireccional Producto <-> DetalleFactura <-> Factura.
     */
    public void addDetalle(DetalleFactura detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("El detalle no puede ser nulo.");
        }
        if (detalle.getProducto() != this) {
            throw new IllegalArgumentException("El detalle pertenece a otro producto.");
        }

        // Evita guardar dos veces el mismo objeto DetalleFactura.
        for (int i = 0; i < cantidadDetalles; i++) {
            if (detalles[i] == detalle) {
                return;
            }
        }

        if (!tieneEspacioParaDetalle()) {
            throw new IllegalStateException("El producto ya no admite mas detalles.");
        }

        detalles[cantidadDetalles] = detalle;
        cantidadDetalles++;
    }

    /**
     * Suma los subtotales de este producto en todas las facturas historicas.
     */
    public double calcularIngresosTotales() {
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
        return codigo + " - " + nombre;
    }
}