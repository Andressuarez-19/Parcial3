package co.edu.uniquindio.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    // PRUEBA 1: Agregar un detalle al pedido

    @Test
    void probarAgregarDetalle() {

        Cliente cliente = new Cliente(
                "109490001",
                "Raul",
                "3001234567",
                "raul@gmail.com",
                "Calle 10"
        );

        Pedido pedido = new Pedido(
                "P001",
                LocalDate.now(),
                cliente
        );

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

        boolean resultado = pedido.agregarDetalle(bebida, 2);

        assertTrue(resultado);
        assertEquals(1, pedido.getListaDetalles().size());
        assertEquals(22000.0, pedido.calcularValorTotal(), 0.01);
    }


    // PRUEBA 2: Confirmar un pedido

    @Test
    void probarConfirmarPedido() {

        Cliente cliente = new Cliente(
                "109490001",
                "Raul",
                "3001234567",
                "raul@gmail.com",
                "Calle 10"
        );

        Pedido pedido = new Pedido(
                "P002",
                LocalDate.now(),
                cliente
        );

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

        pedido.agregarDetalle(bebida, 2);

        boolean resultado = pedido.confirmarPedido();

        assertTrue(resultado);
        assertEquals(EstadoPedido.CONFIRMADO, pedido.getEstado());
    }


    // PRUEBA 3: Calcular el valor total

    @Test
    void probarCalcularValorTotal() {

        Cliente cliente = new Cliente(
                "109490001",
                "Raul",
                "3001234567",
                "raul@gmail.com",
                "Calle 10"
        );

        Pedido pedido = new Pedido(
                "P003",
                LocalDate.now(),
                cliente
        );

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

        pedido.agregarDetalle(bebida, 2);

        double resultado = pedido.calcularValorTotal();

        assertEquals(22000.0, resultado, 0.01);
    }
}
