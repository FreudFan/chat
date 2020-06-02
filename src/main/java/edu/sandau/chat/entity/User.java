package edu.sandau.chat.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*** 用户名 **/
    @Column(nullable = false, unique = true)
    private String name;

    /*** 密码 **/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    /*** 性别 **/
    @Column(nullable = false)
    private Integer gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String telephone;

    /*** 头像 **/
    private Integer headPortrait;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CreatedDate
    private LocalDateTime createTime;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @LastModifiedDate
    private LocalDateTime updateTime;
}
