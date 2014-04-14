package edu.columbia.cs.vo;

public class Tweet {
	private String keyword;
	private String userlocation;
	private String geolocation;
	private String username;
	private String content;
	private String tweetid;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getUserlocation() {
		return userlocation;
	}
	public void setUserlocation(String userlocation) {
		this.userlocation = userlocation;
	}
	public String getGeolocation() {
		return geolocation;
	}
	public void setGeolocation(String geolocation) {
		this.geolocation = geolocation;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTweetid() {
		return tweetid;
	}
	public void setTweetid(String tweetid) {
		this.tweetid = tweetid;
	}
}
