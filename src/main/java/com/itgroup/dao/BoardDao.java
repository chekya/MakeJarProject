package com.itgroup.dao;

import com.itgroup.bean.Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao extends SuperDao{
    public BoardDao() {
        super();
    }

    public List<Board> selectAll() {
        //전체 게시물을 최신 항목부터 조회하여 반환합니다.
        List<Board> boardList = new ArrayList<Board>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from boards order by no desc ";
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()){
                Board bean = this.makeBean(rs);

                boardList.add(bean);
            }
        }catch (Exception ex){
            ex.printStackTrace();   //예외가 발생한 위치와 원인을 콘솔에 출력해주는 메서드
        }finally {
            try {
                if (rs != null){rs.close();}
                if (pstmt != null){pstmt.close();}
                if (conn != null){conn.close();}
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        return boardList ;
    }

    private Board makeBean(ResultSet rs) {  //`ResultSet rs`: DB에서 읽어온 한 줄(row)의 데이터라는 의미(rs ->row)
        //ResultSet에서 데이터를 읽어 와서 Bean 객체에 담아 반환합니다.
        Board bean = null;  //아직 Board 객체를 만들지 않고 **빈 변수(bean)**로 선언만 해둔 거예요.
        // 왜 null로 먼저 만들까?  try 문 안에서 만들기 위해서예요. try 밖에서 만들면 try 안에서도 쓸 수 있으니까!

        try {
            bean = new Board();      //이제 진짜로 `Board` 객체를 하나 만들어서 bean에 담아요.
            bean.setNo(rs.getInt("no"));
            bean.setWriter(rs.getString("writer"));
            bean.setPassword(rs.getString("password"));
            bean.setSubject(rs.getString("subject"));
            bean.setContent(rs.getString("content"));
            bean.setReadhit(rs.getInt("readhit"));
            bean.setRegdate(rs.getString("regdate"));


        } catch (SQLException e) {    //DB에서 데이터를 읽을 때 문제가 생기면(예: 컬럼 이름 틀림)
            e.printStackTrace();    //그 에러를 출력해줘요.
        }

        return  bean;  //값을 다 넣은 Board 객체를 **결과로 돌려줘요.**
    }

    public List<Board> selectEvenData() {  //`selectEvenData()`라는 이름의 **메서드(기능 하나)**를 만든 거예요.//이 메서드는 **Board 객체들이 들어 있는 리스트**를 결과로 돌려줍니다.

        List<Board> boardList = new ArrayList<Board>();  //결과를 담을 **빈 바구니(리스트)**를 만들어요.//나중에 게시글을 하나씩 읽어서 여기에 넣을 거예요.
        // 데이터베이스랑 대화하려면 **세 가지 준비물이 필요해요**
        Connection conn = null;  //`conn`: 연결 줄
        PreparedStatement pstmt = null;  //`pstmt`: SQL 명령문 담을 도구
        ResultSet rs = null;  //`rs`: 결과를 받을 통
        String sql = "select * from boards where mod(no, 2) = 0 order by no desc ";  //boards라는 테이블에서 no가 짝수인 글만 가져와요
        // mod(no, 2) = 0: 숫자를 2로 나눴을 때 나머지가 0 → 짝수!//order by no desc: 번호를 큰 순서로 정렬 (최신 글 먼저)
        try {  //혹시 **에러가 나더라도 프로그램이 멈추지 않도록** 감싸주는 구문
            conn = super.getConnection();   //데이터베이스랑 연결 시작!//super.getConnection()은 부모 클래스에 있는 DB 연결 함수를 부른 거예요.
            pstmt = conn.prepareStatement(sql);  //SQL 명령문을 실행할 준비를 해요.// `sql`이라는 문자열을 DB가 이해할 수 있도록 준비합니다.
            rs = pstmt.executeQuery();  //SQL을 실제로 실행하고,결과를 rs(ResultSet 객체)에 담아요.

            while (rs.next()){  //결과가 한 줄씩 있으면 계속 반복해요.한 줄 → 게시글 하나예요.
                Board bean = this.makeBean(rs);  //한 줄의 결과를 Board 객체로 바꿔요.//이 작업은 따로 makeBean()이라는 도우미 함수가 해줘요.
                boardList.add(bean);  //만든 게시글(Board)을 리스트에 추가해요.
            }
        }catch (Exception ex){  //도중에 에러가 나면 그걸 콘솔에 출력해줘요.
            ex.printStackTrace();  //예외가 발생한 위치와 원인을 콘솔에 출력해주는 메서드
        }finally {  //에러가 나든 안 나든, **무조건 마지막에 실행되는 부분**
            try {
                if (rs != null){rs.close();}     //**사용한 자원(DB 연결 등)**을 닫아줘요.안 닫으면 나중에 메모리 누수 생길 수 있어요.
                if (pstmt != null){pstmt.close();}
                if (conn != null){conn.close();}
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        return  boardList ;  //지금까지 만든 **게시글 리스트**를 결과로 돌려줘요.
    }
}
