package edu.sandau.chat.entity.user;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * 聊天消息
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*** 发送用户id */
    @Column(nullable = false)
    private Integer senderUserId;

    /*** 接受用户id */
    @Column(nullable = false)
    private Integer acceptUserId;

    /*** 消息 */
    @Column(nullable = false)
    private String message;

    /*** 消息是否签收状态(0:未签收 1：签收) */
    private Integer signFlag;

    @CreatedDate
    private LocalDateTime createTime;
}
