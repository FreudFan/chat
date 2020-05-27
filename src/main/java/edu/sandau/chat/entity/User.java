package edu.sandau.chat.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private Integer gender;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String telephone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CreatedDate
    private LocalDateTime createTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @LastModifiedDate
    private LocalDateTime updateTime;
}
