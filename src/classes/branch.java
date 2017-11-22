package classes;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import interfaces.Visitor;
import interfaces.treeComponent;

//branches are composites. they can hold leaves or other branches
public class branch implements treeComponent {

	private List<treeComponent> TreeComponentsList;
	private String ID;
	private boolean IsEmpty;
	private DefaultMutableTreeNode Node;
	private long CreationTime;

	public branch(String id) {
		this.ID = id;
		this.CreationTime = System.currentTimeMillis();
		IsEmpty = true;
		TreeComponentsList = new ArrayList<treeComponent>();
		Node = new DefaultMutableTreeNode(id, true);
		Node.add(new DefaultMutableTreeNode("<EMPTY>"));
	}

	public void add(treeComponent incomingComponent) {
		if (IsEmpty) {
			Node.removeAllChildren();
		}
		IsEmpty = false;

		TreeComponentsList.add(incomingComponent);
		Node.add(incomingComponent.getNode());
	}

	public List<treeComponent> getList() {
		return TreeComponentsList;
	}

	public DefaultMutableTreeNode getNode() {
		return Node;
	}

	public String getID() {
		return ID;
	}

	public String getType() {
		return "branch";
	}

	public void receive(Visitor visitor) {
		visitor.visitGroup(this);
	}

	public long getCreationTime() {
		return CreationTime;
	}
}
