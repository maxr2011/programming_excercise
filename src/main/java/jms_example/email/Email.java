package jms_example.email;

class Email {

    private final String to;
    private final String body;

// --Commented out by Inspection START (05.09.18 15:04):
//    public Email() {
//    }
// --Commented out by Inspection STOP (05.09.18 15:04)

    public Email(String to, String body) {
        this.to = to;
        this.body = body;
    }

    private String getTo() {
        return to;
    }

// --Commented out by Inspection START (05.09.18 16:14):
//    public void setTo(String to) {
//        this.to = to;
//    }
// --Commented out by Inspection STOP (05.09.18 16:14)

    private String getBody() {
        return body;
    }

// --Commented out by Inspection START (05.09.18 16:14):
//    public void setBody(String body) {
//        this.body = body;
//    }
// --Commented out by Inspection STOP (05.09.18 16:14)

    @Override
    public String toString() {
        return String.format("Email{to=%s, body=%s}", getTo(), getBody());
    }

}
