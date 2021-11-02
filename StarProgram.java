import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import javax.swing.Timer;
import javax.swing.event.*;

public class StarProgram
{
	public static void main( String args[] ) throws InterruptedException
	{
		MyFrame frame = new MyFrame();
	}


}

class MyFrame extends JFrame implements ActionListener , ChangeListener
{
	boolean running = true;
	Vector<Star> starArray = new Vector<Star>();
	int speed = 1;
	Timer timer;
	int height;
	int width;
	StarPanel sp;
	JSlider speedSlider;
	JSlider lifeSlider;

	MyFrame() throws InterruptedException
	{


			for (int i = 0; i < 10; i++)
    	        starArray.add(new Star());

			sp = new StarPanel(starArray);
			JPanel buttonPanel = new JPanel();

			JButton addButton = new JButton("Add a star");
			addButton.setActionCommand("ADD");
			addButton.addActionListener(this);

			JButton addLotsButton = new JButton("Add several stars");
			addLotsButton.setActionCommand("ADDLOTS");
			addLotsButton.addActionListener(this);

			JButton clearButton = new JButton("Clear");
			clearButton.setActionCommand("CLEAR");
			clearButton.addActionListener(this);

			JButton pauseButton = new JButton("Start/Stop");
			pauseButton.setActionCommand("PAUSE");
			pauseButton.addActionListener(this);

			JButton fallButton = new JButton("Activate Gravity");
			fallButton.setActionCommand("FALL");
			fallButton.addActionListener(this);

			JButton chaseButton = new JButton("Chase the Mouse");
			chaseButton.disable();

			speedSlider = new JSlider(0, 10, 1);
			speedSlider.setMinorTickSpacing(1);
			speedSlider.setPaintTicks(true);

			lifeSlider = new JSlider(1, 2000, 1000);
			lifeSlider.setMinorTickSpacing(100);
			lifeSlider.setPaintTicks(true);


			buttonPanel.add(addButton);
			buttonPanel.add(addLotsButton);
			buttonPanel.add(clearButton);
			buttonPanel.add(pauseButton);
			buttonPanel.add(fallButton);
			buttonPanel.add(chaseButton);
			buttonPanel.add(speedSlider);
			buttonPanel.add(lifeSlider);

			buttonPanel.setLayout(new GridLayout(8,0));
			add( sp, BorderLayout.CENTER );
			add(buttonPanel, BorderLayout.EAST);
			setSize( 600, 600 );
			setLocationRelativeTo(null);

			int height = getHeight();
			int width = getWidth();
			System.out.println(height);
			System.out.println(width);

			timer = new Timer(10, this);
			timer.setActionCommand("TIME");
			timer.start();

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			setVisible( true );

//Use slider to change speed for x and y
		}

		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("ADD"))
			{
				int life = lifeSlider.getValue();
				starArray.add(new Star(life));
			}
			else if(e.getActionCommand().equals("ADDLOTS"))
			{
				int life = lifeSlider.getValue();
				starArray.add(new Star(life));
				starArray.add(new Star(life));
				starArray.add(new Star(life));
			}
			else if(e.getActionCommand().equals("CLEAR"))
			{
				starArray.clear();
			}

			else if(e.getActionCommand().equals("PAUSE"))
			{
				if(running)
				{
					running = false;
				}
				else if(!running)
				{
					running = true;
				}
			}

			else if(e.getActionCommand().equals("FALL"))
			{
				sp.toggleGravity();
			}
			else if(e.getActionCommand().equals("CHASE"))
			{

			}

			else if(e.getActionCommand().equals("TIME"))
			{
				if(running)
				{
					height = getHeight();
					width = getWidth();
					speed = speedSlider.getValue();
					sp.update(height, width, speed);
					sp.repaint();
				}
			}

	}

	public void stateChanged(ChangeEvent ce)
	{
		speed = speedSlider.getValue();
	}
}

class StarPanel extends JPanel implements ActionListener
{
	int panelHeight = getHeight();
	int panelWidth = getWidth();
	Vector<Star> starArray;

	public StarPanel(Vector<Star> starArray)
	{
		this.starArray = starArray;
	}

	public void actionPerformed(ActionEvent e) // will run when the timer fires
	{
		for(Star aStarArray : starArray)
			repaint();
	}

	@Override
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		for (Star aStarArray : starArray)
			aStarArray.draw(graphics);
	}

	public void toggleGravity()
	{
		for (Star aStarArray : starArray)
			aStarArray.toggleGravity();
	}

	void update(int height, int width, int speed)
	{
		panelHeight = getHeight();
		panelWidth = getWidth();
		Iterator<Star> starIterator = starArray.iterator();
		while(starIterator.hasNext())
		{
			Star s = starIterator.next();
			s.update(panelHeight, panelWidth, speed);

			if(s.getOpacity() == 0)
				starIterator.remove();
		}
	}

}

class Star extends MovingObject
{
	double outerRadius, innerRadius, alpha;
	int numArms;
	Color color;
	Random rand = new Random();
	double height, width;
	int experationDate;
	int age = 0;
	boolean gravity;
	Color c = new Color(1,1,1);
	int opacity = 255;



	Star()
	{
		xPos = rand.nextInt(200);
		yPos = rand.nextInt(200);
		rotationAngle = rand.nextInt(1);
		rotationSpeed = rand.nextInt(1);
		rotationAccel = rand.nextInt(1);
		xSpeed = rand.nextInt(3) + 1;
		ySpeed = rand.nextInt(3) + 1;
		xAccel = rand.nextInt(3);
		yAccel = rand.nextInt(3);
		innerRadius = rand.nextInt(45);
		outerRadius = innerRadius*2;
		color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		experationDate = rand.nextInt(1000)+100;
	}

	Star(int life)
	{
		xPos = rand.nextInt(200);
		yPos = rand.nextInt(200);
		rotationAngle = rand.nextInt(1);
		rotationSpeed = rand.nextInt(1);
		rotationAccel = rand.nextInt(1);
		xSpeed = rand.nextInt(3) + 1;
		ySpeed = rand.nextInt(3) + 1;
		xAccel = rand.nextInt(3);
		yAccel = rand.nextInt(3);
		innerRadius = rand.nextInt(45);
		outerRadius = innerRadius*2;
		color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		experationDate = life;
	}

	Star(Color color, int xPos, int yPos, double rotationAngle, double rotationSpeed, double rotationAccel, double xSpeed, double ySpeed, double xAccel, double yAccel, double outerRadius, double innerRadius, int numArms)
	{
		super();  //(xPos, yPos, rotationAngle, rotationSpeed, rotationAccel, xSpeed, ySpeed, xAccel, yAccel);
		this.outerRadius = outerRadius;
		this.innerRadius = innerRadius;
		this.numArms = numArms;
	}

	public int getOpacity()
	{
		return opacity;
	}


	public void update(double h, double w, int scalar)
	{
		height = h;
		width = w;
		double out = outerRadius;
		age++;

		if(!gravity)
		{
			if (xPos < out)
			{
				Toolkit.getDefaultToolkit().beep();
				xSpeed = Math.abs(xSpeed);
				color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			}
			if (xPos > width - out)
			{
				xSpeed = -Math.abs(xSpeed);
				color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			}
			if (yPos < out)
			{
				ySpeed = Math.abs(ySpeed);
				color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			}
			if (yPos > height - out)
			{
				ySpeed = -Math.abs(ySpeed);
				color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			}
		}
		else
		{
			c = color;
			ySpeed+=0.3;

			if (xPos < out)
			{
				xSpeed = Math.abs(xSpeed);
				color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			}
			if (xPos > width - out)
			{
				xSpeed = -Math.abs(xSpeed);
				color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			}
			if (yPos < out)
			{
				ySpeed = Math.abs(ySpeed);
				color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			}
			if (yPos > height - out)
			{
				ySpeed *= -0.8;
				color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			}
			if(ySpeed < 0 && ySpeed>-2.1)
			{
				ySpeed = 0;
				color = c;
				if(xSpeed>0)
					xSpeed = xSpeed - .02;
				if(xSpeed<0)
					xSpeed = xSpeed + .02;
				if(xSpeed == 0)
					color = c;
			}

		}


		if(age > (experationDate*3/4))
		{
			alpha++;
			if(opacity != 0)
				opacity--;
		}


		if(!gravity)
			alpha = (alpha - (r.nextFloat()%.1))%360;
		else
			alpha = .25;


		// adjust star position
		xPos += (xSpeed*scalar);
		yPos += (ySpeed*scalar);

	}

	void toggleGravity()
	{
		if(!gravity)
		{
			gravity = true;
			ySpeed = (Math.abs(ySpeed));
		}
		else if(gravity)
		{
			gravity = false;
			ySpeed = rand.nextInt(3) + 1;
			xSpeed = rand.nextInt(3) + 1;
		}

	}

	@Override
	public void draw(Graphics graphics)
	{
		Graphics2D g;
		g = (Graphics2D)graphics;

		int numArms = 10;
		int[] xCord = new int[20];
		int[] yCord = new int[20];

		double starAlpha = alpha;

		double alphaChange = (2*Math.PI)/numArms;

		for(int n = 0; n < numArms * 2; n = n + 2)
		{
			xCord[n] = xPos+ (int)(innerRadius *Math.cos(starAlpha));
			yCord[n] = yPos+ (int)(innerRadius *Math.sin(starAlpha));

			starAlpha += alphaChange;

			xCord[n+1] = xPos+  (int)(outerRadius *Math.cos(starAlpha));
			yCord[n+1] = yPos+  (int)(outerRadius *Math.sin(starAlpha));


			starAlpha += alphaChange;
		}

		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();

		Color cc = new Color(red, green, blue, opacity);
		g.setColor(cc);
		g.fillPolygon(xCord, yCord, numArms);
	}
}

abstract class MovingObject
{
	Random rand = new Random();
	int xPos, yPos;
	double rotationAngle, rotationSpeed, rotationAccel, xSpeed, ySpeed, xAccel, yAccel;

	Random r = new Random();

	void MovingObject()
	{
		xPos = rand.nextInt(400);
		yPos = rand.nextInt(400);
		rotationAngle = rand.nextInt(1);
		rotationSpeed = rand.nextInt(1);
		rotationAccel = rand.nextInt(1);
		xSpeed = rand.nextInt(3);
		ySpeed = rand.nextInt(3);
		xAccel = rand.nextInt(3);
		yAccel = rand.nextInt(3);
	}


	void MovingObject(int xPos, int yPos, double rotationAngle, double rotationSpeed, double rotationAccel, double xSpeed, double ySpeed, double xAccel, double yAccel)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.rotationAngle = rotationAngle;
		this.rotationSpeed = rotationSpeed;
		this.rotationAccel = rotationAccel;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.xAccel = xAccel;
		this.yAccel = yAccel;
	}

	void basicUpdate()
	{
	}

	abstract void draw(Graphics g);

}
