package co.edu.uniquindio.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Restaurante {

    private String nombre;
    private String nit;
    private String direccion;

    private List<Producto> listaProductos;
    private List<Cliente> listaClientes;
    private List<Pedido> listaPedidos;

    public Restaurante(String nombre, String nit, String direccion) {

        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;

        this.listaProductos = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.listaPedidos = new ArrayList<>();
    }

    // REGISTRO Y ADMINISTRACION DE PRODUCTOS

    public boolean registrarProducto(Producto producto) {

        boolean registrado = false;

        if (producto != null
                && producto.getCodigo() != null
                && !producto.getCodigo().equals("")
                && buscarProducto(producto.getCodigo()) == null) {

            listaProductos.add(producto);
            registrado = true;
        }

        return registrado;
    }
    public boolean eliminarPedidoPendiente(String codigo) {
        boolean eliminado = false;

        Pedido pedido = buscarPedido(codigo);

        if (pedido != null && pedido.getEstado() == EstadoPedido.PENDIENTE) {

            if (pedido.getServicioDomicilio() != null) {
                pedido.getServicioDomicilio().cancelarServicio();
            }

            if (pedido.getCliente() != null) {
                pedido.getCliente().eliminarPedido(pedido);
            }

            listaPedidos.remove(pedido);
            eliminado = true;
        }

        return eliminado;
    }

    public Producto buscarProducto(String codigo) {

        Producto productoEncontrado = null;

        if (codigo != null) {

            for (Producto producto : listaProductos) {

                if (producto.getCodigo().equals(codigo)) {
                    productoEncontrado = producto;
                    break;
                }
            }
        }

        return productoEncontrado;
    }

    public boolean eliminarProducto(String codigo) {

        boolean eliminado = false;

        if (codigo != null) {

            Producto producto = buscarProducto(codigo);

            if (producto != null) {

                boolean utilizadoEnPedido = false;

                for (Pedido pedido : listaPedidos) {

                    for (DetallePedido detalle : pedido.getListaDetalles()) {

                        if (detalle.getProducto().getCodigo().equals(codigo)) {
                            utilizadoEnPedido = true;
                            break;
                        }
                    }

                    if (utilizadoEnPedido) {
                        break;
                    }
                }

                if (!utilizadoEnPedido) {
                    listaProductos.remove(producto);
                    eliminado = true;
                }
            }
        }

        return eliminado;
    }

    public List<Producto> consultarProductos() {

        List<Producto> productos = new ArrayList<>(listaProductos);

        return productos;
    }

    public List<Producto> consultarProductosPorCategoria(
            CategoriaProducto categoria) {

        List<Producto> productosCategoria = new ArrayList<>();

        if (categoria != null) {

            for (Producto producto : listaProductos) {

                if (producto.getCategoria() == categoria) {
                    productosCategoria.add(producto);
                }
            }
        }

        return productosCategoria;
    }

    public List<Producto> consultarProductosDisponibles() {

        List<Producto> productosDisponibles = new ArrayList<>();

        for (Producto producto : listaProductos) {

            if (producto.verificarCalidad()) {
                productosDisponibles.add(producto);
            }
        }

        return productosDisponibles;
    }

    public boolean consultarCalidadProducto(String codigo) {

        boolean calidadAprobada = false;

        Producto producto = buscarProducto(codigo);

        if (producto != null) {
            calidadAprobada = producto.verificarCalidad();
        }

        return calidadAprobada;
    }

    // REGISTRO Y ADMINISTRACION DE CLIENTES

    public boolean registrarCliente(Cliente cliente) {

        boolean registrado = false;

        if (cliente != null
                && cliente.getDocumento() != null
                && !cliente.getDocumento().equals("")
                && buscarCliente(cliente.getDocumento()) == null) {

            listaClientes.add(cliente);
            registrado = true;
        }

        return registrado;
    }

    public Cliente buscarCliente(String documento) {

        Cliente clienteEncontrado = null;

        if (documento != null) {

            for (Cliente cliente : listaClientes) {

                if (cliente.getDocumento().equals(documento)) {
                    clienteEncontrado = cliente;
                    break;
                }
            }
        }

        return clienteEncontrado;
    }

    public boolean eliminarCliente(String documento) {

        boolean eliminado = false;

        if (documento != null) {

            Cliente cliente = buscarCliente(documento);

            if (cliente != null && !cliente.tienePedidos()) {

                listaClientes.remove(cliente);
                eliminado = true;
            }
        }

        return eliminado;
    }

    public List<Cliente> consultarClientes() {

        List<Cliente> clientes = new ArrayList<>(listaClientes);

        return clientes;
    }

    public String consultarHistorialCliente(String documento) {

        String historial = "No se encontró el cliente.";

        Cliente cliente = buscarCliente(documento);

        if (cliente != null) {
            historial = cliente.consultarInformacionCompleta();
        }

        return historial;
    }

    // CREACION Y ADMINISTRACION DE PEDIDOS

    public Pedido crearPedido(String codigo, String documentoCliente) {

        Pedido pedidoCreado = null;

        pedidoCreado = crearPedido(
                codigo,
                documentoCliente,
                ModalidadEntrega.CONSUMO_RESTAURANTE
        );

        return pedidoCreado;
    }

    public Pedido crearPedido(String codigo,
                              String documentoCliente,
                              ModalidadEntrega modalidadEntrega) {

        Pedido pedidoCreado = null;

        Cliente cliente = buscarCliente(documentoCliente);

        if (codigo != null
                && !codigo.equals("")
                && cliente != null
                && modalidadEntrega != null
                && buscarPedido(codigo) == null) {

            Pedido pedido = new Pedido(
                    codigo,
                    LocalDate.now(),
                    cliente,
                    modalidadEntrega
            );

            listaPedidos.add(pedido);

            pedidoCreado = pedido;
        }

        return pedidoCreado;
    }

    public Pedido buscarPedido(String codigo) {

        Pedido pedidoEncontrado = null;

        if (codigo != null) {

            for (Pedido pedido : listaPedidos) {

                if (pedido.getCodigo().equals(codigo)) {
                    pedidoEncontrado = pedido;
                    break;
                }
            }
        }

        return pedidoEncontrado;
    }

    public boolean existePedido(String codigo) {

        boolean existe = false;

        if (buscarPedido(codigo) != null) {
            existe = true;
        }

        return existe;
    }

    public boolean agregarProductoPedido(String codigoPedido,
                                         String codigoProducto,
                                         int cantidad) {

        boolean agregado = false;

        Pedido pedido = buscarPedido(codigoPedido);
        Producto producto = buscarProducto(codigoProducto);

        if (pedido != null && producto != null) {

            agregado = pedido.agregarDetalle(producto, cantidad);
        }

        return agregado;
    }

    public boolean eliminarProductoPedido(String codigoPedido,
                                          String codigoProducto) {

        boolean eliminado = false;

        Pedido pedido = buscarPedido(codigoPedido);

        if (pedido != null) {

            eliminado = pedido.eliminarDetalle(codigoProducto);
        }

        return eliminado;
    }

    public boolean modificarCantidadPedido(String codigoPedido,
                                           String codigoProducto,
                                           int cantidad) {

        boolean modificado = false;

        Pedido pedido = buscarPedido(codigoPedido);

        if (pedido != null) {

            modificado = pedido.modificarCantidad(
                    codigoProducto,
                    cantidad
            );
        }

        return modificado;
    }

    public boolean confirmarPedido(String codigo) {

        boolean confirmado = false;

        Pedido pedido = buscarPedido(codigo);

        if (pedido != null) {

            confirmado = pedido.confirmarPedido();
        }

        return confirmado;
    }

    public boolean entregarPedido(String codigo) {

        boolean entregado = false;

        Pedido pedido = buscarPedido(codigo);

        if (pedido != null) {

            entregado = pedido.entregarPedido();
        }

        return entregado;
    }

    public boolean cancelarPedido(String codigo) {

        boolean cancelado = false;

        Pedido pedido = buscarPedido(codigo);

        if (pedido != null) {

            cancelado = pedido.cancelarPedido();
        }

        return cancelado;
    }

    public List<Pedido> consultarPedidos() {

        List<Pedido> pedidos = new ArrayList<>(listaPedidos);

        return pedidos;
    }

    public List<Pedido> consultarPedidosCliente(String documento) {

        List<Pedido> pedidosCliente = new ArrayList<>();

        if (documento != null) {

            for (Pedido pedido : listaPedidos) {

                if (pedido.getCliente() != null
                        && pedido.getCliente().getDocumento().equals(documento)) {

                    pedidosCliente.add(pedido);
                }
            }
        }

        return pedidosCliente;
    }

    public List<Pedido> consultarPedidosPorEstado(EstadoPedido estado) {

        List<Pedido> pedidosEstado = new ArrayList<>();

        if (estado != null) {

            for (Pedido pedido : listaPedidos) {

                if (pedido.getEstado() == estado) {
                    pedidosEstado.add(pedido);
                }
            }
        }

        return pedidosEstado;
    }

    public List<Pedido> consultarPedidosPorModalidad(
            ModalidadEntrega modalidadEntrega) {

        List<Pedido> pedidosModalidad = new ArrayList<>();

        if (modalidadEntrega != null) {

            for (Pedido pedido : listaPedidos) {

                if (pedido.getModalidadEntrega() == modalidadEntrega) {
                    pedidosModalidad.add(pedido);
                }
            }
        }

        return pedidosModalidad;
    }

    public String consultarInformacionPedido(String codigo) {

        String informacion = "No se encontró el pedido.";

        Pedido pedido = buscarPedido(codigo);

        if (pedido != null) {
            informacion = pedido.consultarInformacion();
        }

        return informacion;
    }

    // ADMINISTRACION DE DOMICILIOS

    public boolean registrarDomicilio(String codigoPedido,
                                      EmpresaDomicilio empresa,
                                      double distancia,
                                      String direccion) {

        boolean registrado = false;

        Pedido pedido = buscarPedido(codigoPedido);

        if (pedido != null
                && empresa != null
                && pedido.getEstado() == EstadoPedido.PENDIENTE
                && pedido.getModalidadEntrega() == ModalidadEntrega.DOMICILIO
                && pedido.getServicioDomicilio() == null) {

            ServicioDomicilio servicio = new ServicioDomicilio();

            boolean empresaSeleccionada =
                    servicio.seleccionarEmpresa(empresa);

            if (empresaSeleccionada) {

                boolean servicioRegistrado =
                        servicio.registrarServicio(distancia, direccion);

                if (servicioRegistrado) {

                    registrado =
                            pedido.asociarServicioDomicilio(servicio);
                }
            }
        }

        return registrado;
    }

    public boolean cancelarDomicilio(String codigoPedido) {

        boolean cancelado = false;

        Pedido pedido = buscarPedido(codigoPedido);

        if (pedido != null
                && pedido.getEstado() == EstadoPedido.PENDIENTE
                && pedido.getModalidadEntrega() == ModalidadEntrega.DOMICILIO
                && pedido.getServicioDomicilio() != null) {

            cancelado = pedido.getServicioDomicilio().cancelarServicio();
        }

        return cancelado;
    }

    public String consultarDomicilio(String codigoPedido) {

        String informacion = "No se encontró el domicilio.";

        Pedido pedido = buscarPedido(codigoPedido);

        if (pedido != null
                && pedido.getModalidadEntrega() == ModalidadEntrega.DOMICILIO
                && pedido.getServicioDomicilio() != null) {

            informacion =
                    pedido.getServicioDomicilio().consultarInformacion();
        }

        return informacion;
    }

    // REPORTES DE VENTAS

    public double calcularIngresosPlatosFuertes() {

        double ingresos = 0;

        for (Pedido pedido : listaPedidos) {

            if (pedido.getEstado() == EstadoPedido.ENTREGADO) {

                for (DetallePedido detalle : pedido.getListaDetalles()) {

                    if (detalle.getProducto().getCategoria()
                            == CategoriaProducto.PLATO_FUERTE) {

                        ingresos = ingresos + detalle.calcularSubtotal();
                    }
                }
            }
        }

        return ingresos;
    }

    public double calcularIngresosBebidas() {

        double ingresos = 0;

        for (Pedido pedido : listaPedidos) {

            if (pedido.getEstado() == EstadoPedido.ENTREGADO) {

                for (DetallePedido detalle : pedido.getListaDetalles()) {

                    if (detalle.getProducto().getCategoria()
                            == CategoriaProducto.BEBIDA) {

                        ingresos = ingresos + detalle.calcularSubtotal();
                    }
                }
            }
        }

        return ingresos;
    }

    public double calcularIngresosPostres() {

        double ingresos = 0;

        for (Pedido pedido : listaPedidos) {

            if (pedido.getEstado() == EstadoPedido.ENTREGADO) {

                for (DetallePedido detalle : pedido.getListaDetalles()) {

                    if (detalle.getProducto().getCategoria()
                            == CategoriaProducto.POSTRE) {

                        ingresos = ingresos + detalle.calcularSubtotal();
                    }
                }
            }
        }

        return ingresos;
    }

    public double calcularIngresosDomicilios() {

        double ingresos = 0;

        for (Pedido pedido : listaPedidos) {

            if (pedido.getEstado() == EstadoPedido.ENTREGADO
                    && pedido.getModalidadEntrega() == ModalidadEntrega.DOMICILIO
                    && pedido.getServicioDomicilio() != null) {

                ingresos = ingresos
                        + pedido.getServicioDomicilio().getTarifa();
            }
        }

        return ingresos;
    }

    public String consultarProductoMasVendido() {

        String productoMasVendido = "No hay ventas registradas";

        int mayorCantidad = 0;

        for (Producto producto : listaProductos) {

            int cantidadVendida = 0;

            for (Pedido pedido : listaPedidos) {

                if (pedido.getEstado() == EstadoPedido.ENTREGADO) {

                    for (DetallePedido detalle : pedido.getListaDetalles()) {

                        if (detalle.getProducto().getCodigo()
                                .equals(producto.getCodigo())) {

                            cantidadVendida = cantidadVendida
                                    + detalle.getCantidad();
                        }
                    }
                }
            }

            if (cantidadVendida > mayorCantidad) {

                mayorCantidad = cantidadVendida;
                productoMasVendido = producto.getNombre();
            }
        }

        return productoMasVendido;
    }

    public String consultarCategoriaMayorIngreso() {

        double ingresosPlatos = calcularIngresosPlatosFuertes();
        double ingresosBebidas = calcularIngresosBebidas();
        double ingresosPostres = calcularIngresosPostres();

        String categoriaMayorIngreso = "Sin ventas registradas";

        double mayorIngreso = 0;

        if (ingresosPlatos > mayorIngreso) {

            mayorIngreso = ingresosPlatos;
            categoriaMayorIngreso = "Platos fuertes";
        }

        if (ingresosBebidas > mayorIngreso) {

            mayorIngreso = ingresosBebidas;
            categoriaMayorIngreso = "Bebidas";
        }

        if (ingresosPostres > mayorIngreso) {

            mayorIngreso = ingresosPostres;
            categoriaMayorIngreso = "Postres";
        }

        return categoriaMayorIngreso;
    }

    public ResumenVentas generarResumenVentas() {

        double ingresosPlatos = calcularIngresosPlatosFuertes();
        double ingresosBebidas = calcularIngresosBebidas();
        double ingresosPostres = calcularIngresosPostres();
        double ingresosDomicilios = calcularIngresosDomicilios();

        String productoMasVendido = consultarProductoMasVendido();
        String categoriaMayorIngreso = consultarCategoriaMayorIngreso();

        ResumenVentas resumen = new ResumenVentas(
                ingresosPlatos,
                ingresosBebidas,
                ingresosPostres,
                ingresosDomicilios,
                productoMasVendido,
                categoriaMayorIngreso
        );

        return resumen;
    }

    public String consultarReporteVentas() {

        ResumenVentas resumen = generarResumenVentas();

        String informacion = "REPORTE DE VENTAS"
                + "\nIngresos por platos fuertes: $"
                + resumen.ingresosPlatosFuertes()
                + "\nIngresos por bebidas: $"
                + resumen.ingresosBebidas()
                + "\nIngresos por postres: $"
                + resumen.ingresosPostres()
                + "\nIngresos por domicilios: $"
                + resumen.ingresosDomicilios()
                + "\nIngresos de productos: $"
                + resumen.calcularIngresosProductos()
                + "\nIngresos totales: $"
                + resumen.calcularIngresosTotales()
                + "\nProducto más vendido: "
                + resumen.productoMasVendido()
                + "\nCategoría con mayores ingresos: "
                + resumen.categoriaMayorIngreso();

        return informacion;
    }

    // REPORTES COMPLETOS

    public String consultarIngresosPorCategoria() {

        double ingresosPlatos = calcularIngresosPlatosFuertes();
        double ingresosBebidas = calcularIngresosBebidas();
        double ingresosPostres = calcularIngresosPostres();

        String informacion = "INGRESOS POR CATEGORÍA"
                + "\n\nPlatos fuertes: $" + ingresosPlatos
                + "\nBebidas: $" + ingresosBebidas
                + "\nPostres: $" + ingresosPostres
                + "\n\nTotal de ingresos por productos: $"
                + (ingresosPlatos + ingresosBebidas + ingresosPostres);

        return informacion;
    }

    public String generarReporteGeneral() {

        int cantidadProductos = listaProductos.size();
        int cantidadClientes = listaClientes.size();
        int cantidadPedidos = listaPedidos.size();

        int pedidosPendientes = 0;
        int pedidosConfirmados = 0;
        int pedidosEntregados = 0;
        int pedidosCancelados = 0;

        for (Pedido pedido : listaPedidos) {

            if (pedido.getEstado() == EstadoPedido.PENDIENTE) {
                pedidosPendientes++;
            } else if (pedido.getEstado() == EstadoPedido.CONFIRMADO) {
                pedidosConfirmados++;
            } else if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
                pedidosEntregados++;
            } else if (pedido.getEstado() == EstadoPedido.CANCELADO) {
                pedidosCancelados++;
            }
        }

        String informacion = "REPORTE GENERAL DEL RESTAURANTE"
                + "\n\nNombre: " + nombre
                + "\nNIT: " + nit
                + "\nDirección: " + direccion
                + "\n\nCANTIDADES"
                + "\nProductos registrados: " + cantidadProductos
                + "\nClientes registrados: " + cantidadClientes
                + "\nPedidos registrados: " + cantidadPedidos
                + "\n\nESTADO DE LOS PEDIDOS"
                + "\nPendientes: " + pedidosPendientes
                + "\nConfirmados: " + pedidosConfirmados
                + "\nEntregados: " + pedidosEntregados
                + "\nCancelados: " + pedidosCancelados
                + "\n\n" + consultarIngresosPorCategoria()
                + "\n\nIngresos por domicilios: $"
                + calcularIngresosDomicilios()
                + "\n\n" + consultarReporteVentas();

        return informacion;
    }

    // INFORMACION GENERAL DEL RESTAURANTE

    public String consultarInformacion() {

        String informacion = "INFORMACIÓN DEL RESTAURANTE"
                + "\nNombre: " + nombre
                + "\nNIT: " + nit
                + "\nDirección: " + direccion
                + "\nCantidad de productos: " + listaProductos.size()
                + "\nCantidad de clientes: " + listaClientes.size()
                + "\nCantidad de pedidos: " + listaPedidos.size();

        return informacion;
    }

    // GETTERS Y SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Producto> getListaProductos() {

        List<Producto> productos = new ArrayList<>(listaProductos);

        return productos;
    }

    public List<Cliente> getListaClientes() {

        List<Cliente> clientes = new ArrayList<>(listaClientes);

        return clientes;
    }

    public List<Pedido> getListaPedidos() {

        List<Pedido> pedidos = new ArrayList<>(listaPedidos);

        return pedidos;
    }
}