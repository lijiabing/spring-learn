
package com.drips.disk.entity;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "目录")
@Table(name = "DIRECTORY")
@Entity(name = "Directory")
public class Directory implements Serializable {

    @ApiModelProperty(value = "主键id", required = true, dataType = "Long")
    @Id
    @GeneratedValue(generator = "_increment")
    @GenericGenerator(name = "_increment", strategy = "increment")
    private Long id;
    @ApiModelProperty(value = "目录名称", required = true)
    @Column(name = "NAME")
    private String name;
    @ApiModelProperty(value = "父目录")
    @ManyToOne(targetEntity = Directory.class, cascade = {CascadeType.REMOVE})
    private Directory parentDirectory;
    @ApiModelProperty(value = "修改时间")
    @Column(name = "GMT_MODIFIED")
    private Date gmtModified;
    @ApiModelProperty(value = "创建时间")
    @Column(name = "GMT_CREATED")
    private Date gmtCreated;
    @ApiModelProperty(value = "创建人的id", example = "admin")
    @Column(name = "USER_ID")
    private String userId;
    @ApiModelProperty(value = "文件列表")
    @OneToMany(targetEntity = Document.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY, mappedBy = "directory", orphanRemoval = true
    )
    @JsonIgnore
    private List<Document> documentList;
    @ApiModelProperty(value = "子目录列表")
    @OneToMany(targetEntity = Directory.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY, mappedBy = "parentDirectory", orphanRemoval = true
    )
    @JsonIgnore
    private List<Directory> directoryList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
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

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public List<Directory> getDirectoryList() {
        return directoryList;
    }

    public void setDirectoryList(List<Directory> directoryList) {
        this.directoryList = directoryList;
    }
}
