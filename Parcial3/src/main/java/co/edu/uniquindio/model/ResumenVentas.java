package co.edu.uniquindio.model;

public record ResumenVentas(
        double ingresosPlatosFuertes,
        double ingresosBebidas,
        double ingresosPostres,
        double ingresosDomicilios,
        String productoMasVendido,
        String categoriaMayorIngreso
) {

    public double calcularIngresosProductos() {

        double ingresosProductos = ingresosPlatosFuertes
                + ingresosBebidas
                + ingresosPostres;

        return ingresosProductos;
    }

    public double calcularIngresosTotales() {

        double ingresosTotales = ingresosPlatosFuertes
                + ingresosBebidas
                + ingresosPostres
                + ingresosDomicilios;

        return ingresosTotales;
    }

}