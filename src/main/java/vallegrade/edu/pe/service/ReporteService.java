package vallegrade.edu.pe.service;

import vallegrade.edu.pe.model.Producto;

// Imports de iText para PDF
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// Imports de Apache POI para Word y Excel
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.Desktop;
import java.io.File;

public class ReporteService {

    private static final String RUTA_REPORTES = "reportes/";

    public ReporteService() {
        // Crear carpeta de reportes si no existe
        File carpeta = new File(RUTA_REPORTES);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    // ======================= GENERAR PDF =========================
    public boolean generarReportePDF(List<Producto> productos) {
        String nombreArchivo = RUTA_REPORTES + "Reporte_Productos_" + obtenerFechaHora() + ".pdf";

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            // Título
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
            Paragraph titulo = new Paragraph("REPORTE DE PRODUCTOS", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Fecha de generación
            Font fontFecha = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.GRAY);
            Paragraph fecha = new Paragraph("Fecha de generación: " + obtenerFechaHoraLegible(), fontFecha);
            fecha.setAlignment(Element.ALIGN_RIGHT);
            fecha.setSpacingAfter(20);
            document.add(fecha);

            // Crear tabla
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Encabezados de la tabla
            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);

            PdfPCell cellId = new PdfPCell(new Phrase("ID", fontHeader));
            PdfPCell cellNombre = new PdfPCell(new Phrase("Nombre", fontHeader));
            PdfPCell cellPrecio = new PdfPCell(new Phrase("Precio", fontHeader));
            PdfPCell cellStock = new PdfPCell(new Phrase("Stock", fontHeader));

            // Estilo de encabezados
            cellId.setBackgroundColor(BaseColor.DARK_GRAY);
            cellNombre.setBackgroundColor(BaseColor.DARK_GRAY);
            cellPrecio.setBackgroundColor(BaseColor.DARK_GRAY);
            cellStock.setBackgroundColor(BaseColor.DARK_GRAY);

            cellId.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPrecio.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellStock.setHorizontalAlignment(Element.ALIGN_CENTER);

            cellId.setPadding(8);
            cellNombre.setPadding(8);
            cellPrecio.setPadding(8);
            cellStock.setPadding(8);

            table.addCell(cellId);
            table.addCell(cellNombre);
            table.addCell(cellPrecio);
            table.addCell(cellStock);

            // Agregar datos
            Font fontData = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
            for (Producto p : productos) {
                PdfPCell celdaId = new PdfPCell(new Phrase(String.valueOf(p.getId()), fontData));
                PdfPCell celdaNombre = new PdfPCell(new Phrase(p.getNombre(), fontData));
                PdfPCell celdaPrecio = new PdfPCell(new Phrase(String.format("%.2f", p.getPrecio()), fontData));
                PdfPCell celdaStock = new PdfPCell(new Phrase(String.valueOf(p.getStock()), fontData));

                celdaId.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaPrecio.setHorizontalAlignment(Element.ALIGN_RIGHT);
                celdaStock.setHorizontalAlignment(Element.ALIGN_CENTER);

                celdaId.setPadding(5);
                celdaNombre.setPadding(5);
                celdaPrecio.setPadding(5);
                celdaStock.setPadding(5);

                table.addCell(celdaId);
                table.addCell(celdaNombre);
                table.addCell(celdaPrecio);
                table.addCell(celdaStock);
            }

            document.add(table);

            // Total de productos
            Paragraph total = new Paragraph("\nTotal de productos: " + productos.size(), fontFecha);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();

            // Abrir el archivo automáticamente
            abrirArchivo(nombreArchivo);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ======================= GENERAR DOC (WORD) =========================
    public boolean generarReporteDOC(List<Producto> productos) {
        String nombreArchivo = RUTA_REPORTES + "Reporte_Productos_" + obtenerFechaHora() + ".docx";

        try {
            XWPFDocument document = new XWPFDocument();

            // Título
            XWPFParagraph titulo = document.createParagraph();
            titulo.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun runTitulo = titulo.createRun();
            runTitulo.setText("REPORTE DE PRODUCTOS");
            runTitulo.setBold(true);
            runTitulo.setFontSize(18);
            runTitulo.addBreak();

            // Fecha
            XWPFParagraph fecha = document.createParagraph();
            fecha.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun runFecha = fecha.createRun();
            runFecha.setText("Fecha de generación: " + obtenerFechaHoraLegible());
            runFecha.setFontSize(10);
            runFecha.addBreak();

            // Crear tabla
            XWPFTable table = document.createTable();

            // Encabezados
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("ID");
            headerRow.addNewTableCell().setText("Nombre");
            headerRow.addNewTableCell().setText("Precio");
            headerRow.addNewTableCell().setText("Stock");

            // Estilo de encabezados
            for (XWPFTableCell cell : headerRow.getTableCells()) {
                cell.setColor("2C3E50");
                XWPFParagraph p = cell.getParagraphs().get(0);
                p.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r = p.getRuns().get(0);
                r.setBold(true);
                r.setColor("FFFFFF");
            }

            // Agregar datos
            for (Producto p : productos) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(String.valueOf(p.getId()));
                row.getCell(1).setText(p.getNombre());
                row.getCell(2).setText(String.format("%.2f", p.getPrecio()));
                row.getCell(3).setText(String.valueOf(p.getStock()));

                // Centrar contenido
                for (XWPFTableCell cell : row.getTableCells()) {
                    XWPFParagraph para = cell.getParagraphs().get(0);
                    para.setAlignment(ParagraphAlignment.CENTER);
                }
            }

            // Total
            XWPFParagraph total = document.createParagraph();
            total.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun runTotal = total.createRun();
            runTotal.addBreak();
            runTotal.setText("Total de productos: " + productos.size());
            runTotal.setBold(true);

            // Guardar archivo
            FileOutputStream out = new FileOutputStream(nombreArchivo);
            document.write(out);
            out.close();
            document.close();

            // Abrir el archivo automáticamente
            abrirArchivo(nombreArchivo);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ======================= GENERAR EXCEL =========================
    public boolean generarReporteExcel(List<Producto> productos) {
        String nombreArchivo = RUTA_REPORTES + "Reporte_Productos_" + obtenerFechaHora() + ".xlsx";

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Productos");

            // Estilo para el título
            CellStyle estiloTitulo = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font fontTitulo = workbook.createFont();
            fontTitulo.setBold(true);
            fontTitulo.setFontHeightInPoints((short) 16);
            estiloTitulo.setFont(fontTitulo);
            estiloTitulo.setAlignment(HorizontalAlignment.CENTER);

            // Estilo para encabezados
            CellStyle estiloEncabezado = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font fontEncabezado = workbook.createFont();
            fontEncabezado.setBold(true);
            fontEncabezado.setColor(IndexedColors.WHITE.getIndex());
            estiloEncabezado.setFont(fontEncabezado);
            estiloEncabezado.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
            estiloEncabezado.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            estiloEncabezado.setAlignment(HorizontalAlignment.CENTER);
            estiloEncabezado.setBorderBottom(BorderStyle.THIN);
            estiloEncabezado.setBorderTop(BorderStyle.THIN);
            estiloEncabezado.setBorderRight(BorderStyle.THIN);
            estiloEncabezado.setBorderLeft(BorderStyle.THIN);

            // Estilo para datos
            CellStyle estiloDatos = workbook.createCellStyle();
            estiloDatos.setBorderBottom(BorderStyle.THIN);
            estiloDatos.setBorderTop(BorderStyle.THIN);
            estiloDatos.setBorderRight(BorderStyle.THIN);
            estiloDatos.setBorderLeft(BorderStyle.THIN);

            // Estilo para números
            CellStyle estiloNumeros = workbook.createCellStyle();
            estiloNumeros.cloneStyleFrom(estiloDatos);
            estiloNumeros.setAlignment(HorizontalAlignment.RIGHT);

            // Título
            Row rowTitulo = sheet.createRow(0);
            Cell cellTitulo = rowTitulo.createCell(0);
            cellTitulo.setCellValue("REPORTE DE PRODUCTOS");
            cellTitulo.setCellStyle(estiloTitulo);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 3));

            // Fecha
            Row rowFecha = sheet.createRow(1);
            Cell cellFecha = rowFecha.createCell(0);
            cellFecha.setCellValue("Fecha: " + obtenerFechaHoraLegible());
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 3));

            // Encabezados
            Row headerRow = sheet.createRow(3);
            String[] encabezados = {"ID", "Nombre", "Precio", "Stock"};
            for (int i = 0; i < encabezados.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(encabezados[i]);
                cell.setCellStyle(estiloEncabezado);
            }

            // Datos
            int rowNum = 4;
            for (Producto p : productos) {
                Row row = sheet.createRow(rowNum++);

                Cell cellId = row.createCell(0);
                cellId.setCellValue(p.getId());
                cellId.setCellStyle(estiloDatos);

                Cell cellNombre = row.createCell(1);
                cellNombre.setCellValue(p.getNombre());
                cellNombre.setCellStyle(estiloDatos);

                Cell cellPrecio = row.createCell(2);
                cellPrecio.setCellValue(p.getPrecio());
                cellPrecio.setCellStyle(estiloNumeros);

                Cell cellStock = row.createCell(3);
                cellStock.setCellValue(p.getStock());
                cellStock.setCellStyle(estiloDatos);
            }

            // Total
            Row rowTotal = sheet.createRow(rowNum + 1);
            Cell cellTotal = rowTotal.createCell(0);
            cellTotal.setCellValue("Total de productos: " + productos.size());
            org.apache.poi.ss.usermodel.Font fontTotal = workbook.createFont();
            fontTotal.setBold(true);
            CellStyle estiloTotal = workbook.createCellStyle();
            estiloTotal.setFont(fontTotal);
            cellTotal.setCellStyle(estiloTotal);

            // Ajustar ancho de columnas
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            // Guardar archivo
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            // Abrir el archivo automáticamente
            abrirArchivo(nombreArchivo);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ======================= MÉTODOS AUXILIARES =========================

    private String obtenerFechaHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return LocalDateTime.now().format(formatter);
    }

    private String obtenerFechaHoraLegible() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    private void abrirArchivo(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (Exception e) {
            System.out.println("No se pudo abrir el archivo automáticamente: " + e.getMessage());
        }
    }
}