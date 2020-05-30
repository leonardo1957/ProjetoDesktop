package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

class JStringFormatField extends PlainDocument {

	private String mask;

	public JStringFormatField(String mask) {
		this.mask = mask;
	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (offs + str.length() > mask.length()) {
			return;
		}
		if (resolveString(str, offs)) {
			super.insertString(offs, str, a);
		}
	}

	private boolean resolveString(String str, int offset) {
		boolean accept = true;
		for (int i = offset; i < offset + str.length(); i++) {
			accept = accept & resolveChar(str.charAt(i - offset), i);
		}
		return accept;
	}

	private boolean resolveChar(char ch, int index) {
		switch (mask.charAt(index)) {
		case '#':
			return Character.isDigit(ch);
		case 'L':
			return Character.isLetter(ch);
		default:
			return false;
		}
	}
}