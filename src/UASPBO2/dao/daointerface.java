package UASPBO2.dao;
/**
 * @author - AbednegoSteven 1972009
 */

import java.util.List;

public interface daointerface<T> {
    public int addData(T data);
    public int delData(T data);
    public int updateData(T data);
    List<T> getAll();
}
