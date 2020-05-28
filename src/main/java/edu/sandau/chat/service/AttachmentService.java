package edu.sandau.chat.service;

import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    /***
     * 保存附件，返回附件id
     * @param file MultipartFile类型文件，直接从前端获取
     * @return 附件表唯一id
     */
    Integer saveAttachment(MultipartFile file);
}
