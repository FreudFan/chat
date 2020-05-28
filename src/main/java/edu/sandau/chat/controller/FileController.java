package edu.sandau.chat.controller;

import edu.sandau.chat.exception.AttachmentException;
import edu.sandau.chat.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/up")
    public ResponseEntity upLoadAttachment(@RequestParam MultipartFile file) throws IOException {
        if(file == null) {
            throw new AttachmentException("上传文件失败，文件为空 !");
        }
        Integer attachmentId = attachmentService.saveAttachment(file);
        return new ResponseEntity<>(attachmentId, HttpStatus.OK);
    }


}
