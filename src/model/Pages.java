package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown=true)
public class Pages<T> {
List<T> content;
Integer number;//页数
public List<T> getContent() {
	return content;
}
public void setContent(List<T> content) {
	this.content = content;
}
public Integer getNumber() {
	return number;
}
public void setNumber(Integer number) {
	this.number = number;
}
}
