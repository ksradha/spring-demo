package com.example.springdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_image_t")
public class Image {

	@Id
	@GeneratedValue
	@Column(name="image_id")
	private int imageId;
	@Column(name="image_name")
	private String imageName;
	@Column(name="image_type")
	private String imageType;
	@Lob
	@Column(name="image_data")
	private byte[] imageData;
	@Column(name="user_name")
	private String userName;

	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public byte[] getImageData() {
		return imageData;
	}
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}



}
