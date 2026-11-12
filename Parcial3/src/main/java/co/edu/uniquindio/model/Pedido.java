package co.edu.uniquindio.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private String codigo;
    private LocalDate fecha;
    private EstadoPedido estado;
    private Cliente cliente;
    private List<DetallePedido> listaDetalles;
    private ServicioDomicilio servicioDomicilio;
    private ModalidadEntrega modalidadEntrega;
    private double valorTotal;

    public Pedido(String codigo, LocalDate fecha) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.estado = EstadoPedido.PENDIENTE;
        this.listaDetalles = new ArrayList<>();
        this.modalidadEntrega = ModalidadEntrega.CONSUMO_RESTAURANTE;
        this.valorTotal = 0;
    }

    public Pedido(String codigo, LocalDate fecha, Cliente cliente) {
        this(codigo, fecha);
        asociarCliente(cliente);
    }

    public Pedido(String codigo, LocalDate fecha, Cliente cliente,
                  ModalidadEntrega modalidadEntrega) {
        this(codigo, fecha, cliente);
        this.modalidadEntrega = modalidadEntrega;
    }

    // ASOCIAR CLIENTE

    public boolean asociarCliente(Cliente cliente) {

        boolean asociado = false;

        if (cliente != null && this.cliente == null) {
            this.cliente = cliente;
            cliente.agregarPedido(this);
            asociado = true;
        }

        return asociado;
    }

    // ADMINISTRAR PRODUCTOS

    public boolean agregarDetalle(Producto producto, int cantidad) {

        boolean agregado = false;

        if (estado == EstadoPedido.PENDIENTE
                && producto != null
                && cantidad > 0
                && producto.verificarCalidad()) {

            DetallePedido detalleExistente =
                    buscarDetalle(producto.getCodigo());

            if (detalleExistente == null) {

                DetallePedido nuevoDetalle =
                        new DetallePedido(producto, cantidad);

                listaDetalles.add(nuevoDetalle);
                agregado = true;

            } else {

                int nuevaCantidad =
                        detalleExistente.getCantidad() + cantidad;

                detalleExistente.actualizarCantidad(nuevaCantidad);
                agregado = true;
            }

            calcularValorTotal();
        }

        return agregado;
    }

    public boolean eliminarDetalle(String codigoProducto) {

        boolean eliminado = false;

        if (estado == EstadoPedido.PENDIENTE
                && codigoProducto != null) {

            DetallePedido detalle = buscarDetalle(codigoProducto);

            if (detalle != null) {
                listaDetalles.remove(detalle);
                calcularValorTotal();
                eliminado = true;
            }
        }

        return eliminado;
    }

    public boolean modificarCantidad(String codigoProducto,
                                     int nuevaCantidad) {

        boolean modificado = false;

        if (estado == EstadoPedido.PENDIENTE
                && codigoProducto != null
                && nuevaCantidad > 0) {

            DetallePedido detalle = buscarDetalle(codigoProducto);

            if (detalle != null) {
                detalle.actualizarCantidad(nuevaCantidad);
                calcularValorTotal();
                modificado = true;
            }
        }

        return modificado;
    }

    public DetallePedido buscarDetalle(String codigoProducto) {

        DetallePedido encontrado = null;

        if (codigoProducto != null) {

            for (DetallePedido detalle : listaDetalles) {

                if (detalle.getProducto().getCodigo()
                        .equals(codigoProducto)) {

                    encontrado = detalle;
                    break;
                }
            }
        }

        return encontrado;
    }

    // CALCULAR VALOR TOTAL

    public double calcularValorTotal() {

        double total = 0;

        for (DetallePedido detalle : listaDetalles) {
            total = total + detalle.calcularSubtotal();
        }

        if (modalidadEntrega == ModalidadEntrega.DOMICILIO
                && servicioDomicilio != null
                && servicioDomicilio.isServicioActivo()) {

            total = total + servicioDomicilio.getTarifa();
        }

        valorTotal = total;

        return valorTotal;
    }

    // VERIFICAR DISPONIBILIDAD

    public boolean verificarDisponibilidad() {

        boolean disponible = true;

        if (listaDetalles.isEmpty()) {
            disponible = false;
        }

        for (DetallePedido detalle : listaDetalles) {

            if (!detalle.verificarDisponibilidad()) {
                disponible = false;
            }
        }

        return disponible;
    }

    // ASOCIAR DOMICILIO

    public boolean asociarServicioDomicilio(
            ServicioDomicilio servicioDomicilio) {

        boolean asociado = false;

        if (estado == EstadoPedido.PENDIENTE
                && modalidadEntrega == ModalidadEntrega.DOMICILIO
                && servicioDomicilio != null
                && servicioDomicilio.getEmpresaDomicilio() != null
                && servicioDomicilio.isServicioActivo()) {

            this.servicioDomicilio = servicioDomicilio;
            calcularValorTotal();
            asociado = true;
        }

        return asociado;
    }

    // CONFIRMAR PEDIDO

    public boolean confirmarPedido() {

        boolean confirmado = false;

        if (estado == EstadoPedido.PENDIENTE
                && cliente != null
                && verificarDisponibilidad()) {

            if (modalidadEntrega
                    == ModalidadEntrega.CONSUMO_RESTAURANTE) {

                estado = EstadoPedido.CONFIRMADO;
                confirmado = true;

            } else if (modalidadEntrega == ModalidadEntrega.DOMICILIO
                    && servicioDomicilio != null
                    && servicioDomicilio.getEmpresaDomicilio() != null
                    && servicioDomicilio.isServicioActivo()) {

                estado = EstadoPedido.CONFIRMADO;
                confirmado = true;
            }

            calcularValorTotal();
        }

        return confirmado;
    }

    // ENTREGAR PEDIDO

    public boolean entregarPedido() {

        boolean entregado = false;

        if (estado == EstadoPedido.CONFIRMADO) {
            estado = EstadoPedido.ENTREGADO;
            entregado = true;
        }

        return entregado;
    }

    // CANCELAR PEDIDO

    public boolean cancelarPedido() {

        boolean cancelado = false;

        if (estado == EstadoPedido.PENDIENTE
                || estado == EstadoPedido.CONFIRMADO) {

            estado = EstadoPedido.CANCELADO;

            if (servicioDomicilio != null
                    && servicioDomicilio.isServicioActivo()) {

                servicioDomicilio.cancelarServicio();
            }

            calcularValorTotal();
            cancelado = true;
        }

        return cancelado;
    }

    // CONSULTAR DETALLES

    public String mostrarDetalles() {

        String informacion = "";

        if (listaDetalles.isEmpty()) {

            informacion = "El pedido no tiene productos.";

        } else {

            for (DetallePedido detalle : listaDetalles) {

                informacion = informacion
                        + detalle.consultarInformacion() + "\n";
            }
        }

        return informacion;
    }

    // CONSULTAR INFORMACION DEL PEDIDO

    public String consultarInformacion() {

        String informacion = "";

        informacion = "Código del pedido: " + codigo
                + "\nFecha: " + fecha
                + "\nCliente: ";

        if (cliente != null) {

            informacion = informacion
                    + cliente.getNombreCompleto()
                    + "\nDocumento: " + cliente.getDocumento();

        } else {

            informacion = informacion + "Sin cliente asociado";
        }

        informacion = informacion
                + "\nModalidad: " + modalidadEntrega
                + "\nEstado: " + estado
                + "\nProductos:\n" + mostrarDetalles();

        if (modalidadEntrega == ModalidadEntrega.DOMICILIO) {

            if (servicioDomicilio != null) {

                informacion = informacion
                        + "\nInformación del domicilio:\n"
                        + servicioDomicilio.consultarInformacion();

            } else {

                informacion = informacion
                        + "\nDomicilio pendiente de registrar.";
            }
        }

        informacion = informacion
                + "\nValor total: $" + calcularValorTotal();

        return informacion;
    }

    // GETTERS Y SETTERS

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {

        if (estado == EstadoPedido.PENDIENTE
                && codigo != null
                && !codigo.equals("")) {

            this.codigo = codigo;
        }
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {

        if (estado == EstadoPedido.PENDIENTE && fecha != null) {
            this.fecha = fecha;
        }
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {

        if (estado != null) {
            this.estado = estado;
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<DetallePedido> getListaDetalles() {
        return listaDetalles;
    }

    public ServicioDomicilio getServicioDomicilio() {
        return servicioDomicilio;
    }

    public ModalidadEntrega getModalidadEntrega() {
        return modalidadEntrega;
    }

    public void setModalidadEntrega(ModalidadEntrega modalidadEntrega) {

        if (estado == EstadoPedido.PENDIENTE
                && modalidadEntrega != null
                && servicioDomicilio == null) {

            this.modalidadEntrega = modalidadEntrega;
            calcularValorTotal();
        }
    }

    public double getValorTotal() {

        double total = calcularValorTotal();

        return total;
    }

    @Override
    public String toString() {

        String informacion = consultarInformacion();

        return informacion;
    }
}