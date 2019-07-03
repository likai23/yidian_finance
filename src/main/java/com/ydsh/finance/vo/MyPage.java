package com.ydsh.finance.vo;

import com.ydsh.generator.common.JsonResult;

public class MyPage<T> {
    //第几页
    private int pageNum;
    //每页多少条数据
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public JsonResult<T> setPageSize(int pageSize) {
        JsonResult<T> result = new JsonResult<T>();
        if(pageSize>500){
//            this.pageSize = 500;
            return result.error("每页最多数据限制500条");
        }else{
            this.pageSize = pageSize;
        }
        return result;
    }
}

