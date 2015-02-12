/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.iphoto;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class PhotoDialogTest {

    public PhotoDialogTest() {
    }

    @Test
    public void testSomeMethod() {
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Parser parser = new Parser();
                Library lib = null;
                try {
                    lib = parser.load();
                } catch (Exception ex) {
                    Logger.getLogger(PhotoDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                PhotoDialog dialog = new PhotoDialog(new javax.swing.JFrame(), true, lib);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
                System.out.println(dialog.getReturnStatus());
            }
        });
    }

}
