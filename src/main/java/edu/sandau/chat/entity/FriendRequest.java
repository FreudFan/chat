package edu.sandau.chat.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/***
 * 好友申请表
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*** 发送用户id **/
    @Column(nullable = false)
    private Integer sendUserId;

    /*** 目标用户id **/
    @Column(nullable = false)
    private Integer acceptUserId;

    /*** 发送请求的时间 **/
    @CreatedDate
    private LocalDateTime requestDateTime;
}
