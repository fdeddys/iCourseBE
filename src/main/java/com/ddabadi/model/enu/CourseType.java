package com.ddabadi.model.enu;

public enum CourseType {

    PELAJARAN_SEKOLAH(0),
    BAHASA_INGGRIS(1),
    MIA (2);

    private Integer courseTypeCode;

    CourseType(Integer courseTypeCode) {
        this.courseTypeCode = courseTypeCode;
    }

    public Integer getCourseTypeCode() {
        return courseTypeCode;
    }

}
