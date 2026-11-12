package co.edu.uniquindio;

import co.edu.uniquindio.model.*;

public class MainRecord {

    public static Restaurante crearRestaurante() {

        // CREAR RESTAURANTE

        Restaurante restaurante = new Restaurante(
                "El Buen Comer",
                "901234567-8",
                "Armenia, Quindío"
        );

        // REGISTRAR POSTRES

        Postre postre1 = new Postre(
                "P001",
                "Torta de chocolate",
                "Porción de torta de chocolate",
                10000,
                15,
                150,
                4,
                true
        );

        Postre postre2 = new Postre(
                "P002",
                "Cheesecake",
                "Porción de cheesecake",
                12000,
                10,
                180,
                5,
                true
        );

        restaurante.registrarProducto(postre1);
        restaurante.registrarProducto(postre2);

        // REGISTRAR CLIENTES

        Cliente cliente1 = new Cliente(
                "109490001",
                "Carlos Ramírez",
                "3101234567",
                "carlos@gmail.com",
                "Armenia, Quindío"
        );

        Cliente cliente2 = new Cliente(
                "109490002",
                "Laura Gómez",
                "3201234567",
                "laura@gmail.com",
                "Armenia, Quindío"
        );

        Cliente cliente3 = new Cliente(
                "109490003",
                "Andrés López",
                "3001234567",
                "andres@gmail.com",
                "Armenia, Quindío"
        );

        restaurante.registrarCliente(cliente1);
        restaurante.registrarCliente(cliente2);
        restaurante.registrarCliente(cliente3);

        return restaurante;
    }
}