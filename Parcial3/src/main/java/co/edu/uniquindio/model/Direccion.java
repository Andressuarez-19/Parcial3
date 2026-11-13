package co.edu.uniquindio.model;

public record Direccion(
        String calle,
        String barrio,
        String ciudad,
        String referencia
) {

    public String obtenerDireccionCompleta() {

        String direccionCompleta = calle + ", "
                + barrio + ", "
                + ciudad + ". Referencia: "
                + referencia;

        return direccionCompleta;
    }

}