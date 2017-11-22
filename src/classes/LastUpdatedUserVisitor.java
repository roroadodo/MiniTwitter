package classes;

import java.util.Date;

import javax.swing.JOptionPane;

import interfaces.Visitor;
import interfaces.treeComponent;

public class LastUpdatedUserVisitor implements Visitor {
	private User lastUpdatedUser;
	private long time = Long.MIN_VALUE;

	public void visitGroup(treeComponent branchNode) {
		// do not check group time
	}

	public void visitUser(User user) {
		if (user.getUpdateTime() > this.time) {
			lastUpdatedUser = user;
			time = user.getUpdateTime();
		}
	}

	public void output() {
		if (lastUpdatedUser == null) {
			JOptionPane.showMessageDialog(null, "there is no user with the Lastest Updated Time");
		} else {
			JOptionPane.showMessageDialog(null,
					"User '" + lastUpdatedUser.getID() + "' was the last updated user, at time " + new Date(time));
		}
	}

	public int getCount() {
		return 0;
	}

}
