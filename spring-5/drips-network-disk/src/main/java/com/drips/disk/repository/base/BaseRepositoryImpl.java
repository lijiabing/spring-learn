package com.drips.disk.repository.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author: lijb
 * @Date: 2020-02-27
 * @Time: 15:58
 */
@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final Class<T> domainClass;
    private final EntityManager entityManager;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.domainClass = domainClass;
        this.entityManager = entityManager;
    }

    @Override
    public boolean support(Class clazz) {
        return domainClass.getName().equalsIgnoreCase(clazz.getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findListByNativeSql(String sql, Class<T> clzss) {
        return entityManager.createNativeQuery(sql, clzss).getResultList();
    }

    @Override
    public List<T> listHqlBy(Map<String, Object> params, Class<T> clazz) {
        StringBuilder hql = new StringBuilder(String.format("select e from %s e where 1 = 1 ", clazz.getSimpleName()));
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            hql.append(String.format("and %s = :%s ", entry.getKey(), entry.getKey()));
        }
        TypedQuery<T> query = entityManager.createQuery(hql.toString(), clazz);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    @Override
    public List<T> findWhereIn(String whereInKey, List<String> list, Class<T> clazz) {
        if (StringUtils.isEmpty(whereInKey) || (list == null || list.size() == 0)) {
            return new ArrayList<>();
        }
        StringBuilder hql = new StringBuilder(String.format("select e from %s e ", clazz.getSimpleName()));
        hql.append(" where ").append("e.").append(whereInKey).append("  in (  ");
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                hql.append("'").append(list.get(i)).append("'");
            } else {
                hql.append("'").append(list.get(i)).append("'").append(", ");
            }
        }
        hql.append("  )");

        TypedQuery<T> query = entityManager.createQuery(hql.toString(), clazz);
//        query.setParameter(whereInKey, list);
        return query.getResultList();
    }


}
