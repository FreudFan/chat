package edu.sandau.chat.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/***
 * 群成员
 */
@Data
@Entity(name = "user_group_to_user")
@EntityListeners(AuditingEntityListener.class)
public class GroupUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*** 用户id **/
    @Column(nullable = false)
    private Integer userId;

    /*** 群id **/
    @Column(nullable = false)
    private Integer groupId;

    /*** 群内用户昵称 **/
    @Column(nullable = false)
    private String nickname;

    @CreatedDate
    private LocalDateTime createTime;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @LastModifiedDate
    private LocalDateTime updateTime;
}
