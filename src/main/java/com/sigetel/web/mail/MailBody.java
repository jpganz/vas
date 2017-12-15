package com.sigetel.web.mail;

public class MailBody {

	private String from;
	private String to;
	private String[] toArray;
	private String subject;
	private String message;
	private String attachment;

	public MailBody(String from, String to, String subject, String message, String attachment) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.message = message;
		this.attachment = attachment;
	}
	
	public MailBody(String from, String[] toArray, String subject, String message, String attachment) {
		this.from = from;
		this.toArray = toArray;
		this.subject = subject;
		this.message = message;
		this.attachment = attachment;
	}

	public MailBody() {
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String[] getToArray() {
		return toArray;
	}

	public void setToArray(String[] toArray) {
		this.toArray = toArray;
	}
}
