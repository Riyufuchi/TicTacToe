package Utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

public class FinalValues 
{
	//Colors
	public static final Color DEFAULT_BUTTON_BACKGROUND = new Color(214,217,223);
	public static final Color DEFAULT_PANE_BACKGROUND = new Color(192,192,192);
	public static final Color OK = new Color(75,175,75);
	//Visuals
	public static final Font GAME_FIELD_FONT = new Font((new JButton()).getFont().getName(), Font.PLAIN, 16);
	public static final Font ERROR_WINDOW_FONT = new Font((new JLabel()).getFont().getName(), Font.PLAIN, 16);
	public static final Font DEFAULT_FONT = new Font((new JLabel()).getFont().getName(), Font.BOLD, 16);
	//public static final Font ERROR_WINDOW_FONT = javax.swing.UIManager.getDefaults().getFont("Label.font");
	//Game logic
	public static final String CAPPED = "OCCUPIED";
	//Misc
	public static final String GAME_TITTLE = "TicTacToe - " + FinalValues.VERSION;
	public static final String VERSION = "2.3";
	public static final String HOW_TO_PLAY = "Players take turns.\nFirst player that make line (horizontally, vertically or diagonally) of his team symbol wins.";
	public static final String LICENSE = "Copyright (c) 2021 Riyufuchi\n\n" + 
			"This licence is only aplicable for code with my copyright header and not for used libraries.\n" + 
			"The code for this software is public for DEMOSTRATIONAL, PRESTATIONAL and EDUCATONAL purposes.\n" + 
			"This software can be used for personal use (without limitation the rights to use, copy and modify), but not for redistribution with goal of making profit.\n" + 
			"If modification are made end user have to be notified.\n" + 
			"\n" + 
			"The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n" + 
			"\n" + 
			"THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" + 
			"IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" + 
			"FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" + 
			"AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" + 
			"LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" + 
			"OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" + 
			"SOFTWARE.";
}
