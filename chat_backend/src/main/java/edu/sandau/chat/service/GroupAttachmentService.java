package edu.sandau.chat.service;

import edu.sandau.chat.vo.GroupAttachmentVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GroupAttachmentService {

    /***
     * 上传群文件
     * @param groupId
     * @param file
     * @return
     */
    Integer uploadGroupAttachment(Integer groupId, MultipartFile file);

    /***
     * 展示群文件列表
     * @param groupId
     * @return
     */
    List<GroupAttachmentVO> showGroupAttachmentList(Integer groupId);

    /***
     * 删除群文件
     * @param groupId
     * @param fileId
     * @return
     */
    boolean deleteGroupAttachment(Integer groupId, String fileId);

}
