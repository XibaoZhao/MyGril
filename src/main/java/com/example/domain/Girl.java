package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Girl {

	@Id
	@GeneratedValue
	private Integer id;
	
	
	@Column(name = "age")
	private Integer age;
	
	@Column(name = "cup_size")
	private String cupSize;

	@Column(name = "is_use")
	private String isUse;

	public String getIsUse() {
		return this.isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCupSize() {
		return this.cupSize;
	}

	public void setCupSize(String cupSize) {
		this.cupSize = cupSize;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String toString() {
		return "Girl [id=" + this.id + ", cupSize=" + this.cupSize + ", age=" + this.age + ", isUse=" + this.isUse
				+ "]";
	}
}