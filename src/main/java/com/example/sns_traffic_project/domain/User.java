package com.example.sns_traffic_project.domain;

import com.example.sns_traffic_project.domain.constant.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "\"user\"")
@Getter
@ToString(callSuper = true)
@Setter
@SQLDelete(sql = "UPDATED \"user\" SET deleted_at = NOW() where id=?")      // jpa에서 delete사용하여 delete 쿼리문 동작 시 자동으로 user DB에 deletedAt칸에는 현재 시간이 자동으로 들어감
@Where(clause = "deleted_at is NULL")       // where문 쿼리 동작 시 deletedAt이 null인 값 즉, 아직 삭제되지 않은 정보만 가져옴
public class User extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    protected User(){

    }

    private User(){

    }

    public static User of(){

    }
}
