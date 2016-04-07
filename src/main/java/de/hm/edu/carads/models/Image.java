package de.hm.edu.carads.models;

public class Image {
	
	private String id;
	private String url;
	private String altText;
	private boolean isTitle;
	
	public Image(String url){
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAltText() {
		return altText;
	}
	public void setAltText(String altText) {
		this.altText = altText;
	}
	public boolean isTitle() {
		return isTitle;
	}
	public void setTitle(boolean isTitle) {
		this.isTitle = isTitle;
	}
	

	
	
}
