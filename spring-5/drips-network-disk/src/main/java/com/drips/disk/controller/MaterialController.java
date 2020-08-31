//package com.drips.disk.controller;
//
//
//import com.drips.disk.entity.Directory;
//import com.drips.disk.service.impl.DirectoryServiceImpl;
//import com.drips.disk.service.impl.DocumentServiceImpl;
//import io.swagger.annotations.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/materials")
//@Api(value = "资料管理",tags = {"material"})
//public class MaterialController {
//    @Autowired
//    private DirectoryServiceImpl directoryServiceImpl;
//    @Autowired
//    private DocumentServiceImpl documentServiceImpl;
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @ApiOperation(value = "创建目录", notes = "返回创建成功的目录")
//    @PostMapping("/dirs/create")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "dirName", value = "目录名", required = true, paramType = "form"),
//            @ApiImplicitParam(name = "currentDirId", value = "当前目录id，为空时，默认根目录", paramType = "form"),
//    })
//    @ApiResponses(value = {
//            @ApiResponse(code = 200,message = "返回目录",response = Directory.class)
//    })
//    public Directory mkDir(String dirName, String currentDirId, HttpSession httpSession) {
//        return directoryServiceImpl.mkdir(dirName, currentDirId, httpSession);
//    }
//
//    @ApiOperation(value = "批量删除", notes = "批量删除目录和批量删除文件合在一起，返回值是true或者false")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "map",value = "要删除的文件或者目录的map，map包含两个数组，字段为dirIds,fileIds,eg: {\"dirIds\":\\[],\"fileIds\":\\[]}",paramType = "body",dataType = "Map")
//    })
//    @PostMapping("/dirs/batchDeleteAll")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200,message = "删除成功是，data的值为true，删除失败时，data的值是false")
//    })
//    public Result<Boolean> batchDelete(@ApiParam("删除的文件数组")@RequestBody Map<String,Object> map){
//        try{
//           return  ResultGenerator.genSuccess(directoryServiceImpl.deleteAll((List<String>)map.get("dirIds"),(List<String>)map.get("fileIds")));
//        }catch (MyServiceException e1){
//            return ResultGenerator.genFail(e1.getMessage(),false);
//        }catch (Exception e){
//           return ResultGenerator.genFail("删除时失败",false);
//        }
//    }
//
//
////    @ApiOperation(value = "批量删除目录", notes = "")
////    @ApiImplicitParams({
////            @ApiImplicitParam(name = "isAlldelete", value = "是否全部删除", required = true, paramType = "query",dataType = "Boolean"),
////            @ApiImplicitParam(name = "dirIds", value = "要删除的目录数组", paramType = "body",dataType = "List<String>"),
////    })
////    @PostMapping("/dirs/batchDelete")
////    @ApiResponses(value = {
////            @ApiResponse(code = 200,message = "当isAlldelete为true时表示：是否全部删除，返回结果中的data的值为true表示删除成功，false表示删除失败；当isAlldelete为为false时表示查看目录中是否存在文件，返回结果中的data" +
////                    "为true时，表示存在文件，为false时，表示不存在文件。")
////    })
////    public Result<Boolean> batchDelete( @RequestParam Boolean isAlldelete,@ApiParam("被删除目录id数组") @RequestBody List<String> dirIds){
////        Boolean state =directoryService.batchDelete(dirIds,isAlldelete);
////        return ResultGenerator.genSuccess(state);
////    }
//
//    @ApiOperation(value = "重命名目录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "新目录名", paramType = "form", required = true),
//            @ApiImplicitParam(name = "id", value = "目录id", paramType = "path"),
//    })
//    @PostMapping("/dirs/{id}/rename")  //Get dirs/123/rename/456.pdf ,文件名出现在path上会出现下载框
//    public Result renameDir(@RequestParam String name, @PathVariable("id") String id) throws MyServiceException {
//        Directory directory = directoryServiceImpl.renameDir(name, id);
//        return ResultGenerator.genSuccess();
//    }
//
//    @ApiOperation(value = "创建文件", notes = "返回创建成功的文件")
//    @PostMapping("/docs/create")
//    @ApiImplicitParams({
//
//    })
//    public Result<Document> createDoc(@Valid @RequestBody DocumentModel model, BindingResult bindingResult, HttpSession httpSession) throws MyServiceException {
//        String errorMessage = CommonsUtil.genErrorMessage(bindingResult);
//        if(errorMessage != null){
//            throw new MyServiceException("创建文件失败："+errorMessage);
//        }
//        Document doc = documentServiceImpl.createDoc(model.getName(), model.getDirId(), model.getPath(), model.getSize(), httpSession);
//        return ResultGenerator.genSuccess(doc);
//    }
//
//    @ApiOperation(value = "批量创建文件",notes = "返回创建成功的文件")
//    @PostMapping("/docs/batchCreate")
//    @ApiImplicitParams({
//
//    })
//    public Result<List<Document>> batchCreateDoc(@Valid @RequestBody List<DocumentModel> docs, BindingResult bindingResult, HttpSession httpSession) throws MyServiceException {
//        String errorMessage = CommonsUtil.genErrorMessage(bindingResult);
//        if(errorMessage != null){
//            throw new MyServiceException("批量创建文件失败："+errorMessage);
//        }
//        List<Document> list = new ArrayList<>();
//        Document doc = null;
//        for(DocumentModel m : docs){
//            doc = null;
//            try {
//                doc = documentServiceImpl.createDoc(m.getName(), m.getDirId(), m.getPath(), m.getSize(), httpSession);
//            }catch (MyServiceException e){
//                logger.warn("批量创建文件中，文件[{}]创建失败：{}", m.getName(), e.getMessage());
//                continue;
//            }
//            list.add(doc);
//        }
//        return ResultGenerator.genSuccess(list);
//    }
//
//    @ApiOperation(value = "批量删除文件", notes = "返回删除成功的文件名数组")
//    @PostMapping("/docs/batchDelete")
//    public Result<List<String>> batchDelete(@ApiParam("被删除文件id数组") @RequestBody List<String> docIds){
//        if(docIds == null || docIds.isEmpty()){
//            return ResultGenerator.genFail("被删除文件数量为0");
//        }
//        List<String> sucNames = documentServiceImpl.batchDelete(docIds);
//        return ResultGenerator.genSuccess(sucNames);
//    }
//
//    @ApiOperation(value = "重命名文件")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "新文件名", paramType = "form", required = true),
//            @ApiImplicitParam(name = "id", value = "文件id", paramType = "path"),
//    })
//    @PostMapping("/docs/{id}/rename") //Get docs/123/rename/456.pdf ,文件名出现在path上会出现下载框
//    public Result renameDoc(@RequestParam String name, @PathVariable("id") String id) throws MyServiceException {
//        Document doc = documentServiceImpl.renameDoc(name, id);
//        return ResultGenerator.genSuccess();
//    }
//
////    @ApiOperation(value = "查看某目录下的子目录和子目录的文件")
////    @PostMapping("/browserdir")
////    @ApiImplicitParams({
////            @ApiImplicitParam(name = "currentDirId",value = "目标目录id,为空时，默认根目录",paramType = "form",dataType = "String",required = false)
////    })
////    public Result<List<Directory>> listDir(@RequestParam(required = false) String currentDirId){
////        try{
////            return ResultGenerator.genSuccess(directoryService.listDir(currentDirId));
////        }catch (Exception e){
////            e.printStackTrace();
////            return ResultGenerator.genFail("failed");
////        }
////    }
//
//    @ApiOperation(value = "获取资料库文件的层级路径")
//    @PostMapping("/listDocDir")
//    public Result listDocDir(@RequestParam("id") String id) throws Exception {
//        return ResultGenerator.genSuccess(documentServiceImpl.listDocDir(id));
//    }
//
//    @ApiOperation(value = "查看某目录下的子目录和该目录下的文件")
//    @PostMapping("/browsertargetdir")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "currentDirId",value = "目标目录id,为空时，默认根目录",paramType = "query",dataType = "String",required = false),
//            @ApiImplicitParam(name = "type",value = "目录类型,1- 资料库，0-其他,当type为0时，currentDirId必须要传，否则会报错，当type为1时，currentDirId可不传",paramType = "form",dataType = "Integer",required = true)
//    })
//    @ApiResponses(value = {
//            @ApiResponse(code = 200,message = "成功则，返回目录，失败则返回的data为null")
//    })
//    public Result<List<Directory>> listTargetDir(@RequestParam(required = false) String currentDirId,@RequestParam(required = true) Integer type){
//        try{
//            if(type==null||!(type==1||type==0)){
//                throw new MyServiceException();
//            }
//            if(type==0&&currentDirId==null){
//                throw new  MyServiceException();
//            }
//            return ResultGenerator.genSuccess(directoryServiceImpl.listTargetDir(currentDirId,type));
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResultGenerator.genFail("failed");
//        }
//    }
//
//
//    @ApiOperation(value = "搜索文件")
//    @PostMapping("/searchDocument")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name",value = "文件名称",paramType = "form",dataType = "String"),
//            @ApiImplicitParam(name = "type",value = "文件类型",paramType = "form",dataType = "String")
//    })
//    @ApiResponse(code = 200,message = "成功则返回文件列表，失败则返回的data里面是null")
//    public Result<List<Document>> searchDocument(@RequestParam(required = false) String name,@RequestParam(required = false) String type){
//        try{
//            return ResultGenerator.genSuccess(documentServiceImpl.listByOptions(name,type));
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResultGenerator.genFail("搜索失败",null);
//        }
//    }
//
//
//// ================ 资料库新增功能 ==================
//
//    @ApiOperation("文件/文件夹排序(支持批量)")
//    @PostMapping("sortFile")
//    public Result sortFile(@ApiParam("文件夹集合,只需要按顺序给出id") @RequestParam(required = false) List<String> dirIds,@ApiParam("文件集合，只需要按顺序给出id") @RequestParam(required = false) List<String> docIds){
//        try {
//            directoryServiceImpl.sortFile(dirIds,docIds);
//            return ResultGenerator.genSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultGenerator.genFail("排序失败");
//        }
//    }
//
//
//    @ApiOperation("文件/文件夹移动到(支持批量)")
//    @PostMapping("moveToDir")
//    public Result moveToDir(@ApiParam("要移动的文件夹的id集合") @RequestParam(required = false) List<String> dirids,
//                          @ApiParam("要移动文件的id集合") @RequestParam(required = false) List<String> docids,
//                          @ApiParam("目标文件夹的id") @RequestParam(required = true) String targetDirId){
//        try {
//            directoryServiceImpl.moveToDir(dirids,docids,targetDirId);
//            return ResultGenerator.genSuccess();
//        } catch (MyServiceException e){
//            return ResultGenerator.genFail(e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultGenerator.genFail("文件/文件夹移动失败");
//        }
//    }
//
//    @ApiOperation("获取文件夹树")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "currentDirId",value = "目标目录id,为空时，默认根目录",paramType = "query",dataType = "String",required = false),
//            @ApiImplicitParam(name = "type",value = "目录类型,1- 资料库，0-其他,当type为0时，currentDirId必须要传，否则会报错，当type为1时，currentDirId可不传",paramType = "form",dataType = "Integer",required = true)
//    })
//    @PostMapping("getDirTree")
//    public Result<List<Directory>> getDirTree(@RequestParam(required = false) String currentDirId,@RequestParam(required = true) Integer type){
//        try {
//            List<Directory> result = directoryServiceImpl.getDirTree(currentDirId,type);
//            return ResultGenerator.genSuccess(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultGenerator.genFail("");
//        }
//    }
//}
