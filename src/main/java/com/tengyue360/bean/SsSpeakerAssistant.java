package com.tengyue360.bean;
import java.io.Serializable;
import java.util.Date;
/**
 * @author panjt
 * @date 2018/8/15 下午11:48
 */
public class SsSpeakerAssistant  implements Serializable {



        private Integer id;

        private String uid;

        /**
         * 关联前台账号ID
         */
        private String frontId;
        /**
         * 类型(0,主讲、1,助教)
         */
        private Integer type;
        /**
         * 姓名
         */
        private String name;
        /**
         * 性别
         */
        private String sex;
        /**
         *教授科目(语文、数学、英语)
         */
        private String subjects;
        /**
         *教授年级(一年级、二年级、三年级)
         */
        private String grade;
        /**
         * 照片URL 地址
         */
        private String photo;
        /**
         * 简介
         */
        private String synopsis;
        /**
         *所属学校
         */
        private String schoolId;
        /**
         *创建时间
         */
        private Date createTime;
        /**
         *创建人
         */
        private String createMan;
        /**
         *更新时间
         */
        private Date updateTime;
        /**
         *更新人
         */
        private String updateMan;
        /**
         *删除标识(0,删除，1正常)
         */
        private String deleteState;

        public SsSpeakerAssistant(Integer id, String uid, String frontId, Integer type, String name, String sex, String subjects, String grade, String photo, String synopsis, String schoolId, Date createTime, String createMan, Date updateTime, String updateMan, String deleteState) {
            this.id = id;
            this.uid = uid;
            this.frontId = frontId;
            this.type = type;
            this.name = name;
            this.sex = sex;
            this.subjects = subjects;
            this.grade = grade;
            this.photo = photo;
            this.synopsis = synopsis;
            this.schoolId = schoolId;
            this.createTime = createTime;
            this.createMan = createMan;
            this.updateTime = updateTime;
            this.updateMan = updateMan;
            this.deleteState = deleteState;
        }

        public SsSpeakerAssistant() {
            super();
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid == null ? null : uid.trim();
        }

        public String getFrontId() {
            return frontId;
        }

        public void setFrontId(String frontId) {
            this.frontId = frontId == null ? null : frontId.trim();
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name == null ? null : name.trim();
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex == null ? null : sex.trim();
        }

        public String getSubjects() {
            return subjects;
        }

        public void setSubjects(String subjects) {
            this.subjects = subjects == null ? null : subjects.trim();
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade == null ? null : grade.trim();
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo == null ? null : photo.trim();
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis == null ? null : synopsis.trim();
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId == null ? null : schoolId.trim();
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public String getCreateMan() {
            return createMan;
        }

        public void setCreateMan(String createMan) {
            this.createMan = createMan == null ? null : createMan.trim();
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateMan() {
            return updateMan;
        }

        public void setUpdateMan(String updateMan) {
            this.updateMan = updateMan == null ? null : updateMan.trim();
        }

        public String getDeleteState() {
            return deleteState;
        }

        public void setDeleteState(String deleteState) {
            this.deleteState = deleteState == null ? null : deleteState.trim();
        }
    }

