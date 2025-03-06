package com.filesToPdf;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ChartToReplace {
    public static void main(String[] args) {
        try {
            String fullName = "Ander";

            // Rutas de los archivos
            String jrxmlPath = "src/main/resources/templates/chartToReplace.jrxml";

            //Aldagaia zerrendan sartu. Gehiago badaude, gehiago gehitu beharko dira.
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fullName", fullName);

            //Zerrenda hartu DBtik
            List<Producto> listaDeProductos = Producto.loadProductList();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDeProductos);
            parameters.put("productDataset", dataSource);

            List<ProductChart> productListForChart = ProductChart.loadProductList();
            JRBeanCollectionDataSource dataSourceForChart = new JRBeanCollectionDataSource(productListForChart);
            parameters.put("chartParamDataset", dataSourceForChart);

            //Konpilatu .jrxml .jasper fitxategi batera
            String jasperPath = "src/main/resources/templates/chartToReplace.jasper";
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(jasperPath));

            // Report-a osatu (DBko konexiorik gabe, soilik parametroak)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            String home = System.getProperty("user.home");
            String outputPath = home + "/Desktop/chartToReplaceReport.pdf";

            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

            System.out.println("Reporte generado en: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
