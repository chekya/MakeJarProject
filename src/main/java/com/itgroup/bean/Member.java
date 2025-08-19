package com.itgroup.bean;  //1번 데이터를 담는 객체(생성자(빈 생성자 포함 각 객체),gutter, setter, overloding(회원 한 명이 정보가 뭐가 있나 정의 하는것))

public class Member { //데이터 구조 (회원 정보 객체(체이터 저장용))
    private String id ;
    private String name ;
    private String password ;
    private String gender ;
    private String birth ;
    private String marriage ;
    private int salary ;
    private String address ;
    private String manager ;

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", birth='" + birth + '\'' +
                ", marriage='" + marriage + '\'' +
                ", salary=" + salary +
                ", address='" + address + '\'' +
                ", manager='" + manager + '\'' +
                '}';
    }

    public Member() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}


