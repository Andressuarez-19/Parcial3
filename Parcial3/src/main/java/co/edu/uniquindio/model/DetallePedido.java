package co.edu.uniquindio.model;

public class DetallePedido {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public DetallePedido(Producto producto, int cantidad) {

        this.producto = producto;
        this.cantidad = cantidad;

        if (producto != null) {
            this.precioUnitario = producto.calcularPrecioVenta();
        } else {
            this.precioUnitario = 0;
        }
    }

    public double calcularSubtotal() {

        double subtotal = 0;

        if (producto != null && cantidad > 0) {
            subtotal = precioUnitario * cantidad;
        }

        return subtotal;
    }

    public boolean verificarDisponibilidad() {

        boolean disponible = false;

        if (producto != null && cantidad > 0) {
            disponible = producto.verificarCalidad();
        }

        return disponible;
    }

    public boolean actualizarCantidad(int nuevaCantidad) {

        boolean actualizacionCorrecta = false;

        if (nuevaCantidad > 0 && producto != null) {

            cantidad = nuevaCantidad;

            actualizacionCorrecta = true;
        }

        return actualizacionCorrecta;
    }

    public String consultarInformacion() {

        String informacion = "DETALLE DEL PEDIDO";

        if (producto != null) {

            informacion = informacion
                    + "\nProducto: " + producto.getNombre()
                    + "\nCódigo: " + producto.getCodigo()
                    + "\nCantidad: " + cantidad
                    + "\nPrecio unitario: $" + precioUnitario
                    + "\nSubtotal: $" + calcularSubtotal()
                    + "\nCalidad del producto: ";

            if (verificarDisponibilidad()) {
                informacion = informacion + "Aprobada";
            } else {
                informacion = informacion + "No aprobada";
            }

        } else {

            informacion = informacion + "\nNo hay un producto asociado.";
        }

        return informacion;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {

        if (producto != null) {

            this.producto = producto;
            this.precioUnitario = producto.calcularPrecioVenta();
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {

        if (cantidad > 0) {
            this.cantidad = cantidad;
        }
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    @Override
    public String toString() {

        String informacion = consultarInformacion();

        return informacion;
    }
}
