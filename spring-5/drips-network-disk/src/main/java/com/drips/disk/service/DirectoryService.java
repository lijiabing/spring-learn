package com.drips.disk.service;

import com.drips.disk.entity.Directory;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * get update queryByPid lazyQuery remove batchDelete
 */
public interface DirectoryService {

    Directory mkdir(String dirName, String currentDirId, HttpSession httpSession);

    Directory renameDir(String name, String id);

    List<String> listDirNames(String dirId);

    List<Directory> listTargetDir(String id, int type);

    boolean deleteAll(List<String> dirIds, List<String> fileIds);

    List<Directory> getDirTree(String currentDirId, Integer type);

    void sortFile(List<String> dirs, List<String> documents);

    void moveToDir(List<String> dirs, List<String> docs, String targetDirId);
}
