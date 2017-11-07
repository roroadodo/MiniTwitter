package classes;

import javax.swing.tree.DefaultMutableTreeNode;

import interfaces.Visitor;
import interfaces.treeComponent;

public class leaf implements treeComponent{
	private String ID;
	private User user;
	private DefaultMutableTreeNode Node;
	
	public leaf(String id) {
		this.ID = id;
		user = new User(id);
		Node = new DefaultMutableTreeNode(id, false);
	}
	
	public void openUserPanel() {
		user.openPanel();
	}

	public DefaultMutableTreeNode getNode() {
		return Node;
	}

	public User getUser() {
		return user;
	}
	
	public String getID() {
		return ID;
	}

	public String getType() {
		return "leaf";
	}

	public void receive(Visitor visitor) {
		visitor.visitUser(this.user);
	}
}
