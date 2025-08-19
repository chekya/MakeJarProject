package com.itgroup.dao;  //2번 DB와 통신하는 코드 만들기 (1. 오라클에 연결하려면 JDBC 드라이버 필요 2.연결 정보를 담은 getConnection() 함수 작성 3. 필요한 SQL문 정의 및 실행(select, insert ect.)  //이 클래스는 SQL을 담당(DB 입출력만 처리), 결과 받아서 객체로 반환

import com.itgroup.bean.Member;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//데이터 베이스와 직접 연동하여 CRUD 작업을 수행해주는 DAO 클래스
public class MemberDao {   //DB연동 ( DB에 직접 연결해서 CRUD 처리)
    public MemberDao() {
        // 드라이버 관련 OracleDriver 클래스는 ojdbc6.jar 파일에 포함되어 있는 자바 클래스입니다.
        String driver = "oracle.jdbc.driver.OracleDriver"; //이 문자열을 하단 forName에 넣으면 ojdbc6.jar 파일에 있는자바클래스를 동적으로 만들어준다.

        try {
            Class.forName(driver); //동적 객체 생성하는 문법입니다.
        } catch (ClassNotFoundException e) {
            // throw new RuntimeException(e);  //e.fillInStackTrace(); 대신 이렇게 자동생성되는 문구 그대로 사용해도 됨.
            System.out.println("해당 드라이버가 존재하지 않습니다.");
            e.fillInStackTrace();
        }
    }

    public Connection getConnection() {  //DB 연결 정보 설정
        Connection conn = null;  //접속 객체
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "oraman";
        String password = "oracle";  //해당계정의 비번

        try {
            conn = DriverManager.getConnection(url, id, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public int getSize() {  //select count(*) 쿼리 실행해서 회원 수 가져옴
        String sql = "select count(*) as cnt from members ";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        int cnt = 0;  //검색된 회원 명수

        try {
            conn = this.getConnection(); //접속 객체 구하기 1번
            pstmt = conn.prepareStatement(sql); //문장 객체 2번
            rs = pstmt.executeQuery();  //결과 집합  3번(닫을 때는 3,2,1번 순서로)

            if (rs.next()) {  //한 건이라도 조회되면 true/ 0건은 false
                cnt = rs.getInt("cnt");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {  //fially안에 try...catch 중첩할 수 있다.  유의미한 데이터데 close 작업한다.
            try {
                if (rs != null) {rs.close();}
                if (pstmt != null) {pstmt.close();}
                if (conn != null) {conn.close();}

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
        return cnt;
    }

    public Member getMemberOne(String id) {
        //로그인 id 정보를 이용하여 해당 사용자의 정보를 bean 형태로 반환해줍니다.(내 개인정보 보기, 수정 할 때 필요)
        //조회이므로 dql
        Connection conn = null ;
        PreparedStatement pstmp = null ;
        ResultSet rs = null ;
        Member bean = null ; //찾고자하는 회원의 정보
        String sql = "select * from members where id = ? " ;  //? 표 치환은  실행하다라는 ececute(라인86번)메소드 직전에 한다.

        try {
            conn = this.getConnection();
            pstmp = conn.prepareStatement(sql);

            pstmp.setString(1,id);
            rs = pstmp.executeQuery();

            if(rs.next()){  //1건 발견됨
                bean = new Member();

                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(rs.getString("birth"));
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));
               //생성자, 찾는 사람 발견되면 그 때 객체생성

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                if (rs != null){rs.close();}
                if (pstmp != null){pstmp.close();}
                if (conn != null){conn.close();}
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        return bean;
    }
    public List<Member> selectAll() {
        List<Member> members = new ArrayList<Member>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;  //ResultSet 즉 rs(현재 16행 9열 테이블의) 는 데이터 결과 집합(컴퓨터 메모리에 존재)
        Connection conn = null;

        String sql = "select * from members order by name asc ";


        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
              /*System.out.println(rs.getString(2));
                System.out.println(rs.getInt(7));
                System.out.println(rs.getString("id"));  //이 한줄이 의미하는 것은 멤버 한줄 인 bean,멤버클래스로 만들어지는 객체에 이 데이터를 담는다.
                System.out.println(rs.getString("gender"));  */


                Member bean = new Member();
                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(rs.getString("birth"));
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));        //이 전체가 한 행

                //break;
                members.add(bean);  //현재 원소 16개
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null){rs.close();}
                if (pstmt != null){pstmt.close();}
                if (conn != null){conn.close();}
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }

        return members;

    }

    public List<Member> findByGender(String gender) {  //성별 컬럼 gender 을 사용하여 특정 성별의 회원들만 조회합니다.

       /* Connection conn = null ;
        PreparedStatement pstmp = null ;
        ResultSet rs = null ;
        Member bean = null ; //찾고자하는 회원의 정보
        String sql = "select * from members where gender = ? " ;

        try {
            conn = this.getConnection();
            pstmp = conn.prepareStatement(sql);

            pstmp.setString(1,gender);
            rs = pstmp.executeQuery();

            if(rs.next()){  //1건 발견됨
                bean = new Member();

                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(rs.getString("birth"));
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));
                //생성자, 찾는 사람 발견되면 그 때 객체생성

            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                try {
                    if (rs != null){rs.close();}
                    if (pstmp != null){pstmp.close();}
                    if (conn != null){conn.close();}
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            return bean;
        } */

        List<Member> members = new ArrayList<Member>();
        return  members ;
    }


}
