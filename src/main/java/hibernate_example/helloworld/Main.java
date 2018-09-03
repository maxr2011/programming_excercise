package hibernate_example.helloworld;

public class Main {

	public static void main(String[] args) {
		Message msg = new Message();
		msg.setText("Hello!");
		System.out.println(msg.getText());
	}

}
