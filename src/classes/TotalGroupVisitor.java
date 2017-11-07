package classes;

import javax.swing.JOptionPane;

import interfaces.Visitor;
import interfaces.treeComponent;

public class TotalGroupVisitor implements Visitor {
	private int Count;

	public TotalGroupVisitor() {
		Count = 0;
	}

	public void visitGroup(treeComponent branchNode) {
		Count++;
	}

	public void visitUser(User user) {
		//don't count
	}

	public void output() {
		JOptionPane.showMessageDialog(null, "Total Groups Count: " + Count);
	}

	public int getCount() {
		return Count;
	}

}
