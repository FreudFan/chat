package edu.sandau.chat.entity.group;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/***
 * 群文件表
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class GroupAttachment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /***  群id  ***/
    @Column(nullable = false)
    private Integer groupId;

    /*** 群文件名 **/
    @Column(nullable = false)
    private String fileId;

}
