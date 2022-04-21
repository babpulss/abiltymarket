package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMBERADDR")
@SequenceGenerator(name = "SEQ_MEMADDR_NO", sequenceName = "SEQ_MEMADDR_NO", allocationSize = 1, initialValue = 1)

// 회원 주소 테이블
public class MemberAddrEntity {

    // 주소코드
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMADDR_NO")
    private Long ucode;

    // 주소
    @Column(length = 150)
    private String uaddress;

    // 등록일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date uregdate;

    // 대표주소
    private Long uchk;

    // KM설정
    private Long ukm;
    
    // 회원테이블
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

}