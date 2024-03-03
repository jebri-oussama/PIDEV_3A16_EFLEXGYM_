package GestionFinance.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public static void generatePdf(Node node, String outputPath) {

        Document document = new Document(PageSize.A4);
        FileOutputStream fileOutputStream = null;

        try {  fileOutputStream = new FileOutputStream(outputPath);
            PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
            document.open();

             WritableImage image = node.snapshot(new SnapshotParameters(), null);

              PdfContentByte contentByte = writer.getDirectContent();
            com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(
                    SwingFXUtils.fromFXImage(image, null),
                    null
            );
            pdfImage.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            pdfImage.setAbsolutePosition(0, 0);
            contentByte.addImage(pdfImage);


            document.close();
            System.out.println("Document PDF du dashboard généré avec succès.");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
