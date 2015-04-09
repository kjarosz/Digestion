package Core.LevelEditor.Components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Core.LevelEditor.Models.LevelModel;
import Menu.MenuStack;

public class EditorToolbar extends JToolBar {
   private MenuStack mStack;
   
   private LevelModel mLevelModel;
   private File mLevelFile;
   
   private boolean mUnsaved = false;
   
   public EditorToolbar(MenuStack stack, LevelModel levelModel) {
      mLevelModel = levelModel;
      mLevelModel.addPropertyChangeListener(createLevelChangeTracker());
      mLevelFile = null;
      
      makeToolBarButton("New",     createNewLevelAction());
      makeToolBarButton("Open",    createOpenLevelAction());
      makeToolBarButton("Save",    createSaveLevelAction());
      makeToolBarButton("Save As", createSaveAsAction());
      makeToolBarButton("Quit",    createQuitAction());
   }
   
   private PropertyChangeListener createLevelChangeTracker() {
      return new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent e) {
            mUnsaved = true;
         }
      };
   }
   
   private void makeToolBarButton(String label, ActionListener listener) {
      JButton button = new JButton(label);
      button.addActionListener(listener);
      add(button);
   }
   
   /* ********************************************************************** */
   /*                                CONTROL                                 */
   /* ********************************************************************** */   
   private ActionListener createNewLevelAction() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if(dealWithUnsaved()) {
               mLevelModel.reset();
               mUnsaved = false;
               mLevelFile = null;
            }
         }
      };
   }
   
   private ActionListener createOpenLevelAction() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if(dealWithUnsaved()) {
               File levelFile = findLevelFile();
               if(levelFile == null) return;
               //openLevel(levelFile);
            }
         }
      };
   }
   
   private ActionListener createSaveLevelAction() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if(mUnsaved) {
               //save();
            }
         }
      };
   }
   
   private ActionListener createSaveAsAction() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            //saveAs();
         }
      };
   }
   
   private ActionListener createQuitAction() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if(dealWithUnsaved()) {
               mStack.popScreen();
            }
         }
      };
      
   }
   
   /* ********************************************************************** */
   /*                               UTILITIES                                */
   /* ********************************************************************** */   
   private boolean dealWithUnsaved() {      
      return !mUnsaved || unsavedDiscardAction();
   }
   
   private boolean unsavedDiscardAction() {
      int choice = JOptionPane.showConfirmDialog(null,
            "You have unsaved changes in your level.\n"
            + "Do you want to save your changes?",
            "Unsaved Changes",
            JOptionPane.YES_NO_CANCEL_OPTION);
      if(choice == JOptionPane.YES_OPTION) {
         //save();
         return true;
      } else if(choice == JOptionPane.NO_OPTION) {
         return true;
      } else {
         return false;
      }
   }
   
   private FileFilter getLevelFileFilter() {
      return new FileNameExtensionFilter(
            "Level Script", ".py");
   }
   
   private JFileChooser getLevelFileChooser() {
      JFileChooser chooser = new JFileChooser();
      FileFilter filter = getLevelFileFilter();
      chooser.setFileFilter(filter);
      return chooser;
   }
   
   private File findLevelFile() {
      JFileChooser chooser = getLevelFileChooser();
      int choice = chooser.showOpenDialog(this);
      if(choice == JFileChooser.APPROVE_OPTION) {
         return chooser.getSelectedFile();
      } else {
         return null;
      }
   }
   
   private File chooseNewLevelFile() {
      JFileChooser chooser = getLevelFileChooser();
      int choice = chooser.showSaveDialog(this);
      if(choice == JFileChooser.APPROVE_OPTION) {
         File file = chooser.getSelectedFile();
         file = validateFilename(file);
         return file;
      } else {
         return null;
      }
   }
   
   private File validateFilename(File file) {
      String name = file.getName();
      if(name.endsWith(".py")) {
         return file;
      }
      return new File(file.getPath(),
            name.concat(".py"));
   }
   
     //////////////////////////////////////////////////////////////////////////
    ////////////////// OLD BULLSHIT //////////////////////////////////////////
   //////////////////////////////////////////////////////////////////////////
}
