package com.filesToPdf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Producto {
    private String nombre;
    private String marca;
    private float precio;
    private String tipo;

    public Producto(String nombre, String marca, float precio, String tipo) {
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", precio=" + precio +
                ", tipo='" + tipo + '\'' +
                '}';
    }


    public static List<Producto> loadProductList() {

        List<Producto> productos = new ArrayList<>();

        try (
                Connection connection = DBConnection.connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT nombre, marca, precio, tipo FROM ordenadores")) {

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String marca = resultSet.getString("marca");
                float precio = resultSet.getFloat("precio");
                String tipo = resultSet.getString("tipo");

                productos.add(new Producto(nombre, marca, precio, tipo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;

    }
}

