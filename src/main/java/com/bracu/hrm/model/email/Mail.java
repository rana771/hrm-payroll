package com.bracu.hrm.model.email;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mail")
public class Mail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Integer version;
    private String from;
    private String to;
    private String subject;
    private String content;
    private String attachment;
    
    
    @Column(name ="sending_date")
    private Date sendingDate;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
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
	                "from='" + from + '\'' +
	                ", to='" + to + '\'' +
	                ", subject='" + subject + '\'' +
	                ", content='" + content + '\'' +
	                '}';
	    }

}
