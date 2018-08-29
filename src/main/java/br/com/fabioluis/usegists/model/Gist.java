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

import java.util.Map;
import java.util.Objects;

public class Gist {

    private String url;
    @SerializedName("forks_url")
    private String forksUrl;
    @SerializedName("commits_url")
    private String commitsUrl;
    private String id;
    @SerializedName("node_id")
    private String nodeId;
    @SerializedName("git_pull_url")
    private String gitPullUrl;
    @SerializedName("git_push_url")
    private String gitPushUrl;
    @SerializedName("html_url")
    private String htmlUrl;
    private Map<String, GistFile> files;
    private Boolean isPublic;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    private String description;
    private Integer comments;
    private String user;
    @SerializedName("commentsUrl")
    private String comments_url;
    private GistOwner owner;
    private Boolean truncated;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getForksUrl() {
        return forksUrl;
    }

    public void setForksUrl(String forksUrl) {
        this.forksUrl = forksUrl;
    }

    public String getCommitsUrl() {
        return commitsUrl;
    }

    public void setCommitsUrl(String commitsUrl) {
        this.commitsUrl = commitsUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getGitPullUrl() {
        return gitPullUrl;
    }

    public void setGitPullUrl(String gitPullUrl) {
        this.gitPullUrl = gitPullUrl;
    }

    public String getGitPushUrl() {
        return gitPushUrl;
    }

    public void setGitPushUrl(String gitPushUrl) {
        this.gitPushUrl = gitPushUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Map<String, GistFile> getFiles() {
        return files;
    }

    public void setFiles(Map<String, GistFile> files) {
        this.files = files;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public GistOwner getOwner() {
        return owner;
    }

    public void setOwner(GistOwner owner) {
        this.owner = owner;
    }

    public Boolean getTruncated() {
        return truncated;
    }

    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    @Transient
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gist gist = (Gist) o;
        return Objects.equals(id, gist.id);
    }

    @Transient
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
