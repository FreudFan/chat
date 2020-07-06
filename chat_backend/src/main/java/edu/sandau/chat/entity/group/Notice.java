package edu.sandau.chat.entity.group;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * 群公告
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*** 公告标题 **/
    @Column(nullable = false)
    private String title;

    /*** 公告内容 **/
    @Column(nullable = false)
    private String content;

    /*** 上传用户 **/
    @Column(nullable = false)
    private Integer ownerId;

    /*** 所属群Id **/
    @Column(nullable = false)
    private Integer groupId;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
