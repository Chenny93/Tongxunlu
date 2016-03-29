package com.android.tongxunlu.entity;

/**
 * 封装联系人的信息
 *
 */
public class Contact {
	private int id;
	private String name;
	private String email;
	private String phone;
	private String address;
	private int photoId;
	private String photo;

	public Contact() {
		// TODO Auto-generated constructor stub
	}

	public Contact(int id, String name, String email, String phone,
			String address, int photoId) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.photoId = photoId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + ", photoId="
				+ photoId + "]";
	}
	
}
