package com.bindord.jaipro.resourceserver.utils;

public class Constants {

    public static final String RESOURCE_NOT_FOUND = "Resource not found";

    //Path Save Images in Storage
    public static final String CUSTOMER_PHOTO_PATH = "jaipro/customers/[ID]/[FILENAME]";
    public static final String SPECIALIST_PHOTO_PATH = "jaipro/specialist/[ID]/[FILENAME]";
    public static final String SPECIALIST_GALLERY_PATH = "jaipro/specialist/[ID]/gallery/[FILENAME]";
    
    public static final Integer MAX_GALLERY_FILES = 6;

    //Errors message
    public static final String ERROR_EXPERIENCE_REPEATED = "Profesion ya registrada como experiencia";

}
