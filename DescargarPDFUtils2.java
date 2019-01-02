/*
 * Equifax Ecuador C.A. Sistema: Fast Decision Creado: 28 dic. 2018 Los contenidos de este archivo son propiedad intelectual de
 * Equifax Ecuador C.A. Copyright 2008-2018 Equifax Ecuador C.A. Todos los derechos reservados.
 */
package ec.com.equifax.fastDecision.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.itextpdf.io.source.ByteArrayOutputStream;

/**
 * @author yxh24
 * @version $Revision: $
 */
public final class DescargarArchivoUtils {
    /**
     * Tamanno del Buffer
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * Crea una nueva instancia de la clase DescargarArchivoUtils
     */
    public DescargarArchivoUtils() {
        super();
    }

    /**
     * Descargar PDF Spring
     * @param archivoPDF
     * @return ResponseEntity<InputStreamResource>
     * @throws FileNotFoundException
     */
    public static ResponseEntity<InputStreamResource> descargarArchivoPDF(ByteArrayOutputStream archivoPDF)
            throws FileNotFoundException {
        InputStream in = new ByteArrayInputStream(archivoPDF.toByteArray());
        InputStreamResource resource = new InputStreamResource(in);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).contentLength(archivoPDF.size()).body(
                resource);
    }

    /**
     * Descargar PDF Spring
     * @param archivoPDF
     * @return ResponseEntity<ByteArrayResource>
     * @throws IOException
     */
    public static ResponseEntity<ByteArrayResource> descargarArchivoPDFByte(ByteArrayOutputStream archivoPDF) throws IOException {
        byte[] data = archivoPDF.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(data.length)
                .body(resource);
    }

    /**
     * Descargar PDF
     * @param response
     * @param rutaArchivo
     * @throws IOException
     */
    public static void descargarArchivoPDFResponse(HttpServletResponse response, String rutaArchivo) throws IOException {
        File file = new File(rutaArchivo);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
        while ((bytesRead = inStrem.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        inStrem.close();
    }
}
