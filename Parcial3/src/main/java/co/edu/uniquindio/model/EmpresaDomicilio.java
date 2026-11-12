package co.edu.uniquindio.model;

public interface EmpresaDomicilio {

    String getNombreEmpresa();

    boolean aceptarServicio(double distancia);

    double calcularTarifa(double distancia);

}