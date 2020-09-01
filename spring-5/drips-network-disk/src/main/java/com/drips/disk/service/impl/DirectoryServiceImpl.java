package com.drips.disk.service.impl;

import com.drips.disk.entity.Directory;
import com.drips.disk.entity.Document;
import com.drips.disk.repository.base.BaseRepository;
import com.drips.disk.repository.base.BaseRepositoryMaster;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = RuntimeException.class)
@Service
public class DirectoryServiceImpl {


    @Autowired
    private BaseRepositoryMaster baseRepositoryMaster;

//    @Autowired
//    private DocumentServiceImpl documentServiceImpl;

    //创建目录
    @SuppressWarnings("unchecked")
    public Directory mkdir(String name, Long dirId) {
//        String currentUID = userRoleService.getCurrentUID(httpSession);
//        if (StringUtils.isBlank(currentUID)) {
//            throw new MyServiceException("创建目录失败：当前用户id为空");
//        }
//        WPUser user = dataset.get(currentUID, WPUser.class);
//        if (user == null) {
//            throw new MyServiceException("创建目录失败：当前用户不存在");
//        }
        String currentUID = "admin";
        name = name.toLowerCase();
        if (dirId == null) {
            throw new RuntimeException("创建目录失败：父目录不存在!");
        }
        Directory directory = validateName(name, dirId);
        //保存记录
        Directory dir = new Directory();
        dir.setName(name);
        dir.setUserId(currentUID);
        dir.setParentDirectory(directory);
        dir.setGmtCreated(new Date());
        BaseRepository<Directory, Long> baseRepository = baseRepositoryMaster.repository(Directory.class);
        Directory d = baseRepository.save(dir);
        if (d == null) {
            throw new RuntimeException("创建目录失败！");
        }
        return d;
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Directory validateName(String name, Long dirId) throws RuntimeException {
        BaseRepository<Directory, Long> baseRepository = baseRepositoryMaster.repository(Directory.class);
        Directory directory = null;
        try {
            directory = baseRepository.findById(dirId).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (directory == null) {
            throw new RuntimeException("获取目录信息失败！");
        }

        //查找同一级别下是否有相同文件的目录或文件
        List<String> dirNames = directory.getDirectoryList().stream().map(Directory::getName).collect(Collectors.toList());
        ;
        if (dirNames.stream().anyMatch(e -> e.equalsIgnoreCase(name))) {
            throw new RuntimeException("创建目录失败：同目录下存在相同名称目录");
        }
        List<String> docNames = directory.getDocumentList().stream().map(Document::getName).collect(Collectors.toList());
        if (docNames.stream().anyMatch(e -> e.equalsIgnoreCase(name))) {
            throw new RuntimeException("创建目录失败：同目录下存在相同名称文件");
        }
        return directory;
    }
//
//    //重命名目录
//    public Directory renameDir(String name, String id) throws MyServiceException {
//        Directory dir = dataset.get(id, Directory.class);
//        if (dir == null) {
//            throw new MyServiceException("重命名失败, 目录不存在");
//        }
//        name = name.toLowerCase();
//        String pid = dir.getPid();
//        validateName(name, pid);
//        dir.setName(name);
//        dir.setModifyTime(new Date());
//        dataset.update(dir);
//        return dir;
//    }
//
//    //查询某一目录下的所有目录名
//    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
//    @SuppressWarnings("unchecked")
//    public List<String> listDirNames(Long dirId) {
//        List<String>        dirNames = null;
//        BaseRepository<Directory,Long> baseRepository=baseRepositoryMaster.repository(Directory.class);
//        Directory directory=null;
//        try {
//            directory = baseRepository.findById(dirId).orElse(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (directory != null && directory.getDirectoryList()!=null) {
//            dirNames = directory.getDirectoryList().stream().map(Directory::getName).collect(Collectors.toList());
//        }
//        return dirNames == null || dirNames.isEmpty() ? null : dirNames;
//    }
//
//
////    //查询某一目录下的所有目录名
////    @Transactional(readOnly = true)
////    public List<Directory> listDir(String pid) throws Exception {
////        Map<String,Object> paramMap = new HashMap<>();
////        paramMap.put("pid", pid);
////        List<Directory> directories = null;
////        directories = queryService.query("listDirNameByPid2", paramMap, Directory.class, 0, 0);
////        for (Directory dir:directories){
////            List<Directory> dirList = dir.getDirectoryList();
////            if(dirList != null) {
////                Hibernate.initialize(dirList);
////                for (Directory d : dirList) {
////                    d.setDocumentList(null);
////                    d.setDirectoryList(null);
////                }
////            }
////            List<Document> docList = dir.getDocumentList();
////            if(docList != null) {
////                Hibernate.initialize(docList);
////            }
////        }
////
////        return directories;
////    }
//
//    /**
//     * @param id
//     * @param type 1- 资料库，0-其他
//     * @return
//     * @throws Exception
//     */
//
//    @Transactional(readOnly = true)
//    @AuthoxResource(value = "yw_zlk",access = "manage")
//    public List<Directory> listTargetDir(String id, int type) throws Exception {
//        try {
//            Map<String, Object> paramMap = new HashMap<>();
//            paramMap.put("id", id);
//            paramMap.put("type", type);
//            List<Directory> list = queryService.query("listTargetDir", paramMap, Directory.class, 0, 0);
//
//            for (Directory d : list) {
//                List<Directory> directories = d.getDirectoryList();
//
//                //过滤掉null值并更新数据库
////                synchronized (directories){
////                    for(Directory dir : directories){
////                        if(dir == null){
////                            directories.remove(dir);
////                        }
////                    }
////                }
//
////                dataset.update(d);
//
//                if (directories != null) {
//                    Hibernate.initialize(directories);
//                    for (Directory dd : directories) {
//                        if (dd != null) {
//                            dd.setDirectoryList(null);
//                            dd.setDocumentList(null);
//                        }
//                    }
//                }
//                List<Document> documents = d.getDocumentList();
//                Hibernate.initialize(documents);
////                Collections.sort(directories);
//
////                if (directories != null && directories.size() > 0) {
////                    for (int i = directories.size() - 1; i >= 0; i--) {
////                        if (directories.get(i).getName().equals("工作制度")) {
////                            Directory temp = directories.get(i);
////                            for (int j = i; j >= 1; j--) {
////                                directories.set(j, directories.get(j - 1));
////                            }
////                            directories.set(0, temp);
////                        }
////
////
////                    }
////                }
//
//            }
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public boolean deleteAll(List<String> dirIds, List<String> fileIds) throws Exception {
//        for (int i = 0; i < dirIds.size(); i++) {
//            Directory targetDirectry = dataset.get(dirIds.get(i), Directory.class);
//            if (targetDirectry == null) {
//                throw new MyServiceException("删除目录失败：该目录不存在");
//            }
//            Directory parent = dataset.get(targetDirectry.getPid(), Directory.class);
//            if (parent == null) {
//                throw new MyServiceException("删除目录失败：父目录不存在");
//            }
//            List<Directory> list = parent.getDirectoryList();
//
//            list.remove(targetDirectry);
//            dataset.remove(dirIds.get(i), "com.sucsoft.wupu.entity.Directory");
//            dataset.update(parent);
//        }
//
//        documentServiceImpl.batchDelete(fileIds);
//        return true;
//    }
//
//
//
//    //格式化文件大小
//    public String formatSize(Long size) {
//        if (size == null || size <= 0) {
//            return "0B";
//        }
//        String[]   danwei      = {"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB", "BB"};
//        int        danweiIndex = 0;
//        BigDecimal s           = new BigDecimal(size.toString());
//        BigDecimal radix       = new BigDecimal("1024");
//
//        while (s.compareTo(radix) >= 0) {
//            s = s.divide(radix, 2, BigDecimal.ROUND_HALF_UP);
//            danweiIndex++;
//        }
//        return s.toString() + danwei[danweiIndex];
//    }
//
//    //    =================== 新增功能 ======================
//    //获取文件夹树
//    @Transactional(readOnly = true)
//    public List<Directory> getDirTree(String currentDirId, Integer type) throws Exception{
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("id", currentDirId);
//        paramMap.put("type", type);
//        List<Directory> list = queryService.query("listTargetDir", paramMap, Directory.class, 0, 0);
//        if(list.size() == 0 || list == null){
//            throw new MyServiceException("目录不存在");
//        }
//        List<Directory> directories = list.get(0).getDirectoryList();
//        Hibernate.initialize(directories);
//        for (Directory dd : directories) {
//            if (dd != null) {
//                dd.setDirectoryList(null);
//                dd.setDocumentList(null);
//            }
//        }
//        return directories;
//    }
//
//    //排序
//    @Transactional(rollbackFor = {RuntimeException.class,MyServiceException.class})
//    public void sortFile(List<String> dirs, List<String> documents) throws Exception{
//        if(dirs != null && dirs.size() > 0){
//            for(int i = 0 ; i < dirs.size() ; i++){
//                Query query = dataset.createNativeQuery("update directory set directory_index = :index where id = :id");
//                query.setParameter("index",i).setParameter("id",dirs.get(i));
//                query.executeUpdate();
//            }
//        }
//        if(documents != null && documents.size() > 0){
//            for(int i = 0 ; i < documents.size() ; i++){
//                dataset.createNativeQuery("update document set document_index = :index where id = :id").setParameter("index",i).setParameter("id",documents.get(i)).executeUpdate();
//            }
//        }
//    }
//
//    //移动到指定文件夹下
//    @Transactional(rollbackFor = {RuntimeException.class,MyServiceException.class})
//    public void moveToDir(List<String> dirs, List<String> docs, String targetDirId) throws Exception{
//
//        //获取目标目录下的文件夹名称集合以及文件名称集合
//        Map<String,Object> map = new HashMap<>();
//        map.put("dirid",targetDirId);
//        List<Map> targetDirNameMaps = queryService.query("listTargetDirNames",map,Map.class,0,0);
//        List<Map> targetDocNameMaps = queryService.query("listTargetDocNames",map,Map.class,0,0);
//        List<String> targetDirNames = new ArrayList<>();
//        List<String> targetDocNames = new ArrayList<>();
//        for(Map m : targetDirNameMaps){
//            targetDirNames.add(m.get("name").toString());
//        }
//        for(Map m : targetDocNameMaps){
//            targetDocNames.add(m.get("name").toString());
//        }
//        //文件夹移动
//        if(dirs != null && dirs.size() > 0){
//            for(String dirid : dirs){
//                Directory targetDirectory = dataset.get(dirid,Directory.class);
//                String oldpid = targetDirectory.getPid();
//                String name = targetDirectory.getName();
//                if(targetDirId.equals(oldpid)){
//                    throw new MyServiceException("目标文件夹不能是本文件夹");
//                }
//                if(targetDirNames.contains(name)){
//                    throw new MyServiceException("目标文件夹下有重名文件夹，移动失败");
//                }
//
//                targetDirectory.setPid(targetDirId);
//                dataset.update(targetDirectory);
//
//                //维护关系，新目录下增加被移动的文件夹
//                Directory newDirectory = dataset.get(targetDirId,Directory.class);
//                List<Directory> newDirectories = newDirectory.getDirectoryList();
//                if (newDirectories == null) {
//                    newDirectories = new ArrayList<>();
//                }
//                newDirectories.add(targetDirectory);
//                newDirectory.setDirectoryList(newDirectories);
//                Directory b = (Directory) dataset.update(newDirectory);
//                System.out.println();
//
//                //维护关系，对文件夹进行重新排序
//                Map<String,Object> params = new HashMap<>();
//                params.put("pid",oldpid);
//                List<Directory> directories = queryService.query("listDirByPid",params,Directory.class,0,0);
//                for(int i = 0 ; i < directories.size() ; i++){
//                    String id = directories.get(i).getId();
//                    dataset.createNativeQuery("update directory set directory_index = :index where id = :id").setParameter("index",i).setParameter("id",id).executeUpdate();
//                }
//
//            }
//        }
//        //文件移动
//        if(docs != null && docs.size() > 0){
//            for(String docid : docs){
//                Document targetDoc = dataset.get(docid,Document.class);
//                String name = targetDoc.getName();
//                String oldDirId = targetDoc.getDirId();
//                if(targetDirId.equals(oldDirId)){
//                    throw new MyServiceException("目标文件夹不能是本文件夹");
//                }
//                if(targetDocNames.contains(name)){
//                    throw new MyServiceException("目标文件夹下有同名文件，移动失败");
//                }
//                targetDoc.setDirId(targetDirId);
//                dataset.update(targetDoc);
//
//                //维护关系，新目录下增加
//                Directory newDirectory = dataset.get(targetDirId,Directory.class);
//                List<Document> newDocuments = newDirectory.getDocumentList();
//                if(newDocuments == null){
//                    newDocuments = new ArrayList<>();
//                }
//                newDocuments.add(targetDoc);
//                newDirectory.setDocumentList(newDocuments);
//                dataset.update(newDirectory);
//
//                //维护关系，对文件进行重新排序
//                Map<String,Object> params = new HashMap<>();
//                params.put("dirId",oldDirId);
//                List<Document> documents = queryService.query("listDocByDirid",params,Document.class,0,0);
//                for(int i = 0 ; i < documents.size() ; i++){
//                    String id = documents.get(i).getId();
//                    dataset.createNativeQuery("update document set document_index = :index where id = :id").setParameter("index",i).setParameter("id",id).executeUpdate();
//                }
//            }
//        }
//    }
//
//


//    /**
//     * 批量删除目录
//     * @param dirIds 目录列表
//     *
//     */
//
//    public boolean batchDelete(List<String> dirIds, Boolean isAlldelete)  {
//
//        if(isAlldelete){//返回true则删除成功，false 删除失败
//
//            try{
//                for (int i=0;i<dirIds.size();i++){
//                    Directory targetDirectry=dataset.get(dirIds.get(i),Directory.class);
//                    Directory parent=dataset.get(targetDirectry.getPid(),Directory.class);
//                    if (parent == null) {
//                        throw new MyServiceException("创建目录失败：父目录不存在");
//                    }
//                    List<Directory> list=parent.getDirectoryList();
//
//                    list.remove(targetDirectry);
//                    dataset.remove(dirIds.get(i),"com.sucsoft.wupu.entity.Directory");
//                    dataset.update(parent);
//                }
//                return true;
//            }catch (Exception e){
//                return false;
//            }
//        }else{//返回目录中是否有文件的结果，true-存在，false-不存在
//            return hasFileInDirectory(dirIds);
//        }
//    }

//    /**
//     * 判断目录中是否存在文件，true存在，false，不存在
//     * @param dirIds
//     * @return
//     */
//    public boolean hasFileInDirectory(List<String> dirIds){
//        try {
//            HashMap<String, Object> params = new HashMap<String, Object>();
//            for (int i = 0; i < dirIds.size(); i++) {
//                params.put("id", dirIds.get(i));
//                List<Directory> list = queryService.query("listByDir", params, Directory.class, 0, 0);
//                for (Directory d : list) {
//                    List<Directory> directories = d.getDirectoryList();
//                    if (directories != null) {
//                        Hibernate.initialize(directories);
//                    }
//                    List<Document> documents = d.getDocumentList();
//                    Hibernate.initialize(documents);
//                }
//                delete(list);
//            }
//        }catch (Exception e){
//            return true;
//        }
//        return false;
//    }


//    public void delete(List<Directory> directorys) throws Exception {
//        if(directorys!=null&&directorys.size()>0){
//            for(int i=0;i<directorys.size();i++){
//                Directory directory=directorys.get(i);
//                if(directory.getDocumentList()!=null&&directory.getDocumentList().size()>0){
//                    System.out.println("该文件夹下存在文件");
//                    throw new Exception("该文件夹下存在文件");
//                }else{
//                    delete(directory.getDirectoryList());
//                }
//            }
//        }
//
//    }
}
