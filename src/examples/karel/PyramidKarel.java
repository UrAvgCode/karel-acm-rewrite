package examples.karel;

import acm.karel.SuperKarel;

public class PyramidKarel extends SuperKarel {
	public void run() {
		putFirstLineOfBeepers();
		putSecondLineOfBeepers();
		while (beepersPresent()) {
			putNthLineOfBeepers();
			move();
		}
	}

	private void putNthLineOfBeepers() {
		move();
		while (beepersPresent()) {
			putOneBeeperAbove();
		}
		removeLastBeeper();
		moveBack();
	}

	private void moveBack() {
        do {
            move();
        } while (beepersPresent());
		turnAround();
	}

	private void removeLastBeeper() {
		turnLeft();
		move();
		turnLeft();
		move();
		if (beepersPresent()) {
			pickBeeper();
		}
	}

	private void putOneBeeperAbove() {
		turnLeft();
		move();
		putBeeper();
		turnRight();
		move();
		turnRight();
		move();
		turnLeft();
	}

	private void putSecondLineOfBeepers() {
		turnLeft();
		move();
		turnLeft();
		move();
		putFirstLineOfBeepers();
		pickBeeper();
		turnAround();
		move();
	}

	private void putFirstLineOfBeepers() {
		while (frontIsClear()) {
			putBeeper();
			move();
		}
		putBeeper();
	}
}
