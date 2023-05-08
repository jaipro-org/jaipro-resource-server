package com.bindord.jaipro.resourceserver.utils;

public class Constants {

    public static final String RESOURCE_NOT_FOUND = "Resource not found";

    //Path Save Images in Storage
    public static final String CUSTOMER_PHOTO_PATH = "jaipro/customers/[FILENAME].[EXTENSION]";
    public static final String SPECIALIST_PHOTO_PATH = "jaipro/specialist/[ID]/[FILENAME].[EXTENSION]";
    public static final String SPECIALIST_GALLERY_PATH = "jaipro/specialist/[ID]/gallery/[FILENAME]";
    
    public static final Integer MAX_GALLERY_FILES = 6;

    //Errors message
    public static final String ERROR_EXPERIENCE_REPEATED = "Profesion ya registrada como experiencia";
    public static final String ERROR_BANK_ACCOUNT_REMOVED_PREFERED = "No se puede eliminar una cuenta bancaria preferida";
    public static final String ERROR_USER_RECOVER_TICKET_NOT_FOUND = "Ticket no valido para iniciar el proceso de actualizacion de password";
    public static final String ERROR_USER_RECOVER_TICKET_EXPIRED_OR_USED = "Ticket expirado para iniciar el proceso de actualizacion de password o ya utilizado";

    public static final String DOMAIN_FRONTEND = "http://pure-anthem-382602.uc.r.appspot.com";
}
