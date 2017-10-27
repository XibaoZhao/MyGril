package com.example.domain;

import java.util.List;

public class NewsMessage extends BaseMessage {
	private int ArticleCount;
	private List<News> Articles;

	public int getArticleCount() {
		return this.ArticleCount;
	}

	public void setArticleCount(int ArticleCount) {
		this.ArticleCount = ArticleCount;
	}

	public List<News> getArticles() {
		return this.Articles;
	}

	public void setArticles(List<News> articles) {
		this.Articles = articles;
	}
}