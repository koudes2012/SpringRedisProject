package com.junyu;

public class User {

    private String name;  
    private Integer age;  
    private String address;  
    private Double classz;  
    private Float classz2;  
    
    public User() {  
        super();  
    }  
    
    public User(String name, Integer age, String address, Double classz,  
            Float classz2) {  
        super();  
        this.name = name;  
        this.age = age;  
        this.address = address;  
        this.classz = classz;  
        this.classz2 = classz2;  
    }  
    
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public Integer getAge() {  
        return age;  
    }  
    public void setAge(Integer age) {  
        this.age = age;  
    }  
    public String getAddress() {  
        return address;  
    }  
    public void setAddress(String address) {  
        this.address = address;  
    }  
    public Double getClassz() {  
        return classz;  
    }  
    public void setClassz(Double classz) {  
        this.classz = classz;  
    }  
    public Float getClassz2() {  
        return classz2;  
    }  
    public void setClassz2(Float classz2) {  
        this.classz2 = classz2;  
    }
    
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", address=" + address + ", classz=" + classz + ", classz2="
				+ classz2 + "]";
	}  
	
}
