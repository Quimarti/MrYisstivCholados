-- ============================================================
-- MR YISSTIV cholados — Base de datos relacional (MySQL 8+)
-- 14 entidades normalizadas, alineadas con el diagrama de clases
-- y las 19 Historias de Usuario de la evidencia GA7-220501096-AA2-EV01.
-- ============================================================

DROP DATABASE IF EXISTS mryisstiv_db;
CREATE DATABASE mryisstiv_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mryisstiv_db;

-- ---------------------------------------------------
-- 1. ROL
-- ---------------------------------------------------
CREATE TABLE rol (
    id_rol      INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol  VARCHAR(30) NOT NULL UNIQUE,
    descripcion VARCHAR(150)
);

-- ---------------------------------------------------
-- 2. USUARIO (HU-01, HU-02, HU-13)
-- ---------------------------------------------------
CREATE TABLE usuario (
    id_usuario       INT AUTO_INCREMENT PRIMARY KEY,
    id_rol           INT NOT NULL,
    nombres          VARCHAR(80) NOT NULL,
    apellidos        VARCHAR(80) NOT NULL,
    cedula           VARCHAR(20) NOT NULL UNIQUE,
    fecha_nacimiento DATE,
    correo           VARCHAR(120) NOT NULL UNIQUE,
    contrasena       VARCHAR(255) NOT NULL,
    activo           BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- ---------------------------------------------------
-- 3. CATEGORIA (HU-03, HU-12)
-- ---------------------------------------------------
CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(60) NOT NULL UNIQUE,
    descripcion  VARCHAR(200)
);

-- ---------------------------------------------------
-- 4. PRODUCTO (HU-03, HU-04, HU-11, HU-15)
-- ---------------------------------------------------
CREATE TABLE producto (
    id_producto  INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT NOT NULL,
    nombre       VARCHAR(100) NOT NULL,
    descripcion  VARCHAR(255),
    precio       DECIMAL(10,2) NOT NULL,
    imagen       VARCHAR(255),
    es_favorito  BOOLEAN NOT NULL DEFAULT FALSE,
    stock        INT NOT NULL DEFAULT 50,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

-- ---------------------------------------------------
-- 5. DIRECCION (HU-10)
-- ---------------------------------------------------
CREATE TABLE direccion (
    id_direccion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario   INT NOT NULL,
    direccion    VARCHAR(200) NOT NULL,
    ciudad       VARCHAR(80),
    telefono     VARCHAR(20),
    es_principal BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_direccion_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- ---------------------------------------------------
-- 6. CARRITO (HU-05)
-- ---------------------------------------------------
CREATE TABLE carrito (
    id_carrito INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado     VARCHAR(20) NOT NULL DEFAULT 'Activo', -- Activo, Convertido, Vaciado
    CONSTRAINT fk_carrito_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- ---------------------------------------------------
-- 7. DETALLE_CARRITO (HU-05)
-- ---------------------------------------------------
CREATE TABLE detalle_carrito (
    id_detalle_carrito INT AUTO_INCREMENT PRIMARY KEY,
    id_carrito          INT NOT NULL,
    id_producto          INT NOT NULL,
    cantidad             INT NOT NULL DEFAULT 1,
    precio_unit          DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detcarrito_carrito FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito) ON DELETE CASCADE,
    CONSTRAINT fk_detcarrito_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- ---------------------------------------------------
-- 8. DIRECCION ya creada arriba — PEDIDO (HU-06, HU-07, HU-14)
-- ---------------------------------------------------
CREATE TABLE pedido (
    id_pedido    INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario   INT NOT NULL,
    id_direccion INT NOT NULL,
    fecha        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    subtotal     DECIMAL(10,2) NOT NULL,
    total        DECIMAL(10,2) NOT NULL,
    estado       VARCHAR(20) NOT NULL DEFAULT 'Confirmado', -- Confirmado, Preparando, Listo, En camino, Entregado, Cancelado
    observaciones VARCHAR(255),
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_pedido_direccion FOREIGN KEY (id_direccion) REFERENCES direccion(id_direccion)
);

-- ---------------------------------------------------
-- 9. DETALLE_PEDIDO (HU-06)
-- ---------------------------------------------------
CREATE TABLE detalle_pedido (
    id_detalle_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido         INT NOT NULL,
    id_producto       INT NOT NULL,
    cantidad          INT NOT NULL,
    precio_unit       DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detpedido_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE,
    CONSTRAINT fk_detpedido_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- ---------------------------------------------------
-- 10. METODO_PAGO (HU-07 — patrón Strategy)
-- ---------------------------------------------------
CREATE TABLE metodo_pago (
    id_metodo_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido      INT NOT NULL,
    tipo_tarjeta   VARCHAR(30) NOT NULL, -- PagoEfectivo, PagoTarjeta, PagoNequi, PagoDaviplata
    CONSTRAINT fk_metpago_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE
);

-- ---------------------------------------------------
-- 11. RESERVA (HU-08)
-- ---------------------------------------------------
CREATE TABLE reserva (
    id_reserva    INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario    INT NOT NULL,
    fecha         DATE NOT NULL,
    hora          VARCHAR(10) NOT NULL,
    personas      INT NOT NULL,
    estado        VARCHAR(20) NOT NULL DEFAULT 'Pendiente', -- Pendiente, Confirmada, Cancelada
    observaciones VARCHAR(255),
    CONSTRAINT fk_reserva_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- ---------------------------------------------------
-- 12. DOMICILIO (HU-17, HU-18)
-- ---------------------------------------------------
CREATE TABLE domicilio (
    id_domicilio  INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido     INT NOT NULL,
    domiciliario  VARCHAR(20), -- cédula del usuario con rol domiciliario
    estado        VARCHAR(20) NOT NULL DEFAULT 'Asignado', -- Asignado, En camino, Entregado
    fecha_entrega DATETIME,
    propina       DECIMAL(10,2) DEFAULT 0,
    CONSTRAINT fk_domicilio_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE
);

-- ---------------------------------------------------
-- 13. FAVORITO (HU-04, HU-09)
-- ---------------------------------------------------
CREATE TABLE favorito (
    id_favorito    INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario     INT NOT NULL,
    id_producto    INT NOT NULL,
    fecha_agregado DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    semana         VARCHAR(10), -- ej. "2026-W38" cuando aplica como "favorito de la semana"
    CONSTRAINT fk_favorito_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_favorito_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE CASCADE,
    UNIQUE KEY uk_favorito_usuario_producto (id_usuario, id_producto)
);

-- ---------------------------------------------------
-- 14. ERROR_LOG (HU-19)
-- ---------------------------------------------------
CREATE TABLE error_log (
    id_error   INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NULL,
    fecha      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_error VARCHAR(50),
    mensaje    VARCHAR(255) NOT NULL,
    pantalla   VARCHAR(100),
    CONSTRAINT fk_errorlog_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE SET NULL
);

-- ============================================================
-- DATOS SEMILLA
-- ============================================================

INSERT INTO rol (nombre_rol, descripcion) VALUES
    ('cliente', 'Compra productos, hace reservas y gestiona su perfil'),
    ('trabajador', 'Gestiona pedidos, sabores, inventario y precios'),
    ('domiciliario', 'Entrega los pedidos asignados'),
    ('admin', 'Control total del sistema: usuarios, productos, reportes');

-- Contraseñas de ejemplo (en producción deben ir con hash, ej. BCrypt)
INSERT INTO usuario (id_rol, nombres, apellidos, cedula, fecha_nacimiento, correo, contrasena, activo) VALUES
    ((SELECT id_rol FROM rol WHERE nombre_rol='admin'), 'Mr', 'Yisstiv', 'admin', '1995-01-01', 'admin@mryisstiv.com', 'Admin1234', TRUE),
    ((SELECT id_rol FROM rol WHERE nombre_rol='trabajador'), 'Diego', 'Ramírez', 'trabajador', '1998-03-15', 'trabajador@mryisstiv.com', 'Trabajo1234', TRUE),
    ((SELECT id_rol FROM rol WHERE nombre_rol='domiciliario'), 'Julián', 'Torres', 'domiciliario', '2000-06-20', 'domiciliario@mryisstiv.com', 'Domicilio1234', TRUE);

INSERT INTO categoria (nombre, descripcion) VALUES
    ('cholados', 'Cholados tradicionales con fruta natural'),
    ('helados', 'Helados artesanales de distintos sabores'),
    ('postres', 'Postres para compartir'),
    ('combos', 'Combos y promociones');

INSERT INTO producto (id_categoria, nombre, descripcion, precio, imagen, es_favorito, stock) VALUES
    ((SELECT id_categoria FROM categoria WHERE nombre='cholados'), 'Cholado Tradicional', 'Fruta de temporada, mucho sabor y el toque artesanal de siempre.', 10000, 'cholado-tradicional.png', TRUE, 50),
    ((SELECT id_categoria FROM categoria WHERE nombre='cholados'), 'Cholado Mora Azul', 'Mora azul con crema de leche y toppings.', 11000, 'cholado-mora.png', FALSE, 50),
    ((SELECT id_categoria FROM categoria WHERE nombre='helados'), 'Helado de Vainilla', 'Helado artesanal cremoso de vainilla.', 8000, 'helado-vainilla.png', FALSE, 50),
    ((SELECT id_categoria FROM categoria WHERE nombre='postres'), 'Postre de Fresa', 'Postre frío de fresa con crema.', 9000, 'postre-fresa.png', FALSE, 50);

-- ============================================================
-- Notas de conexión:
--   Usuario/contraseña por defecto en Conexion.java -> root / (vacío)
--   Ajusta USUARIO/CONTRASENA en backend/utilidad/Conexion.java según tu MySQL local.
-- ============================================================
