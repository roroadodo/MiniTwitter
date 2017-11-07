package classes;

import javax.swing.JOptionPane;

import interfaces.Visitor;
import interfaces.treeComponent;

public class PositiveMsgPercentVisitor implements Visitor{
	double positiveMsg;
	double totalMsg;
	double percentage;
	
	public PositiveMsgPercentVisitor() {
		positiveMsg = 0;
		totalMsg = 0;
	}

	public void visitGroup(treeComponent branchNode) {
		// don't count group
	}

	public void visitUser(User user) {
		totalMsg = totalMsg + user.getMessageCount();
		positiveMsg = positiveMsg + user.getPositiveMsgCount();
		percentage = (positiveMsg/totalMsg)*100;
	}

	public void output() {
		JOptionPane.showMessageDialog(null, "Positive Message Percentage: " + percentage + "%");
	}

	public int getCount() {
		return (int) totalMsg;
	}

}
