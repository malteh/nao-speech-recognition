package recorder

import javax.swing._

class TestFrame extends JFrame("Hello World") {
   import JFrame._;

   setDefaultLookAndFeelDecorated(true);

   setDefaultCloseOperation(EXIT_ON_CLOSE);

   val button = new JButton("Hello world");
   button addActionListener Print("Hello world");
   getContentPane() add button;

   pack;
   setSize(400, 300);
   setVisible(true);
   
   import java.awt.event._;
   
   case class Print(msg : String) extends ActionListener with Application {
      def actionPerformed(e : ActionEvent) =
         Console println msg
   }
}

object TestFrame extends Application {
   val win = new TestFrame
}