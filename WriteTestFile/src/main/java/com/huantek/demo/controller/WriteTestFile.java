package com.huantek.demo.controller;

import com.huantek.demo.service.WriteTestFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("WriteTestFile")
public class WriteTestFile {

    @Autowired
    private WriteTestFileService writeTestFileService;

    @RequestMapping(value = "/writeFile" , method = RequestMethod.GET)
    @ResponseBody
    public String writeFile() throws IOException, InterruptedException {
        String s = writeTestFileService.writeFile();
        return s;
    }
}
