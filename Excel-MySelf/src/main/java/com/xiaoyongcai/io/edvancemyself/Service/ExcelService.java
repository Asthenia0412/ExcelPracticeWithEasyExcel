package com.xiaoyongcai.io.edvancemyself.Service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xiaoyongcai.io.edvancemyself.Pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {
    public void exportExcel(HttpServletResponse response) throws IOException{
        List<User> users = new ArrayList<>();
        users.add(new User(1L,"张三",25,"zhangsan@qq.com"));
        users.add(new User(2L, "李四", 30, "lisi@example.com"));
        users.add(new User(3L, "王五", 28, "wangwu@example.com"));

        // 设置响应头 - 告诉浏览器这是一个Excel文件
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        /*
         * MIME类型说明：
         * application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
         * 表示这是一个Office Open XML格式的Excel文件(.xlsx)
         */

// 设置字符编码为UTF-8，确保中文等特殊字符能正确显示
        response.setCharacterEncoding("utf-8");

// 处理文件名编码，防止中文乱码
        String fileName = URLEncoder.encode("用户数据", "UTF-8").replaceAll("\\+", "%20");
        /*
         * URLEncoder.encode() 对文件名进行URL编码
         * replaceAll("\\+", "%20") 将编码后的+号替换为%20
         * 因为URL编码中空格会被编码为+，但在HTTP头中应该用%20表示空格
         */

// 使用try-with-resources语法确保OutputStream正确关闭
        try (OutputStream outputStream = response.getOutputStream()) {
            // 使用EasyExcel构建Excel写入器
            EasyExcel.write(outputStream, User.class)
                    // 显式指定Excel类型为XLSX格式
                    .excelType(ExcelTypeEnum.XLSX)
                    /*
                     * ExcelTypeEnum.XLSX 表示使用Office 2007+的.xlsx格式
                     * 也可以使用ExcelTypeEnum.XLS表示老式的.xls格式
                     */

                    // 指定工作表名称为"用户列表"
                    .sheet("用户列表")
                    /*
                     * 如果不指定sheet名称，默认会创建"Sheet1"
                     * 可以多次调用.sheet()创建多个工作表
                     */

                    // 执行写入操作，将用户数据写入Excel
                    .doWrite(users);
            /*
             * doWrite() 是实际执行写入的方法
             * users 是要写入的数据集合(List<User>)
             * EasyExcel会自动根据User类的注解(@ExcelProperty)映射字段
             */
        }
        /*
         * try-with-resources 语法说明：
         * 1. 自动管理OutputStream资源
         * 2. 无论是否发生异常，都会在try块结束时自动调用outputStream.close()
         * 3. 等价于传统的try-finally块，但更简洁安全
         */
    }
    public List<User> importExcel(MultipartFile file) throws IOException{
        List<User> users = EasyExcel.read(file.getInputStream())
                .head(User.class)
                .sheet()
                .doReadSync();

        //添加到数据库等业务操作
        return users;
    }
}
