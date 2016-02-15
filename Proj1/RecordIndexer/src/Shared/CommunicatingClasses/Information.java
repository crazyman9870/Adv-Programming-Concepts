package Shared.CommunicatingClasses;

import java.net.URL;

import Shared.ModelClasses.Record;

public class Information {
	
	private Record record;
	private String link;
	private URL url;

	public Information(Record rec, String link)
	{
		this.record = rec;
		this.link = link;
		url = null;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
}