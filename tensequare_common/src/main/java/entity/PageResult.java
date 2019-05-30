package entity;

import java.util.List;

/**
 * @author lhh
 * @date 2019/5/26 15:59
 */
public class PageResult<T> {

    private Long total;   //总记录数
    private List<T> rows; //每页数据集合

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
