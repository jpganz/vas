package com.sigetel.web.mail;


import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sigetel.web.service.AppConfigService;


@RestController
@RequestMapping("/ovasSendMail")
public class MailMail {
    
    @Autowired
	private JavaMailSender mailSender;    
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> constructMail(@RequestBody MailBody mailBody) {
    	
    	try {
    		MimeMessageHelper mimeHelper;
    		
    		if(null != mailBody.getTo()) {
    		
    			mimeHelper = setMailParameters(mailBody.getFrom(), 
					mailBody.getTo(), 
					mailBody.getSubject(), 
					mailBody.getMessage());
    		}else {
    			mimeHelper = setMailParameters(mailBody.getFrom(), 
    					mailBody.getToArray(), 
    					mailBody.getSubject(), 
    					mailBody.getMessage());
    		}
			
			if(mailBody.getAttachment() != null
					&& !mailBody.getAttachment().isEmpty()) {
				mimeHelper = setAttachMent(mimeHelper, mailBody.getAttachment());
			}
			
			sendMail(mimeHelper.getMimeMessage());
			
			return ResponseEntity.ok("Mail send!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return ResponseEntity.badRequest().build();
    }

	private MimeMessageHelper setMailParameters(String from, String to, String subject, String msg) {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = null;

		try {
			helper = new MimeMessageHelper(message, true);
			
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(msg);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return helper;
	}
	
	private MimeMessageHelper setMailParameters(String from, String[] to, String subject, String msg) {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = null;

		try {
			helper = new MimeMessageHelper(message, true);
			
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(msg);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return helper;
	}

	private MimeMessageHelper setAttachMent(MimeMessageHelper messageHelper, String pathFile) {

		try {

			FileSystemResource file = new FileSystemResource(pathFile);

			messageHelper.addAttachment(file.getFilename(), file);

		} catch (MessagingException e) {

			e.printStackTrace();
		}

		return messageHelper;
	}

	private void sendMail(MimeMessage message) {

		mailSender.send(message);
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public JavaMailSenderImpl getDefaultMailConfiguration(AppConfigService appConfigService) {

		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost(appConfigService.findOneByRoute("mail.host").getValue());
		javaMailSenderImpl.setPort(Integer.parseInt(appConfigService.findOneByRoute("mail.port").getValue()));
		javaMailSenderImpl.setUsername(appConfigService.findOneByRoute("mail.username").getValue());
		javaMailSenderImpl.setPassword(appConfigService.findOneByRoute("mail.password").getValue());

		Properties properties = new Properties();
		
		boolean starttls = false;
		if(null != appConfigService.findOneByRoute("mail.security")) {
			starttls = true;
		}
		
		properties.put("mail.smtp.starttls.enable", starttls);
		properties.put("mail.smtp.starttls.required", starttls);
		properties.put("mail.smtp.auth", true);

		javaMailSenderImpl.setJavaMailProperties(properties);

		return javaMailSenderImpl;
	}
}