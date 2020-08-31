package com.drips.disk.repository.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description TODO
 * @Author: lijb
 * @Date: 2020-02-27
 * @Time: 17:02
 */
@Component
public class BaseRepositoryMaster {

    @Autowired
    private List<BaseRepository> repositoryList;

    public BaseRepository repository(Class clazz) {
        if (repositoryList == null || repositoryList.size() == 0) {
            return null;
        }
        return repositoryList.stream().filter(p -> p.support(clazz)).findFirst().orElse(null);
    }


}
