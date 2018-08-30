package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;

import java.awt.*;

class PiechartTest {

	public static void main(String[] args) {

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("JAVA", 40.1);
		dataset.setValue("C#", 22);
		dataset.setValue("C++", 81);
		// Creation Of Chart.
		JFreeChart chart = ChartFactory.createPieChart("Programming Languages", dataset, true, // legend?
				true, // tooltips?
				false // URLs?
		);


		ChartFrame frame = new ChartFrame("JFreeChart", chart);
		chart.setBackgroundPaint(Color.blue); // Setting the BackGround Color
		/* You can also set color using below code
		   Paint p = new GradientPaint(0, 0, Color.white, 1000, 0, Color.green));
           chart.setBackgroundPaint(p);
		 */

		chart.setBorderPaint(Color.red);

		LegendTitle legend = chart.getLegend();
		legend.setPosition(RectangleEdge.BOTTOM);

		frame.pack();
		frame.setVisible(true);

	}


}
