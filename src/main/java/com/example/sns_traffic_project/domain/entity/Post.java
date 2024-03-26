package com.example.sns_traffic_project.domain.entity;

import com.example.sns_traffic_project.domain.AuditingFields;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "\"post\"",
        indexes = {
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")}
)
@Getter
@ToString(callSuper = true)
@SQLDelete(sql = "UPDATED \"post\" SET deleted_at = NOW() where id=?")      // jpa에서 delete사용하여 delete 쿼리문 동작 시 자동으로 user DB에 deletedAt칸에는 현재 시간이 자동으로 들어감
@Where(clause = "deleted_at is NULL")       // where문 쿼리 동작 시 deletedAt이 null인 값 즉, 아직 삭제되지 않은 정보만 가져옴
public class Post extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name = "body", columnDefinition = "TEXT")   // colum을 TEXT형식으로 저장
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    protected Post(){

    }

    private Post(String title, String body, User user){
        this.title = title;
        this.body = body;
        this.user = user;
        this.createdBy = user.getUsername();
        this.modifiedBy = user.getUsername();
    }

    public static Post of(String title, String body, User user){
        return new Post(title,body,user);
    }

}
