package sistemaventas;

import java.time.LocalDate;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        // 1. Catalogo con al menos cuatro productos.
        Producto[] catalogo = {
            new Producto("P-001", "Cereal", 35.50),
            new Producto("P-002", "Leche", 12.75),
            new Producto("P-003", "Pan", 10.00),
            new Producto("P-004", "Cafe", 48.90)
        };

        // 2. Tres facturas para clientes diferentes.
        Factura[] facturas = {
            new Factura(1, LocalDate.of(2026, 8, 20), "Ana Lopez"),
            new Factura(2, LocalDate.of(2026, 8, 20), "Carlos Perez"),
            new Factura(3, LocalDate.of(2026, 8, 20), "Maria Garcia")
        };

        // 3. Compras que cruzan productos y facturas con cantidades distintas.
        new DetalleFactura(catalogo[0], facturas[0], 3); // 3 cereales
        new DetalleFactura(catalogo[1], facturas[0], 2); // 2 leches
        new DetalleFactura(catalogo[2], facturas[0], 1); // 1 pan

        new DetalleFactura(catalogo[3], facturas[1], 2); // 2 cafes
        new DetalleFactura(catalogo[1], facturas[1], 4); // 4 leches
        new DetalleFactura(catalogo[2], facturas[1], 2); // 2 panes

        new DetalleFactura(catalogo[0], facturas[2], 1); // 1 cereal
        new DetalleFactura(catalogo[3], facturas[2], 1); // 1 cafe
        new DetalleFactura(catalogo[2], facturas[2], 3); // 3 panes

        // 4. Mostrar el total de cada factura.
        System.out.println("===== TOTALES DE FACTURAS =====");
        for (Factura factura : facturas) {
            System.out.printf(
                "Factura No. %d | Cliente: %-14s | Fecha: %s | Total: Q%.2f%n",
                factura.getNumero(),
                factura.getNombreCliente(),
                factura.getFecha(),
                factura.calcularTotal()
            );
        }

        // 5. Encontrar el producto que genero menores ingresos.
        Producto productoMenoresIngresos = buscarProductoMenoresIngresos(catalogo);
        System.out.println("\n===== PRODUCTO CON MENORES INGRESOS =====");
        System.out.printf(
            "%s | Ingresos: Q%.2f%n",
            productoMenoresIngresos,
            productoMenoresIngresos.calcularIngresosTotales()
        );

        // 6. Encontrar la factura mas grande segun su total.
        Factura facturaMasGrande = buscarFacturaMasGrande(facturas);
        System.out.println("\n===== FACTURA MAS GRANDE =====");
        System.out.printf(
            "Factura No. %d | Cliente: %s | Total: Q%.2f%n",
            facturaMasGrande.getNumero(),
            facturaMasGrande.getNombreCliente(),
            facturaMasGrande.calcularTotal()
        );
    }

    private static Producto buscarProductoMenoresIngresos(Producto[] catalogo) {
        if (catalogo == null || catalogo.length == 0) {
            throw new IllegalArgumentException("El catalogo no puede estar vacio.");
        }

        Producto productoMenor = null;
        double menorIngreso = Double.POSITIVE_INFINITY;

        for (Producto producto : catalogo) {
            if (producto != null) {
                double ingresoActual = producto.calcularIngresosTotales();
                if (ingresoActual < menorIngreso) {
                    menorIngreso = ingresoActual;
                    productoMenor = producto;
                }
            }
        }

        if (productoMenor == null) {
            throw new IllegalArgumentException("El catalogo no contiene productos validos.");
        }

        return productoMenor;
    }

    private static Factura buscarFacturaMasGrande(Factura[] facturas) {
        if (facturas == null || facturas.length == 0) {
            throw new IllegalArgumentException("El historial no puede estar vacio.");
        }

        Factura facturaMayor = null;
        double mayorTotal = Double.NEGATIVE_INFINITY;

        for (Factura factura : facturas) {
            if (factura != null) {
                double totalActual = factura.calcularTotal();
                if (totalActual > mayorTotal) {
                    mayorTotal = totalActual;
                    facturaMayor = factura;
                }
            }
        }

        if (facturaMayor == null) {
            throw new IllegalArgumentException("El historial no contiene facturas validas.");
        }

        return facturaMayor;
    }
}