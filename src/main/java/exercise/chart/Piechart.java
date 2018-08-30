package exercise.chart;

import exercise.output.GenerateOutputFiles;
import exercise.objects.Country;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Gleicher Übergabewert, es wird vorgeschlagen die Werte zu hardcoden
@SuppressWarnings("SameParameterValue")
public class Piechart extends ApplicationFrame {

	//Variablen
	private static List<Country> countries = new ArrayList<>();

	private static final int HEIGHT = 750;
	private static final int WIDTH = 537;

	//Pfad der PNG-Datei
	private static final String PNG_FILE = "piechart-example.png";

	//Pfad der PDF-Datei
	private static final String PDF_FILE = "piechart-example.pdf";

	//Konstruktor
	public Piechart(String title, List<Country> cl) {
		super(title);
		countries = cl;

		JPanel cp = createDemoPanel();

		//cp.setBackground(Color.WHITE);

		setContentPane(cp);
	}

	// Dataset erzeugen
	private static PieDataset createDataset(java.util.List<Country> cl) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Country c : cl) {
			dataset.setValue(c.getName(), c.getWeight());
		}

		return dataset;
	}

	// Chart erzeugen
	private static JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("ANTEIL AM FONDSVERMÖGEN",   // chart title
				dataset,          // data
				true,             // include legend
				true, false);

		// Pieplot
		PiePlot plot = (PiePlot) chart.getPlot();

		// Hintergrund weis (entfernt den grauen Standard Hintergrund)
		plot.setBackgroundPaint(Color.white);
		plot.setOutlinePaint(Color.white);

		// Entfernt den Shatten
		plot.setShadowPaint(Color.white);
		plot.setShadowXOffset(0);
		plot.setShadowYOffset(0);

		// Entfernt die Default Labels
		plot.setLabelGenerator(null);

		// Sektionen färben
		ArrayList<Color> colorlist = new ArrayList<>();
		colorlist.add(new Color(0x7ED096));
		colorlist.add(new Color(0x299D87));
		colorlist.add(new Color(0x1990D4));
		colorlist.add(new Color(0x5F5519));
		colorlist.add(new Color(0x165A3F));
		colorlist.add(new Color(0x867D19));
		colorlist.add(new Color(0xDFC70D));
		colorlist.add(new Color(0xEEA615));
		colorlist.add(new Color(0xF5C933));

		int j = 0;

		for (Country c : countries) {
			plot.setSectionPaint(c.getName(), colorlist.get(j++));
		}

		plot.setSimpleLabels(true);

		// Sektion hervorheben
		//plot.setExplodePercent("Deutschland", 0.3);

		// Sektionen Outline
		plot.setSectionOutlinePaint(o -> 0, Color.white);

		// Legende mit quadratischen Colorboxen
		plot.setLegendItemShape(new java.awt.Rectangle(15, 15));

		// Legende
		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.getLegend().setItemLabelPadding(new RectangleInsets(5.0, 2.0, 10.0, 900.0));
		chart.getLegend().setPadding(new RectangleInsets(10.0, 10.0, 0.0, 0.0));

		return chart;
	}

	// Demopanel erzeugen
	private static JPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset(countries));

		// PNG export
		GenerateOutputFiles.exportPNG(new File(PNG_FILE), chart, WIDTH, HEIGHT);

		// PDF export
		GenerateOutputFiles.exportPDF(new File(PDF_FILE), chart, WIDTH, HEIGHT);

		return new ChartPanel(chart);
	}

}
