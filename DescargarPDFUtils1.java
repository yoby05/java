
package ec.com.equifax.fastDecision.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
     * @param rutaArchivo
     * @return ResponseEntity<InputStreamResource>
     * @throws FileNotFoundException
     */
    public static ResponseEntity<InputStreamResource> descargarArchivoPDF(String rutaArchivo) throws FileNotFoundException {
        File archivo = new File(rutaArchivo);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(archivo));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0"); // "attachment;filename=" + archivo.getName()
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).contentLength(archivo.length()).body(
                resource);
    }

    /**
     * Descargar PDF Spring
     * @param rutaArchivo
     * @return ResponseEntity<ByteArrayResource>
     * @throws IOException
     */
    public static ResponseEntity<ByteArrayResource> descargarArchivoPDFByte(String rutaArchivo) throws IOException {
        Path path = Paths.get(rutaArchivo);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
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
