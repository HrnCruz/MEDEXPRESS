package com.example.medexexpress.Model;

public class Category {
    private String Name;
    private String Imagen;

    public Category() {
    }

    public Category(String name, String imagen) {
        Name = name;
        Imagen = imagen;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
