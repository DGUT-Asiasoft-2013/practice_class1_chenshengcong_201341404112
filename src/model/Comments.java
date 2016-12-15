package model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown=true)
public class Comments{
Integer id;
String text;
Article article;
User author;

Date creatDate;
Date editDate;
public User getAuthor() {
	return author;
}
public void setAuthor(User author) {
	this.author = author;
}

public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public Article getArticle() {
	return article;
}
public void setArticle(Article article) {
	this.article = article;
}
public Date getCreatDate() {
	return creatDate;
}
public void setCreatDate(Date creatDate) {
	this.creatDate = creatDate;
}
public Date getEditDate() {
	return editDate;
}
public void setEditDate(Date editDate) {
	this.editDate = editDate;
}



}
