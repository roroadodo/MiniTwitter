package interfaces;

import classes.User;

public interface Visitor {
	public void visitGroup(treeComponent branchNode);
	public void visitUser(User user);
	public void output();
	public int getCount();
}
