package com.enterprise_backend.Service;

import com.enterprise_backend.Entity.Producto;
import com.enterprise_backend.Repository.ProductoRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.FontFactory;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;




@Service
public class InventarioService {

    @Autowired
    private ProductoRepository productoRepository;

    public byte[] generarPDFOpen() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Map<String, List<Producto>> inventario = productoRepository.findAll().stream()
                .collect(Collectors.groupingBy(p -> p.getEmpresa().getNombre()));

        for (Map.Entry<String, List<Producto>> entry : inventario.entrySet()) {
            document.add(new Paragraph("Empresa: " + entry.getKey(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Producto");
            table.addCell("Características");
            table.addCell("Precio");
            table.addCell("Código");

            for (Producto p : entry.getValue()) {
                table.addCell(p.getNombre());
                table.addCell(p.getCaracteristicas());
                table.addCell(String.valueOf(p.getPrecio()));
                table.addCell(p.getCodigo());
            }

            document.add(table);
            document.add(new Paragraph(" "));
        }

        document.close();
        return baos.toByteArray();
    }
}

