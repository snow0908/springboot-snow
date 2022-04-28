package com.snow.common.pojo;


import com.alibaba.fastjson.JSON;
import com.snow.common.eumn.ResultStatus;
import com.snow.common.util.JsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gushao
 * @CreateDate: 2018/12/19
 * @Description: 自定义响应结构
 * @Version: 1.0
 */
public class PublicResult<T> implements Serializable{

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private T data;

    public static PublicResult success () {
        return new PublicResult(ResultStatus.SUCCESS.getValue(), ResultStatus.SUCCESS.getReasonPhrase(), null);
    }

    public static<T> PublicResult<T> success (String msg, T data) {
        return new PublicResult(ResultStatus.SUCCESS.getValue(), msg, data);
    }

    public static<T> PublicResult<T> success (T data) {
        return new PublicResult(ResultStatus.SUCCESS.getValue(), ResultStatus.SUCCESS.getReasonPhrase(), data);
    }

    public static PublicResult failed () {
        return new PublicResult(ResultStatus.FAILED.getValue(), ResultStatus.FAILED.getReasonPhrase(), null);
    }

    public static<T> PublicResult<T> failed (String msg, T data) {
        return new PublicResult(ResultStatus.FAILED.getValue(), msg, data);
    }

    public static<T> PublicResult<T> failed( T data) {
        return new PublicResult(ResultStatus.FAILED.getValue(), ResultStatus.FAILED.getReasonPhrase(), data);
    }

    public static<T> PublicResult<T> build (Integer status, String msg, T data) {
        return new PublicResult(status, msg, data);
    }

    public static PublicResult buildAddSucess () {
        return new PublicResult(ResultStatus.SUCCESS_ADD.getValue(), ResultStatus.SUCCESS_ADD.getReasonPhrase(), null);
    }
    public static<T> PublicResult<T> buildAddSucess (T data) {
        return new PublicResult(ResultStatus.SUCCESS_ADD.getValue(), ResultStatus.SUCCESS_ADD.getReasonPhrase(), data);
    }
    public static PublicResult buildDeleteSucess () {
        return new PublicResult(ResultStatus.SUCCESS_DELETE.getValue(), ResultStatus.SUCCESS_DELETE.getReasonPhrase(), null);
    }
    public static<T> PublicResult<T> buildDeleteSucess (T data) {
        return new PublicResult(ResultStatus.SUCCESS_DELETE.getValue(), ResultStatus.SUCCESS_DELETE.getReasonPhrase(), data);
    }
    public static PublicResult buildUpdateSucess () {
        return new PublicResult(ResultStatus.SUCCESS_UPDATE.getValue(), ResultStatus.SUCCESS_UPDATE.getReasonPhrase(), null);
    }
    public static<T> PublicResult<T> buildUpdateSucess (T data) {
        return new PublicResult(ResultStatus.SUCCESS_UPDATE.getValue(), ResultStatus.SUCCESS_UPDATE.getReasonPhrase(), data);
    }
    public static PublicResult buildQuerySucess () {
        return new PublicResult(ResultStatus.SUCCESS_QUERY.getValue(), ResultStatus.SUCCESS_QUERY.getReasonPhrase(), null);
    }
    public static<T> PublicResult<T> buildQuerySucess (T data) {
        return new PublicResult(ResultStatus.SUCCESS_QUERY.getValue(), ResultStatus.SUCCESS_QUERY.getReasonPhrase(), data);
    }

    public static PublicResult buildAddFailed () {
        return new PublicResult(ResultStatus.FAILED_ADD.getValue(), ResultStatus.FAILED_ADD.getReasonPhrase(), null);
    }
    public static<T> PublicResult<T> buildAddFailed (T data) {
        return new PublicResult(ResultStatus.FAILED_ADD.getValue(), ResultStatus.FAILED_ADD.getReasonPhrase(), data);
    }
    public static PublicResult buildDeleteFailed () {
        return new PublicResult(ResultStatus.FAILED_DELETE.getValue(), ResultStatus.FAILED_DELETE.getReasonPhrase(), null);
    }
    public static<T> PublicResult<T> buildDeleteFailed (T data) {
        return new PublicResult(ResultStatus.FAILED_DELETE.getValue(), ResultStatus.FAILED_DELETE.getReasonPhrase(), data);
    }
    public static PublicResult buildUpdateFailed () {
        return new PublicResult(ResultStatus.FAILED_UPDATE.getValue(), ResultStatus.FAILED_UPDATE.getReasonPhrase(), null);
    }
    public static<T> PublicResult<T> buildUpdateFailed (T data) {
        return new PublicResult(ResultStatus.FAILED_UPDATE.getValue(), ResultStatus.FAILED_UPDATE.getReasonPhrase(), data);
    }
    public static PublicResult buildQueryFailed () {
        return new PublicResult(ResultStatus.FAILED_QUERY.getValue(), ResultStatus.FAILED_QUERY.getReasonPhrase(), null);
    }
    public static<T> PublicResult<T> buildQueryFailed (T data) {
        return new PublicResult(ResultStatus.FAILED_QUERY.getValue(), ResultStatus.FAILED_QUERY.getReasonPhrase(), data);
    }


    public PublicResult() {

    }

    public PublicResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = null;
    }

    public PublicResult(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public boolean ok(){
        if (this.status == ResultStatus.SUCCESS.getValue()){
            return true;
        }else if (this.status == ResultStatus.SUCCESS_ADD.getValue()){
            return true;
        }else if (this.status == ResultStatus.SUCCESS_DELETE.getValue()){
            return true;
        }else if (this.status == ResultStatus.SUCCESS_UPDATE.getValue()){
            return true;
        }else if (this.status == ResultStatus.SUCCESS_QUERY.getValue()){
            return true;
        }
        return false;
    }

    public <F> F getObject(Class<F> clazz){
        return JsonUtil.parseObject(JsonUtil.toJson(this.data), clazz);
    }

    public <F> F getFailedObject(Class<F> clazz){
        if (!this.ok()){
            return JsonUtil.parseObject(JsonUtil.toJson(this.data), clazz);
        }
        return null;
    }

    public <F> F getSucessObject(Class<F> clazz){
        if (this.ok()){
            return JsonUtil.parseObject(JsonUtil.toJson(this.data), clazz);
        }
        return null;
    }

    public <F> List<F> getListObject(Class<F> clazz){
        return JsonUtil.parseList(JsonUtil.toJson(this.data), clazz);
    }

    public <F> List<F> getFailedListObject(Class<F> clazz){
        if (!this.ok()){
            return JsonUtil.parseList(JsonUtil.toJson(this.data), clazz);
        }
        return new ArrayList<>();
    }

    public <F> List<F>getSucessListObject(Class<F> clazz){
        if (this.ok()){
            return JsonUtil.parseList(JsonUtil.toJson(this.data), clazz);
        }
        return new ArrayList<>();
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static PublicResult format(String json) {
        return JSON.parseObject(json, PublicResult.class);
    }

    @Override
    public String toString() {
        return "PublicResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

