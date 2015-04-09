package Core.LevelEditor.Tabs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PathfindingTab extends JPanel implements ActionListener {
	JPanel mButtonPanel;
	JButton mGeneratePath;
	JButton mClearPath;
	JButton mCombinePlatforms;
	JButton mClearTestPath;

	JPanel mOptionsPanel;
	ButtonGroup mViewGroup;
	JRadioButton mViewAllButton;
	JRadioButton mViewSelectedButton;

	JPanel mStatusPanel;
	JLabel mStatusLabel;

	public PathfindingTab() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		mButtonPanel = new JPanel();
		mButtonPanel.setLayout(new BoxLayout(mButtonPanel, BoxLayout.PAGE_AXIS));
		mGeneratePath = new JButton("Generate Path");
		mGeneratePath.setAlignmentX(Component.CENTER_ALIGNMENT);
		mGeneratePath.setActionCommand("Generate");
		mGeneratePath.addActionListener(this);
		mClearPath = new JButton("Clear");
		mClearPath.setAlignmentX(Component.CENTER_ALIGNMENT);
		mClearPath.setActionCommand("Clear");
		mClearPath.addActionListener(this);
		mCombinePlatforms = new JButton("Recombine");
		mCombinePlatforms.setAlignmentX(Component.CENTER_ALIGNMENT);
		mCombinePlatforms.setActionCommand("Recombine");
		mCombinePlatforms.addActionListener(this);
		mClearTestPath = new JButton("Clear Path");
		mClearTestPath.setAlignmentX(Component.CENTER_ALIGNMENT);
		mClearTestPath.setActionCommand("ClearPath");
		mClearTestPath.addActionListener(this);
		mButtonPanel.add(mGeneratePath);
		mButtonPanel.add(mClearPath);
		mButtonPanel.add(mCombinePlatforms);
		mButtonPanel.add(mClearTestPath);

		mOptionsPanel = new JPanel();
		mOptionsPanel.setLayout(new BoxLayout(mOptionsPanel, BoxLayout.PAGE_AXIS));
		mViewGroup = new ButtonGroup();
		mViewAllButton = new JRadioButton("View All", true);
		mViewAllButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		mViewAllButton.setActionCommand("ViewAll");
		mViewAllButton.addActionListener(this);
		mViewGroup.add(mViewAllButton);
		mViewSelectedButton = new JRadioButton("View Selected", false);
		mViewSelectedButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		mViewSelectedButton.setActionCommand("ViewSelected");
		mViewSelectedButton.addActionListener(this);
		mViewGroup.add(mViewSelectedButton);
		mOptionsPanel.add(mViewAllButton);
		mOptionsPanel.add(mViewSelectedButton);
		
		mStatusPanel = new JPanel();
		mStatusPanel.setLayout(new BoxLayout(mStatusPanel, BoxLayout.PAGE_AXIS));
		mStatusLabel = new JLabel("");
		mStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mStatusPanel.add(mStatusLabel);
		
		add(mButtonPanel);
		add(mOptionsPanel);
		add(mStatusPanel);
		add(Box.createVerticalStrut(600));
	}

	public void SetStatus(String status) {
		mStatusLabel.setText(status);
		invalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//String command = e.getActionCommand();
	}
}
