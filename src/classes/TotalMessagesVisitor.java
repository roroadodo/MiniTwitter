package classes;

import javax.swing.JOptionPane;

import interfaces.Visitor;
import interfaces.treeComponent;

public class TotalMessagesVisitor implements Visitor {
	private int Count;

	public TotalMessagesVisitor() {
		Count = 0;
	}

	public void visitGroup(treeComponent branchNode) {
		// don't count
	}

	public void visitUser(User user) {
		Count = Count + user.getMessageCount();
	}

	public void output() {
		JOptionPane.showMessageDialog(null, "Total Messages Count: " + Count);
	}

	public int getCount() {
		return Count;
	}
}
