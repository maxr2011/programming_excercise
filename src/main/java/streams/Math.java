package streams;

class Math {

	//Eigene Methoden um Ergebnisse zu vergleichen

	// Berechnet Durchschnitt
	public static double avg(int n, double[] values) {

		double v = sum(values);
		v /= n;
		return v;

	}

	// Berechnet Summe
	public static double sum(double[] values) {

		double v = 0.0;

		for (double value : values) {
			v += value;
		}

		return v;

	}

}
