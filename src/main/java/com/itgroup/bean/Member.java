package com.itgroup.bean;  //1번 생성자(빈 생성자 포함 각 객체),gutter, setter, overloding(회원 한 명이 정보가 뭐가 있나 정의 하는것)

public class Member { //데이터 구조 (회원 정보 객체(체이터 저장용))
    private String id ;
    private String name ;

    public Member(String id) {
        this.id = id;
    }

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

    public Member(String id, String name, String password, String gender, String birth, String marriage, int salary, String address, String manager) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.birth = birth;
        this.marriage = marriage;
        this.salary = salary;
        this.address = address;
        this.manager = manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getBirth() {
        return birth;
    }

    public String getMarriage() {
        return marriage;
    }

    public int getSalary() {
        return salary;
    }

    public String getAddress() {
        return address;
    }

    public String getManager() {
        return manager;
    }

    private String password ;
    private String gender ;
    private String birth ;
    private String marriage ;
    private int salary ;
    private String address ;
    private String manager ;
}


