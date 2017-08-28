package arduino_piano;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.BoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.fazecast.jSerialComm.SerialPort;


class BoundedChangeListener implements ChangeListener {
	  public void stateChanged(ChangeEvent changeEvent) {
		
	    Object source = changeEvent.getSource();
	    if (source instanceof BoundedRangeModel) {
	      BoundedRangeModel aModel = (BoundedRangeModel) source;
	      if (!aModel.getValueIsAdjusting()) {
	        System.out.println("Changed: " + aModel.getValue());
	        
	      }
	    } else if (source instanceof JSlider) {
	      JSlider theJSlider = (JSlider) source;
	      if (!theJSlider.getValueIsAdjusting()) {
	        System.out.println("Slider changed: " + theJSlider.getValue());
	      }
	    } else {
	      System.out.println("Something changed: " + source);
	    }
	  }
	}
public class SensorGraph {
	
	static SerialPort chosenPort;
	static int x = 0;
	private static  float slider_value= 1;
	public static void main(String[] args) {
		
		// create and configure the window
		JFrame window = new JFrame();
		window.setTitle("Sensor Graph GUI");
		window.setSize(600, 400);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create a drop-down box and connect button, then place them at the top of the window
		JComboBox<String> portList = new JComboBox<String>();
		JButton connectButton = new JButton("Connect");
		JPanel topPanel = new JPanel();
		topPanel.add(portList);
		topPanel.add(connectButton);
		JSlider jSliderOne = new JSlider();
		jSliderOne.addChangeListener(new BoundedChangeListener());
		topPanel.add(jSliderOne);
		window.add(topPanel, BorderLayout.NORTH);
		System.out.println("flag1");
		// populate the drop-down box
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());
		
		// create the line graph
		
		
		// configure the connect button and use another thread to listen for data
		connectButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Connect")) {
					System.out.println("flag2");
					// attempt to connect to the serial port
					chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if(chosenPort.openPort()) {
						connectButton.setText("Disconnect");
						portList.setEnabled(false);
					}
					System.out.println("flag3");
					// create a new thread that listens for incoming text and populates the graph
							int lol=0;
							int lol1=0;
							int var_lol=0;
							Scanner scanner = new Scanner(chosenPort.getInputStream());
							while(scanner.hasNextLine()) {
								try {
									slider_value=jSliderOne.getValue();
									lol++;
									String line1 = scanner.nextLine();
									String a=line1;
									line1 = scanner.nextLine();
									String b=line1;
									line1 = scanner.nextLine();
									String c=line1;
									line1 = scanner.nextLine();
									String d=line1;
									Clip clip = null;
									System.out.println(""+slider_value);
								
									while(!d.equals("A")){
										line1 = scanner.nextLine();
										d=line1;
									}
									
									
									//String line = scanner.nextLine();
									String correct="A";
									
									int vol=Integer.parseInt(b);
									System.out.println(vol);
									
									System.out.println("\n slider1:"+slider_value);
									slider_value=slider_value/10;
									slider_value=slider_value -10;
									slider_value=slider_value*(-1);
									System.out.println("\n slider2:"+slider_value);
									vol*=slider_value;
									vol=vol+10;
									System.out.println("value:"+vol);
									
									/*float vol_f=vol;
									vol_f=vol_f+vol_f*slider_value;
									vol=vol*Math.round(vol_f);*/
									
									//System.out.println("\n slider:"+slider_value);
									//System.out.println("\n vol:"+vol);
									System.out.println("\n valores a:"+a+", b:"+b+", c:"+c+", d:"+d);
									int number = Integer.parseInt(a);
									if(d.equals(correct)){
										if(number==1){
											//splash
											if(c.equals("0")){
												AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												new File("C:/sons/Splash.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
											}else{
												AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												new File("C:/sons/som1.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
											}
										}
										if(number==2){
											if(c.equals("0")){
												AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
											    new File("C:/sons/Crash.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
											}else{
												AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												new File("C:/sons/som2.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
											}
										}
										if(number==3){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("C:/sons/Tom1.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
										}
										if(number==4){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("C:/sons/Tom2.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
										}
										if(number==5){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("C:/sons/Floor.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
										}
										if(number==6){
											if(c.equals("0")){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("C:/sons/CrashBig.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
											}else{
												AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												new File("C:/sons/Hihat.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
											}
										}
										if(number==7){
											if(c.equals("0")){
												AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
													new File("C:/sons/Open.wav"));
													clip = AudioSystem.getClip();
													clip.open(audioInputStream);
													FloatControl gainControl =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
													gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
													clip.start();
											}else{
												AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												new File("C:/sons/som3.wav"));
												clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
											}
										}
										if(number==8){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
											 new File("C:/sons/Ride2.wav"));
											 clip = AudioSystem.getClip();
											clip.open(audioInputStream);
											FloatControl gainControl = 
											(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
											gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
											clip.start();
										}
										if(number==9){
											if(c.equals("0")){
												AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												new File("C:/sons/Ride1.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
											}else{
												AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												new File("C:/sons/som4.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
											}
										}
										if(number==10){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("C:/sons/Snare1.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
										}
										if(number==11){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("C:/sons/Snare2.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
										}
										if(number==12){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("C:/sons/Kick.wav"));
												 clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
										}
										/*
										if(number==13){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("/src/musica/Kick.wav"));
												Clip clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
										}*/
										if(number==14){
											
												clip.stop();
										}
										if(number==15){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("C:/sons/Hihat.wav"));
												clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
											
										}/*
										if(number==16){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("C:/Users/Pedro/workspace/piano/src/wav/9 Audio Track.wav"));
												Clip clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
										}
										if(number==17){
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
												    new File("C:/Users/Pedro/workspace/piano/src/wav/8 Audio Track.wav"));
												Clip clip = AudioSystem.getClip();
												clip.open(audioInputStream);
												FloatControl gainControl = 
												    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
												gainControl.setValue(-vol ); // Reduce volume by 10 decibels.
												clip.start();
										}*/


										
										
								}else{
									lol1++;
									System.out.println(" numero vez feitas:"+lol+"\n falhou:"+lol1);
								}
								System.out.println("\n");
									window.repaint();
								} catch(Exception e) {}
							}
							scanner.close();
					
				} else {
					// disconnect from the serial port
					chosenPort.closePort();
					portList.setEnabled(true);
					connectButton.setText("Connect");
					
					x = 0;
				}
			}
		});
		
		// show the window
		window.setVisible(true);
	}

}