package edu.sandau.chat.service.impl;

import edu.sandau.chat.ChatApplication;
import edu.sandau.chat.entity.Attachment;
import edu.sandau.chat.service.AttachmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MysqlAttachmentServiceImplTest {
    @Autowired
    private AttachmentService attachmentService;

    @Test
    void saveAttachment() {
    }

    @Test
    void downloadAttachmentFile() {
    }

    @Test
    void getAttachmentInfo() {
        Attachment attachment = attachmentService.getAttachmentInfo("1");
        System.out.println(attachment.toString());
    }
}