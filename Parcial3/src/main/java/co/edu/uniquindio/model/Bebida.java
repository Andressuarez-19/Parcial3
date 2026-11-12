package co.edu.uniquindio.model;

public class Bebida extends Producto {

    private int volumenMililitros;
    private double temperaturaServicio;
    private TipoTemperatura tipoTemperatura;
    private boolean preparacionEspecial;

    public Bebida(String codigo, String nombre, String descripcion,
                  double precioBase, int tiempoPreparacion,
                  int volumenMililitros, double temperaturaServicio,
                  TipoTemperatura tipoTemperatura, boolean preparacionEspecial) {

        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);

        this.volumenMililitros = volumenMililitros;
        this.temperaturaServicio = temperaturaServicio;
        this.tipoTemperatura = tipoTemperatura;
        this.preparacionEspecial = preparacionEspecial;
    }

    @Override
    public double calcularPrecioVenta() {

        double precio = getPrecioBase() * 1.10;

        if (preparacionEspecial) {
            precio = precio + 3000;
        }

        return precio;
    }

    @Override
    public boolean verificarCalidad() {

        boolean calidadAprobada = true;

        if (volumenMililitros < 150 || volumenMililitros > 1500) {
            calidadAprobada = false;
        }

        if (tipoTemperatura == null) {
            calidadAprobada = false;
        } else {

            if (tipoTemperatura == TipoTemperatura.FRIA) {

                if (temperaturaServicio < 0 || temperaturaServicio > 10) {
                    calidadAprobada = false;
                }

            } else if (tipoTemperatura == TipoTemperatura.CALIENTE) {

                if (temperaturaServicio < 55 || temperaturaServicio > 85) {
                    calidadAprobada = false;
                }
            }
        }

        return calidadAprobada;
    }

    @Override
    public CategoriaProducto getCategoria() {

        CategoriaProducto categoria = CategoriaProducto.BEBIDA;

        return categoria;
    }

    @Override
    public String consultarInformacion() {

        String informacion = super.consultarInformacion();

        informacion += "Volumen: " + volumenMililitros + " mililitros\n";
        informacion += "Temperatura de servicio: " + temperaturaServicio + " °C\n";
        informacion += "Tipo de temperatura: " + tipoTemperatura + "\n";

        if (preparacionEspecial) {
            informacion += "Preparación especial: Sí\n";
        } else {
            informacion += "Preparación especial: No\n";
        }

        informacion += "Precio de venta: $" + calcularPrecioVenta() + "\n";

        return informacion;
    }

    public int getVolumenMililitros() {
        return volumenMililitros;
    }

    public void setVolumenMililitros(int volumenMililitros) {
        this.volumenMililitros = volumenMililitros;
    }

    public double getTemperaturaServicio() {
        return temperaturaServicio;
    }

    public void setTemperaturaServicio(double temperaturaServicio) {
        this.temperaturaServicio = temperaturaServicio;
    }

    public TipoTemperatura getTipoTemperatura() {
        return tipoTemperatura;
    }

    public void setTipoTemperatura(TipoTemperatura tipoTemperatura) {
        this.tipoTemperatura = tipoTemperatura;
    }

    public boolean isPreparacionEspecial() {
        return preparacionEspecial;
    }

    public void setPreparacionEspecial(boolean preparacionEspecial) {
        this.preparacionEspecial = preparacionEspecial;
    }
}