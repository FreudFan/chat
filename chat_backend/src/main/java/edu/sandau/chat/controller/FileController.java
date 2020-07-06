package edu.sandau.chat.controller;

import edu.sandau.chat.entity.Attachment;
import edu.sandau.chat.exception.AttachmentException;
import edu.sandau.chat.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/up")
    public ResponseEntity<String> upLoadAttachment(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            throw new AttachmentException("上传文件失败，文件为空 !");
        }
        String attachmentId = attachmentService.saveAttachment(file);
        return new ResponseEntity<>(attachmentId, HttpStatus.OK);
    }

    @GetMapping("/down")
    public void downloadAttachment(@RequestParam("id") String id, HttpServletResponse response) {
        Attachment file = attachmentService.downloadAttachmentFile(id);
        try {
            String fileName = file.getName() + "." + file.getFileType();
            fileName = URLEncoder.encode(fileName, "UTF-8");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.addHeader("Content-Length", "" + file.getContent().length);
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(file.getContent());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("下载文件 [id: {}] 失败!! ", file.getId());
            throw new AttachmentException("下载文件失败!");
        }
    }

    @GetMapping("/photo")
    public void getPhotoById(@RequestParam("id") String id, HttpServletResponse response) {
        Attachment file = attachmentService.downloadAttachmentFile(id);
        try {
            response.setContentType("image/jpeg");
            response.setCharacterEncoding("UTF-8");
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(file.getContent());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("显示图片 [id: {}] 失败!! ", file.getId());
            throw new AttachmentException("下载文件失败!");
        }
    }

}
