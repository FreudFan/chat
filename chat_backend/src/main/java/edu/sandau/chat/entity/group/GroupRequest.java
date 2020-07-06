package edu.sandau.chat.entity.group;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * 群申请
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class GroupRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*** 发送用户id **/
    @Column(nullable = false)
    private Integer sendUserId;

    /*** 目标群id **/
    @Column(nullable = false)
    private Integer groupId;

    /*** 发送请求的时间 **/
    @CreatedDate
    private LocalDateTime requestDateTime;
}
