package model;

import java.util.Date;

public class Article {
Integer id;
Date createDate;
Date editDate;
String text;
String title;
String authorName;
String authorAvatar;
public String getAuthorName() {
	return authorName;
}
public void setAuthorName(String authorName) {
	this.authorName = authorName;
}
public String getAuthorAvatar() {
	return authorAvatar;
}
public void setAuthorAvatar(String authorAvatar) {
	this.authorAvatar = authorAvatar;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Date getCreateDate() {
	return createDate;
}
public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}
public Date getEditDate() {
	return editDate;
}
public void setEditDate(Date editDate) {
	this.editDate = editDate;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}

}