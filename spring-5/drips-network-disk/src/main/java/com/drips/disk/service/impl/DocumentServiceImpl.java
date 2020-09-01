//package com.drips.disk.service.impl;
//
//import com.drips.disk.entity.Directory;
//import com.drips.disk.entity.Document;
//import com.drips.disk.repository.base.BaseRepository;
//import com.drips.disk.repository.base.BaseRepositoryMaster;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpSession;
//import java.io.Serializable;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class DocumentServiceImpl {
//
//
//    @Autowired
//    private BaseRepositoryMaster baseRepositoryMaster;
//
////
////    @Autowired
////    private PojoDataset        dataset;
////    @Autowired
////    private UnsafeQueryService queryService;
////    @Autowired
////    private DirectoryService   directoryService;
////    @Autowired
////    private UserRoleService    userRoleService;
////
////    private final Logger logger = LoggerFactory.getLogger(this.getClass());
////
////    //创建文件
////    public Document createDoc(String name, String dirId, String path, Long size, HttpSession httpSession) throws MyServiceException {
////        WPUser currentUser = userRoleService.getCurrentUser(httpSession);
////        if (currentUser == null) {
////            throw new MyServiceException("当前用户不存在");
////        }
////        String currentUID  = currentUser.getId();
////        String creatorName = currentUser.getName();
////
////        if (StringUtils.isBlank(dirId)) {
//////            dirId = "root";
////            dirId = "2c92ddce6029631a01602964c26e0000";
////        }
////        name = name.toLowerCase();
////        validateName(name, dirId);
////        String fileType = "unknown";
////        if (name.lastIndexOf(".") > 0) {
////            fileType = name.substring(name.lastIndexOf(".") + 1);
////            name = name.substring(0, name.lastIndexOf("."));
////        }
////
////        //保存记录
////        Document doc = new Document();
////        doc.setName(name);
////        doc.setCreateTime(new Date());
////        doc.setSize(size);
////        doc.setPath(path);
////        doc.setFileType(fileType);
////        doc.setCreatorId(currentUID);
////        doc.setCreatorName(creatorName);
////        doc.setModifyTime(new Date());
////        doc.setDirId(dirId);
////        Serializable id = dataset.save(doc);
////        //关系维护
//////        if(! dirId.equals("root")) {
////        Directory parentDir = dataset.get(dirId, Directory.class);
////        if (parentDir == null) {
////            throw new MyServiceException("创建目录失败：父目录不存在");
////        }
////        List<Document> docList = parentDir.getDocumentList();
////        if (docList == null) {
////            docList = new ArrayList<>();
////        }
////        docList.add(doc);
////        parentDir.setDocumentList(docList);
////        dataset.update(parentDir);
//////        }
////        return dataset.get(id, Document.class);
////    }
////
////    //检验name合法性
////    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
////    public void validateName(String name, String dirId) throws MyServiceException {
////        //是否有敏感词
////
////        //是否有同名文件（结合后缀）
////        List<String> dirNames = directoryService.listDirNames(dirId);
////        if (dirNames != null && CommonsUtil.containsIgnoreCase(dirNames, name)) {
////            throw new MyServiceException("创建文件失败：同目录下存在相同名称目录");
////        }
////        List<String> docNames = listDocNames(dirId);
////        if (docNames != null && CommonsUtil.containsIgnoreCase(docNames, name)) {
////            throw new MyServiceException("创建文件失败：同目录下存在相同名称文件");
////        }
////    }
////
////    //重命名文件
////    public Document renameDoc(String name, String id) throws MyServiceException {
////        Document doc = dataset.get(id, Document.class);
////        if (doc == null) {
////            throw new MyServiceException("重命名失败, 文件不存在");
////        }
////        name = name.toLowerCase();
////        String dirId = doc.getDirId();
////        validateName(name, dirId);
//////        String fileType = "unknown";
//////        if(name.lastIndexOf(".")> 0){
//////            fileType = name.substring(name.lastIndexOf("."));
//////        }
////        doc.setName(name);
//////        doc.setFileType(fileType);
////        doc.setModifyTime(new Date());
////        dataset.update(doc);
////        return doc;
////    }
////
////    public Directory getDirectory(String did) {
////        Directory directory = dataset.get(did, Directory.class);
////        return directory;
////    }
//
//    @SuppressWarnings("unchecked")
//    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
//    public List<String> listDocNames(String dirId) {
//        List<String>        docNames = null;
//        BaseRepository<Directory,Long> baseRepository=baseRepositoryMaster.repository(Document.class);
//
//        try {
//            baseRepository.fin
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (map != null && !map.isEmpty()) {
//            docNames = map.stream().filter(e -> e != null).map(e -> ((String) e.get("docName")).concat(".").concat((String) e.get("fileType"))).collect(Collectors.toList());
//        }
//        return docNames == null || docNames.isEmpty() ? null : docNames;
//    }
//
//    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
//    public List<Document> listByOptions(String name, String type) throws Exception {
//        Map<String, Object> paramMap = new HashMap<>();
//        if (StringUtils.isNotBlank(name)) {
//            paramMap.put("name", "%" + name + "%");
//        }
//        if (StringUtils.isNotBlank(type)) {
//            paramMap.put("type", type);
//        }
//        List<Document> listDocument = null;
//
//        listDocument = queryService.query("listDocument", paramMap, Document.class, 0, 0);
//
//        return (listDocument == null || listDocument.isEmpty()) ? null : listDocument;
//    }
//
//    //删除文件
//    public String delete(String id) {
//        Document       doc     = dataset.get(id, Document.class);
//        String         dirId   = doc.getDirId();
//        Directory      dir     = dataset.get(dirId, Directory.class);
//        List<Document> docList = dir.getDocumentList();
//        if (docList != null && !docList.isEmpty()) {
//            docList.remove(doc);
//            dir.setDocumentList(docList);
//            dataset.update(dir);
//        }
//        dataset.remove(doc);
//        return doc.getName() + "." + doc.getFileType();
//    }
//
//    //批量删除文件
//    public List<String> batchDelete(List<String> docIds) {
//        List<String> list = new ArrayList<>();
//        for (String id : docIds) {
//            try {
//                String sucName = delete(id);
//                list.add(sucName);
//            } catch (Exception e) {
//                logger.warn("批量删除文件中，删除文件[{}]失败：{}", id, e.getMessage());
//                continue;
//            }
//        }
//        return list == null || list.isEmpty() ? null : list;
//    }
//
//    /**
//     * 获取文件的层级路径
//     * @param id 文件id
//     * @return
//     */
//    public String listDocDir(String id) throws Exception{
//        StringBuilder sb = new StringBuilder();
//        Map<String,Object> params = new HashMap<>();
//        params.put("id",id);
//        List<Map> result = queryService.query("getDirId",params,Map.class,0,0);
//        String dirid = "";
//        if(result != null && result.size() > 0){
//            dirid = result.get(0).get("dirid") + "";
//        }
//        params.put("id",dirid);
//        List<Map> directory = queryService.query("getDirPath",params,Map.class,0,0);
//        String pid = directory.get(0).get("pid") + "";
//        String pathName = directory.get(0).get("name") + "";
//        if(!pid.equals("2c92ddce6029631a01602964c26e0000")){
//            sb.append(pathName);
//            do{
//                params.put("pid",pid);
//                List<Map> temp = queryService.query("getDirPid",params,Map.class,0,0);
//                pid = temp.get(0).get("pid") + "";
//                pathName = temp.get(0).get("name") + "";
//                sb.insert(0,pathName + "/");
//            }while (!pid.equals("2c92ddce6029631a01602964c26e0000"));
//            return sb.toString();
//        }else{
//            return pathName;
//        }
//    }
//}
