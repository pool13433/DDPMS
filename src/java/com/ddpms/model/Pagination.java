
package com.ddpms.model;

public class Pagination {
    private int countRecordAll;
    private int pageCurrent;
    private int paginLimit = 4;
    private int recordCurrent;
    private int recordLimit;
    private int pages;
    private int pageEnd;
    private int pageBegin;
    private String pageUrl;

    public Pagination(String pageUrl, int countRecordAll, int limit, int offset) {
        this.countRecordAll = countRecordAll;
        this.pages = countRecordAll / limit;
        this.pageCurrent = (offset / limit);
        this.recordCurrent = offset;
        this.recordLimit = limit;
        this.pageBegin = ((pageCurrent - this.paginLimit) < 0 ? 0 : (pageCurrent - this.paginLimit));
        this.pageEnd = this.paginLimit + pageCurrent;
        this.pageUrl = pageUrl;
    }

    public Pagination(String pageUrl, int countRecordAll, int limit, int offset, int paginLimit) {
        this.countRecordAll = countRecordAll;
        this.pages = countRecordAll / limit;
        this.pageCurrent = (offset / limit);
        this.paginLimit = (paginLimit / 2);
        this.recordCurrent = offset;
        this.recordLimit = limit;
        this.pageBegin = ((pageCurrent - this.paginLimit) < 0 ? 0 : (pageCurrent - this.paginLimit));
        this.pageEnd = this.paginLimit + pageCurrent;
        this.pageUrl = pageUrl;
    }

    private String getQueryString(String oldQueryString) {
        String[] twinQuery = oldQueryString.split("&offset=");
        if (twinQuery.length > 1) {
            String[] queryTwo = twinQuery[1].split("&");
            return oldQueryString.replace("&offset=" + queryTwo[0], "");
        } else {
            return oldQueryString;
        }
    }

    public int getCountRecordAll() {
        return countRecordAll;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public int getPaginLimit() {
        return paginLimit;
    }

    public int getRecordCurrent() {
        return recordCurrent;
    }

    public int getRecordLimit() {
        return recordLimit;
    }

    public int getPages() {
        return pages;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public int getPageBegin() {
        return this.pageBegin;
    }

    public String getPageUrl() {
        return getQueryString(pageUrl);
    }
}
