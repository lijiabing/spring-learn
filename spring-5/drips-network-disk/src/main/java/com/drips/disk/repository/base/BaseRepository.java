package com.drips.disk.repository.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author: lijb
 * @Date: 2020-02-27
 * @Time: 16:04
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

    boolean support(Class clazz);

    List<T> findListByNativeSql(String sql, Class<T> clzss);

    List<T> listHqlBy(Map<String, Object> params, Class<T> clazz);

    List<T> findWhereIn(String whereInKey, List<String> list, Class<T> clazz);
}
