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

package br.com.fabioluis.usegists.component;

import br.com.fabioluis.usegists.model.Gist;
import br.com.fabioluis.usegists.model.GistFile;
import br.com.fabioluis.usegists.service.GetGist;
import br.com.fabioluis.usegists.service.ListGists;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.github.authentication.accounts.GithubAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@State(name = "UseGists", storages = {@Storage("UseGists.xml")})
public class UseGists implements ApplicationComponent, PersistentStateComponent<UseGists.Cache> {

    private Cache cache;
    private ListGists listGists;
    private GetGist getGist;

    public UseGists() {
        cache = new Cache();
        listGists = ListGists.getInstance();
        getGist = GetGist.getInstance();
    }

    public List<Gist> getGists() {
        if (cache.getGists().isEmpty() && cache.getAccount() != null) {
            List<Gist> gists = listGists.list(cache.getAccount());
            cache.putGistsToCache(gists);
        }

        return cache.getGists();
    }

    public String getFileContent(String gistId, String fileName) {
        if (!cache.isFileContentInCache(gistId, fileName)) {
            List<Gist> gistsWithContent = getGist.getGistWithContent(cache.getAccount(), gistId);
            putInCache(gistsWithContent);
        }

        return cache.getFileContentFromCache(gistId, fileName);
    }

    private void putInCache(List<Gist> gistsWithContent) {
        for (Gist gist : gistsWithContent) {
            Map<String, GistFile> filesMap = gist.getFiles();
            for (String key : filesMap.keySet()) {
                GistFile gistFile = filesMap.get(key);
                cache.putFileContentToCache(gist.getId(), gistFile.getFilename(), gistFile.getContent());
            }
        }
    }

    public void setAccount(GithubAccount account) {
        cache.setAccount(account);
    }

    public String getAccountName() {
        return cache.getAccount().getName();
    }

    public void limpaCache() {
        cache.limpaCache();
    }

    @Nullable
    @Override
    public Cache getState() {
        return cache;
    }

    @Override
    public void loadState(@NotNull Cache state) {
        this.cache = state;
    }

    static class Cache {
        private GithubAccount account;
        private List<Gist> gists;
        private Map<String, String> filesContent;

        public Cache() {
            filesContent = new HashMap<>();
            gists = new ArrayList<>();
        }

        public GithubAccount getAccount() {
            return account;
        }

        public void setAccount(GithubAccount account) {
            this.account = account;
        }

        public Map<String, String> getFilesContent() {
            return filesContent;
        }

        public void setFilesContent(Map<String, String> filesContent) {
            this.filesContent = filesContent;
        }

        public List<Gist> getGists() {
            return gists;
        }

        public void setGists(List<Gist> gists) {
            this.gists = gists;
        }

        public void limpaCache() {
            filesContent = new HashMap<>();
            gists = new ArrayList<>();
        }

        public boolean isFileContentInCache(String gistId, String fileName) {
            return filesContent.containsKey(gistId + fileName);
        }

        public String getFileContentFromCache(String gistId, String fileName) {
            return filesContent.get(gistId + fileName);
        }

        public void putFileContentToCache(String gistId, String fileName, String content) {
            filesContent.put(gistId + fileName, content);
        }

        public void putGistsToCache(List<Gist> gists) {
            this.gists.addAll(gists);
        }
    }
}
