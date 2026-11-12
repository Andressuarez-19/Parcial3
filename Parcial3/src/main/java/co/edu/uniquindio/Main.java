package co.edu.uniquindio;

import co.edu.uniquindio.model.*;
import javax.swing.JOptionPane;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Restaurante restaurante = MainRecord.crearRestaurante();

        int opcion = 0;

        while (opcion != 7) {

            String menu = "EL BUEN COMER"
                    + "\n\n1. Gestión de productos"
                    + "\n2. Gestión de clientes"
                    + "\n3. Gestión de pedidos"
                    + "\n4. Gestión de domicilios"
                    + "\n5. Reportes de ventas e ingresos"
                    + "\n6. Información del restaurante"
                    + "\n7. Salir";

            opcion = leerEntero(menu);

            switch (opcion) {
                case 1:
                    menuProductos(restaurante);
                    break;
                case 2:
                    menuClientes(restaurante);
                    break;
                case 3:
                    menuPedidos(restaurante);
                    break;
                case 4:
                    menuDomicilios(restaurante);
                    break;
                case 5:
                    menuReportes(restaurante);
                    break;
                case 6:
                    mostrar(restaurante.consultarInformacion());
                    break;
                case 7:
                    mostrar("Gracias por utilizar El Buen Comer.");
                    break;
                default:
                    mostrar("Opción no válida.");
                    break;
            }
        }
    }

    // ==========================================
    // GESTIÓN DE PRODUCTOS
    // ==========================================

    public static void menuProductos(Restaurante restaurante) {

        int opcion = 0;

        while (opcion != 7) {

            String menu = "GESTIÓN DE PRODUCTOS"
                    + "\n\n1. Registrar producto"
                    + "\n2. Consultar todos los productos"
                    + "\n3. Buscar producto"
                    + "\n4. Consultar productos por categoría"
                    + "\n5. Verificar calidad"
                    + "\n6. Eliminar producto"
                    + "\n7. Volver";

            opcion = leerEntero(menu);

            switch (opcion) {
                case 1:
                    registrarProducto(restaurante);
                    break;
                case 2:
                    consultarProductos(restaurante);
                    break;
                case 3:
                    buscarProducto(restaurante);
                    break;
                case 4:
                    consultarProductosPorCategoria(restaurante);
                    break;
                case 5:
                    verificarCalidadProducto(restaurante);
                    break;
                case 6:
                    eliminarProducto(restaurante);
                    break;
                case 7:
                    break;
                default:
                    mostrar("Opción no válida.");
                    break;
            }
        }
    }

    public static void registrarProducto(Restaurante restaurante) {

        int tipo = leerEntero(
                "TIPO DE PRODUCTO"
                        + "\n\n1. Postre"
                        + "\n2. Plato fuerte"
                        + "\n3. Bebida"
                        + "\n\nEscriba el número del tipo de producto:"
        );

        if (tipo < 1 || tipo > 3) {
            return;
        }

        String codigo = leerTexto(
                "CÓDIGO DEL PRODUCTO"
                        + "\n\nDebe ser único y no estar registrado."
                        + "\n\nIngrese el código:"
        );

        if (codigo == null) {
            return;
        }

        if (restaurante.buscarProducto(codigo) != null) {
            mostrar("Ya existe un producto con ese código.");
            return;
        }

        String nombre = leerTexto(
                "NOMBRE DEL PRODUCTO"
                        + "\n\nIngrese el nombre:"
        );

        if (nombre == null) {
            return;
        }

        String descripcion = leerTexto(
                "DESCRIPCIÓN DEL PRODUCTO"
                        + "\n\nIngrese la descripción:"
        );

        if (descripcion == null) {
            return;
        }

        double precio = leerDecimal(
                "PRECIO BASE"
                        + "\n\nEl precio debe ser mayor que cero."
                        + "\n\nIngrese el precio base:"
        );

        if (precio <= 0 || precio == -999) {
            mostrar("El precio debe ser mayor que cero.");
            return;
        }

        int tiempo;

        if (tipo == 2) {
            tiempo = leerEntero(
                    "TIEMPO DE PREPARACIÓN DEL PLATO FUERTE"
                            + "\n\nREQUISITO DE CALIDAD:"
                            + "\nEl tiempo debe estar entre 10 y 60 minutos."
                            + "\n\nIngrese el tiempo de preparación:"
            );
        } else {
            tiempo = leerEntero(
                    "TIEMPO DE PREPARACIÓN"
                            + "\n\nIngrese el tiempo de preparación en minutos:"
            );
        }

        if (tiempo <= 0) {
            mostrar("El tiempo debe ser mayor que cero.");
            return;
        }

        Producto producto = null;

        if (tipo == 1) {

            int peso = leerEntero(
                    "PESO DEL POSTRE"
                            + "\n\nREQUISITO DE CALIDAD:"
                            + "\nEl peso debe estar entre 80 y 500 gramos."
                            + "\n\nIngrese el peso:"
            );

            if (peso <= 0) {
                mostrar("El peso debe ser mayor que cero.");
                return;
            }

            int refrigeracion = leerEntero(
                    "REFRIGERACIÓN DEL POSTRE"
                            + "\n\n1. Sí requiere refrigeración"
                            + "\n2. No requiere refrigeración"
                            + "\n\nEscriba una opción:"
            );

            if (refrigeracion != 1 && refrigeracion != 2) {
                return;
            }

            boolean necesitaRefrigeracion = refrigeracion == 1;

            double temperatura;

            if (necesitaRefrigeracion) {
                temperatura = leerDecimal(
                        "TEMPERATURA DEL POSTRE REFRIGERADO"
                                + "\n\nREQUISITO DE CALIDAD:"
                                + "\nLa temperatura debe estar entre 0 y 6 °C."
                                + "\n\nIngrese la temperatura:"
                );
            } else {
                temperatura = leerDecimal(
                        "TEMPERATURA DEL POSTRE SIN REFRIGERAR"
                                + "\n\nREQUISITO DE CALIDAD:"
                                + "\nLa temperatura debe estar entre 15 y 30 °C."
                                + "\n\nIngrese la temperatura:"
                );
            }

            if (temperatura == -999) {
                return;
            }

            producto = new Postre(
                    codigo, nombre, descripcion, precio,
                    tiempo, peso, temperatura, necesitaRefrigeracion
            );

        } else if (tipo == 2) {

            int peso = leerEntero(
                    "PESO DEL PLATO FUERTE"
                            + "\n\nREQUISITO DE CALIDAD:"
                            + "\nEl peso debe estar entre 200 y 900 gramos."
                            + "\n\nIngrese el peso:"
            );

            if (peso <= 0) {
                mostrar("El peso debe ser mayor que cero.");
                return;
            }

            int preparacion = leerEntero(
                    "TIPO DE PREPARACIÓN"
                            + "\n\n1. ASADO"
                            + "\n2. FRITO"
                            + "\n3. HORNEADO"
                            + "\n4. A LA PLANCHA"
                            + "\n\nEscriba una opción:"
            );

            if (preparacion < 1 || preparacion > 4) {
                return;
            }

            TipoPreparacion tipoPreparacion = null;

            if (preparacion == 1) {
                tipoPreparacion = TipoPreparacion.ASADO;
            } else if (preparacion == 2) {
                tipoPreparacion = TipoPreparacion.FRITO;
            } else if (preparacion == 3) {
                tipoPreparacion = TipoPreparacion.HORNEADO;
            } else if (preparacion == 4) {
                tipoPreparacion = TipoPreparacion.A_LA_PLANCHA;
            }

            int coccion = leerEntero(
                    "APROBACIÓN DE LA COCCIÓN"
                            + "\n\nREQUISITO DE CALIDAD:"
                            + "\nLa cocción debe estar aprobada."
                            + "\n\n1. Sí está aprobada"
                            + "\n2. No está aprobada"
                            + "\n\nEscriba una opción:"
            );

            if (coccion != 1 && coccion != 2) {
                return;
            }

            boolean coccionAprobada = coccion == 1;

            producto = new PlatoFuerte(
                    codigo, nombre, descripcion, precio,
                    tiempo, peso, tipoPreparacion, coccionAprobada
            );

        } else if (tipo == 3) {

            int volumen = leerEntero(
                    "VOLUMEN DE LA BEBIDA"
                            + "\n\nREQUISITO DE CALIDAD:"
                            + "\nEl volumen debe estar entre 150 y 1500 mililitros."
                            + "\n\nIngrese el volumen:"
            );

            if (volumen <= 0) {
                mostrar("El volumen debe ser mayor que cero.");
                return;
            }

            int opcionTemperatura = leerEntero(
                    "TIPO DE TEMPERATURA"
                            + "\n\n1. Fría"
                            + "\n2. Caliente"
                            + "\n\nEscriba una opción:"
            );

            if (opcionTemperatura != 1 && opcionTemperatura != 2) {
                return;
            }

            TipoTemperatura tipoTemperatura;
            double temperatura;

            if (opcionTemperatura == 1) {

                tipoTemperatura = TipoTemperatura.FRIA;

                temperatura = leerDecimal(
                        "TEMPERATURA DE LA BEBIDA FRÍA"
                                + "\n\nREQUISITO DE CALIDAD:"
                                + "\nLa temperatura debe estar entre 0 y 10 °C."
                                + "\n\nIngrese la temperatura:"
                );

            } else {

                tipoTemperatura = TipoTemperatura.CALIENTE;

                temperatura = leerDecimal(
                        "TEMPERATURA DE LA BEBIDA CALIENTE"
                                + "\n\nREQUISITO DE CALIDAD:"
                                + "\nLa temperatura debe estar entre 55 y 85 °C."
                                + "\n\nIngrese la temperatura:"
                );
            }

            if (temperatura == -999) {
                return;
            }

            int especial = leerEntero(
                    "PREPARACIÓN ESPECIAL"
                            + "\n\n1. Sí tiene preparación especial"
                            + "\n2. No tiene preparación especial"
                            + "\n\nEscriba una opción:"
            );

            if (especial != 1 && especial != 2) {
                return;
            }

            boolean preparacionEspecial = especial == 1;

            producto = new Bebida(
                    codigo, nombre, descripcion, precio,
                    tiempo, volumen, temperatura,
                    tipoTemperatura, preparacionEspecial
            );
        }

        if (producto != null) {

            boolean registrado = restaurante.registrarProducto(producto);

            if (registrado) {

                String calidad;

                if (producto.verificarCalidad()) {
                    calidad = "APROBADA";
                } else {
                    calidad = "NO APROBADA";
                }

                mostrar("PRODUCTO REGISTRADO CORRECTAMENTE"
                        + "\n\nNombre: " + producto.getNombre()
                        + "\nCódigo: " + producto.getCodigo()
                        + "\nCalidad: " + calidad
                        + "\nPrecio de venta: $"
                        + producto.calcularPrecioVenta());

            } else {
                mostrar("No fue posible registrar el producto.");
            }
        }
    }

    public static void consultarProductos(Restaurante restaurante) {

        List<Producto> productos = restaurante.consultarProductos();

        String informacion = "PRODUCTOS REGISTRADOS\n\n";

        if (productos.isEmpty()) {
            informacion = "No hay productos registrados.";
        } else {
            for (Producto producto : productos) {
                informacion += producto.consultarInformacion() + "\n";
            }
        }

        mostrar(informacion);
    }

    public static void buscarProducto(Restaurante restaurante) {

        String codigo = leerTexto("Ingrese el código del producto:");

        if (codigo == null) {
            return;
        }

        Producto producto = restaurante.buscarProducto(codigo);

        if (producto != null) {
            mostrar(producto.consultarInformacion());
        } else {
            mostrar("No se encontró el producto.");
        }
    }

    public static void consultarProductosPorCategoria(
            Restaurante restaurante) {

        int opcion = leerEntero(
                "CONSULTAR PRODUCTOS POR CATEGORÍA"
                        + "\n\n1. Plato fuerte"
                        + "\n2. Bebida"
                        + "\n3. Postre"
                        + "\n\nEscriba una opción:"
        );

        if (opcion < 1 || opcion > 3) {
            return;
        }

        CategoriaProducto categoria = null;

        if (opcion == 1) {
            categoria = CategoriaProducto.PLATO_FUERTE;
        } else if (opcion == 2) {
            categoria = CategoriaProducto.BEBIDA;
        } else if (opcion == 3) {
            categoria = CategoriaProducto.POSTRE;
        }

        List<Producto> productos =
                restaurante.consultarProductosPorCategoria(categoria);

        String informacion = "PRODUCTOS DE LA CATEGORÍA\n\n";

        if (productos.isEmpty()) {
            informacion = "No hay productos en esta categoría.";
        } else {
            for (Producto producto : productos) {
                informacion += producto.consultarInformacion() + "\n";
            }
        }

        mostrar(informacion);
    }

    public static void verificarCalidadProducto(Restaurante restaurante) {

        String codigo = leerTexto("Ingrese el código del producto:");

        if (codigo == null) {
            return;
        }

        Producto producto = restaurante.buscarProducto(codigo);

        if (producto == null) {
            mostrar("No se encontró el producto.");
        } else {

            String calidad;

            if (restaurante.consultarCalidadProducto(codigo)) {
                calidad = "APROBADA";
            } else {
                calidad = "NO APROBADA";
            }

            mostrar("Producto: " + producto.getNombre()
                    + "\nCódigo: " + producto.getCodigo()
                    + "\nCalidad: " + calidad);
        }
    }

    public static void eliminarProducto(Restaurante restaurante) {

        String codigo = leerTexto("Ingrese el código del producto:");

        if (codigo == null) {
            return;
        }

        boolean eliminado = restaurante.eliminarProducto(codigo);

        if (eliminado) {
            mostrar("Producto eliminado correctamente.");
        } else {
            mostrar("No se pudo eliminar el producto."
                    + "\nPuede que no exista o esté utilizado en un pedido.");
        }
    }

    // ==========================================
    // GESTIÓN DE CLIENTES
    // ==========================================

    public static void menuClientes(Restaurante restaurante) {

        int opcion = 0;

        while (opcion != 6) {

            String menu = "GESTIÓN DE CLIENTES"
                    + "\n\n1. Registrar cliente"
                    + "\n2. Consultar clientes"
                    + "\n3. Buscar cliente"
                    + "\n4. Consultar historial de pedidos"
                    + "\n5. Eliminar cliente"
                    + "\n6. Volver";

            opcion = leerEntero(menu);

            switch (opcion) {
                case 1:
                    registrarCliente(restaurante);
                    break;
                case 2:
                    consultarClientes(restaurante);
                    break;
                case 3:
                    buscarCliente(restaurante);
                    break;
                case 4:
                    consultarHistorialCliente(restaurante);
                    break;
                case 5:
                    eliminarCliente(restaurante);
                    break;
                case 6:
                    break;
                default:
                    mostrar("Opción no válida.");
                    break;
            }
        }
    }

    public static void registrarCliente(Restaurante restaurante) {

        String documento = leerTexto(
                "DOCUMENTO DEL CLIENTE"
                        + "\n\nDebe ser único y no estar registrado."
                        + "\n\nIngrese el documento:"
        );

        if (documento == null) {
            return;
        }

        if (restaurante.buscarCliente(documento) != null) {
            mostrar("Ya existe un cliente con ese documento.");
            return;
        }

        String nombre = leerTexto(
                "NOMBRE COMPLETO"
                        + "\n\nIngrese los nombres y apellidos del cliente:"
        );

        if (nombre == null) {
            return;
        }

        String telefono = leerTexto(
                "TELÉFONO DEL CLIENTE"
                        + "\n\nIngrese un número de contacto:"
        );

        if (telefono == null) {
            return;
        }

        String correo = leerTexto(
                "CORREO ELECTRÓNICO"
                        + "\n\nIngrese el correo electrónico del cliente:"
        );

        if (correo == null) {
            return;
        }

        String direccion = leerTexto(
                "DIRECCIÓN DE RESIDENCIA"
                        + "\n\nIngrese la dirección del cliente:"
        );

        if (direccion == null) {
            return;
        }

        Cliente cliente = new Cliente(
                documento, nombre, telefono, correo, direccion
        );

        boolean registrado = restaurante.registrarCliente(cliente);

        if (registrado) {
            mostrar("CLIENTE REGISTRADO CORRECTAMENTE"
                    + "\n\nNombre: " + cliente.getNombreCompleto()
                    + "\nDocumento: " + cliente.getDocumento());
        } else {
            mostrar("No fue posible registrar el cliente.");
        }
    }

    public static void consultarClientes(Restaurante restaurante) {

        List<Cliente> clientes = restaurante.consultarClientes();

        String informacion = "CLIENTES REGISTRADOS\n\n";

        if (clientes.isEmpty()) {
            informacion = "No hay clientes registrados.";
        } else {
            for (Cliente cliente : clientes) {
                informacion += cliente.consultarInformacion() + "\n\n";
            }
        }

        mostrar(informacion);
    }

    public static void mostrarClientesDisponibles(Restaurante restaurante) {

        List<Cliente> clientes = restaurante.consultarClientes();

        String informacion = "CLIENTES DISPONIBLES\n\n";

        if (clientes.isEmpty()) {
            informacion = "No hay clientes registrados.";
        } else {
            for (Cliente cliente : clientes) {
                informacion += "Documento: " + cliente.getDocumento()
                        + "\nNombre: " + cliente.getNombreCompleto()
                        + "\n------------------------------\n";
            }
        }

        mostrar(informacion);
    }

    public static void buscarCliente(Restaurante restaurante) {

        String documento = leerTexto("Ingrese el documento del cliente:");

        if (documento == null) {
            return;
        }

        Cliente cliente = restaurante.buscarCliente(documento);

        if (cliente != null) {
            mostrar(cliente.consultarInformacion());
        } else {
            mostrar("No se encontró el cliente.");
        }
    }

    public static void consultarHistorialCliente(Restaurante restaurante) {

        String documento = leerTexto("Ingrese el documento del cliente:");

        if (documento == null) {
            return;
        }

        mostrar(restaurante.consultarHistorialCliente(documento));
    }

    public static void eliminarCliente(Restaurante restaurante) {

        String documento = leerTexto("Ingrese el documento del cliente:");

        if (documento == null) {
            return;
        }

        boolean eliminado = restaurante.eliminarCliente(documento);

        if (eliminado) {
            mostrar("Cliente eliminado correctamente.");
        } else {
            mostrar("No se pudo eliminar el cliente."
                    + "\nPuede que no exista o tenga pedidos asociados.");
        }
    }

    // ==========================================
    // GESTIÓN DE PEDIDOS
    // ==========================================

    public static void menuPedidos(Restaurante restaurante) {

        int opcion = 0;

        while (opcion != 12) {

            String menu = "GESTIÓN DE PEDIDOS"
                    + "\n\n1. Crear pedido"
                    + "\n2. Agregar producto a un pedido"
                    + "\n3. Modificar cantidad"
                    + "\n4. Eliminar producto de un pedido"
                    + "\n5. Consultar pedido"
                    + "\n6. Consultar todos los pedidos"
                    + "\n7. Confirmar pedido"
                    + "\n8. Entregar pedido en el local"
                    + "\n9. Cancelar pedido"
                    + "\n10. Consultar pedidos por cliente"
                    + "\n11. Consultar pedidos por estado"
                    + "\n12. Volver";

            opcion = leerEntero(menu);

            switch (opcion) {
                case 1:
                    crearPedido(restaurante);
                    break;
                case 2:
                    agregarProductoPedido(restaurante);
                    break;
                case 3:
                    modificarCantidadPedido(restaurante);
                    break;
                case 4:
                    eliminarProductoPedido(restaurante);
                    break;
                case 5:
                    consultarPedido(restaurante);
                    break;
                case 6:
                    consultarPedidos(restaurante);
                    break;
                case 7:
                    confirmarPedido(restaurante);
                    break;
                case 8:
                    entregarPedido(restaurante);
                    break;
                case 9:
                    cancelarPedido(restaurante);
                    break;
                case 10:
                    consultarPedidosCliente(restaurante);
                    break;
                case 11:
                    consultarPedidosPorEstado(restaurante);
                    break;
                case 12:
                    break;
                default:
                    mostrar("Opción no válida.");
                    break;
            }
        }
    }

    public static void crearPedido(Restaurante restaurante) {

        List<Cliente> clientes = restaurante.consultarClientes();
        List<Producto> productos =
                restaurante.consultarProductosDisponibles();

        if (clientes.isEmpty()) {
            mostrar("Primero debe registrar un cliente.");
            return;
        }

        if (productos.isEmpty()) {
            mostrar("No hay productos aprobados disponibles para los pedidos.");
            return;
        }

        mostrarClientesDisponibles(restaurante);

        String documento = leerTexto(
                "CLIENTE DEL PEDIDO"
                        + "\n\nEscriba el documento de uno de los clientes mostrados:"
        );

        if (documento == null) {
            return;
        }

        Cliente cliente = restaurante.buscarCliente(documento);

        if (cliente == null) {
            mostrar("No se encontró el cliente.");
            return;
        }

        String codigo = leerTexto(
                "CÓDIGO DEL PEDIDO"
                        + "\n\nIngrese un código que no esté registrado:"
        );

        if (codigo == null) {
            return;
        }

        if (restaurante.existePedido(codigo)) {
            mostrar("Ya existe un pedido con ese código.");
            return;
        }

        int opcionModalidad = leerEntero(
                "MODALIDAD DEL PEDIDO"
                        + "\n\n1. Consumo en restaurante"
                        + "\n2. Domicilio"
                        + "\n\nEscriba una opción:"
        );

        if (opcionModalidad != 1 && opcionModalidad != 2) {
            return;
        }

        ModalidadEntrega modalidad;

        if (opcionModalidad == 1) {
            modalidad = ModalidadEntrega.CONSUMO_RESTAURANTE;
        } else {
            modalidad = ModalidadEntrega.DOMICILIO;
        }

        Pedido pedido = restaurante.crearPedido(
                codigo, documento, modalidad
        );

        if (pedido == null) {
            mostrar("No fue posible crear el pedido.");
            return;
        }

        boolean productosAgregados =
                agregarProductosDuranteCreacion(restaurante, codigo);

        if (!productosAgregados) {
            restaurante.eliminarPedidoPendiente(codigo);
            mostrar("La creación del pedido fue cancelada.");
            return;
        }

        if (modalidad == ModalidadEntrega.DOMICILIO) {

            boolean domicilioRegistrado =
                    registrarDomicilio(restaurante, codigo);

            if (!domicilioRegistrado) {
                restaurante.eliminarPedidoPendiente(codigo);

                mostrar("No fue posible registrar el domicilio."
                        + "\nLa creación del pedido fue cancelada.");
                return;
            }
        }

        mostrar("PEDIDO CREADO CORRECTAMENTE"
                + "\nEl pedido quedó PENDIENTE."
                + "\nPuede agregar, modificar o eliminar sus productos."
                + "\nPara confirmarlo utilice la opción 7 del menú."
                + "\n\n" + restaurante.consultarInformacionPedido(codigo));
    }

    public static boolean agregarProductosDuranteCreacion(
            Restaurante restaurante, String codigoPedido) {

        boolean continuar = true;
        boolean finalizar = false;

        while (continuar && !finalizar) {

            List<Producto> productos =
                    restaurante.consultarProductosDisponibles();

            String informacion = "PRODUCTOS APROBADOS DISPONIBLES\n\n";

            for (Producto producto : productos) {
                informacion += "Código: " + producto.getCodigo()
                        + "\nNombre: " + producto.getNombre()
                        + "\nPrecio: $" + producto.calcularPrecioVenta()
                        + "\n\n";
            }

            mostrar(informacion);

            String codigoProducto = leerTexto(
                    "PRODUCTO DEL PEDIDO"
                            + "\n\nEscriba el código de uno de los productos mostrados:"
            );

            if (codigoProducto == null) {
                continuar = false;
            } else {

                Producto producto = restaurante.buscarProducto(codigoProducto);

                if (producto == null) {
                    mostrar("No se encontró el producto.");
                } else if (!producto.verificarCalidad()) {
                    mostrar("El producto no está aprobado y no puede agregarse.");
                } else {

                    int cantidad = leerEntero(
                            "CANTIDAD DEL PRODUCTO"
                                    + "\n\nDebe ingresar una cantidad mayor que cero."
                                    + "\n\nIngrese la cantidad:"
                    );

                    if (cantidad == -1) {
                        continuar = false;
                    } else if (cantidad <= 0) {
                        mostrar("La cantidad debe ser mayor que cero.");
                    } else {

                        boolean agregado = restaurante.agregarProductoPedido(
                                codigoPedido, codigoProducto, cantidad
                        );

                        if (agregado) {
                            mostrar("Producto agregado correctamente.");
                        } else {
                            mostrar("No fue posible agregar el producto.");
                        }
                    }
                }
            }

            if (continuar) {

                int respuesta = leerEntero(
                        "AGREGAR OTRO PRODUCTO"
                                + "\n\n1. Sí"
                                + "\n2. No"
                                + "\n\nEscriba una opción:"
                );

                if (respuesta == 2) {
                    finalizar = true;
                } else if (respuesta != 1) {
                    continuar = false;
                }
            }
        }

        return continuar;
    }

    public static void agregarProductoPedido(Restaurante restaurante) {

        String codigoPedido = leerTexto("Ingrese el código del pedido:");

        if (codigoPedido == null) {
            return;
        }

        Pedido pedido = restaurante.buscarPedido(codigoPedido);

        if (pedido == null) {
            mostrar("No se encontró el pedido.");
            return;
        }

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            mostrar("Solo se pueden modificar pedidos pendientes.");
            return;
        }

        String codigoProducto = leerTexto("Ingrese el código del producto:");

        if (codigoProducto == null) {
            return;
        }

        Producto producto = restaurante.buscarProducto(codigoProducto);

        if (producto == null) {
            mostrar("No se encontró el producto.");
            return;
        }

        if (!producto.verificarCalidad()) {
            mostrar("El producto no está aprobado y no puede agregarse.");
            return;
        }

        int cantidad = leerEntero(
                "CANTIDAD"
                        + "\n\nIngrese una cantidad mayor que cero:"
        );

        if (cantidad <= 0) {
            mostrar("La cantidad debe ser mayor que cero.");
            return;
        }

        boolean agregado = restaurante.agregarProductoPedido(
                codigoPedido, codigoProducto, cantidad
        );

        if (agregado) {
            mostrar("Producto agregado correctamente."
                    + "\n\n" + restaurante.consultarInformacionPedido(codigoPedido));
        } else {
            mostrar("No fue posible agregar el producto.");
        }
    }

    public static void modificarCantidadPedido(Restaurante restaurante) {

        String codigoPedido = leerTexto("Ingrese el código del pedido:");

        if (codigoPedido == null) {
            return;
        }

        Pedido pedido = restaurante.buscarPedido(codigoPedido);

        if (pedido == null) {
            mostrar("No se encontró el pedido.");
            return;
        }

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            mostrar("Solo se pueden modificar pedidos pendientes.");
            return;
        }

        String codigoProducto = leerTexto("Ingrese el código del producto:");

        if (codigoProducto == null) {
            return;
        }

        int cantidad = leerEntero(
                "NUEVA CANTIDAD"
                        + "\n\nDebe ser mayor que cero."
                        + "\n\nIngrese la nueva cantidad:"
        );

        if (cantidad <= 0) {
            mostrar("La cantidad debe ser mayor que cero.");
            return;
        }

        boolean modificado = restaurante.modificarCantidadPedido(
                codigoPedido, codigoProducto, cantidad
        );

        if (modificado) {
            mostrar("Cantidad modificada correctamente."
                    + "\n\n" + restaurante.consultarInformacionPedido(codigoPedido));
        } else {
            mostrar("No fue posible modificar la cantidad.");
        }
    }

    public static void eliminarProductoPedido(Restaurante restaurante) {

        String codigoPedido = leerTexto("Ingrese el código del pedido:");

        if (codigoPedido == null) {
            return;
        }

        Pedido pedido = restaurante.buscarPedido(codigoPedido);

        if (pedido == null) {
            mostrar("No se encontró el pedido.");
            return;
        }

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            mostrar("Solo se pueden modificar pedidos pendientes.");
            return;
        }

        String codigoProducto = leerTexto("Ingrese el código del producto:");

        if (codigoProducto == null) {
            return;
        }

        boolean eliminado = restaurante.eliminarProductoPedido(
                codigoPedido, codigoProducto
        );

        if (eliminado) {
            mostrar("Producto eliminado del pedido."
                    + "\n\n" + restaurante.consultarInformacionPedido(codigoPedido));
        } else {
            mostrar("No fue posible eliminar el producto.");
        }
    }

    public static void consultarPedido(Restaurante restaurante) {

        String codigo = leerTexto("Ingrese el código del pedido:");

        if (codigo == null) {
            return;
        }

        mostrar(restaurante.consultarInformacionPedido(codigo));
    }

    public static void consultarPedidos(Restaurante restaurante) {

        List<Pedido> pedidos = restaurante.consultarPedidos();

        String informacion = "PEDIDOS REGISTRADOS\n\n";

        if (pedidos.isEmpty()) {
            informacion = "No hay pedidos registrados.";
        } else {
            for (Pedido pedido : pedidos) {
                informacion += pedido.consultarInformacion() + "\n\n";
            }
        }

        mostrar(informacion);
    }

    public static void confirmarPedido(Restaurante restaurante) {

        String codigo = leerTexto(
                "CONFIRMAR PEDIDO"
                        + "\n\nIngrese el código del pedido pendiente:"
        );

        if (codigo == null) {
            return;
        }

        Pedido pedido = restaurante.buscarPedido(codigo);

        if (pedido == null) {
            mostrar("No se encontró el pedido.");
            return;
        }

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            mostrar("El pedido no está pendiente y no puede confirmarse.");
            return;
        }

        mostrar(restaurante.consultarInformacionPedido(codigo));

        int respuesta = leerEntero(
                "CONFIRMACIÓN DEL PEDIDO"
                        + "\n\n1. Confirmar pedido"
                        + "\n2. No confirmar"
                        + "\n\nEscriba una opción:"
        );

        if (respuesta != 1) {
            mostrar("El pedido continúa pendiente.");
            return;
        }

        boolean confirmado = restaurante.confirmarPedido(codigo);

        if (confirmado) {
            mostrar("PEDIDO CONFIRMADO CORRECTAMENTE"
                    + "\n\n" + restaurante.consultarInformacionPedido(codigo));
        } else {
            mostrar("No fue posible confirmar el pedido."
                    + "\nVerifique que tenga productos disponibles"
                    + "\ny que el servicio de domicilio esté activo, si corresponde.");
        }
    }

    public static void entregarPedido(Restaurante restaurante) {

        String codigo = leerTexto(
                "ENTREGA EN EL RESTAURANTE"
                        + "\n\nIngrese el código del pedido:"
        );

        if (codigo == null) {
            return;
        }

        Pedido pedido = restaurante.buscarPedido(codigo);

        if (pedido == null) {
            mostrar("No se encontró el pedido.");
            return;
        }

        if (pedido.getModalidadEntrega()
                != ModalidadEntrega.CONSUMO_RESTAURANTE) {

            mostrar("Este pedido es a domicilio."
                    + "\nDebe entregarlo desde Gestión de domicilios.");
            return;
        }

        if (pedido.getEstado() != EstadoPedido.CONFIRMADO) {
            mostrar("Solo se pueden entregar pedidos confirmados.");
            return;
        }

        int respuesta = leerEntero(
                "ENTREGA EN EL LOCAL"
                        + "\n\n1. Confirmar entrega"
                        + "\n2. Volver"
                        + "\n\nEscriba una opción:"
        );

        if (respuesta != 1) {
            return;
        }

        boolean entregado = restaurante.entregarPedido(codigo);

        if (entregado) {
            mostrar("Pedido entregado correctamente."
                    + "\nModalidad: Consumo en restaurante"
                    + "\n\n" + restaurante.consultarInformacionPedido(codigo));
        } else {
            mostrar("No fue posible entregar el pedido.");
        }
    }

    public static void cancelarPedido(Restaurante restaurante) {

        String codigo = leerTexto("Ingrese el código del pedido:");

        if (codigo == null) {
            return;
        }

        Pedido pedido = restaurante.buscarPedido(codigo);

        if (pedido == null) {
            mostrar("No se encontró el pedido.");
            return;
        }

        if (pedido.getEstado() == EstadoPedido.ENTREGADO
                || pedido.getEstado() == EstadoPedido.CANCELADO) {

            mostrar("Este pedido no se puede cancelar.");
            return;
        }

        int respuesta = leerEntero(
                "CANCELAR PEDIDO"
                        + "\n\n¿Está seguro de cancelar el pedido?"
                        + "\n\n1. Sí"
                        + "\n2. No"
                        + "\n\nEscriba una opción:"
        );

        if (respuesta != 1) {
            return;
        }

        boolean cancelado = restaurante.cancelarPedido(codigo);

        if (cancelado) {
            mostrar("Pedido cancelado correctamente."
                    + "\nEl servicio de domicilio asociado también fue cancelado.");
        } else {
            mostrar("No fue posible cancelar el pedido.");
        }
    }

    public static void consultarPedidosCliente(Restaurante restaurante) {

        mostrarClientesDisponibles(restaurante);

        String documento = leerTexto(
                "CONSULTAR PEDIDOS POR CLIENTE"
                        + "\n\nEscriba el documento del cliente:"
        );

        if (documento == null) {
            return;
        }

        Cliente cliente = restaurante.buscarCliente(documento);

        if (cliente == null) {
            mostrar("No se encontró el cliente.");
            return;
        }

        List<Pedido> pedidos =
                restaurante.consultarPedidosCliente(documento);

        String informacion = "PEDIDOS DEL CLIENTE"
                + "\nNombre: " + cliente.getNombreCompleto()
                + "\nDocumento: " + cliente.getDocumento()
                + "\n\n";

        if (pedidos.isEmpty()) {
            informacion += "El cliente no tiene pedidos.";
        } else {
            for (Pedido pedido : pedidos) {
                informacion += pedido.consultarInformacion() + "\n\n";
            }
        }

        mostrar(informacion);
    }

    public static void consultarPedidosPorEstado(Restaurante restaurante) {

        int opcion = leerEntero(
                "CONSULTAR PEDIDOS POR ESTADO"
                        + "\n\n1. Pendiente"
                        + "\n2. Confirmado"
                        + "\n3. Entregado"
                        + "\n4. Cancelado"
                        + "\n\nEscriba una opción:"
        );

        if (opcion < 1 || opcion > 4) {
            return;
        }

        EstadoPedido estado = null;

        if (opcion == 1) {
            estado = EstadoPedido.PENDIENTE;
        } else if (opcion == 2) {
            estado = EstadoPedido.CONFIRMADO;
        } else if (opcion == 3) {
            estado = EstadoPedido.ENTREGADO;
        } else if (opcion == 4) {
            estado = EstadoPedido.CANCELADO;
        }

        List<Pedido> pedidos =
                restaurante.consultarPedidosPorEstado(estado);

        String informacion = "PEDIDOS CON ESTADO " + estado + "\n\n";

        if (pedidos.isEmpty()) {
            informacion = "No hay pedidos con ese estado.";
        } else {
            for (Pedido pedido : pedidos) {
                informacion += pedido.consultarInformacion() + "\n\n";
            }
        }

        mostrar(informacion);
    }

    // ==========================================
    // GESTIÓN DE DOMICILIOS
    // ==========================================

    public static void menuDomicilios(Restaurante restaurante) {

        int opcion = 0;

        while (opcion != 7) {

            String menu = "GESTIÓN DE DOMICILIOS"
                    + "\n\n1. Consultar domicilios de RapidoExpress"
                    + "\n2. Consultar domicilios de EntregaSegura"
                    + "\n3. Consultar domicilio de un pedido"
                    + "\n4. Cancelar domicilio"
                    + "\n5. Consultar estado de las empresas"
                    + "\n6. Entregar pedido a domicilio"
                    + "\n7. Volver";

            opcion = leerEntero(menu);

            switch (opcion) {
                case 1:
                    consultarDomiciliosPorEmpresa(restaurante, "RapidoExpress");
                    break;
                case 2:
                    consultarDomiciliosPorEmpresa(restaurante, "EntregaSegura");
                    break;
                case 3:
                    consultarDomicilio(restaurante);
                    break;
                case 4:
                    cancelarDomicilio(restaurante);
                    break;
                case 5:
                    consultarEstadoEmpresas(restaurante);
                    break;
                case 6:
                    entregarPedidoDomicilio(restaurante);
                    break;
                case 7:
                    break;
                default:
                    mostrar("Opción no válida.");
                    break;
            }
        }
    }

    public static boolean registrarDomicilio(
            Restaurante restaurante, String codigoPedido) {

        int opcionEmpresa = leerEntero(
                "EMPRESA DE DOMICILIO"
                        + "\n\n1. RapidoExpress(cubre entre 1 y 12km)"
                        + "\n2. EntregaSegura(cubre entre 1 y 20km)"
                        + "\n\nEscriba una opción:"
        );

        if (opcionEmpresa != 1 && opcionEmpresa != 2) {
            return false;
        }

        EmpresaDomicilio empresa = null;

        if (opcionEmpresa == 1) {
            empresa = new RapidoExpress();
        } else {
            empresa = new EntregaSegura();
        }

        double distancia = leerDecimal(
                "DISTANCIA DEL DOMICILIO"
                        + "\n\nIngrese la distancia en kilómetros."
                        + "\nDebe ser mayor que cero:"
        );

        if (distancia <= 0 || distancia == -999) {
            mostrar("La distancia debe ser mayor que cero.");
            return false;
        }

        String direccion = leerTexto(
                "DIRECCIÓN DE ENTREGA"
                        + "\n\nIngrese la dirección completa:"
        );

        if (direccion == null) {
            return false;
        }

        boolean registrado = false;

        if (empresa.aceptarServicio(distancia)) {

            double tarifa = empresa.calcularTarifa(distancia);

            int confirmacion = leerEntero(
                    "INFORMACIÓN DEL DOMICILIO"
                            + "\n\nEmpresa: " + empresa.getNombreEmpresa()
                            + "\nDistancia: " + distancia + " km"
                            + "\nDirección: " + direccion
                            + "\nTarifa: $" + tarifa
                            + "\n\n1. Registrar domicilio"
                            + "\n2. Cancelar"
                            + "\n\nEscriba una opción:"
            );

            if (confirmacion != 1) {
                return false;
            }

            registrado = restaurante.registrarDomicilio(
                    codigoPedido, empresa, distancia, direccion
            );

            if (registrado) {
                mostrar("Domicilio registrado correctamente."
                        + "\nEmpresa: " + empresa.getNombreEmpresa()
                        + "\nTarifa: $" + tarifa);
            } else {
                mostrar("No fue posible registrar el domicilio.");
            }

        } else {
            mostrar("La empresa seleccionada no acepta esa distancia.");
        }

        return registrado;
    }

    // CORRECCIÓN: NORMALIZAR NOMBRES DE EMPRESAS

    public static String normalizarNombreEmpresa(String nombre) {

        nombre = nombre.replace(" ", "");

        nombre = nombre.replace("á", "a");
        nombre = nombre.replace("é", "e");
        nombre = nombre.replace("í", "i");
        nombre = nombre.replace("ó", "o");
        nombre = nombre.replace("ú", "u");

        nombre = nombre.replace("Á", "A");
        nombre = nombre.replace("É", "E");
        nombre = nombre.replace("Í", "I");
        nombre = nombre.replace("Ó", "O");
        nombre = nombre.replace("Ú", "U");

        return nombre;
    }

    // CORRECCIÓN: CONSULTAR DOMICILIOS POR EMPRESA

    public static void consultarDomiciliosPorEmpresa(
            Restaurante restaurante, String nombreEmpresa) {

        List<Pedido> pedidos = restaurante.consultarPedidos();

        String informacion = "DOMICILIOS DE " + nombreEmpresa + "\n\n";

        int cantidad = 0;

        for (Pedido pedido : pedidos) {

            if (pedido.getModalidadEntrega() == ModalidadEntrega.DOMICILIO) {

                ServicioDomicilio servicio = pedido.getServicioDomicilio();

                if (servicio != null
                        && servicio.getEmpresaDomicilio() != null) {

                    String empresa = servicio.getEmpresaDomicilio()
                            .getNombreEmpresa();

                    String empresaNormalizada =
                            normalizarNombreEmpresa(empresa);

                    String nombreNormalizado =
                            normalizarNombreEmpresa(nombreEmpresa);

                    if (empresaNormalizada.equalsIgnoreCase(nombreNormalizado)) {

                        informacion += pedido.consultarInformacion()
                                + "\nEstado del servicio: ";

                        if (servicio.isServicioActivo()) {
                            informacion += "ACTIVO\n\n";
                        } else {
                            informacion += "CANCELADO\n\n";
                        }

                        cantidad++;
                    }
                }
            }
        }

        if (cantidad == 0) {
            informacion = "No hay domicilios registrados para "
                    + nombreEmpresa + ".";
        } else {
            informacion += "\nCantidad de domicilios: " + cantidad;
        }

        mostrar(informacion);
    }

    public static void consultarDomicilio(Restaurante restaurante) {

        String codigo = leerTexto("Ingrese el código del pedido:");

        if (codigo == null) {
            return;
        }

        mostrar(restaurante.consultarDomicilio(codigo));
    }

    public static void cancelarDomicilio(Restaurante restaurante) {

        String codigo = leerTexto("Ingrese el código del pedido:");

        if (codigo == null) {
            return;
        }

        Pedido pedido = restaurante.buscarPedido(codigo);

        if (pedido == null) {
            mostrar("No se encontró el pedido.");
            return;
        }

        if (pedido.getModalidadEntrega() != ModalidadEntrega.DOMICILIO) {
            mostrar("Este pedido no tiene modalidad de domicilio.");
            return;
        }

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            mostrar("Solo se puede cancelar el domicilio de un pedido pendiente.");
            return;
        }

        boolean cancelado = restaurante.cancelarDomicilio(codigo);

        if (cancelado) {
            mostrar("Domicilio cancelado correctamente."
                    + "\nLa tarifa del domicilio ya no se suma al total del pedido.");
        } else {
            mostrar("No fue posible cancelar el domicilio."
                    + "\nPuede que no exista o ya esté inactivo.");
        }
    }

    // CORRECCIÓN: CONTAR DOMICILIOS POR EMPRESA

    public static void consultarEstadoEmpresas(Restaurante restaurante) {

        List<Pedido> pedidos = restaurante.consultarPedidos();

        int totalRapido = 0;
        int activosRapido = 0;

        int totalSeguro = 0;
        int activosSeguro = 0;

        for (Pedido pedido : pedidos) {

            if (pedido.getModalidadEntrega() == ModalidadEntrega.DOMICILIO) {

                ServicioDomicilio servicio = pedido.getServicioDomicilio();

                if (servicio != null
                        && servicio.getEmpresaDomicilio() != null) {

                    String empresa = normalizarNombreEmpresa(
                            servicio.getEmpresaDomicilio().getNombreEmpresa()
                    );

                    if (empresa.equalsIgnoreCase("RapidoExpress")) {

                        totalRapido++;

                        if (servicio.isServicioActivo()) {
                            activosRapido++;
                        }

                    } else if (empresa.equalsIgnoreCase("EntregaSegura")) {

                        totalSeguro++;

                        if (servicio.isServicioActivo()) {
                            activosSeguro++;
                        }
                    }
                }
            }
        }

        int canceladosRapido = totalRapido - activosRapido;
        int canceladosSeguro = totalSeguro - activosSeguro;

        String informacion = "ESTADO DE LAS EMPRESAS DE DOMICILIO"

                + "\n\nRAPIDOEXPRESS"
                + "\nTotal de domicilios: " + totalRapido
                + "\nServicios activos: " + activosRapido
                + "\nServicios cancelados: " + canceladosRapido

                + "\n\nENTREGASEGURA"
                + "\nTotal de domicilios: " + totalSeguro
                + "\nServicios activos: " + activosSeguro
                + "\nServicios cancelados: " + canceladosSeguro;

        mostrar(informacion);
    }

    public static void entregarPedidoDomicilio(Restaurante restaurante) {

        String codigo = leerTexto(
                "ENTREGA DE PEDIDO A DOMICILIO"
                        + "\n\nIngrese el código del pedido:"
        );

        if (codigo == null) {
            return;
        }

        Pedido pedido = restaurante.buscarPedido(codigo);

        if (pedido == null) {
            mostrar("No se encontró el pedido.");
            return;
        }

        if (pedido.getModalidadEntrega() != ModalidadEntrega.DOMICILIO) {
            mostrar("Este pedido no tiene modalidad de domicilio."
                    + "\nDebe entregarlo desde Gestión de pedidos.");
            return;
        }

        if (pedido.getEstado() != EstadoPedido.CONFIRMADO) {
            mostrar("Solo se pueden entregar pedidos confirmados.");
            return;
        }

        ServicioDomicilio servicio = pedido.getServicioDomicilio();

        if (servicio == null || !servicio.isServicioActivo()) {
            mostrar("El pedido no tiene un servicio de domicilio activo.");
            return;
        }

        int respuesta = leerEntero(
                "INFORMACIÓN DE ENTREGA"
                        + "\n\nEmpresa: "
                        + servicio.getEmpresaDomicilio().getNombreEmpresa()
                        + "\nDirección: " + servicio.getDireccionEntrega()
                        + "\n\n1. Confirmar entrega"
                        + "\n2. Cancelar"
                        + "\n\nEscriba una opción:"
        );

        if (respuesta != 1) {
            return;
        }

        boolean entregado = restaurante.entregarPedido(codigo);

        if (entregado) {
            mostrar("PEDIDO ENTREGADO A DOMICILIO"
                    + "\n\nEmpresa: "
                    + servicio.getEmpresaDomicilio().getNombreEmpresa()
                    + "\nDirección: " + servicio.getDireccionEntrega()
                    + "\n\n" + restaurante.consultarInformacionPedido(codigo));
        } else {
            mostrar("No fue posible entregar el pedido.");
        }
    }

    // ==========================================
    // REPORTES
    // ==========================================

    public static void menuReportes(Restaurante restaurante) {

        int opcion = 0;

        while (opcion != 5) {

            String menu = "REPORTES DE VENTAS E INGRESOS"
                    + "\n\n1. Reporte general del restaurante"
                    + "\n2. Consultar ingresos por categoría"
                    + "\n3. Consultar producto más vendido"
                    + "\n4. Consultar categoría con mayores ingresos"
                    + "\n5. Volver";

            opcion = leerEntero(menu);

            switch (opcion) {
                case 1:
                    mostrar(restaurante.generarReporteGeneral());
                    break;
                case 2:
                    mostrar(restaurante.consultarIngresosPorCategoria());
                    break;
                case 3:
                    mostrar("PRODUCTO MÁS VENDIDO\n\n"
                            + restaurante.consultarProductoMasVendido());
                    break;
                case 4:
                    mostrar("CATEGORÍA CON MAYORES INGRESOS\n\n"
                            + restaurante.consultarCategoriaMayorIngreso());
                    break;
                case 5:
                    break;
                default:
                    mostrar("Opción no válida.");
                    break;
            }
        }
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================

    public static String leerTexto(String mensaje) {

        String texto = JOptionPane.showInputDialog(mensaje);

        if (texto != null && texto.equals("")) {
            mostrar("El campo no puede estar vacío.");
            texto = null;
        }

        return texto;
    }

    public static int leerEntero(String mensaje) {

        int numero = -1;

        String texto = JOptionPane.showInputDialog(mensaje);

        if (texto != null) {

            try {
                numero = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                mostrar("Debe ingresar un número entero.");
                numero = -1;
            }
        }

        return numero;
    }

    public static double leerDecimal(String mensaje) {

        double numero = -999;

        String texto = JOptionPane.showInputDialog(mensaje);

        if (texto != null) {

            try {
                texto = texto.replace(",", ".");
                numero = Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                mostrar("Debe ingresar un número válido.");
                numero = -999;
            }
        }

        return numero;
    }

    public static void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}