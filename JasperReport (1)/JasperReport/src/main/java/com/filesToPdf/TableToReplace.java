package com.filesToPdf;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TableToReplace {
    public static void main(String[] args) {
        try {
            // Pedir el nombre del usuario por Scanner
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce tu nombre: ");
            String fullName = scanner.nextLine();
            scanner.close();

            // Rutas de los archivos
            String jrxmlPath = "src/main/resources/templates/tableToReplace.jrxml";

            //Aldagaia zerrendan sartu. Gehiago badaude, gehiago gehitu beharko dira.
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fullName", fullName);

            //Zerrenda hartu DBtik
            List<Producto> listaDeProductos = Producto.loadProductList();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDeProductos);
            parameters.put("productDataset", dataSource);


            //Konpilatu .jrxml .jasper fitxategi batera
            String jasperPath = "src/main/resources/templates/tableToReplace.jasper";
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(jasperPath));

            // Report-a osatu (DBko konexiorik gabe, soilik parametroak)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            String home = System.getProperty("user.home");
            String outputPath = home + "/Desktop/tableToReplaceReport.pdf";

            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

            System.out.println("Reporte generado en: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
