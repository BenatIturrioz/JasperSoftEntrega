package com.filesToPdf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductChart {

    public String marca;
    public Integer ekipoKop;

    public ProductChart(String marca, Integer ekipoKop) {
        this.marca = marca;
        this.ekipoKop = ekipoKop;
    }

    public static List<ProductChart> loadProductList() {

        List<ProductChart> chartData = new ArrayList<>();

        try (
            Connection connection = DBConnection.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT marca, COUNT(id) AS ekipoKop FROM ordenadores GROUP BY marca")) {

            while (resultSet.next()) {
                String marca = resultSet.getString("marca");
                int ekipoKop = resultSet.getInt("ekipoKop");
                chartData.add(new ProductChart(marca, ekipoKop));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chartData;

    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getEkipoKop() {
        return ekipoKop;
    }

    public void setEkipoKop(Integer ekipoKop) {
        this.ekipoKop = ekipoKop;
    }


}

