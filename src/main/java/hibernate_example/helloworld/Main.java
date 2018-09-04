package hibernate_example.helloworld;

class Main {

	public static void main(String[] args) {
		Message msg = new Message();
		msg.setText("Hello!");
		System.out.println(msg.getText());
	}

}
