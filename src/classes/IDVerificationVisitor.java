package classes;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import interfaces.Visitor;
import interfaces.treeComponent;

public class IDVerificationVisitor implements Visitor {
	private Set IDset = new HashSet<String>();
	private boolean allValid = true;

	public void visitGroup(treeComponent branchNode) {
		String ID = branchNode.getID();
		if (ID.contains(" ")) {
			allValid = false;
		} else if (IDset.contains(ID)) {
			allValid = false;
		} else {
			IDset.add(ID);
		}

	}

	public void visitUser(User user) {
		String ID = user.getID();
		if (ID.contains(" ")) {
			allValid = false;
		} else if (IDset.contains(ID)) {
			allValid = false;
		} else {
			IDset.add(ID);
		}
	}

	public void output() {
		if(allValid) {
			JOptionPane.showMessageDialog(null, "All ID were valid");
		}else {
			JOptionPane.showMessageDialog(null, "Not all IDs were valid");
		}
	}

	public int getCount() {
		return 0;
	}

}
