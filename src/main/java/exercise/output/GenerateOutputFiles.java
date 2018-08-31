package exercise.output;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class GenerateOutputFiles {


	// Methode zum Speichern als PNG
	public static void exportPNG(File name, JFreeChart chart, int x, int y) {

		try {
			ChartUtilities.saveChartAsPNG(name, chart, x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Methode zum Exportieren als GIF
	public static void exportGIF(File name, JFreeChart chart, int x, int y) {

		try {

			exportPNG(name, chart, x, y);
			FileInputStream fin = new FileInputStream(name);
			BufferedImage image = ImageIO.read(fin);
			String sPath = name.getPath();
			ImageIO.write(image, "GIF", new File(sPath));

		} catch (IOException f) {
			f.printStackTrace();
		}

	}

	/* TODO
	Exportieren als SVG Datei
	 */

	// Methode zum Exportieren als PDF
	public static void exportPDF(File name, JFreeChart chart, int x, int y) {

		Document document = new Document(new Rectangle(x, y), 50, 50, 50, 50);
		PdfWriter writer = null;

		try {

			writer = PdfWriter.getInstance(document, new FileOutputStream(name));
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			PdfTemplate tp = cb.createTemplate(x, y);
			Graphics2D g2 = tp.createGraphics(x, y, new DefaultFontMapper());

			java.awt.Rectangle recta = new java.awt.Rectangle(x, y);
			chart.draw(g2, recta);
			g2.dispose();
			cb.addTemplate(tp, 0, 0);


		} catch (DocumentException | FileNotFoundException e) {

			// Fehler mit dem Dokument
			e.printStackTrace();

		} finally {
			document.close();
			if(writer != null) {
				writer.close();
			}
		}

	}

	// 2 PDFs zusammenfügen
	public static void mergePDF(String source1, String source2, String destination) {

		try {
			PDFMergerUtility ut = new PDFMergerUtility();
			ut.addSource(new File(source1));
			ut.addSource(source2);
			ut.setDestinationFileName(destination);
			ut.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
