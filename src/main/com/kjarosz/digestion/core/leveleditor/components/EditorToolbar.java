package com.kjarosz.digestion.core.leveleditor.components;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.kjarosz.digestion.core.game.Game;
import com.kjarosz.digestion.core.leveleditor.models.LevelModel;
import com.kjarosz.digestion.core.leveleditor.utils.LevelModelReader;
import com.kjarosz.digestion.core.leveleditor.utils.LevelModelWriter;

public class EditorToolbar extends JToolBar {
   private Game mGame; 
   
   private LevelModel mLevelModel;
   private File mLevelFile;
   
   private boolean mUnsaved = false;
   
   public EditorToolbar(Game game, LevelModel levelModel) {
      mGame = game;
      
      mLevelModel = levelModel;
      mLevelModel.addPropertyChangeListener(e -> mUnsaved = true);
      mLevelFile = null;
      
      makeToolBarButton("New",     e -> createNewLevel());
      makeToolBarButton("Open",    e -> openLevel());
      makeToolBarButton("Save",    e -> save());
      makeToolBarButton("Save As", e -> saveAs());
      makeToolBarButton("Quit",    e -> quit());
   }
   
   private void makeToolBarButton(String label, ActionListener listener) {
      JButton button = new JButton(label);
      button.addActionListener(listener);
      add(button);
   }
   
   /* ********************************************************************** */
   /*                               UTILITIES                                */
   /* ********************************************************************** */
   private void createNewLevel() {
		if(dealWithUnsaved()) {
			mLevelModel.reset();
			mUnsaved = false;
			mLevelFile = null;
		} 
   }

   private void openLevel() {
		if(dealWithUnsaved()) {
			File levelFile = findLevelFile();
			if(levelFile == null) return;
			LevelModelReader reader = new LevelModelReader(levelFile, mLevelModel);
			reader.addPropertyChangeListener(e -> checkLoadingState(e));
			reader.execute();
		}
   }
   
   private void checkLoadingState(PropertyChangeEvent e) {
      if(isWorkerDone(e)) {
         LevelModelReader reader = (LevelModelReader)e.getSource();
         mLevelFile = reader.getLevelFile();
      }
   }
   
   private boolean isWorkerDone(PropertyChangeEvent e) {
      String prop = e.getPropertyName();
      if("state".equals(prop)) {
         String state = e.getNewValue().toString();
         if("DONE".equals(state)) {
            return true;
         }
      }
      return false;
   }
   
   private void save() {
      if(mLevelFile == null) {
         saveAs();
      } else {
         LevelModelWriter writer = new LevelModelWriter(mLevelFile, mLevelModel);
         writer.addPropertyChangeListener(e -> checkSavingState(e));
         writer.execute();
      }
   }
   
   private void checkSavingState(PropertyChangeEvent e) {
      if(isWorkerDone(e)) {
         LevelModelWriter writer = (LevelModelWriter)e.getSource();
         try {
            mLevelFile = writer.get();
         } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
         }
      }
   }
   
   private void saveAs() {
      mLevelFile = chooseNewLevelFile();
      if(mLevelFile != null)
         save();
   }
   
   private void quit() {
		if(dealWithUnsaved()) {
		   mGame.switchToState("TITLE SCREEN");
		}
   }
   
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
         save();
         return true;
      } else if(choice == JOptionPane.NO_OPTION) {
         return true;
      } else {
         return false;
      }
   }
   
   private FileFilter getLevelFileFilter() {
      return new FileNameExtensionFilter(
            "Level Script", "json");
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
      if(name.endsWith(".json")) {
         return file;
      }
      return new File(file.getParent(),
            name.concat(".json"));
   }
}
