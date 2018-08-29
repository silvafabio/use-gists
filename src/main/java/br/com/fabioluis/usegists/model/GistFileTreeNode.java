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

import javax.swing.tree.DefaultMutableTreeNode;

public class GistFileTreeNode extends DefaultMutableTreeNode {

    private final String id;
    private final GistFile gistFile;

    public GistFileTreeNode(GistFile gistFile, String id) {
        this.gistFile = gistFile;
        this.id = id;
    }

    public GistFile getGistFile() {
        return gistFile;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return gistFile.getFilename();
    }
}
