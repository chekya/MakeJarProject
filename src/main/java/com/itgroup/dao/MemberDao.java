package com.itgroup.dao;  //2번 DB와 통신하는 코드 만들기 (1. 오라클에 연결하려면 JDBC 드라이버 필요 2.연결 정보를 담은 getConnection() 함수 작성 3. 필요한 SQL문 정의 및 실행(select, insert ect.)  //이 클래스는 SQL을 담당(DB 입출력만 처리), 결과 받아서 객체로 반환

import com.itgroup.bean.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//데이터 베이스와 직접 연동하여 CRUD 작업을 수행해주는 DAO 클래스
public class MemberDao extends SuperDao {   //DB연동 ( DB에 직접 연결해서 CRUD 처리)
    public MemberDao() {
        
    }
    public int updateData(Member bean) {  //"Member"라는 친구 하나를 받아서, 그 친구의 정보를 데이터베이스에서 바꿔줄 함수야.
                                          //이 함수는 나중에 **몇 명이 수정되었는지 숫자(int)**를 알려줄 거야.
        // 수정된 나의 정보 bean을 사용하여 데이터 베이스에 수정합니다.
        int cnt = -1;
        //members 테이블에서 값을 수정하는 SQL 시작
        String sql = "update members set name = ?, password = ?, gender = ?, birth = ?, marriage = ?, salary = ?, address = ?, manager = ?" ;
        sql += " where id = ? ";  //어떤 회원을 수정할지 지정. id는 고유 식별자이므로 where절에 둠.

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = super.getConnection();  //예상되는 에러 : DB 연결 실패, 설정 오류(지금 이 클래스 안에 있는 getConnection() 메서드를 호출하겠다"**는 뜻)
            pstmt = conn.prepareStatement(sql);  //예상되는 에러 : SQL 문법 오류, 잘못된 테이블/컬럼

            // set 파라미터 순서 주의: insert와는 다르게 id는 마지막 (WHERE 조건에 사용됨), 위의 순서와 갖게(라인29번)
            pstmt.setString(1, bean.getName());      //?에 들어갈 값들을 순서대로 설정, 모든 ?에 대하여 빠짐없이 세팅
            pstmt.setString(2, bean.getPassword());
            pstmt.setString(3, bean.getGender());
            pstmt.setString(4, bean.getBirth());
            pstmt.setString(5, bean.getMarriage());
            pstmt.setInt(6, bean.getSalary());
            pstmt.setString(7, bean.getAddress());
            pstmt.setString(8, bean.getManager());
            pstmt.setString(9, bean.getId());  //where 절에 사용될 id(이 문장의 순서로 9째에 위치하므로)

            cnt = pstmt.executeUpdate(); //실행 : 변경된 행 수 리턴(실제 DB에 반영, 반영된 행 수 리턴,수정된 사람 수를 cnt에 저장하라는 의미)
            conn.commit();  //변경 내용을 저장. (Auto-commit 아닐 경우 필요)

             } catch (Exception e) {  //예외(Exception)가 발생하면 catch (Exception e) 블록이 그걸 받아서 처리하게 됩니다.
            e.printStackTrace();  // 예외가 생기면 여기로 와서 에러 내용을 화면에 보여준다.
            try {
               if(conn != null)  //DB 연결이 되어 있다면 그 다음 코드를 실행하고, 연결이 안 되어 있다면(null이면) 아무것도 하지 말자
               conn.rollback();   //예외 발생 시 트랜잭션 되돌리기
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {    //자원 해제: DB 연결 종료 등
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return  cnt;   // 성공 시 업데이트된 행 수, 실패 시 -1
    }

    public int insertData(Member bean) {//가입할 정보 /bean.도트   ...
        //웹 페이지에서 회원 정보를 입력하고 '가입' 버튼을 눌렀습니다.
        int cnt = -1;  //추가된 건수가 없다는 의미

        String sql = "insert into members(id, name, password, gender, birth, marriage, salary, address, manager)";
        sql += " values(?,?,?,?,?,?,?,?,?)";  //문장이 길어지면 앞에 스페이스 한칸 띄는(공백) 습관을 들인다.--> " values(...)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = super.getConnection();  //// ← 여기서 this는 'MemberDAO 자신' 즉,this는 **현재 클래스 자신(자기 자신)**을 의미
            pstmt = conn.prepareStatement(sql);
            //id, name, password, gender, birth, marriage, salary, address, manager
            pstmt.setString(1, bean.getId());
            pstmt.setString(2, bean.getName());
            pstmt.setString(3, bean.getPassword());
            pstmt.setString(4, bean.getGender());
            pstmt.setString(5, bean.getBirth());
            pstmt.setString(6, bean.getMarriage());
            pstmt.setInt(7, bean.getSalary());
            pstmt.setString(8, bean.getAddress());
            pstmt.setString(9, bean.getManager());  //라인 39~47 -->라인 29번을 치환 //순서 똑같이 한다.
            cnt = pstmt.executeUpdate(); //실행 / 치환은 이 줄 위에.
            conn.commit();   //성공시(try 절에)
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();  //실패시(catch 절에)
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return cnt;
    }


    public int getSize() {  //select count(*) 쿼리 실행해서 회원 수 가져옴
        String sql = "select count(*) as cnt from members ";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        int cnt = 0;  //검색된 회원 명수

        try {
            conn = super.getConnection(); //접속 객체 구하기 1번
            pstmt = conn.prepareStatement(sql); //문장 객체 2번
            rs = pstmt.executeQuery();  //결과 집합  3번(닫을 때는 3,2,1번 순서로)

            if (rs.next()) {  //한 건이라도 조회되면 true/ 0건은 false
                cnt = rs.getInt("cnt");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {  //fially안에 try...catch 중첩할 수 있다.  유의미한 데이터데 close 작업한다.
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
        return cnt;
    }

    public Member getMemberOne(String id) {
        //로그인 id 정보를 이용하여 해당 사용자의 정보를 bean 형태로 반환해줍니다.(내 개인정보 보기, 수정 할 때 필요)
        //조회이므로 dql
        Connection conn = null;
        PreparedStatement pstmp = null;
        ResultSet rs = null;
        Member bean = null; //찾고자하는 회원의 정보
        String sql = "select * from members where id = ? ";  //? 표 치환은  실행하다라는 ececute(라인86번)메소드 직전에 한다.

        try {
            conn = super.getConnection();
            pstmp = conn.prepareStatement(sql);

            pstmp.setString(1, id);
            rs = pstmp.executeQuery();

            if (rs.next()) {  //1건 발견됨
                bean = new Member();

                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(String.valueOf(rs.getDate("birth")));  //날짜타입은 getDate로 갖고와서 문자 value인 String.valueOf 로 코딩한다.
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));
                //생성자, 찾는 사람 발견되면 그 때 객체생성

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmp != null) {
                    pstmp.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return bean;
    }

    public int deleteData(String id) { // 기본키를 사용하여 회원 탈퇴를 시도합니다.
        int cnt = -1;
        String sql = "delete from members where id = ? ";

        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            cnt = pstmt.executeUpdate();

            conn.commit();  //DML문장의 종지부
        } catch (Exception ex) {
            try {
                conn.rollback(); //
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return cnt;
    }

    public List<Member> selectAll() {
        List<Member> members = new ArrayList<Member>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;  //ResultSet 즉 rs(현재 16행 9열 테이블의) 는 데이터 결과 집합(컴퓨터 메모리에 존재)
        Connection conn = null;

        String sql = "select * from members order by name asc ";


        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
             // System.out.println(rs.getString(2));
             // System.out.println(rs.getInt(7));
             // System.out.println(rs.getString("id"));  //이 한줄이 의미하는 것은 멤버 한줄 인 bean,멤버클래스로 만들어지는 객체에 이 데이터를 담는다.
             // System.out.println(rs.getString("gender"));


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
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }

        return members;

    }

    public List<Member> findByGender(String gender) {
        // 성별 컬럼 gender을 사용하여 특정 성별의 회원들만 조회합니다.
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;

        String sql = "select * from members where gender = ? ";

        List<Member> members = new ArrayList<Member>();

        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gender);
            rs = pstmt.executeQuery();

            while (rs.next()) {  //1건 발견됨
                Member bean = new Member();
                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(rs.getString("birth"));
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));

                members.add(bean);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return members;
    }




    // List<Member> members = new ArrayList<Member>();
    // return  members ;
}

// public List<Member> findByGender(String gender) {  //성별 컬럼 gender 을 사용하여 특정 성별의 회원들만 조회합니다.

       /* Connection conn = null ;
        PreparedStatement pstmp = null ;
        ResultSet rs = null ;
        Member bean = null ; //찾고자하는 회원의 정보
        String sql = "select * from members where gender = ? " ;

        try {
            conn = super.getConnection();
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





