package com.itgroup;  //3번 DAO를 직접 쓰지 않고, 비즈니스 로직을 따로 분리한다. (사용자 요청 ->Manager가 판단->Dao한테 시킴, 이렇게 해야 Main에서 DB 코드를 직접 안 쓰게 됨. 이 클래스는 업무 중심 기능(회원 등록, 수정,삭제 등)을 처리한다.

import com.itgroup.dao.MemberDao;
// 메인 클래스 대신 실제 모든 업무를 총 책임지는 매니져 클래스
public class MemberManager {  //비즈니스 로직(사용자의 요청 처리(기능 구현))
private MemberDao dao = null ; //실제 데이터 베이스와 연동하는 다오 클래스
    public MemberManager() {
        this.dao = new MemberDao();
    }

    public void selectAll() {  //모든 회원 정보 조회  //이 메소드 1개가 html 문서 1개
    }

    public void getSize() {  //몇 명의 회원인지 조회하는 구문입니다.
        int cnt = dao.getSize();  //양자택일 구문이 좋다.
        String message = "검색된 회원은 총 " + cnt + "명입니다.";
        if(cnt == 0){
            message = "검색된 회원이 존재하지 않습니다.";

        }else {
            message = "검색된 회원은 총 " + cnt + "명입니다.";
        }
        System.out.println(message);
         }
}
