package com.xiaoyongcai.io.edvancemyself.Pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @ExcelProperty("用户Id")
    private Long id;
    @ExcelProperty("用户名")
    private String username;
    @ExcelProperty("年龄")
    private Integer age;
    @ExcelProperty("邮件")
    private String email;
}
