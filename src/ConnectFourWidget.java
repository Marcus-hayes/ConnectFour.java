

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConnectFourWidget extends JPanel implements SpotListener, ActionListener {

	private enum Player {
		BLUE, RED
	};

	private ConnectFourSpotBoard board;
	private JLabel message;
	private boolean game_won;
	private Player next_to_play;
	private int ttlMoves;
	private int x;
	private int y;
	private Color index;
	private Spot[] spots;

	public ConnectFourWidget() {

		board = new ConnectFourSpotBoard(7, 6);
		message = new JLabel();
		spots = new Spot[4];

		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);

		JPanel resetmessage_panel = new JPanel();
		resetmessage_panel.setLayout(new BorderLayout());

		JButton reset_button = new JButton("Restart");
		reset_button.addActionListener(this);
		resetmessage_panel.add(reset_button, BorderLayout.EAST);
		resetmessage_panel.add(message, BorderLayout.CENTER);

		add(resetmessage_panel, BorderLayout.SOUTH);

		board.addSpotListener(this);

		resetGame();
	}

	private void resetGame() {

		for (Spot s : board) {
			s.clearSpot();
		}

		for (int i = 0; i < spots.length; i++) {

			if (spots[i] != null) {
				spots[i].unhighlightSpot();
				spots[i] = null;
			}
		}

		ttlMoves = 0;
		game_won = false;
		next_to_play = Player.RED;

		message.setText("Welcome to Connect Four. Red to play");
	}

	public void actionPerformed(ActionEvent e) {

		resetGame();
	}

	public void spotClicked(Spot s) {

		if (game_won) {
			return;
		}

		String player_name = null;
		String next_player_name = null;
		Color player_color = null;

		if (s.isEmpty()) {

			if (next_to_play == Player.BLUE) {
				player_color = Color.BLUE;
				player_name = "Blue";
				next_player_name = "Red";
				next_to_play = Player.RED;

			} else {

				player_color = Color.RED;
				player_name = "Red";
				next_player_name = "Blue";
				next_to_play = Player.BLUE;
			}

			index = player_color;

			for (int j = board.getSpotHeight() - 1; j >= 0; j--) {

				if (board.getSpotAt(s.getSpotX(), j).isEmpty()) {

					board.getSpotAt(s.getSpotX(), j).setSpotColor(player_color);
					board.getSpotAt(s.getSpotX(), j).toggleSpot();

					y = j;
					x = s.getSpotX();
					index = board.getSpotAt(x, y).getSpotColor();
					break;
				}
			}

			ttlMoves++;

			hasWon();

		}

		if (game_won) {

			message.setText(player_name + " has won! Game over.");
			highlightWin(spots);

		} else if (ttlMoves == (board.getSpotHeight() * board.getSpotWidth()) && !game_won) {

			message.setText("Draw Game! Game Over.");

		} else {

			message.setText(next_player_name + " to play.");

		}

	}

	@Override
	public void spotEntered(Spot s) {

		if (game_won) {
			highlightWin(spots);
			return;
		}
		
		for (int i = 0; i < board.getSpotHeight(); i++) {
			if (board.getSpotAt(s.getSpotX(), i).isEmpty()) {
				board.getSpotAt(s.getSpotX(), i).highlightSpot();
			}

		}

	}

	@Override
	public void spotExited(Spot s) {

		for (int i = 0; i < board.getSpotHeight(); i++) {

			board.getSpotAt(s.getSpotX(), i).unhighlightSpot();

		}
	}

	private void hasWon() {

		int counter = -1;

		// Horizontal Check (To right)
		for (int i = x; i < x + 4; i++) {

			if (i >= board.getSpotWidth()) {
				break;
			}

			if (board.getSpotAt(i, y).isEmpty()) {
				break;
			}

			if (board.getSpotAt(i, y).getSpotColor().equals(index)) {
				counter++;
				spots[counter] = board.getSpotAt(i, y);
			}
		}

		if (counter == 3) {
			game_won = true;
			return;
		}

		// Horizontal Check (to left)
		for (int i = x - 1; i > x - 4; i--) {

			if (i < 0) {
				break;
			}

			if (board.getSpotAt(i, y).isEmpty()) {
				break;
			}

			if (board.getSpotAt(i, y).getSpotColor().equals(index)) {
				counter++;
				spots[counter] = board.getSpotAt(i, y);
			}
		}

		if (counter == 3) {
			game_won = true;
			return;
		}

		counter = -1;

		// Vertical Check (From top to bottom)
		for (int i = y; i < y + 4; i++) {

			if (i >= board.getSpotHeight()) {
				break;
			}

			if (board.getSpotAt(x, i).isEmpty()) {
				break;
			}

			if (board.getSpotAt(x, i).getSpotColor().equals(index)) {
				counter++;
				spots[counter] = board.getSpotAt(x, i);
			}
		}

		if (counter == 3) {
			game_won = true;
			return;
		}

		// Diagonal Check (To down-right)

		counter = -1;
		int j = y;

		for (int i = x; i < x + 4; i++) {

			if (i >= board.getSpotWidth()) {
				break;
			}

			if (j >= board.getSpotHeight()) {
				break;
			}

			if (board.getSpotAt(i, j).isEmpty()) {
				break;
			}

			if (board.getSpotAt(i, j).getSpotColor().equals(index)) {
				counter++;
				spots[counter] = board.getSpotAt(i, j);
			}

			j++;

		}

		if (counter == 3) {
			game_won = true;
			return;
		}

		// Diagonal Check (to up-left)

		j = y - 1;

		for (int i = x - 1; i > x - 4; i--) {

			if (i < 0) {
				break;
			}

			if (j < 0) {
				break;
			}

			if (board.getSpotAt(i, j).isEmpty()) {
				break;
			}

			if (board.getSpotAt(i, j).getSpotColor().equals(index)) {
				counter++;
				spots[counter] = board.getSpotAt(i, j);
			}

			j--;

		}

		if (counter == 3) {
			game_won = true;
			return;
		}

		// Diagonal Check (to down-left)

		j = y;
		counter = -1;

		for (int i = x; i > x - 4; i--) {

			if (i < 0) {
				break;
			}

			if (j >= board.getSpotHeight()) {
				break;
			}

			if (board.getSpotAt(i, j).isEmpty()) {
				break;
			}

			if (board.getSpotAt(i, j).getSpotColor().equals(index)) {
				counter++;
				spots[counter] = board.getSpotAt(i, j);
			}

			j++;
		}

		if (counter == 3) {
			game_won = true;
			return;
		}

		// Diagonal Check (To up-right)

		j = y - 1;

		for (int i = x + 1; i < x + 4; i++) {

			if (i >= board.getSpotWidth()) {
				break;
			}

			if (j < 0) {
				break;
			}

			if (board.getSpotAt(i, j).isEmpty()) {
				break;
			}

			if (board.getSpotAt(i, j).getSpotColor().equals(index)) {
				counter++;
				spots[counter] = board.getSpotAt(i, j);
			}

			j--;

		}

		if (counter == 3) {
			game_won = true;
			return;
		}

	}

	private void highlightWin(Spot[] spots) {

		for (int i = 0; i < spots.length; i++) {
			spots[i].highlightSpot();
		}
	}
}
