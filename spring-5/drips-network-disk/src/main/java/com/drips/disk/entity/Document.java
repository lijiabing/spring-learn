
package com.drips.disk.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "文件")
@Table(name = "DOCUMENT")
@Entity
public class Document implements Serializable {

    @ApiModelProperty(value = "主键id", required = true, dataType = "Long")
    @Id
    @GeneratedValue(generator = "_increment")
    @GenericGenerator(name = "_increment", strategy = "increment")
    private Long id;
    @ApiModelProperty(value = "文件的当前目录")
    @ManyToOne(targetEntity = Directory.class, cascade = {CascadeType.REMOVE})
//    @Column(name = "DIRECTORY_ID")
    private Directory directory;
    @ApiModelProperty("文件相对路径")
    @Column(name = "RELATIVE_PATH")
    private String relativePath;
    @ApiModelProperty(value = "文件大小 byte")
    @Column(name = "SIZE")
    private Long size;
    @ApiModelProperty(value = "文件名称", required = true)
    @Column(name = "NAME")
    private String name;
    @ApiModelProperty(value = "文件类型 pdf jpg等等")
    @Column(name = "FILE_TYPE")
    private String fileType;
    @ApiModelProperty(value = "修改时间")
    @Column(name = "GMT_MODIFIED")
    private Date gmtModified;
    @ApiModelProperty(value = "创建时间")
    @Column(name = "GMT_CREATED")
    private Date gmtCreated;
    @ApiModelProperty(value = "创建人的id")
    @Column(name = "USER_ID")
    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
