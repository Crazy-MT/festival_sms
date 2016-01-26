package com.example.newslist;

public class NewsBean {
	public String newsIconUrl;
	public String newsTitle;
	public String newsContent;
	public String getNewsIconUrl() {
		return newsIconUrl;
	}
	public void setNewsIconUrl(String newsIconUrl) {
		this.newsIconUrl = newsIconUrl;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	@Override
	public String toString() {
		return "NewsBean [newsIconUrl=" + newsIconUrl + ", newsTitle=" + newsTitle + ", newsContent=" + newsContent
				+ "]";
	}
	
	
}
