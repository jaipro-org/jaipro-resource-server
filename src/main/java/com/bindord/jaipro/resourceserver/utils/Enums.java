package com.bindord.jaipro.resourceserver.utils;

public class Enums {

    public enum ResponseCode {
        REGISTRO(-1),
        ACTUALIZACION(-2),
        ELIMINACION(-3),
        EXITO_GENERICA(-4),
        EX_VALIDATION_FAILED(-5),
        EMPTY_RESPONSE(-6),
        SESSION_VALUE_NOT_FOUND(-7),
        SUCCESS_QUERY(-8),
        EX_GENERIC(-9),
        EX_NULL_POINTER(-10),
        EX_JACKSON_INVALID_FORMAT(-11),
        NOT_FOUND_MATCHES(-12),
        EX_NUMBER_FORMAT(-99),
        EX_MAX_SIZE_MULTIPART(-100),
        EX_MAX_UPLOAD_SIZE(-101),
        EX_ARRAY_INDEX_OUT(-102),
        EX_SQL_EXCEPTION(-103),
        EX_SQL_GRAMMAR_EXCEPTION(-104),
        VF_USUARIO_REPETIDO(-150);

        final int code;

        ResponseCode(int code) {
            this.code = code;
        }

        public String get() {
            return String.valueOf(code);
        }
    }
    public enum TipoMedia {
        AUDIO(1),
        VIDEO(2),
        TEXTO(3);

        final int id;

        TipoMedia(int id) {
            this.id = id;
        }

        public int get() {
            return id;
        }

    }

    public enum Mail {
        INIT_CAMBIO_PASSWORD(1),
        FINAL_CAMBIO_PASSWORD(2),
        REGISTRO_VISITANTE_CONFIRMAR_CORREO(3);

        final int id;

        Mail(int id) {
            this.id = id;
        }

        public int get() {
            return id;
        }
    }
}
