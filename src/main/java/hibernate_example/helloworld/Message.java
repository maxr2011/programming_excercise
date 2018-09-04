package hibernate_example.helloworld;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Message {

	@Id
	@GeneratedValue
	@SuppressWarnings("unused")
	private Long id;

	private String text;

	private String recipient;

// --Commented out by Inspection START (04.09.18 11:03):
//	public Long getId() {
//		return id;
//	}
// --Commented out by Inspection STOP (04.09.18 11:03)

// --Commented out by Inspection START (04.09.18 13:29):
//	public String getRecipient() {
//		return recipient;
//	}
// --Commented out by Inspection STOP (04.09.18 13:29)

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
