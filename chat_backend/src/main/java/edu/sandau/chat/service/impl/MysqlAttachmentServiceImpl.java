package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.AttachmentDao;
import edu.sandau.chat.entity.Attachment;
import edu.sandau.chat.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class MysqlAttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentDao dao;

    @Override
    public String saveAttachment(MultipartFile file) {
        Integer attachmentId = null;
        try {
            String originalName = file.getOriginalFilename();
            assert originalName != null;
            String fileType = originalName.substring(originalName.lastIndexOf(".") + 1);
            String fileName = originalName.substring(0, originalName.lastIndexOf("."));
            Attachment attachment = new Attachment();
            attachment.setName(fileName);
            attachment.setFileType(fileType);
            attachment.setContentSize(String.valueOf(file.getSize()));
            attachment.setContent(file.getBytes());
            attachment.setOwnerId(1);
//            attachment.setOwnerId(RequestContent.getCurrentUser().getId());
            attachmentId = dao.getRepository().save(attachment).getId();
        } catch (Exception e) {
            log.error("附件保存失败！", e);
            try {
                file.getInputStream().close();
            } catch (IOException ex) {
                log.error("关闭文件流失败", ex);
            }
        }
        return String.valueOf(attachmentId);
    }

    @Override
    public Attachment downloadAttachmentFile(String id) {
        return dao.getMapper().getAttachmentById(id);
    }

    @Override
    public Attachment getAttachmentInfo(String id) {
        return dao.getMapper().getAttachmentInfoById(id);
    }

    @Override
    public void deleteAttachment(String id) {
        dao.getRepository().deleteById(Integer.parseInt(id));
    }

}
