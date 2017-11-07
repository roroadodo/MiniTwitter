package classes;

import javax.swing.JOptionPane;

import interfaces.Visitor;
import interfaces.treeComponent;

public class TotalUserVisitor implements Visitor {
	private int Count;

	public TotalUserVisitor() {
		Count = 0;
	}

	public void visitGroup(treeComponent branchNode) {
		// don't count
	}

	public void visitUser(User user) {
		Count++;
	}

	public void output() {
		JOptionPane.showMessageDialog(null, "Total Users Count: " + Count);

	}

	public int getCount() {
		return Count;
	}
}
