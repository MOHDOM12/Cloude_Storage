package com.example.NewPro_cloudeStorage.controller;

import com.example.NewPro_cloudeStorage.model.File;
import com.example.NewPro_cloudeStorage.model.User;
import com.example.NewPro_cloudeStorage.services.FileService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/file")
    public String upload(@RequestParam MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {

        User user = (User) authentication.getPrincipal();
        File file = new File();
        if (fileUpload.isEmpty()){
            model.addAttribute("error", true);
            model.addAttribute("error", "Please choose a file!");
            return "redirect:/result?error";
        }
        if (fileService.isFilenameExist(user.getUserId(), fileUpload.getOriginalFilename())){
            model.addAttribute("error", true);
            model.addAttribute("error", "File name Exist!");
            return "redirect:/result?error";
        }
        file.setUserid(user.getUserId());
        file.setFilesize(Long.toString(fileUpload.getSize()));
        file.setFilename(fileUpload.getOriginalFilename());
        file.setContenttype(fileUpload.getContentType());
        file.setFiledata(fileUpload.getBytes());

        fileService.addFile(file,user.getUserId());

        model.addAttribute("success", true);
        return "redirect:/result?success";
    }

    @GetMapping("/file/delete")
    public String deleteFile(@RequestParam("fileId") Integer fileId, Model model){
        if (fileId > 0){
            fileService.deleteFile(fileId);
            model.addAttribute("success", true);
            return "redirect:/result?success";
        }
        model.addAttribute("error", true);
        model.addAttribute("error", "There is error deleting the file, try again!");
        return "redirect:/result?error";
    }

    @GetMapping("/file/download")
    public ResponseEntity<Resource> download(@RequestParam("filename") String filename,
                                             HttpServletResponse resp, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        File file = fileService.getFile(filename, user.getUserId());


        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename()   + "\"")
                .body(new ByteArrayResource(file.getFiledata()));

    }
}
