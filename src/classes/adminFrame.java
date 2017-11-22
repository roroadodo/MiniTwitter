package classes;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;

import interfaces.treeComponent;

public class adminFrame extends JFrame {
	// ==============================================
	// ALL FIELDS/BUTTONS/WIDGETS/SWING COMPONENTS
	// ==============================================
	private static adminFrame instance;
	private JPanel topPane = new JPanel();
	private JPanel treePane = new JPanel();
	private JPanel buttonsPane = new JPanel();

	private JPanel addPane = new JPanel();
	private JPanel userPane = new JPanel();
	private JPanel counterPane = new JPanel();

	private JTextField txtUserID = new JTextField();
	private JTextField txtGroupID = new JTextField();

	private JButton addUser = new JButton("Add User");
	private JButton addGroup = new JButton("Add Group");
	private JButton BtnOpenUserPanel = new JButton("Open User Panel");
	// visitor buttons
	private JButton BtnUserTotal = new JButton("Show User Total");
	private JButton BtnGroupTotal = new JButton("showGroupTotal");
	private JButton BtnMsgTotal = new JButton("Show Messages Total");
	private JButton BtnPosPercent = new JButton("Show Positive Percentage");
	private JButton BtnAllValid = new JButton("Check ID Validity");
	private JButton BtnLatestUpdate = new JButton("Find the last updated User");
	private branch root = new branch("root");
	private JTree tree;

	private String selectedComponent = "";
	private boolean updatedTree = false;
	// ==============================================
	// END OF FIELDS/BUTTONS/WIDGETS/SWING COMPONENTS
	// ==============================================

	// constructor is mostly for gui setup
	private adminFrame() {
		setTitle("MiniTwitter");
		setSize(800, 600);

		// set panes
		setPanes();
		// add buttons
		addButtons();
		addTree();
		setLayouts();
		// button events
		setEvents();

		// add to frame
		add(topPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void setPanes() {
		topPane.add(treePane);
		topPane.add(buttonsPane);
		buttonsPane.add(addPane);
		buttonsPane.add(userPane);
		buttonsPane.add(counterPane);
	}

	private void addButtons() {
		addPane.add(txtUserID);
		addPane.add(addUser);
		addPane.add(txtGroupID);
		addPane.add(addGroup);
		userPane.add(BtnOpenUserPanel);

		counterPane.add(BtnUserTotal);
		counterPane.add(BtnGroupTotal);
		counterPane.add(BtnMsgTotal);
		counterPane.add(BtnPosPercent);
		counterPane.add(BtnAllValid);
		counterPane.add(BtnLatestUpdate);
	}

	private void addTree() {
		TreeTranverser.getTreeTranverser().setRoot(root);

		treePane.setSize(400, 600);
		tree = new JTree(root.getNode());
		treePane.add(tree);
	}

	private void setLayouts() {
		addPane.setLayout(new GridLayout(2, 2));
		treePane.setLayout(new GridLayout(1, 1));
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.Y_AXIS));
		counterPane.setLayout(new GridLayout(3, 2));
	}

	private void setEvents() {
		BtnOpenUserPanel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				treeComponent selectedNode = TreeTranverser.getTreeTranverser().get(selectedComponent);
				if (selectedNode == null || selectedNode.getType().equals("branch")) {
					JOptionPane.showMessageDialog(null, "Please Select A User");
				} else {
					leaf user = (leaf) selectedNode;
					user.openUserPanel();
				}
			}
		});
		addUser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updatedTree = true;
				tree.clearSelection();

				treeComponent selectedNode = TreeTranverser.getTreeTranverser().get(selectedComponent);
				if (selectedNode == null || selectedNode.getType().equals("leaf")) {
					JOptionPane.showMessageDialog(null, "Please Select A Group");
					updatedTree = false;
				} else {
					System.out.println(selectedNode.getID());
					branch branch = (branch) selectedNode;
					branch.add(new leaf(txtUserID.getText()));

					DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
					model.reload(root.getNode());
					expandAllNodes(tree, 0, tree.getRowCount());
				}
				selectedComponent = "";
			}
		});
		addGroup.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updatedTree = true;
				tree.clearSelection();

				treeComponent selectedNode = TreeTranverser.getTreeTranverser().get(selectedComponent);
				if (selectedNode == null || selectedNode.getType().equals("leaf")) {
					JOptionPane.showMessageDialog(null, "Please Select A Group");
					updatedTree = false;
				} else {
					System.out.println(selectedNode.getID());
					branch branch = (branch) selectedNode;
					branch.add(new branch(txtGroupID.getText()));

					DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
					model.reload(root.getNode());
					expandAllNodes(tree, 0, tree.getRowCount());
				}
				selectedComponent = "";
			}
		});

		tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent e) {
				if (!updatedTree) {
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					selectedComponent = selectedNode.getUserObject().toString();
				} else
					updatedTree = false;
			}
		});

		BtnUserTotal.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TotalUserVisitor visitor = new TotalUserVisitor();
				TreeTranverser.getTreeTranverser().visit(visitor);
				visitor.output();
			}
		});
		BtnGroupTotal.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TotalGroupVisitor visitor = new TotalGroupVisitor();
				TreeTranverser.getTreeTranverser().visit(visitor);
				visitor.output();

			}
		});
		BtnMsgTotal.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TotalMessagesVisitor visitor = new TotalMessagesVisitor();
				TreeTranverser.getTreeTranverser().visit(visitor);
				visitor.output();
			}
		});
		BtnPosPercent.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PositiveMsgPercentVisitor visitor = new PositiveMsgPercentVisitor();
				TreeTranverser.getTreeTranverser().visit(visitor);
				visitor.output();
			}
		});
		BtnAllValid.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				IDVerificationVisitor visitor = new IDVerificationVisitor();
				TreeTranverser.getTreeTranverser().visit(visitor);
				visitor.output();
			}
		});
		BtnLatestUpdate.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				LastUpdatedUserVisitor visitor = new LastUpdatedUserVisitor();
				TreeTranverser.getTreeTranverser().visit(visitor);
				visitor.output();
			}
		});
	}

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}
		if (tree.getRowCount() != rowCount) {
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

	public static adminFrame getAdminFrame() {
		if (instance == null) {
			instance = new adminFrame();
		}
		return instance;

	}
}
