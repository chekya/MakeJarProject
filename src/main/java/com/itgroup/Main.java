package com.itgroup;   //4번 이제 사용자 인터페이스를 구성한다. 메뉴 선택->Manager에 요청->Dao까지 전달.
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main { // 사용자 인터페이스(메뉴 출력 & 사용자 입력)

   public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DataManager manager = new DataManager();

        while (true){
            System.out.println("메뉴 선택");
            System.out.println("0:종료, 1:목록 조회, 2:가입, 3:수정, 4:총회원수, 5:탈퇴, 6:회원정보, 7:성별조회");
            System.out.println("11:게시물 전체, 12:등록, 13:수정, 14:전체 건수, 15:삭제, 16:1건 정보, 17:짝수만 조회");

            int menu = scan.nextInt(); // 선택한 메뉴
            switch (menu){
                case  0:
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0); //운영체제에게 종료됨을 알리고 빠져 나가기
                    break;
                case  1:
                    manager.selectAll();
                    break;
                case  2:
                    manager.insertData();
                    break;
                case  3:
                    manager.updateData();
                    break;
                case  4:
                    manager.getSize();
                    break;
                case  5:
                    manager.deleteData();
                    break;
                case  6:
                    manager.getMemberOne();
                    break;
                case  7:
                    manager.findByGender();
                    break;
                case  11:
                    manager.selectAllBoard();
                    break;
                case  12:
                    break;
                case  13:
                    break;
                case  14:
                    break;
                case  15:
                    break;
                case  16:
                    break;
                case  17:
                    manager.selectEvenData();
                    break;


            }
        }
    }
}
/* 정리: “생각의 순서” 다시 한 번 요약
단계	    생각	                     코드
1	“회원 정보를 다루고 싶다”	          Member.java 작성(사용자에게 메뉴 보여주고 선택 받음, 입력 받은 메뉴에 따라 Manager호출)
2	“DB에 저장하고 불러오고 싶다”	      MemberDao.java에서 DB 연결, SQL 작성(DB연결(JDBC),SQL문 실행(select,insert ect.),결과받아서 객체로 반환)
3	“사용자 요청을 처리할 로직 필요”	  MemberManager.java에서 로직 작성(기능별 로직담당,회원목록조회,추가,삭제등 기능호출,실제 DB연동은 Dao에 위임)
4	“사용자 입력받고 메뉴 보여줘야지”	  Main.java 작성

참고: 중요한 설계 원칙
역할 분리 (Separation of Concerns)
→ 클래스마다 "하나의 책임"만 가지게 한다
유지보수 쉬움
→ DB 구조 바뀌어도 DAO만 수정하면 된다
테스트 용이
→ 비즈니스 로직과 DB가 분리돼 있어 테스트가 쉽다.

 시각적으로 쉽게 기억하려면
Main = 사용자와 직접 대화
Manager = 중간 관리자 (기능 조정)
Dao = DB 전문가 (SQL만 담당)
Member = 데이터를 들고 다니는 택배 상자      */