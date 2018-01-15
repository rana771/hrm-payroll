package com.bracu.hrm.model.email;

import com.bracu.hrm.model.BaseEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mail")
public class Mail extends BaseEntity{


	@Column(name ="from_email")
    private String fromEmail;
	@Column(name ="to_email")
    private String toEmail;
    private String subject;
    private String content;
    private String attachment;
    
    
    @Column(name ="sending_date")
    private Date sendingDate;

	public String getFrom() {
		return fromEmail;
	}


	public void setFrom(String fromEmail) {
		this.fromEmail = fromEmail;
	}


	public String getTo() {
		return toEmail;
	}


	public void setTo(String toEmail) {
		this.toEmail = toEmail;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getAttachment() {
		return attachment;
	}


	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}


	public Date getSendingDate() {
		return sendingDate;
	}


	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}
    
	 @Override
	    public String toString() {
	        return "Mail{" +
	                "from='" + fromEmail + '\'' +
	                ", to='" + toEmail + '\'' +
	                ", subject='" + subject + '\'' +
	                ", content='" + content + '\'' +
	                '}';
	    }

}
