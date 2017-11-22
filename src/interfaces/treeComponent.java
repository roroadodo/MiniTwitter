package interfaces;

import javax.swing.tree.DefaultMutableTreeNode;

public interface treeComponent {
	public DefaultMutableTreeNode getNode();
	public String getID();
	public String getType();
	public long getCreationTime();
	public void receive(Visitor visitor);
}
