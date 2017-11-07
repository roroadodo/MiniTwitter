package classes;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import interfaces.TwitterObserver;
import interfaces.TwitterSubject;
import interfaces.treeComponent;

public class User implements TwitterObserver, TwitterSubject {

	// ==============================================
	// ALL FIELDS/BUTTONS/WIDGETS/SWING COMPONENTS
	// ==============================================
	private User instance = this;
	private String ID;
	private List<User> observers = new ArrayList<User>();
	private List<String> following = new ArrayList<String>();
	private List<String> tweetFeed = new ArrayList<String>();

	private JFrame Frame;
	private JPanel TopPanel = new JPanel();
	private JPanel userInfoPanel = new JPanel();
	private JPanel findOthersPanel = new JPanel();
	private JPanel listFollowingPanel = new JPanel();
	private JPanel tweetPanel = new JPanel();
	private JPanel newsFeedPanel = new JPanel();

	private JTextField txtUserID = new JTextField(20);
	private JTextField txtTweet = new JTextField(20);

	private JButton btnFollowUser = new JButton("Follow User");
	private JButton btnPostTweet = new JButton("Post Tweet");

	private int positiveMsgCount = 0;
	// ==============================================
	// END OF FIELDS/BUTTONS/WIDGETS/SWING COMPONENTS
	// ==============================================

	public User(String id) {
		this.ID = id;
		observers = new ArrayList<User>();
		Frame = new JFrame("User Panel: " + ID);
		Frame.setSize(700, 500);

		setLayouts();
		addToPanels();
		setEvents();
		addToFrame();
	}

	public void openPanel() {
		Frame.setVisible(true);
	}

	private void setEvents() {
		btnFollowUser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				treeComponent found = TreeTranverser.getTreeTranverser().get(txtUserID.getText());
				if (found != null && found.getType().equals("leaf")) {
					leaf node = (leaf) found;
					User foundUser = node.getUser();

					foundUser.addObserver(instance);

					following.add(node.getID());
					listFollowingPanel.remove(0);
					listFollowingPanel.add(new JScrollPane(new JList(following.toArray())));
					listFollowingPanel.validate();
					listFollowingPanel.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "User " + txtUserID.getText() + " not found");
				}
			}
		});

		btnPostTweet.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				notifyObservers();
			}
		});
	}

	private void setLayouts() {
		findOthersPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 7));
		tweetPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 7));
		listFollowingPanel.setLayout(new GridLayout(1, 1));
		newsFeedPanel.setLayout(new GridLayout(1, 1));
		userInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		TopPanel.setLayout(new BoxLayout(TopPanel, BoxLayout.Y_AXIS));
	}

	// Adds buttons/widgets to panels
	private void addToPanels() {
		findOthersPanel.add(new JLabel("FOLLOW OTHER USERS: "));
		findOthersPanel.add(txtUserID);
		findOthersPanel.add(btnFollowUser);
		tweetPanel.add(new JLabel("VIEW/POST TWEETS: "));
		tweetPanel.add(txtTweet);
		tweetPanel.add(btnPostTweet);
		listFollowingPanel.add(new JScrollPane(new JList(following.toArray())));
		newsFeedPanel.add(new JScrollPane(new JList(tweetFeed.toArray())));
		userInfoPanel.add(new JLabel("Panel Of User: " + ID));
	}

	private void addToFrame() {
		TopPanel.add(userInfoPanel);
		TopPanel.add(findOthersPanel);
		TopPanel.add(listFollowingPanel);
		TopPanel.add(tweetPanel);
		TopPanel.add(newsFeedPanel);
		Frame.add(TopPanel);
	}

	private void checkPositiveMsg(String msg) {
		if (msg.contains("good")) {
			positiveMsgCount++;
		}
	}

	public int getPositiveMsgCount() {
		return positiveMsgCount;
	}

	public void notifyObservers() {
		checkPositiveMsg(txtTweet.getText());
		String tweet = ID + ": " + txtTweet.getText();
		txtTweet.setText("");

		for (User follower : observers) {
			follower.getUpdate(ID, tweet);
		}

		tweetFeed.add(tweet);
		newsFeedPanel.remove(0);
		newsFeedPanel.add(new JScrollPane(new JList(tweetFeed.toArray())));
		newsFeedPanel.validate();
		newsFeedPanel.repaint();
	}

	public void getUpdate(String id, String tweet) {
		tweetFeed.add(tweet);
		newsFeedPanel.remove(0);
		newsFeedPanel.add(new JScrollPane(new JList(tweetFeed.toArray())));
		newsFeedPanel.validate();
		newsFeedPanel.repaint();
	}

	public void addObserver(TwitterObserver observer) {
		observers.add((User) observer);
	}

	public int getMessageCount() {
		return tweetFeed.size();
	}
}
