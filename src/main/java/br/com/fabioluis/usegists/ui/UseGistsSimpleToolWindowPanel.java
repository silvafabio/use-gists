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

package br.com.fabioluis.usegists.ui;

import br.com.fabioluis.usegists.component.UseGists;
import br.com.fabioluis.usegists.model.Gist;
import br.com.fabioluis.usegists.model.GistFile;
import br.com.fabioluis.usegists.model.GistFileTreeNode;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.TreeSpeedSearch;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.SimpleTree;
import org.jetbrains.plugins.github.authentication.GithubAuthenticationManager;
import org.jetbrains.plugins.github.authentication.accounts.GithubAccount;
import org.jetbrains.plugins.github.authentication.ui.GithubChooseAccountDialog;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

class UseGistsSimpleToolWindowPanel extends SimpleToolWindowPanel {

    private static final String JBSPLITER_PROPORTION_KEY = "UseGistsJBSplitterProportionKey";
    private static final float JBSPLITER_DEFAULT_PROPORTION = 0.20f;

    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private SimpleTree tree;
    private Editor editor;
    private UseGists useGists;

    public UseGistsSimpleToolWindowPanel() {
        super(false, true);

        useGists = ApplicationManager.getApplication().getComponent(UseGists.class);
        createView();
        updateTree();
    }

    private void createView() {
        createEditor();
        createTree();
        createToolbar();

        JBSplitter splitPane = new JBSplitter(JBSPLITER_PROPORTION_KEY, JBSPLITER_DEFAULT_PROPORTION);
        JBScrollPane jbScrollPane = new JBScrollPane(tree);
        splitPane.setFirstComponent(jbScrollPane);
        splitPane.setSecondComponent(editor.getComponent());

        add(splitPane);
    }

    private void createEditor() {
        EditorFactory instance = EditorFactory.getInstance();
        Document document = instance.createDocument("");

        //TODO: Look for cool properties to enable
        editor = instance.createEditor(document);
        EditorSettings settings = editor.getSettings();
        settings.setLineMarkerAreaShown(true);
        settings.setLineNumbersShown(true);
        settings.setFoldingOutlineShown(true);
        settings.setAnimatedScrolling(true);
        settings.setWheelFontChangeEnabled(true);
        settings.setVariableInplaceRenameEnabled(true);
    }

    private void createTree() {
        rootNode = new DefaultMutableTreeNode("Gists");
        treeModel = new DefaultTreeModel(rootNode);
        tree = new SimpleTree(treeModel);

        tree.addTreeSelectionListener(e -> {
            SimpleTree simpleTree = (SimpleTree) e.getSource();
            if (simpleTree.getLastSelectedPathComponent() instanceof GistFileTreeNode) {
                GistFileTreeNode gistFile = (GistFileTreeNode) simpleTree.getLastSelectedPathComponent();

                //TODO: Is it possible to make editor recognize the content type of source code to display colored (sintax) text?
//                String type = gistFile.getGistFile().getType();
//                FileType fileType = FileTypeManager.getInstance().getFileTypeByExtension(type);
//                editor.setFileType(fileType);

                String fileContent = useGists.getFileContent(gistFile.getId(), gistFile.getGistFile().getFilename());
                writeGistFileToEditor(fileContent);
            }
        });

        //TODO: The filter works only for the first typed character
        new TreeSpeedSearch(tree);
    }

    private void createToolbar() {
        ActionManager actionManager = ActionManager.getInstance();
        DefaultActionGroup group = new DefaultActionGroup();

        group.add(new SelecionarConta());
        group.add(new Atualizar());

        //TODO: The toolbar look and feel is not cool, it's confusing with the tree area
        ActionToolbar toolbar = actionManager.createActionToolbar("Use Gists", group, false);
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(toolbar.getComponent(), BorderLayout.NORTH);
        setToolbar(buttonsPanel);
    }

    private void writeGistFileToEditor(String fileContent) {
        editor.getDocument().setReadOnly(false);
        ApplicationManager.getApplication().runWriteAction(() -> {
            editor.getDocument().setText(fileContent);
        });
        editor.getDocument().setReadOnly(true);
    }

    private void updateTree() {
        List<Gist> gists = useGists.getGists();
        if (!gists.isEmpty()) {
            writeGistFileToEditor("");
            rootNode.removeAllChildren();

            DefaultMutableTreeNode accountNode = new DefaultMutableTreeNode(useGists.getAccountName());

            for (Gist gist : gists) {
                DefaultMutableTreeNode gistNode = new DefaultMutableTreeNode(gist.getDescription());

                Map<String, GistFile> files = gist.getFiles();
                for (String key : files.keySet()) {
                    gistNode.add(new GistFileTreeNode(files.get(key), gist.getId()));
                }

                accountNode.add(gistNode);
            }

            rootNode.add(accountNode);
            treeModel.reload();
        }
    }

    private class SelecionarConta extends AnAction {
        private SelecionarConta() {
            super(AllIcons.General.GearPlain);
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            GithubAuthenticationManager instance = GithubAuthenticationManager.getInstance();
            //TODO: Use i18n for texts
            GithubChooseAccountDialog githubChooseAccountDialog = new GithubChooseAccountDialog(
                    e.getProject(),
                    null,
                    instance.getAccounts(),
                    "Which account would you like to use gists?",
                    false,
                    false,
                    "Select an account",
                    "Ok");
            githubChooseAccountDialog.show();

            try {
                GithubAccount newAccount = githubChooseAccountDialog.getAccount();
                useGists.setAccount(newAccount);
                updateTree();
            } catch (IllegalStateException ise) {
                //Quando nenhuma conta é selecionada.
            }
        }
    }

    private class Atualizar extends AnAction {
        private Atualizar() {
            super(AllIcons.Actions.Refresh);
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            useGists.limpaCache();
            updateTree();
        }
    }
}
