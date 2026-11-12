package co.edu.uniquindio.model;

public class Postre extends Producto {

    private int pesoGramos;
    private double temperaturaAlmacenamiento;
    private boolean refrigeracion;

    public Postre(String codigo, String nombre, String descripcion,
                  double precioBase, int tiempoPreparacion,
                  int pesoGramos, double temperaturaAlmacenamiento,
                  boolean refrigeracion) {

        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);

        this.pesoGramos = pesoGramos;
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
        this.refrigeracion = refrigeracion;
    }

    @Override
    public double calcularPrecioVenta() {

        double precio = getPrecioBase() * 1.15;

        return precio;
    }

    @Override
    public boolean verificarCalidad() {

        boolean calidadAprobada = true;

        if (pesoGramos < 80 || pesoGramos > 500) {
            calidadAprobada = false;
        }

        if (refrigeracion) {

            if (temperaturaAlmacenamiento < 0 ||
                    temperaturaAlmacenamiento > 6) {

                calidadAprobada = false;
            }

        } else {

            if (temperaturaAlmacenamiento < 15 ||
                    temperaturaAlmacenamiento > 30) {

                calidadAprobada = false;
            }
        }

        return calidadAprobada;
    }

    @Override
    public CategoriaProducto getCategoria() {

        CategoriaProducto categoria = CategoriaProducto.POSTRE;

        return categoria;
    }

    @Override
    public String consultarInformacion() {

        String informacion = super.consultarInformacion();

        informacion += "Peso: " + pesoGramos + " gramos\n";
        informacion += "Temperatura de almacenamiento: "
                + temperaturaAlmacenamiento + " °C\n";

        if (refrigeracion) {
            informacion += "Requiere refrigeración: Sí\n";
        } else {
            informacion += "Requiere refrigeración: No\n";
        }

        informacion += "Precio de venta: $" + calcularPrecioVenta() + "\n";

        return informacion;
    }

    public int getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(int pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public double getTemperaturaAlmacenamiento() {
        return temperaturaAlmacenamiento;
    }

    public void setTemperaturaAlmacenamiento(double temperaturaAlmacenamiento) {
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
    }

    public boolean isRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(boolean refrigeracion) {
        this.refrigeracion = refrigeracion;
    }
}