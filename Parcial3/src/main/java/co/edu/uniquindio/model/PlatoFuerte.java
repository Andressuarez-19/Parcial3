package co.edu.uniquindio.model;

public class PlatoFuerte extends Producto {

    private int pesoGramos;
    private TipoPreparacion tipoPreparacion;
    private boolean coccionAprobada;

    public PlatoFuerte(String codigo, String nombre, String descripcion,
                       double precioBase, int tiempoPreparacion,
                       int pesoGramos, TipoPreparacion tipoPreparacion,
                       boolean coccionAprobada) {

        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);

        this.pesoGramos = pesoGramos;
        this.tipoPreparacion = tipoPreparacion;
        this.coccionAprobada = coccionAprobada;
    }

    @Override
    public double calcularPrecioVenta() {

        double precio = getPrecioBase() * 1.20;

        return precio;
    }

    @Override
    public boolean verificarCalidad() {

        boolean calidadAprobada = true;

        if (pesoGramos < 200 || pesoGramos > 900) {
            calidadAprobada = false;
        }

        if (getTiempoPreparacion() < 10 || getTiempoPreparacion() > 60) {
            calidadAprobada = false;
        }

        if (tipoPreparacion == null) {
            calidadAprobada = false;
        }

        if (coccionAprobada == false) {
            calidadAprobada = false;
        }

        return calidadAprobada;
    }

    @Override
    public CategoriaProducto getCategoria() {

        CategoriaProducto categoria = CategoriaProducto.PLATO_FUERTE;

        return categoria;
    }

    @Override
    public String consultarInformacion() {

        String informacion = super.consultarInformacion();

        informacion += "Peso: " + pesoGramos + " gramos\n";
        informacion += "Tipo de preparación: " + tipoPreparacion + "\n";
        informacion += "Cocción aprobada: " + (coccionAprobada ? "Sí" : "No") + "\n";
        informacion += "Precio de venta: $" + calcularPrecioVenta() + "\n";

        return informacion;
    }

    public int getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(int pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public TipoPreparacion getTipoPreparacion() {
        return tipoPreparacion;
    }

    public void setTipoPreparacion(TipoPreparacion tipoPreparacion) {
        this.tipoPreparacion = tipoPreparacion;
    }

    public boolean isCoccionAprobada() {
        return coccionAprobada;
    }

    public void setCoccionAprobada(boolean coccionAprobada) {
        this.coccionAprobada = coccionAprobada;
    }
}