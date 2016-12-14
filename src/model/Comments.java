package model;

import java.io.Serializable;
import java.util.Date;

public class Comments implements Serializable{
Integer id;
String text;
public User getAuthour() {
	return authour;
}
public void setAuthour(User authour) {
	this.authour = authour;
}
Article article;
User authour;

Date creatDate;
Date editDate;
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
