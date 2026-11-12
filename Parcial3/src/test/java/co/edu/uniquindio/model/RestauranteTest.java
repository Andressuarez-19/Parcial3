
package co.edu.uniquindio.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteTest {

    @Test
    void probarCrearPedido() {

        Restaurante restaurante =
                new Restaurante("El Buen Comer", "123", "Armenia");

        Cliente cliente = new Cliente(
                "109490001",
                "Raul",
                "3001234567",
                "raul@gmail.com",
                "Calle 10"
        );

        assertTrue(restaurante.registrarCliente(cliente));

        Pedido pedido = restaurante.crearPedido(
                "P001",
                "109490001"
        );

        assertNotNull(pedido);
        assertEquals("P001", pedido.getCodigo());
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
    }

    @Test
    void probarRegistrarDomicilio() {

        Restaurante restaurante =
                new Restaurante("El Buen Comer", "123", "Armenia");

        Cliente cliente = new Cliente(
                "109490001",
                "Raul",
                "3001234567",
                "raul@gmail.com",
                "Calle 10"
        );

        assertTrue(restaurante.registrarCliente(cliente));

        Pedido pedido = restaurante.crearPedido(
                "P002",
                "109490001",
                ModalidadEntrega.DOMICILIO
        );

        assertNotNull(pedido);

        boolean resultado = restaurante.registrarDomicilio(
                "P002",
                new EntregaSegura(),
                4,
                "Calle 10 # 15-20"
        );

        assertTrue(resultado);
        assertNotNull(pedido.getServicioDomicilio());

        assertTrue(
                pedido.getServicioDomicilio().isServicioActivo()
        );

        assertEquals(
                11000.0,
                pedido.getServicioDomicilio().getTarifa(),
                0.01
        );
    }

    @Test
    void probarGenerarResumenVentas() {

        Restaurante restaurante =
                new Restaurante("El Buen Comer", "123", "Armenia");

        Cliente cliente = new Cliente(
                "109490001",
                "Raul",
                "3001234567",
                "raul@gmail.com",
                "Calle 10"
        );

        assertTrue(restaurante.registrarCliente(cliente));

        Bebida bebida = new Bebida(
                "B001",
                "Gaseosa",
                "Bebida fria",
                10000,
                5,
                500,
                5,
                TipoTemperatura.FRIA,
                false
        );

        assertTrue(restaurante.registrarProducto(bebida));

        Pedido pedido = restaurante.crearPedido(
                "P003",
                "109490001"
        );

        assertNotNull(pedido);

        assertTrue(
                restaurante.agregarProductoPedido("P003", "B001", 2)
        );

        assertTrue(restaurante.confirmarPedido("P003"));
        assertTrue(restaurante.entregarPedido("P003"));

        ResumenVentas resumen = restaurante.generarResumenVentas();

        assertNotNull(resumen);

        assertEquals(22000.0, resumen.ingresosBebidas(), 0.01);
        assertEquals("Gaseosa", resumen.productoMasVendido());
        assertEquals("Bebidas", resumen.categoriaMayorIngreso());
    }
}
