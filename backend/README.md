# Backend Java — MR YISSTIV cholados

Backend inicial en **Java 17 + JDBC**, siguiendo el patrón **MVC** y
**Repository/DAO** descritos en la evidencia GA7-220501096-AA2-EV01.

## Estructura

```
backend/
  modelo/     -> 14 clases entidad (Usuario, Producto, Pedido, etc.)
  dao/        -> 14 interfaces (contrato CRUD de cada entidad)
  dao/impl/   -> 14 implementaciones JDBC con PreparedStatement
  utilidad/   -> Conexion.java (abre la conexión a MySQL)
  lib/        -> aquí debes copiar mysql-connector-java-8.0.33.jar
database/
  mryisstiv_db.sql -> script con las 14 tablas normalizadas + datos semilla
```

## Puesta en marcha (IntelliJ IDEA)

1. **Base de datos**: instala MySQL, abre tu cliente (Workbench / consola) y ejecuta
   `database/mryisstiv_db.sql`. Esto crea la base `mryisstiv_db` con las 14 tablas
   y 3 usuarios de ejemplo (admin, trabajador, domiciliario) + un catálogo inicial.

2. **Driver JDBC**: descarga `mysql-connector-j` (versión 8.0.33 o compatible) desde
   el sitio oficial de MySQL y colócalo en `backend/lib/`. Luego agrégalo al proyecto:
   *File > Project Structure > Libraries > + > Java* y selecciona el `.jar`.

3. **Credenciales**: abre `backend/utilidad/Conexion.java` y ajusta `USUARIO` y
   `CONTRASENA` según tu instalación local de MySQL (por defecto usa `root` sin contraseña).

4. **Probar la conexión**: puedes crear una clase `Main.java` temporal:

   ```java
   import dao.impl.ProductoDAOImpl;
   import modelo.Producto;

   public class Main {
       public static void main(String[] args) throws Exception {
           ProductoDAOImpl dao = new ProductoDAOImpl();
           for (Producto p : dao.consultarTodos()) {
               System.out.println(p.getNombre() + " - $" + p.getPrecio() + " (stock: " + p.getStock() + ")");
           }
       }
   }
   ```

## Qué cubre cada entidad (mapeo con las Historias de Usuario)

| Entidad | HU relacionada |
|---|---|
| Usuario, Rol | HU-01, HU-02, HU-13 |
| Producto, Categoria | HU-03, HU-04, HU-11, HU-12, HU-15 |
| Carrito, DetalleCarrito | HU-05 |
| Pedido, DetallePedido | HU-06, HU-14 |
| MetodoPago | HU-07 (patrón Strategy) |
| Reserva | HU-08 |
| Favorito | HU-04, HU-09 |
| Direccion | HU-10 |
| Domicilio | HU-17, HU-18 |
| ErrorLog | HU-19 |

## Patrón Strategy (métodos de pago)

El diagrama de clases modela `MetodoPago` como una entidad persistente
(qué método se usó en cada pedido). El patrón Strategy en sí — el
**comportamiento** distinto de cada método de pago — se implementa como una
interfaz de servicio, por ejemplo:

```java
public interface EstrategiaPago {
    boolean procesar(double monto);
}

public class PagoEfectivo implements EstrategiaPago {
    public boolean procesar(double monto) { return true; } // se paga contra entrega
}

public class PagoNequi implements EstrategiaPago {
    public boolean procesar(double monto) { /* integración con la pasarela */ return true; }
}
```

El `Pedido` (o un `ServicioPago`) recibe la `EstrategiaPago` elegida y la
ejecuta sin conocer los detalles de cada método — así se pueden agregar
nuevos métodos de pago sin modificar el núcleo del sistema, tal como pide
el informe técnico.

## Siguientes pasos sugeridos (Semana 2 en adelante, según el cronograma del documento)

- Crear una capa de **servicio** que use los DAO desde una API REST (Spring Boot
  es la opción más rápida) para que el frontend actual (`frontend/`, hoy en
  localStorage) pueda consumir el backend real vía `fetch`.
- Sustituir las contraseñas en texto plano por hash (BCrypt) antes de producción.
- Agregar pruebas unitarias con JUnit 5 para cada `*DAOImpl` (como indica el
  informe técnico) usando una base de datos de pruebas o un contenedor Docker de MySQL.
