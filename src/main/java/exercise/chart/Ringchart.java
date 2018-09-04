package exercise.chart;

import exercise.interfaces.Chart;
import exercise.objects.Company;
import exercise.output.GenerateOutputFiles;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SameParameterValue")
public class Ringchart extends ApplicationFrame implements Chart {

	//Variablen
	private static List<Company> companies;

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;

	//GIF file name
	private static final String GIF_FILE = "ringchart-example.gif";

	//PDF file name
	private static final String PDF_FILE = "ringchart-example.pdf";

	public Ringchart(String title, List<Company> cl) {
		super(title);
		companies = cl;

		JPanel panel = createDemoPanel();
		panel.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
		setContentPane(panel);
	}

	//Erzeugt das Dataset
	private static PieDataset createDataset(List<Company> cl) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Company c : cl) {
			dataset.setValue(c.getSecurity(), c.getWeighting());
		}

		return dataset;
	}

	//Erzeugt den Chart
	private static JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createRingChart("",  // chart title
				dataset,             // data
				true,               // include legend
				true, false);

		//LegendTitle legend = chart.getLegend();
		//chart.addSubtitle(new TextTitle("JFreeChart WaferMapPlot", new Font("SansSerif", Font.PLAIN, 9)));

		// Ringplot
		RingPlot plot = (RingPlot) chart.getPlot();

		// Schriftart ändern
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		plot.setNoDataMessage("No data available");

		plot.setSectionDepth(0.35);
		plot.setCircular(false);

		// Hintergrund (entfernt den grauen Standard Hintergrund)
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

		// Farbliste
		colorlist.add(new Color(0x7CCB92));
		colorlist.add(new Color(0x299B87));
		colorlist.add(new Color(0x1990D2));
		colorlist.add(new Color(0x395B85));
		colorlist.add(new Color(0x5F5519));
		colorlist.add(new Color(0x165A3F));

		int k = 0;

		for (Company c : companies) {
			plot.setSectionPaint(c.getSecurity(), colorlist.get(k++));
		}

		// Sektion Outline unsichtbar machen
		//plot.setSectionOutlinesVisible(false);

		// Sektion Outline färben
		plot.setSectionOutlinePaint(o -> 0, new Color(0x808080));

		// Seperator Linie unsichtbar machen
		plot.setSeparatorsVisible(false);

		plot.setLabelGap(0.02);

		// Tiefe des Graphens
		plot.setSectionDepth(0.6);

		// Legende mit quadratischen Colorboxen
		plot.setLegendItemShape(new java.awt.Rectangle(15, 15));

		chart.getLegend().setPosition(RectangleEdge.RIGHT);
		chart.getLegend().setFrame(BlockBorder.NONE);

		return chart;

	}

	//Erzeugt das Panel
	private static JPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset(companies));

		// Als GIF exportieren
		GenerateOutputFiles.exportGIF(new File(GIF_FILE), chart, WIDTH, HEIGHT);

		// Als PDF exportieren
		GenerateOutputFiles.exportPDF(new File(PDF_FILE), chart, WIDTH, HEIGHT);

		return new ChartPanel(chart);
	}

}
