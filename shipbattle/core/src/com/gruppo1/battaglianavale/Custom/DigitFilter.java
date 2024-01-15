package com.gruppo1.battaglianavale.Custom;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class DigitFilter implements TextField.TextFieldFilter {

    private char[] accepted;

    public DigitFilter() {
        accepted = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
    }

    @Override
    public boolean acceptChar(TextField textField, char c) {
        for (char a : accepted)
            if (a == c) return true;
        return false;
    }
    //Checks for valid IP Address
    private boolean checkDots(TextField textField){
        String ip = textField.getText();
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}

