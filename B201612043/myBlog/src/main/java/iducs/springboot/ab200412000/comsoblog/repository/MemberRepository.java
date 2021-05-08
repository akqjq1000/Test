package iducs.springboot.ab200412000.comsoblog.repository;

import iducs.springboot.ab200412000.comsoblog.domain.Member;

import java.util.List;

public interface MemberRepository {
    int create(Member member);
    Member readById(Member member); //하나 레코드 가져오기, 유일키 사용
    Member readByEmail(Member member); //하나 레코드 가져오기
    List<Member> readMembers();  //다수의 레코드 가져오기
    int update(Member member);
    int delete(Member member);
}
