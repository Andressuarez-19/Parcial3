package co.edu.uniquindio.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String documento;
    private String nombreCompleto;
    private String telefono;
    private String correoElectronico;
    private String direccionResidencia;

    private List<Pedido> listaPedidos;

    public Cliente(String documento, String nombreCompleto,
                   String telefono, String correoElectronico,
                   String direccionResidencia) {

        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.direccionResidencia = direccionResidencia;

        this.listaPedidos = new ArrayList<>();
    }

    public boolean agregarPedido(Pedido pedido) {

        boolean agregado = false;

        if (pedido != null) {

            boolean existe = false;

            for (Pedido pedidoActual : listaPedidos) {

                if (pedidoActual == pedido) {
                    existe = true;
                }
            }

            if (!existe) {
                listaPedidos.add(pedido);
                agregado = true;
            }
        }

        return agregado;
    }
    public boolean eliminarPedido(Pedido pedido) {
        boolean eliminado = false;

        if (pedido != null) {
            eliminado = listaPedidos.remove(pedido);
        }

        return eliminado;
    }

    public List<Pedido> consultarHistorialPedidos() {

        List<Pedido> historial = new ArrayList<>(listaPedidos);

        return historial;
    }

    public int calcularCantidadPedidos() {

        int cantidadPedidos = listaPedidos.size();

        return cantidadPedidos;
    }

    public boolean tienePedidos() {

        boolean tienePedidos = !listaPedidos.isEmpty();

        return tienePedidos;
    }

    public String consultarInformacion() {

        String informacion = "INFORMACIÓN DEL CLIENTE"
                + "\nDocumento: " + documento
                + "\nNombre completo: " + nombreCompleto
                + "\nTeléfono: " + telefono
                + "\nCorreo electrónico: " + correoElectronico
                + "\nDirección de residencia: " + direccionResidencia
                + "\nCantidad de pedidos: " + calcularCantidadPedidos();

        return informacion;
    }

    public String consultarInformacionCompleta() {

        String informacion = consultarInformacion();

        informacion = informacion + "\n\nHISTORIAL DE PEDIDOS";

        if (listaPedidos.isEmpty()) {

            informacion = informacion
                    + "\nEl cliente todavía no tiene pedidos.";

        } else {

            for (Pedido pedido : listaPedidos) {

                informacion = informacion + "\n" + pedido.toString();
            }
        }

        return informacion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombre() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    @Override
    public String toString() {

        String informacion = consultarInformacion();

        return informacion;
    }
}