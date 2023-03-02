package com.huantek.demo.service;

import com.huantek.demo.model.Result;

import java.io.IOException;

public interface ReadDataService  {
    Result getLatestData() throws IOException;
}
