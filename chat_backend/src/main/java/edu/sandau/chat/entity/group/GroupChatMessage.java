package edu.sandau.chat.entity.group;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * 群聊天记录表
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class GroupChatMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*** 发送用户id */
    @Column(nullable = false)
    private Integer senderUserId;

    /*** 群id */
    @Column(nullable = false)
    private Integer groupId;

    /*** 消息 */
    @Column(nullable = false)
    private String message;

    @CreatedDate
    private LocalDateTime createTime;
}
