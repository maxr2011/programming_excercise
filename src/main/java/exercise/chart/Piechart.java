package exercise.chart;

import exercise.objects.Country;
import exercise.output.GenerateOutputFiles;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.UnitType;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
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

		// Einfache Labels
		plot.setSimpleLabels(true);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}"));

		plot.setLabelBackgroundPaint(null);
		plot.setLabelOutlinePaint(null);
		plot.setLabelShadowPaint(null);
		plot.setLabelPaint(Color.white);
		plot.setLabelFont(new Font("Arial", Font.BOLD, 15));

		plot.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.1, 0.1, 0.1, 0.1));

		int j = 0;

		for (Country c : countries) {

			// Sektionen Farbe
			plot.setSectionPaint(c.getName(), colorlist.get(j++));

			// Sektionen Outline Farbe
			plot.setSectionOutlinePaint(c.getName(), Color.white);

		}

		plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0} = {2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));

		// Sektion hervorheben
		// plot.setExplodePercent("Deutschland", 0.3);

		// Legende mit quadratischen Colorboxen
		plot.setLegendItemShape(new java.awt.Rectangle(17, 17));

		LegendTitle legendTitle = new LegendTitle(chart.getPlot());

		// Legende
		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.getLegend().setItemLabelPadding(new RectangleInsets(5.0, 7.0, 3.0, 900.0));
		chart.getLegend().setPadding(new RectangleInsets(0.0, 50.0, 30.0, 0.0));

		chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 15));

		// chart.addSubtitle(new TextTitle("test"));

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
