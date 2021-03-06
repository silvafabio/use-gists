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

package br.com.fabioluis.usegists.service;

import br.com.fabioluis.usegists.model.Gist;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.progress.util.ProgressIndicatorBase;
import org.apache.http.message.BasicHeader;
import org.jetbrains.plugins.github.api.GithubApiTaskExecutor;
import org.jetbrains.plugins.github.api.GithubConnection;
import org.jetbrains.plugins.github.authentication.accounts.GithubAccount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GetGist {

    private static AtomicBoolean idle = new AtomicBoolean(true);

    public static GetGist getInstance() {
        return ServiceManager.getService(GetGist.class);
    }

    public List<Gist> getGistWithContent(GithubAccount account, String gistId) {
        if (idle.get()) {
            idle.set(false);
            //TODO: Show message on status bar when the task start.
            ProgressIndicatorBase pib = new ProgressIndicatorBase();
            pib.setText("Getting gist ...");

            try {
                GithubApiTaskExecutor githubApiTaskExecutor = GithubApiTaskExecutor.getInstance();
                return githubApiTaskExecutor.execute(pib, account, conn -> {
                    GithubConnection.SingleValuePagedRequest<Gist> request = new GithubConnection.SingleValuePagedRequest<>(
                            "/gists/" + gistId,
                            Gist.class,
                            new BasicHeader("Accept", "application/vnd.github.v3+json"));
                    return request.getAll(conn);
                });
            } catch (IOException e1) {
                notificaErro();
            } finally {
                idle.set(true);
            }
        }

        return new ArrayList<>();
    }

    private void notificaErro() {
        Notification notification = new Notification(
                "Use Gists",
                "Get Gist File",
                "Unable to connect to GitHub",
                NotificationType.ERROR);

        Notifications.Bus.notify(notification);
    }
}
