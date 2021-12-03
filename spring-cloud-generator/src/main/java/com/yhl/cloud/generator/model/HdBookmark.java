package com.yhl.cloud.generator.model;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.yhl.cloud.generator.entity.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;

@ExcelIgnoreUnannotated
@ColumnWidth(16)
@HeadRowHeight(14)
@HeadFontStyle(fontHeightInPoints = 11)
public class HdBookmark extends BaseEntity implements Serializable {

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hd_bookmark.bookmark_link
     *
     * @mbg.generated
     */
    @ExcelProperty("书签链接")
    private String bookmarkLink;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hd_bookmark.bookmark_title
     *
     * @mbg.generated
     */
    @ExcelProperty(value = "书签标题")
    private String bookmarkTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hd_bookmark
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hd_bookmark.bookmark_link
     *
     * @return the value of hd_bookmark.bookmark_link
     * @mbg.generated
     */
    public String getBookmarkLink() {
        return bookmarkLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hd_bookmark.bookmark_link
     *
     * @param bookmarkLink the value for hd_bookmark.bookmark_link
     * @mbg.generated
     */
    public void setBookmarkLink(String bookmarkLink) {
        this.bookmarkLink = bookmarkLink == null ? null : bookmarkLink.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hd_bookmark.bookmark_title
     *
     * @return the value of hd_bookmark.bookmark_title
     * @mbg.generated
     */
    public String getBookmarkTitle() {
        return bookmarkTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hd_bookmark.bookmark_title
     *
     * @param bookmarkTitle the value for hd_bookmark.bookmark_title
     * @mbg.generated
     */
    public void setBookmarkTitle(String bookmarkTitle) {
        this.bookmarkTitle = bookmarkTitle == null ? null : bookmarkTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_bookmark
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        HdBookmark other = (HdBookmark) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
                && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
                && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
                && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
                && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()))
                && (this.getBookmarkLink() == null ? other.getBookmarkLink() == null : this.getBookmarkLink().equals(other.getBookmarkLink()))
                && (this.getBookmarkTitle() == null ? other.getBookmarkTitle() == null : this.getBookmarkTitle().equals(other.getBookmarkTitle()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_bookmark
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        result = prime * result + ((getBookmarkLink() == null) ? 0 : getBookmarkLink().hashCode());
        result = prime * result + ((getBookmarkTitle() == null) ? 0 : getBookmarkTitle().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_bookmark
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bookmarkLink=").append(bookmarkLink);
        sb.append(", bookmarkTitle=").append(bookmarkTitle);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}