package util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class validaciones {

	public static void validar(JTextField txt, int longitud){
		
		txt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e){
				char c = e.getKeyChar();
				int cant = txt.getText().length();
				
				if(!Character.isDigit(c)){
					e.consume();
				}
				
				if(cant>=longitud){
					e.consume();
				}
				
			}
		});
	}
}
