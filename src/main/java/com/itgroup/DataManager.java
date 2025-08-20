package com.itgroup;  //3번 DAO를 직접 쓰지 않고, 비즈니스 로직을 따로 분리한다. (사용자 요청 ->Manager가 판단->Dao한테 시킴, 이렇게 해야 Main에서 DB 코드를 직접 안 쓰게 됨. 이 클래스는 업무 중심 기능(회원 등록, 수정,삭제 등)을 처리한다.

import com.itgroup.bean.Board;
import com.itgroup.bean.Member;
import com.itgroup.dao.BoardDao;
import com.itgroup.dao.MemberDao;

import java.util.List;
import java.util.Scanner;

// 메인 클래스 대신 실제 모든 업무를 총 책임지는 매니져 클래스
public class DataManager {  //비즈니스 로직(사용자의 요청 처리(기능 구현))
    private MemberDao mdao = null; //실제 데이터 베이스와 연동하는 다오 클래스
    private BoardDao bdao = null;
    private Scanner scan = null;  //회원 정보 입격 받기 위한 스캐너 장치

    public DataManager() {  //생성자의 주된 목적은 인스턴수변수의 초기화
        this.mdao = new MemberDao();  //생성자
        this.bdao = new BoardDao();//생성자
        this.scan = new Scanner(System.in);
    }

    public void updateData() {
        int cnt = -1;

        System.out.println("수정하고자 하는 회원 id 입력 : ");
        String findId = scan.next();  // yusin 이라고 가정하고 입력(yusin 이라는 id 입력)

        //여기에서 bean은 이전에 입력했던 나의 정보입니다.
        Member bean = mdao.getMemberOne(findId);  //여기에서 bean은 네가 이전에 넣었던 회원 정보 라는 의미.

        // 편의상 내 이름과 결혼 엽를 변경해 보겠습니다.
        System.out.println("이름 입력 : ");
        String name = scan.next();

        System.out.println("결혼 여부 입력 : ");
        String marriage = scan.next();

        bean.setName(name);
        bean.setMarriage(marriage);;

        cnt = mdao.updateData(bean);

        if (cnt == -1){
            System.out.println("업데이트 실패");

        }else  if (cnt == 1){
            System.out.println("업데이트 성공");

        }else {

        }
    }

    public void insertData() {
        Member bean = new Member();  //여기에서 bean은 새롭게 만든 객체라는 의미
        int cnt = -1;
        // 편의상 2~3개만 입력 받도록 합니다.
        System.out.println("id 입력 : ");
        String id = scan.next();

        System.out.println("이름 입력 : ");
        String name = scan.next();

        bean.setId(id);
        bean.setName(name);
        bean.setPassword("abc123");
        bean.setGender("남자");
        bean.setBirth("2025/08/20");
        bean.setMarriage("결혼");
        bean.setSalary(100);
        bean.setAddress("서대문");
        bean.setManager(null);


        cnt = mdao.insertData(bean);

        if (cnt == -1){
            System.out.println("회원 가입 실패(접속, 가입 실패");

        } else if (cnt == 1) {
            System.out.println("회원 아이디 " + id + "로 가입 성공");

        }else {

        }
        }

    public void deleteData() { // 나의 id를 사용한 탈퇴
        String id = "yusin";
        int cnt = - 1 ;
        cnt = mdao.deleteData(id);

        if(cnt == -1){
            System.out.println("회원 탈퇴 실패(접속, 네트워크 오류)");
        }else if(cnt == 0){
            System.out.println("탈퇴할 회원이 존재하지 않습니다.");
        }else if(cnt > 0){
            System.out.println("회원 탈퇴 완료");
        }else{

        }
    }

    public void getMemberOne() {
        String findId = "xx";  //찾고자 하는 회원(원칙은 로그인 한 그 회원)
        Member someone = mdao.getMemberOne(findId);  //여기이 Member 은 1명 즉, bean

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
        List<Member> members = mdao.selectAll();  //컬렉션이므로 확장 for 필료
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
        List<Member> mydata = mdao.findByGender(gender);//여기서 출력
        //List<Member> mydata = mdao.findByGender(tempgender);
        System.out.println("이름\t급여\t주소\t성별");
        for(Member bean:mydata){
            String name = bean.getName();
            int salary = bean.getSalary();
            String address = bean.getAddress() ;
            //String gender = bean.getGender();
            String membergender = bean.getGender();  //by chat gpt
            String message = name + "\t" + salary + "\t" + address + "\t" + gender  ;
            System.out.println(message);
        }
    }
    public void getSize() {  //몇 명의 회원인지 조회하는(알려주는) 구문입니다.html문서
        int cnt = mdao.getSize();  //양자택일 구문이 좋다.//int가 반환타입(회원명수(정수16명)이므로 int)
        String message = "검색된 회원은 총 " + cnt + "명입니다.";
        if (cnt == 0) {
            message = "검색된 회원이 존재하지 않습니다.";
        } else {
            message = "검색된 회원은 총 " + cnt + "명입니다.";
        }
        System.out.println(message);
    }


    public void selectAllBoard() {
        List<Board> boardList = bdao.selectAll();

        System.out.println("글번호\t작성자\t글제목\t글내용");
        for(Board bean : boardList){  //여기서 목록 출력
            int no = bean.getNo();
            String writer = bean.getWriter();
            String subject = bean.getSubject();
            String content = bean.getContent();
            String message = no + "\t" + writer + "\t" + subject + "\t" + content ;
            System.out.println(message);
        }
    }

    public void selectEvenData() {
        //게시물 번호가 짝수인 항목들만 조회해 봅니다.
        List<Board> boardList = bdao.selectEvenData();

        System.out.println("글번호\t작성자\t글제목\t글내용");
        for(Board bean : boardList){  //여기서 목록 출력
            int no = bean.getNo();
            String writer = bean.getWriter();
            String subject = bean.getSubject();
            String content = bean.getContent();
            String message = no + "\t" + writer + "\t" + subject + "\t" + content ;
            System.out.println(message);
        }
    }
}
