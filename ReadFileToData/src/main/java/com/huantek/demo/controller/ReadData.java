package com.huantek.demo.controller;

import com.huantek.demo.model.Result;
import com.huantek.demo.service.ReadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("ReadData")
public class ReadData {

    @Autowired
    private ReadDataService readDataService;

    @RequestMapping(value = "/getLatestData" , method = RequestMethod.GET)
    @ResponseBody
    public Result getLatestData() throws IOException {
        Result latestData = readDataService.getLatestData();
        return latestData;
    }
}
