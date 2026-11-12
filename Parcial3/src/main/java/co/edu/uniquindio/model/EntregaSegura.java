package co.edu.uniquindio.model;

public class EntregaSegura implements EmpresaDomicilio {

    private final String nombreEmpresa;
    private final double tarifaBase;
    private final double valorKilometro;
    private final double recargoDistancia;
    private final double distanciaMaxima;

    public EntregaSegura() {

        this.nombreEmpresa = "Entrega Segura";
        this.tarifaBase = 7000;
        this.valorKilometro = 1000;
        this.recargoDistancia = 3000;
        this.distanciaMaxima = 20;
    }

    @Override
    public String getNombreEmpresa() {

        return nombreEmpresa;
    }

    @Override
    public boolean aceptarServicio(double distancia) {

        boolean servicioAceptado = distancia >= 0
                && distancia <= distanciaMaxima;

        return servicioAceptado;
    }

    @Override
    public double calcularTarifa(double distancia) {

        double tarifa = 0;

        if (aceptarServicio(distancia)) {

            tarifa = tarifaBase + (distancia * valorKilometro);

            if (distancia > 5) {
                tarifa = tarifa + recargoDistancia;
            }
        }

        return tarifa;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public double getValorKilometro() {
        return valorKilometro;
    }

    public double getRecargoDistancia() {
        return recargoDistancia;
    }

    public double getDistanciaMaxima() {
        return distanciaMaxima;
    }
}