package co.edu.uniquindio.model;

public abstract class Producto {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int tiempoPreparacion;

    public Producto(String codigo, String nombre, String descripcion,
                    double precioBase, int tiempoPreparacion) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public abstract double calcularPrecioVenta();

    public abstract boolean verificarCalidad();

    public abstract CategoriaProducto getCategoria();

    public String consultarInformacion() {

        String informacion = "";

        informacion += "Código: " + codigo + "\n";
        informacion += "Nombre: " + nombre + "\n";
        informacion += "Descripción: " + descripcion + "\n";
        informacion += "Precio base: $" + precioBase + "\n";
        informacion += "Tiempo de preparación: " + tiempoPreparacion + " minutos\n";
        informacion += "Categoría: " + getCategoria() + "\n";

        if (verificarCalidad()) {
            informacion += "Calidad: Aprobada\n";
        } else {
            informacion += "Calidad: No aprobada\n";
        }

        return informacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }
}