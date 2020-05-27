package edu.sandau.chat.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private Integer type;

    @Column(unique = true)
    private String contentSize;

    @Column(unique = true, nullable = false)
    private String fileType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="longblob")
    private byte[] content;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CreatedDate
    private LocalDateTime createTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @LastModifiedDate
    private LocalDateTime updateTime;
}
