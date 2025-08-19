package com.itgroup;  //3번 DAO를 직접 쓰지 않고, 비즈니스 로직을 따로 분리한다. (사용자 요청 ->Manager가 판단->Dao한테 시킴, 이렇게 해야 Main에서 DB 코드를 직접 안 쓰게 됨. 이 클래스는 업무 중심 기능(회원 등록, 수정,삭제 등)을 처리한다.

import com.itgroup.bean.Member;
import com.itgroup.dao.MemberDao;

import java.util.List;

// 메인 클래스 대신 실제 모든 업무를 총 책임지는 매니져 클래스

public class MemberManager {  //비즈니스 로직(사용자의 요청 처리(기능 구현))
    private MemberDao dao = null; //실제 데이터 베이스와 연동하는 다오 클래스

    public MemberManager() {
        this.dao = new MemberDao();
    }

    public void getMemberOne() {
        String findId = "xx";  //찾고자 하는 회원(원칙은 로그인 한 그 회원)
        Member someone = dao.getMemberOne(findId);  //여기이 Member 은 1명 즉, bean

        if (someone == null) {
            System.out.println("찾으시는 회원이 존재하지 않습니다.");
        } else {
            String id = someone.getId();
            String name = someone.getName();
            String gender = someone.getGender();
            String message = id + "\t" + name + "\t" + gender;
            System.out.println(message);
        }
    }
    public void selectAll() {  //모든 회원 정보 조회  //이 메소드 1개가 html 문서 1개//관리자입장에서 바라본.
        List<Member> members = dao.selectAll();  //컬렉션이므로 확장 for 필료
        //반환타입은 멤버들을 포함하고 있는 list컬렉션(제내딕)(모든회원은 멤버,여러명 의미하는 자바용어 컬렉션 중 list(순서 따질 때 list사용)(한명은 bean)
        System.out.println("이름\t급여\t주소");

        for (Member bean : members) {
            String name = bean.getName();
            int salary = bean.getSalary();
            String address = bean.getAddress();
            String message = name + "\t" + salary + "\t" + address;
            System.out.println(message);
        }
    }
    public void findByGender() {
        String gender = "여자" ;
        List<Member> mydata = dao.findByGender(gender);
        //여기서 출력
    }
    public void getSize() {  //몇 명의 회원인지 조회하는(알려주는) 구문입니다.html문서
        int cnt = dao.getSize();  //양자택일 구문이 좋다.//int가 반환타입(회원명수(정수16명)이므로 int)
        String message = "검색된 회원은 총 " + cnt + "명입니다.";
        if (cnt == 0) {
            message = "검색된 회원이 존재하지 않습니다.";
        } else {
            message = "검색된 회원은 총 " + cnt + "명입니다.";
        }
        System.out.println(message);
    }



}
