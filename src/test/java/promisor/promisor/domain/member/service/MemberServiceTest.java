package promisor.promisor.domain.member.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;

import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class MemberServiceTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest //runwith와 springboottest 어노테이션 두 가지가 있어야 spring integration하여 spring 올려서 테스트 할 수 있음
//@Transactional
//public class MemberServiceTest {
//
//    @Autowired MemberService memberService;
//    @Autowired MemberRepository memberRepository;
//    @Test
//    public void 회원가입() throws Exception{
//        //given
//        Member member = new Member();
//        member.setEmail("abc@def.com");
//
//        //when
//        memberService.getMember(member);
//        //then
//        Assert.assertEquals(member, memberRepository.findOne());
//    }
//    @Test
//    public void 중복_회원_예외() throws Exception{
//
//    }
//
//}