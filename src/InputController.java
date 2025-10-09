//Handles keyboard input

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputController implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        //    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //it returns a number of the key that was pressed
        int code = e.getKeyCode();
        
        //when W keycap is pressed
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        //when S keycap is pressed
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        //when A keycap is pressed
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        //when D keycap is pressed
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        //when W keycap is released
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        //when S keycap is released
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        //when A keycap is released
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        //when D keycap is released
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
    
}
