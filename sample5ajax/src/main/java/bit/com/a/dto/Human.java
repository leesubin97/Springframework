package bit.com.a.dto;

import java.io.Serializable;

public class Human implements Serializable{
	private String name;
	private String phone;
	private String email;
	private String birth;
	
	Human(){
		
	}

	public Human(String name, String phone, String email, String birth) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.birth = birth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "Human [name=" + name + ", phone=" + phone + ", email=" + email + ", birth=" + birth + "]";
	}
	
	
	
}
