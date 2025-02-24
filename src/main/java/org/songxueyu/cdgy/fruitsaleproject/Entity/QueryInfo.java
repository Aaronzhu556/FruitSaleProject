package org.songxueyu.cdgy.fruitsaleproject.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryInfo {
    private String querytext;
    private int  pagenum;
    private int  pagesize;
    private String querydata;
    private String querycateid;

    QueryInfo(){}

    public QueryInfo(String querytext, int pagenum, int pagesize) {
        this.querytext = querytext;
        this.pagenum = pagenum;
        this.pagesize = pagesize;
    }

    public QueryInfo(String querytext, int pagenum, int pagesize, String querydata) {
        this.querytext = querytext;
        this.pagenum = pagenum;
        this.pagesize = pagesize;
        this.querydata = querydata;
    }

    public QueryInfo(String querytext, int pagenum, int pagesize, String querydata, String querycateid) {
        this.querytext = querytext;
        this.pagenum = pagenum;
        this.pagesize = pagesize;
        this.querydata = querydata;
        this.querycateid = querycateid;
    }
}
