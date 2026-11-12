package co.edu.uniquindio.model;

public class RapidoExpress implements EmpresaDomicilio {

    private final String nombreEmpresa;
    private final double tarifaBase;
    private final double valorKilometro;
    private final double distanciaMaxima;

    public RapidoExpress() {

        this.nombreEmpresa = "Rápido Express";
        this.tarifaBase = 5000;
        this.valorKilometro = 1500;
        this.distanciaMaxima = 12;
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
        }

        return tarifa;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public double getValorKilometro() {
        return valorKilometro;
    }

    public double getDistanciaMaxima() {
        return distanciaMaxima;
    }
}