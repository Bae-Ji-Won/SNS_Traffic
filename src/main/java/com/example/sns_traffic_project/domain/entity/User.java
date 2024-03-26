package com.example.sns_traffic_project.domain.entity;

import com.example.sns_traffic_project.domain.AuditingFields;
import com.example.sns_traffic_project.domain.constant.UserRole;
//import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "\"user\"",
        indexes = {
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")}
)
@Getter
@ToString(callSuper = true)
@SQLDelete(sql = "UPDATED \"user\" SET deleted_at = NOW() where id=?")      // jpa에서 delete사용하여 delete 쿼리문 동작 시 자동으로 user DB에 deletedAt칸에는 현재 시간이 자동으로 들어감
@Where(clause = "deleted_at is NULL")       // where문 쿼리 동작 시 deletedAt이 null인 값 즉, 아직 삭제되지 않은 정보만 가져옴
public class User extends AuditingFields implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private User(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.createdBy = userName;
        this.modifiedBy = userName;
    }

    public static User of(String userName, String password){
        return new User(userName,password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole().toString()));
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isEnabled() {
        return this.deletedAt == null;
    }
}
