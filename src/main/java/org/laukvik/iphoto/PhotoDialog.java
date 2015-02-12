/*
 * Copyright (C) 2015 Morten Laukvik <morten@laukvik.no>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.laukvik.iphoto;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class PhotoDialog extends javax.swing.JDialog implements TreeSelectionListener, MouseListener {

    private Library library;
    private final DefaultMutableTreeNode root;
    private final ResourceBundle bundle;
    int returnStatus;

    public final static int OK_PRESSED = 1;
    public final static int CANCELLED_PRESSED = 0;

    private final List<PhotoIcon> selectedPhotos;

    public PhotoDialog(java.awt.Frame parent) {
        this(parent, true, new Library());
    }

    public void openDefaultLibrary() {
        Parser parser = new Parser();
        try {
            Library lib = parser.load();
            setLibrary(lib);
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, bundle.getString("library"), bundle.getString("library_failed_to_read"), JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    public PhotoDialog(java.awt.Frame parent, boolean modal, Library library) {
        super(parent, modal);
        getRootPane().putClientProperty("apple.awt.brushMetalLook", Boolean.TRUE);
        selectedPhotos = new ArrayList<>();
        bundle = ResourceBundle.getBundle("messages");
        setTitle(bundle.getString("library"));
        root = new DefaultMutableTreeNode(bundle.getString("library"));
        initComponents();

        tree.setRootVisible(false);
        tree.addTreeSelectionListener(this);
        tree.setCellRenderer(new AlbumTreeRenderer());

        tree.setBackground(new Color(244, 244, 247));
        images.setBackground(PhotoIcon.PHOTO_BACKGROUND_COLOR);
        Dimension btnSize = new Dimension(100, 24);

        okButton.setSize(btnSize);
        okButton.setMinimumSize(btnSize);
        okButton.setPreferredSize(btnSize);

        cancelButton.setSize(btnSize);
        cancelButton.setMinimumSize(btnSize);
        cancelButton.setPreferredSize(btnSize);

        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Float width = size.width * 0.7f;
        Float height = size.height * 0.6f;
        Float split = size.width * 0.15f;
        jSplitPane1.setDividerLocation(split.intValue());
        setSize(width.intValue(), height.intValue());
        setLocationRelativeTo(null);

    }

    public List<Photo> getSelectedPhotos() {
        List<Photo> photos = new ArrayList<>();
        for (PhotoIcon pi : selectedPhotos) {
            photos.add(pi.getPhoto());
        }
        return photos;
    }

    public void setLibrary(Library library) {
        this.library = library;
        root.removeAllChildren();
        for (Album alb : library.list()) {
            root.add(new DefaultMutableTreeNode(alb));
        }
        tree.scrollPathToVisible(new TreePath(root.getFirstLeaf().getPath()));
        setVisible(true);
    }

    public Library getLibrary() {
        return library;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode o = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        Album alb = (Album) o.getUserObject();
        openAlbum(alb);
    }

    /**
     * Opens up an album for showing its photos
     *
     * @param alb
     */
    public void openAlbum(Album alb) {
        images.removeAll();
        for (Photo p : alb.list()) {
            PhotoIcon pi = new PhotoIcon(p);
            pi.addMouseListener(this);
            images.add(pi);
        }
        images.invalidate();
        images.validate();
        images.doLayout();
        imagesScroll.invalidate();
        imagesScroll.validate();
        images.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tree = new JTree(root);
        imagesScroll = new javax.swing.JScrollPane();
        images = new org.laukvik.iphoto.PhotoPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(200);

        tree.setCellRenderer(new AlbumTreeRenderer());
        jScrollPane1.setViewportView(tree);

        jSplitPane1.setLeftComponent(jScrollPane1);

        imagesScroll.setBorder(null);
        imagesScroll.setViewportView(images);

        jSplitPane1.setRightComponent(imagesScroll);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("messages"); // NOI18N
        okButton.setText(bundle.getString("ok")); // NOI18N
        okButton.setFocusable(false);
        okButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        okButton.setPreferredSize(new java.awt.Dimension(27, 24));
        okButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(okButton);

        cancelButton.setText(bundle.getString("cancel")); // NOI18N
        cancelButton.setFocusable(false);
        cancelButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(cancelButton);

        jToolBar1.add(jPanel1);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public boolean isOkPressed() {
        return returnStatus == OK_PRESSED;
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(OK_PRESSED);
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(CANCELLED_PRESSED);
    }

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {
        doClose(CANCELLED_PRESSED);
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PhotoIcon pi = (PhotoIcon) e.getSource();
        if (pi.isSelected()) {
            selectedPhotos.remove(pi);
            pi.setSelected(false);
        } else {
            selectedPhotos.add(pi);
            pi.setSelected(true);
        }
        updateSelection();
    }

    private void updateSelection() {
        if (selectedPhotos.isEmpty()) {
            setTitle(bundle.getString("library"));
        } else {
            setTitle(bundle.getString("library") + " - " + selectedPhotos.size() + " " + bundle.getString("selected"));
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private org.laukvik.iphoto.PhotoPanel images;
    private javax.swing.JScrollPane imagesScroll;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton okButton;
    private javax.swing.JTree tree;
    // End of variables declaration//GEN-END:variables

}
