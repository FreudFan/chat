package edu.sandau.chat.service;

import edu.sandau.chat.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    /***
     * 保存附件，返回附件id
     * @param file MultipartFile类型文件，直接从前端获取
     * @return 附件表唯一id
     */
    String saveAttachment(MultipartFile file);

    /***
     * 下载文件
     * @param id
     * @return
     */
    Attachment downloadAttachmentFile(String id);

    /***
     * 获取文件基本信息
     * @param id
     * @return
     */
    Attachment getAttachmentInfo(String id);

    /***
     * 删除文件
     * @param id
     */
    void deleteAttachment(String id);
}
