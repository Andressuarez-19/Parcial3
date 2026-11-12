package co.edu.uniquindio.model;

public class ServicioDomicilio {

    private EmpresaDomicilio empresaDomicilio;
    private double distancia;
    private String direccionEntrega;
    private double tarifa;
    private boolean servicioActivo;

    public ServicioDomicilio() {
        this.empresaDomicilio = null;
        this.distancia = 0;
        this.direccionEntrega = "";
        this.tarifa = 0;
        this.servicioActivo = false;
    }

    // SELECCIONAR EMPRESA

    public boolean seleccionarEmpresa(EmpresaDomicilio empresaDomicilio) {

        boolean seleccionada = false;

        if (empresaDomicilio != null && !servicioActivo) {
            this.empresaDomicilio = empresaDomicilio;
            seleccionada = true;
        }

        return seleccionada;
    }

    // REGISTRAR DOMICILIO

    public boolean registrarServicio(double distancia, String direccionEntrega) {

        boolean registrado = false;

        if (!servicioActivo
                && empresaDomicilio != null
                && distancia > 0
                && direccionEntrega != null
                && !direccionEntrega.equals("")
                && empresaDomicilio.aceptarServicio(distancia)) {

            this.distancia = distancia;
            this.direccionEntrega = direccionEntrega;
            this.tarifa = empresaDomicilio.calcularTarifa(distancia);
            this.servicioActivo = true;

            registrado = true;
        }

        return registrado;
    }

    // CALCULAR COSTO DEL DOMICILIO

    public double calcularCostoDomicilio() {

        double costo = 0;

        if (empresaDomicilio != null
                && distancia > 0
                && servicioActivo) {

            costo = empresaDomicilio.calcularTarifa(distancia);
        }

        return costo;
    }

    // CANCELAR DOMICILIO

    public boolean cancelarServicio() {

        boolean cancelado = false;

        if (servicioActivo) {

            servicioActivo = false;
            cancelado = true;
        }

        return cancelado;
    }

    // CONSULTAR INFORMACION

    public String consultarInformacion() {

        String informacion = "INFORMACIÓN DEL DOMICILIO";

        informacion = informacion + "\nEmpresa: ";

        if (empresaDomicilio != null) {
            informacion = informacion + empresaDomicilio.getNombreEmpresa();
        } else {
            informacion = informacion + "Sin empresa seleccionada";
        }

        informacion = informacion
                + "\nDirección de entrega: " + direccionEntrega
                + "\nDistancia: " + distancia + " km"
                + "\nTarifa del domicilio: $" + tarifa
                + "\nEstado del servicio: ";

        if (servicioActivo) {
            informacion = informacion + "ACTIVO";
        } else {
            informacion = informacion + "INACTIVO";
        }

        return informacion;
    }

    // GETTERS Y SETTERS

    public EmpresaDomicilio getEmpresaDomicilio() {
        return empresaDomicilio;
    }

    public void setEmpresaDomicilio(EmpresaDomicilio empresaDomicilio) {

        if (!servicioActivo && empresaDomicilio != null) {
            this.empresaDomicilio = empresaDomicilio;
        }
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {

        if (!servicioActivo && distancia > 0) {
            this.distancia = distancia;
        }
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {

        if (!servicioActivo
                && direccionEntrega != null
                && !direccionEntrega.equals("")) {

            this.direccionEntrega = direccionEntrega;
        }
    }

    public double getTarifa() {

        double valor = 0;

        if (servicioActivo) {
            valor = tarifa;
        }

        return valor;
    }

    public void setTarifa(double tarifa) {

        if (!servicioActivo && tarifa >= 0) {
            this.tarifa = tarifa;
        }
    }

    public boolean isServicioActivo() {
        return servicioActivo;
    }

    public void setServicioActivo(boolean servicioActivo) {

        if (servicioActivo
                && empresaDomicilio != null
                && distancia > 0
                && !direccionEntrega.equals("")) {

            this.servicioActivo = true;

        } else if (!servicioActivo) {

            this.servicioActivo = false;
        }
    }

    @Override
    public String toString() {

        String informacion = consultarInformacion();

        return informacion;
    }
}