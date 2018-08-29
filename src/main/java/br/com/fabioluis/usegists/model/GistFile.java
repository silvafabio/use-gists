/*
 *     Use Gists is a Intellij IDEA plugin to use Github gists
 *     Copyright (C) 2018  Fabio Silva
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package br.com.fabioluis.usegists.model;

import com.google.gson.annotations.SerializedName;
import com.intellij.util.xmlb.annotations.Transient;

import java.util.Objects;

public class GistFile {

    private String filename;
    private String type;
    private String language;
    @SerializedName("raw_url")
    private String rawUrl;
    private Long size;
    private Boolean truncated;
    private String content;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRawUrl() {
        return rawUrl;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Boolean getTruncated() {
        return truncated;
    }

    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Transient
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GistFile gistFile = (GistFile) o;
        return Objects.equals(filename, gistFile.filename);
    }

    @Transient
    @Override
    public int hashCode() {
        return Objects.hash(filename);
    }
}
