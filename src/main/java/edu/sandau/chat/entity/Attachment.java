package edu.sandau.chat.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/***
 * 文件表
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*** 文件名 **/
    @Column(nullable = false)
    private String name;

    /*** 文件大小 **/
    @Column(nullable = false)
    private String contentSize;

    /*** 文件类型 **/
    @Column(nullable = false)
    private String fileType;

    /*** 上传用户 **/
    @Column(nullable = false)
    private Integer ownerId;

    /*** 文件大字段 **/
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="longblob")
    private byte[] content;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
