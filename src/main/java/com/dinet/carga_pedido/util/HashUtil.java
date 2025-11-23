package com.dinet.carga_pedido.util;

import java.security.MessageDigest;

public class HashUtil {

    public static String calcularSHA256(byte[] archivoBytes) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hashBytes = digest.digest(archivoBytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}
