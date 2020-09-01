package com.drips.disk.controller;

import com.drips.disk.service.impl.DirectoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lijb
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DirectoryServiceImpl directoryService;

//    @GetMapping("/listDirName")
//    public Object listDirName(@RequestParam Long pid){
//        return directoryService.listDirNames(pid);
//    }


    @GetMapping("/mkdir")
    public Object mkdir(@RequestParam String name, @RequestParam Long pid) {
        return directoryService.mkdir(name, pid);
    }

}
