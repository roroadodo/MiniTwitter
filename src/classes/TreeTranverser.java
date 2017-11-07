package classes;

import interfaces.Visitor;
import interfaces.treeComponent;

public class TreeTranverser {

	private static TreeTranverser instance;
	private branch root;

	public TreeTranverser() {

	}

	public static TreeTranverser getTreeTranverser() {
		if (instance == null) {
			instance = new TreeTranverser();
		}
		return instance;
	}

	//get the treeComponent by ID
	public treeComponent get(String ID) {
		return findNode(ID, root);
	}

	//visit all the nodes on the tree and run the method
	public void visit(Visitor v) {
		visitNodes(v, root);
	}

	public void setRoot(branch node) {
		root = node;
	}

	private treeComponent findNode(String ID, branch node) {
		treeComponent foundNode = null;
		if (ID.equals(node.getID())) {
			return node;
		} else {
			for (treeComponent tc : node.getList()) {
				if (tc.getType().equals("leaf")) {
					if (ID.equals(tc.getID()))
						return tc;
				} else {
					foundNode = findNode(ID, (branch) tc);
					if (foundNode != null)
						return foundNode;
				}
			}

			return foundNode;
		}
	}

	private void visitNodes(Visitor visitor, branch node) {
		node.receive(visitor);
		for (treeComponent tc : node.getList()) {
			if (tc.getType().equals("leaf")) {
				tc.receive(visitor);
			} else {
				visitNodes(visitor, (branch) tc);
			}
		}
	}
}
