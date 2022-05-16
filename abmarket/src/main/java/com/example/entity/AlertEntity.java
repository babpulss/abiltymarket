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

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "ALERT")
@SequenceGenerator(name = "SEQ_ALERT_NO", sequenceName = "SEQ_ALERT_NO", allocationSize = 1, initialValue = 1)

// 알림테이블
public class AlertEntity {
    
    // 알림번호
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ALERT_NO")
    Long alno;

    // 알림메시지
    @Column(length = 255)
    private String almessage;

    // 읽기여부 (0->읽지않음, 1->읽음)
    private Long alread = 0L;

    // 알림종류
    // 문의 답변1, 후기2, 게시판 댓글3, 대댓글4
    private Long altype;

    // 알림생성일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date alregdate;

    // 알림확인일자
    // @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date alreaddate;

    // 알림클릭시 이동할 주소
    @Column(length = 255)
    private String alurl;
    
    // 회원테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;
    
}
