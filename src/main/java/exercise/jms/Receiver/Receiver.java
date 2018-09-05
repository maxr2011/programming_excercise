package exercise.jms.Receiver;

import exercise.chart.Piechart;
import exercise.jdbc.CountryDB;
import exercise.objects.Country;
import exercise.output.GenerateOutputFiles;
import org.jfree.ui.RefineryUtilities;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.List;

@Component
public class Receiver {

	private final JmsOperations jmsOperations;

	public Receiver(JmsTemplate jmsTemplate) {
		this.jmsOperations = jmsTemplate;
	}

	@JmsListener(destination = "queue")
	public String receiveMessage() {
		try {
			TextMessage tm = (TextMessage) jmsOperations.receive();
			String text = tm != null ? tm.getText() : null;

			System.out.println(text);
			assert text != null;
			if(text.equals("Data written to country_table")) {
				System.out.println("some code");

				//Variablen
				int HEIGHT = 750;
				int WIDTH = 537;

				String PDF_FILE = "piechart-example.pdf";

				// Daten aus Datenbank auslesen (JDBC in diesem Fall)
				List<Country> countriesDB = (new CountryDB()).readFromDatabase();

				// Würde die Liste sortieren (brauchen wir hier jedoch nicht)
								/*
								.stream()
							 	.sorted(Comparator.comparing(Country::getWeight).reversed())
							 	.collect(Collectors.toList());
								*/

				// Piechart erstellen
				Piechart demob = new Piechart("Countries", countriesDB);
				demob.setSize(WIDTH, HEIGHT);
				RefineryUtilities.centerFrameOnScreen(demob);
				demob.setVisible(true);

				// Ringchart analog genauso wie in der anderen Main Methode

				// 2 PDFs zusammenfügen
				GenerateOutputFiles.mergePDF(PDF_FILE, "ringchart-example.pdf", "piechart-ringchart-example.pdf");

			}

			return text;
		} catch (JMSException jme) {
			jme.printStackTrace();
		}
		return null;
	}

}
