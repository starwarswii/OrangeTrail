import java.awt.EventQueue;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Display extends JDialog {

	public static JTextArea textBox;

	public Display() {
		setTitle("Orange Trail - Current Stats");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 262);
		getContentPane().add(scrollPane);
		textBox = new JTextArea();
		scrollPane.setViewportView(textBox);
		textBox.setLineWrap(true);
		textBox.setWrapStyleWord(true);
		textBox.setEditable(false);
	}
	
	public static void start() throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Display dialog = new Display();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
	}
	
	public static void setText(Object text) {
		textBox.setText((String)text);
	}
	
	public static void addText(Object text) {
		textBox.setText(textBox.getText()+"\n"+(String)text);
	}
}
