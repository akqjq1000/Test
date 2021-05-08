package induk.myshop.repository;

import induk.myshop.domain.Member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MemberDAO extends OracleDB {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public MemberDAO() {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "shop";
        String pw = "oracle";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, id, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeResource() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Member> readList() {
        ArrayList<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM member";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                members.add(rsToDTO(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return members;
    }

    public boolean insert(Member member) {
        boolean ret = false;
        String sql = "INSERT INTO member VALUES(seq_member.nextval, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getPhone());
            ps.setString(5, member.getAddress());
            ret = ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return ret;
    }
    public Member readByEmail(String email) {
        Member member = null;
        String sql = "SELECT * FROM member WHERE email=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                member = rsToDTO(rs);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }

        return member;
    }

    public boolean update(Member member) {
        boolean ret = false;
        String sql = "UPDATE member SET name=?, phone=?, address=?, password=? WHERE email=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, member.getName());
            ps.setString(2, member.getPhone());
            ps.setString(3, member.getAddress());
            ps.setString(4, member.getPassword());
            ps.setString(5, member.getEmail());
            ret = ps.executeUpdate() == 1;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }

        return ret;
    }

    public Member readByLogin(String email, String password) {
        Member member = null;
        String sql = "SELECT * FROM member WHERE email=? AND password=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                member = rsToDTO(rs);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }

        return member;
    }



    public Member rsToDTO(ResultSet rs) {
        Member member = new Member();
        try {
            member.setNo(rs.getInt("no"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            member.setName(rs.getString("name"));
            member.setPhone(rs.getString("phone"));
            member.setAddress(rs.getString("address"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }
}
