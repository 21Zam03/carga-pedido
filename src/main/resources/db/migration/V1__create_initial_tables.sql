-- V1__create_tables.sql

-- Activar extensión para generar UUIDs
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ========================
-- Tabla: clientes
-- ========================
CREATE TABLE clientes (
    id VARCHAR PRIMARY KEY,
    activo BOOLEAN NOT NULL
);

-- ========================
-- Tabla: zonas
-- ========================
CREATE TABLE zonas (
    id VARCHAR PRIMARY KEY,
    soporte_refrigeracion BOOLEAN NOT NULL
);

-- ========================
-- Tabla: pedidos
-- ========================
CREATE TABLE pedidos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    numero_pedido VARCHAR NOT NULL UNIQUE,
    cliente_id VARCHAR NOT NULL,
    zona_id VARCHAR NOT NULL,
    fecha_entrega DATE NOT NULL,
    estado VARCHAR NOT NULL CHECK (estado IN ('PENDIENTE','CONFIRMADO','ENTREGADO')),
    requiere_refrigeracion BOOLEAN NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Índice compuesto sobre estado y fecha_entrega
CREATE INDEX idx_estado_fecha ON pedidos (estado, fecha_entrega);

-- ========================
-- Tabla: cargas_idempotencia
-- ========================
CREATE TABLE cargas_idempotencia (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    idempotency_key VARCHAR NOT NULL,
    archivo_hash VARCHAR NOT NULL,
    created_at TIMESTAMP,
    CONSTRAINT uk_idempotencia UNIQUE (idempotency_key, archivo_hash)
);