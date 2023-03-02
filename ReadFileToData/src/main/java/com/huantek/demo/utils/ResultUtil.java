package com.huantek.demo.utils;


import com.huantek.demo.model.Result;
import com.huantek.demo.model.ResultEnum;

/**
 * @author wyx
 */
public class ResultUtil {


    /**
     * @param @param  object
     * @param @return 设定文件
     * @return Result 返回类型
     * @Title: success
     * @Description: 成功有返回
     */
    public static Result success(Object obj) {
        Result result = new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        result.setData(obj);
        return result;
    }

    /**
     * @param @param  object
     * @param @return 设定文件
     * @return Result 返回类型
     * @Title: success
     * @Description: 成功有返回
     */
    public static Result error(Object obj) {
        Result result = new Result(ResultEnum.SERVER_EXCEPTION.getCode(), ResultEnum.SERVER_EXCEPTION.getMessage());
        result.setData(obj);
        return result;
    }

    /**
     * @param @return 设定文件
     * @return Result 返回类型
     * @Title: success
     * @Description: 成功无返回
     */
    public static Result success() {
        return ResultUtil.success(null);
    }

    /**
     * @param @param object
     * @param @return 设定文件
     * @return Result 返回类型
     * @Title: paramError
     * @Description: 参数不合法
     */
    public static Result paramError(Object obj) {
        Result result = new Result(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage());
        result.setData(obj);
        return result;
    }

    /**
     * @param @param object
     * @param @return 设定文件
     * @Title: paramError
     * @Description: 参数不合法
     */
    public static Result paramError() {
        Result result = new Result(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage());
        return result;
    }

    /**
     * 返回自定义状态码以及msg
     * @param code 状态码
     * @param message 返回信息
     * @return
     */
    public static Result customResultParam(String code, String message) {
        return new Result(code,message);
    }

    /**
     * @return Result 返回类型
     * @Title: paramError
     * @Description: 数据库错误
     */
    public static Result dataBaseError(){
        return new Result(ResultEnum.DATABASE_ERROR.getCode(), ResultEnum.DATABASE_ERROR.getMessage());
    }

    /**
     * @return Result 返回类型
     * @Title: paramError
     * @Description: 数据库错误
     */
    public static Result dataBaseError(String obj){
        Result result = new Result(ResultEnum.DATABASE_ERROR.getCode(),  obj);
        return result;
    }

    /**
     * @return Result 返回类型
     * @Title: paramError
     * @Description: 服务异常
     */
    public static Result serverError(){
        return new Result(ResultEnum.SERVER_EXCEPTION.getCode(), ResultEnum.SERVER_EXCEPTION.getMessage());
    }

}
