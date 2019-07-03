package com.ydsh.finance.common.mybatisPlusAddFunction.mySqlInject.MySqlEnum;

public enum MySqlEnum {
    INSERT_ONE("insert", "插入一条数据（选择字段插入）", "<script>\nINSERT INTO %s %s VALUES %s\n</script>"),
    FuzzyQuery_By_Page("fuzzyQueryByPage", "分页模糊查询", "<script>\nDELETE FROM %s WHERE %s=#{%s}\n</script>");
    private final String method;
    private final String desc;
    private final String sql;

    private MySqlEnum(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }
    public String getMethod() {
        return this.method;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getSql() {
        return this.sql;
    }
}
