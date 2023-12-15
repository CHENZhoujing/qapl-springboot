package com.lg.qapl.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @GetMapping("/download")
    public ResponseEntity<?> downloadTemplate() throws IOException {
        Resource file = new ClassPathResource("templates/template.xlsx");
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=template.xlsx")
                .body(new InputStreamResource(file.getInputStream()));
    }

    public ResponseEntity<?> uploadTemplate() {
        // 实现上传模板逻辑
        // 返回上传结果
        return null;
    }
}
