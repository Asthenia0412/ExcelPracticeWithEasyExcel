package com.xiaoyongcai.io.edvancemyself.Controller;

import com.xiaoyongcai.io.edvancemyself.Pojo.User;
import com.xiaoyongcai.io.edvancemyself.Service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException{
        excelService.exportExcel(response);
    }
    @PostMapping("/import")
    public ResponseEntity<List<User>> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(excelService.importExcel(file));
    }

}
