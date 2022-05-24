import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;

/* Nine-J symbol calculator. */

public class NJ extends Applet implements ActionListener, Runnable {
    
    Thread playing;
    
    // declare our A.M values out here
    
    BigRational j1,j2,j3,j4,j5,j6,j7,j8,j9;

    // Let's allow the user to input the numbers as reals. 
    // a double will be just fine here.
    double hj1,hj2,hj3,hj4,hj5,hj6,hj7,hj8,hj9;

    
    // the approximate answer as a 'double'

    double approxanswer;

    
    // panels for the rows of GUI things:
    
    // row one has some text:
    Panel row1 = new Panel();
    Label instructions = new Label
	("Enter the values of a.m. in the boxes below..",Label.CENTER);

    // row two has j1-3
    Panel row2 = new Panel();
    Label j1label = new Label("j1=",Label.RIGHT);
    TextField j1field = new TextField(4);
    Label j2label = new Label("j2=",Label.RIGHT);
    TextField j2field = new TextField(4);
    Label j3label = new Label("j3=",Label.RIGHT);
    TextField j3field = new TextField(4);

    // row three has j4-6
    Panel row3 = new Panel();
    Label j4label = new Label("j4=",Label.RIGHT);
    TextField j4field = new TextField(4);
    Label j5label = new Label("j5=",Label.RIGHT);
    TextField j5field = new TextField(4);
    Label j6label = new Label("j6=",Label.RIGHT);
    TextField j6field = new TextField(4);

    // row four has j7-9
    Panel row4 = new Panel();
    Label j7label = new Label("j7=",Label.RIGHT);
    TextField j7field = new TextField(4);
    Label j8label = new Label("j8=",Label.RIGHT);
    TextField j8field = new TextField(4);
    Label j9label = new Label("j9=",Label.RIGHT);
    TextField j9field = new TextField(4);
    

    // row five has the buttons
    Panel row5 = new Panel();
    Button go = new Button("Go!");
    Button clear = new Button("Clear");

    // row six has the answer
    Panel row6 = new Panel();
    TextField outfield = new TextField(80);

    // row seven has the approximate answer
    Panel row7 = new Panel();
    Label approxlabel = new Label("approx:",Label.RIGHT);
    TextField approxfield = new TextField(40);
    
    
    public void init() {
	
	Font myfont = new Font("SansSerif", Font.PLAIN, 12);
	setFont(myfont);
	
	// set up the layout of the GUI
	
	GridLayout mylayout = new GridLayout(6,1,1,1);
	setLayout(mylayout);
	
	FlowLayout layout1 = new FlowLayout(FlowLayout.CENTER, 1,1);
	row1.setLayout(layout1);
	row1.add(instructions);
	//add(row1);
	
	// do "j" row
	FlowLayout layout2 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row2.setLayout(layout2);
	row2.add(j1label);
	row2.add(j1field);
	row2.add(j2label);
	row2.add(j2field);
	row2.add(j3label);
	row2.add(j3field);
	add(row2);
	

	FlowLayout layout3 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row3.setLayout(layout3);
	row3.add(j4label);
	row3.add(j4field);
	row3.add(j5label);
	row3.add(j5field);
	row3.add(j6label);
	row3.add(j6field);
	add(row3);

	FlowLayout layout4 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row4.setLayout(layout4);
	row4.add(j7label);
	row4.add(j7field);
	row4.add(j8label);
	row4.add(j8field);
	row4.add(j9label);
	row4.add(j9field);
	add(row4);

	FlowLayout layout5 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row5.setLayout(layout5);
	row5.add(go);
	row5.add(clear);
	add(row5);

	FlowLayout layout6 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row6.setLayout(layout6);
	outfield.setEditable(false);
	row6.add(outfield);
	add(row6);
	
	FlowLayout layout7 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row7.setLayout(layout7);
	approxfield.setEditable(false);
	row7.add(approxlabel);
	row7.add(approxfield);
	add(row7);
	// done setting up the layout. Now add some listeners (to the buttons)

	go.addActionListener(this);
	clear.addActionListener(this);
    }
    
    
    public void actionPerformed(ActionEvent event) {
	String command = event.getActionCommand();
	if(command == "Go!") { 
	    playing = new Thread(this);
	    playing.start();
	    go.setEnabled(false);
	    clear.setEnabled(false);
	    outfield.setText("");
	}
	if(command == "Clear") 
	    clearAllFields();
    }
    
    void clearAllFields(){
	j1field.setText(null);
	j2field.setText(null);
	j3field.setText(null);
	j4field.setText(null);
	j5field.setText(null);
	j6field.setText(null);
	j7field.setText(null);
	j8field.setText(null);
	j9field.setText(null);
	outfield.setText(null);
	approxfield.setText(null);
    }
    
    public void run() {
	// This is where the thread starts.. we need to pass the values from
	// our boxes to the Clebsch class..
	
	final BigRational ONE_R = new BigRational(1);
	
	
	// We get the input from the input boxes and convert to the 
	// BigInteger type.
	// This can throw an exception if bad input is given, so let's catch
	// it if we can..
	
	try {
	    hj1 = (new java.lang.Double(j1field.getText())).doubleValue();
	    hj2 = (new java.lang.Double(j2field.getText())).doubleValue();
	    hj3 = (new java.lang.Double(j3field.getText())).doubleValue();
	    hj4 = (new java.lang.Double(j4field.getText())).doubleValue();
	    hj5 = (new java.lang.Double(j5field.getText())).doubleValue();
	    hj6 = (new java.lang.Double(j6field.getText())).doubleValue();
	    hj7 = (new java.lang.Double(j7field.getText())).doubleValue();
	    hj8 = (new java.lang.Double(j8field.getText())).doubleValue();
	    hj9 = (new java.lang.Double(j9field.getText())).doubleValue();
	}
	catch (java.lang.NumberFormatException e) {
	    outfield.setText("All input must be numerical");
	    go.setEnabled(true);
	    clear.setEnabled(true);
	    return;
	}

	// convert decimal input to BigRational.
	j1 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj1))),
			     new BigInteger("2"));
	j2 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj2))),
			     new BigInteger("2"));
	j3 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj3))),
			     new BigInteger("2"));
	j4 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj4))),
			     new BigInteger("2"));
	j5 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj5))),
			     new BigInteger("2"));
	j6 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj6))),
			     new BigInteger("2"));
	j7 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj7))),
			     new BigInteger("2"));
	j8 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj8))),
			     new BigInteger("2"));
	j9 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj9))),
			     new BigInteger("2"));


	
	// Conditions: the j's must satisfy some triangle relations..
	
	if(Math.abs(hj1-hj2) > hj3 || Math.abs(hj1+hj2) < hj3) {
	    outfield.setText("0 ( j1, j2 and j3 do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if(Math.abs(hj4-hj5) > hj6 || Math.abs(hj4+hj5) < hj6) {
	    outfield.setText("0 ( j4, j5 and j6 do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if(Math.abs(hj7-hj8) > hj9 || Math.abs(hj7+hj8) < hj9) {
	    outfield.setText("0 ( j7, j8 and j9 do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if(Math.abs(hj1-hj4) > hj7 || Math.abs(hj1+hj4) < hj7) {
	    outfield.setText("0 ( j1, j4 and j7 do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if(Math.abs(hj2-hj5) > hj8 || Math.abs(hj2+hj5) < hj8) {
	    outfield.setText("0 ( j2, j5 and j8 do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if(Math.abs(hj3-hj6) > hj9 || Math.abs(hj3+hj6) < hj9) {
	    outfield.setText("0 ( j3, j6 and j9 do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}

	// and each of the above triads must be able to couple to each other
	// in the sense that there is not a half-unit of angular momentum 
	// left over.
	if( (new Double(hj1+hj2+hj3)).intValue()*2 != 
	    (new Double(2*(hj1+hj2+hj3))).intValue() ) {
	    outfield.setText("0 (j1 and j2 cannot couple to j3)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if( (new Double(hj4+hj5+hj6)).intValue()*2 != 
	    (new Double(2*(hj4+hj5+hj6))).intValue() ) {
	    outfield.setText("0 (j4 and j5 cannot couple to j6)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if( (new Double(hj7+hj8+hj9)).intValue()*2 != 
	    (new Double(2*(hj7+hj8+hj9))).intValue() ) {
	    outfield.setText("0 (j7 and j8 cannot couple to j9)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if( (new Double(hj1+hj4+hj7)).intValue()*2 != 
	    (new Double(2*(hj1+hj4+hj7))).intValue() ) {
	    outfield.setText("0 (j1 and j4 cannot couple to j7)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if( (new Double(hj2+hj5+hj8)).intValue()*2 != 
	    (new Double(2*(hj2+hj5+hj8))).intValue() ) {
	    outfield.setText("0 (j2 and j5 cannot couple to j8)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if( (new Double(hj3+hj6+hj9)).intValue()*2 != 
	    (new Double(2*(hj3+hj6+hj9))).intValue() ) {
	    outfield.setText("0 (j1 and j2 cannot couple to j3)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}	    
	
	//
	//  We get this far if we pass all the tests for suitability of
	// input parameters. Now nothing can go wrong :-)
	//
	
	
	// now we must calculate the delta(j1,j2,j)^2 quantities
	// which sit outside the sum;
	BigRational delta1 = new BigRational();
	BigRational delta2 = new BigRational();
	BigRational delta3 = new BigRational();
	BigRational delta4 = new BigRational();
	BigRational delta5 = new BigRational();
	BigRational delta6 = new BigRational();
	BigRational deltas = new BigRational();
	delta1 = delta(j1,j2,j3);
	delta2 = delta(j4,j5,j6);
	delta3 = delta(j2,j5,j8);
	delta4 = delta(j7,j8,j9);
	delta5 = delta(j1,j4,j7);
	delta6 = delta(j3,j6,j9);
	deltas = (delta1.multiply(delta2).multiply(delta3).multiply(delta4))
	    .divide(delta5.multiply(delta6));

	// also the other factor outside the sum;
	BigRational outfac = 
	    new BigRational
		( factorial(j1.add(j4).subtract(j7).toBigInteger())
		  .multiply(factorial(j3.add(j6).subtract(j9).toBigInteger()))
		  .multiply(factorial(j7.add(j8).add(j9).add(ONE_R)
				      .toBigInteger())),
		  factorial(j1.add(j4).add(j7).add(ONE_R).toBigInteger())
		  .multiply(factorial(j1.subtract(j2).add(j3).toBigInteger()))
		  .multiply(factorial(j2.subtract(j1).add(j3).toBigInteger()))
		  .multiply(factorial(j4.subtract(j5).add(j6).toBigInteger()))
		  .multiply(factorial(j5.subtract(j4).add(j6).toBigInteger()))
		  .multiply(factorial(j2.subtract(j5).add(j8).toBigInteger()))
		  .multiply(factorial(j5.subtract(j2).add(j8).
				      toBigInteger())));
	
	// Now we can do the sum. Happy Happy Joy Joy.
	// the 't' quantities are used to find the limits of the sum for 
	// tht 't' index, likewise those beginning with x,y and z
	BigInteger t1 = j5.multiply(new BigInteger("2")).toBigInteger();
	BigInteger t2 = j2.subtract(j5).add(j7).subtract(j9).toBigInteger();
	BigInteger t3 = j2.add(j5).subtract(j7).add(j9).toBigInteger();

	BigInteger x1 = j1.multiply(new BigInteger("2")).toBigInteger();
	BigInteger x2 = j2.subtract(j1).add(j3).toBigInteger();
	BigInteger x3 = j1.add(j2).subtract(j3).toBigInteger();

	BigInteger y1 = j2.multiply(new BigInteger("2")).toBigInteger();
	BigInteger y2 = j5.subtract(j2).add(j8).toBigInteger();
	BigInteger y3 = j2.add(j5).subtract(j8).toBigInteger();

	BigInteger z1 = j4.multiply(new BigInteger("2")).toBigInteger();
	BigInteger z2 = j5.subtract(j4).add(j6).toBigInteger();
	BigInteger z3 = j5.add(j4).subtract(j6).toBigInteger();

	BigInteger tlo = new BigInteger("0");
	BigInteger thi = t1;
	if(tlo.compareTo(t2.negate()) < 0) tlo = t2.negate();
	if(thi.compareTo(t3) > 0) thi = t3;

	BigInteger xlo = new BigInteger("0");
	BigInteger xhi = x1;
	if(xlo.compareTo(x2.negate()) < 0) xlo = x2.negate();
	if(xhi.compareTo(x3) > 0) xhi = x3;

	BigInteger ylo = new BigInteger("0");
	BigInteger yhi = y1;
	if(ylo.compareTo(y2.negate()) < 0) ylo = y2.negate();
	if(yhi.compareTo(y3) > 0) yhi = y3;

	BigInteger zlo = new BigInteger("0");
	BigInteger zhi = z1;
	if(zlo.compareTo(z2.negate()) < 0) zlo = z2.negate();
	if(zhi.compareTo(z3) > 0) zhi = z3;
	
	BigInteger xlos = xlo;
	BigInteger ylos = ylo;
	BigInteger zlos = zlo;
	BigInteger xhis = xhi;
	BigInteger yhis = yhi;
	BigInteger zhis = zhi;
	
	// now we are ready to perform the sum. Ugh.
	
	BigRational sumvar = new BigRational(0);
	float pcdone = 0.0f;
	int range = (thi.subtract(tlo)).intValue();
	float pcpert = 100.0f/(float) range;
	
	
	// loop over `t'
	for (BigInteger t = tlo; t.compareTo(thi)<=0;
	     t=t.add(new BigInteger("1"))) {

	    // Display "thinking" thing so user doesn't think applet
	    // has crashed.
	    if(range >=10) {
		outfield.setText("Thinking.."+
				 java.lang.Integer.toString((int)pcdone)+
				 "% done.");
		pcdone += pcpert;
	    }
	    
	    // calculate terms which depend only on the looping index 't'
	    BigRational tfac = 
		new BigRational(factorial(t1.subtract(t))
				.multiply(factorial(t2.add(t))),
				factorial(t)
				.multiply(factorial(t3.subtract(t))));
		

	    // loop over 'x' - we further constrain 'x' depending on
	    // the value of t here
	    BigInteger x4 = j6.subtract(j5).subtract(j1).add(j7)
		.toBigInteger().add(t);
	    BigInteger x5 = j3.subtract(j1).subtract(j5).add(j7)
		.subtract(j9).toBigInteger().add(t);
	    xlo = xlos;
	    xhi = xhis;
	    if(xlo.compareTo(x4.negate()) < 0) xlo = x4.negate(); 
	    if(xlo.compareTo(x5.negate()) < 0) xlo = x5.negate(); 

	    for (BigInteger x = xlo; x.compareTo(xhi)<=0;
		 x=x.add(new BigInteger("1"))) {
		
		BigRational xfac = 
		    new BigRational(factorial(x1.subtract(x))
				    .multiply(factorial(x2.add(x)))
				    .multiply(factorial(x4.add(x))),
				    factorial(x)
				    .multiply(factorial(x3.subtract(x)))
				    .multiply(factorial(x5.add(x))));
					       
		// loop over 'y' - including further constraints
		BigInteger y4 = j5.subtract(j2).add(j8)
		    .toBigInteger().subtract(t);
		BigInteger y5 = j2.subtract(j5).add(j7).subtract(j9)
		    .toBigInteger().add(t);
		ylo = ylos;
		yhi = yhis;
		if(ylo.compareTo(y4.negate()) < 0) ylo = y4.negate();
		if(yhi.compareTo(y5) > 0) yhi = y5;

		for (BigInteger y = ylo; y.compareTo(yhi)<=0;
		     y=y.add(new BigInteger("1"))) {

		    BigRational yfac = 
			new BigRational(factorial(y1.subtract(y))
					.multiply(factorial(y2.add(y))),
					factorial(y)
					.multiply(factorial(y3.subtract(y)))
					.multiply(factorial(y4.add(y)))
					.multiply(factorial(y5.subtract(y))));

		    // and loop over 'z' - again constraining the sum
		    // due to the values of t, x and y;
		    BigInteger z4 = j1.add(j4).subtract(j7)
			.toBigInteger().subtract(x);
		    BigInteger z5 = j5.subtract(j4).add(j6)
			.toBigInteger().subtract(t);
		    BigInteger z6 = j3.subtract(j1).subtract(j4)
			.add(j6).add(j7).add(j9)
			.add(ONE_R).toBigInteger().add(x);
		    BigInteger z7 = j3.subtract(j4).add(j5).add(j9)
			.toBigInteger().subtract(t);
		    zlo = zlos;
		    zhi = zhis;
		    if(zlo.compareTo(z7.negate()) < 0) zlo = z7.negate();
		    if(zlo.compareTo(z6.negate()) < 0) zlo = z6.negate();
		    if(zlo.compareTo(z5.negate()) < 0) zlo = z5.negate();
		    if(zhi.compareTo(z4) > 0) zhi = z4;

		    for (BigInteger z = zlo; z.compareTo(zhi)<=0;
			 z=z.add(new BigInteger("1"))) {
			
			BigRational toadd = new 
			    BigRational(factorial(z1.subtract(z))
					.multiply(factorial(z2.add(z)))
					.multiply(factorial(z7.add(z))),
					factorial(z3.subtract(z))
					.multiply(factorial(z))
					.multiply(factorial(z6.add(z)))
					.multiply(factorial(z5.add(z)))
					.multiply(factorial(z4.subtract(z))));
			toadd = toadd.multiply(yfac).multiply(tfac)
			    .multiply(xfac);

			BigInteger phase = j1.subtract(j3).add(j5)
			    .subtract(j7).add(j9).toBigInteger().add(x)
			    .add(y).add(z).add(t);

			if(phase.mod(new BigInteger("2"))
			   .compareTo(new BigInteger("0")) == 0) {

			    sumvar = sumvar.add(toadd);
			}
			else {
			    sumvar = sumvar.subtract(toadd);
			}
		    }			   
		}
	    }
	}
					
	
	// Now we have everything and we just need to do a little 
	// manipulation for output.

	boolean isneg=false;
	
	// multiply result by appropriate phase for 6j;
	if(sumvar.numerator.compareTo(new BigInteger("0"))<0) isneg=true;

	BigRational inside = deltas;
	outfield.setText("Simplifying Expression");
	BigInteger denomfactors = squarefactor(inside.denominator);
	BigInteger numerfactors = squarefactor(inside.numerator);

	inside = (inside.divide(numerfactors.multiply(numerfactors))).
	    multiply(denomfactors.multiply(denomfactors));

	BigRational outside = outfac.multiply(sumvar);
	outside = (outside.multiply(numerfactors)).divide(denomfactors);

	
	// get an approximate (decimal) expression for an answer:

	BigDecimal bigapprox = (new BigDecimal(inside.numerator)).
	    setScale(100);
	bigapprox = bigapprox.divide((new BigDecimal(inside.denominator)).
				     setScale(100),BigDecimal.ROUND_HALF_DOWN);
	approxanswer = java.lang.Math.sqrt(bigapprox.doubleValue());
		   
	bigapprox = (new BigDecimal(outside.numerator)).setScale(100);
	bigapprox = bigapprox.divide((new BigDecimal(outside.denominator)).
				     setScale(100),BigDecimal.ROUND_HALF_DOWN);

	approxanswer = approxanswer * bigapprox.doubleValue();

	approxfield.setText("");
	approxfield.setText(approxfield.getText()+
			    java.lang.Double.toString(approxanswer));


	// print out the analytic answer

	outfield.setText("");
	
	if(isneg==true) outfield.setText("-");
	if(inside.numerator.compareTo(new BigInteger("0"))==0) {
	    outfield.setText("0");} 
	else if(outside.numerator.compareTo(new BigInteger("0"))==0) {
	    outfield.setText("0");} 
	else { 
	    if(outside.equals(ONE_R) == false) {
		outfield.setText(outfield.getText()+"("+
				 outside.abs().toString()+")"); }
	    if (inside.equals(ONE_R)==false) {
		outfield.setText(outfield.getText()+"*sqrt("+
				 inside.toString()+")");}}
	go.setEnabled(true);
	clear.setEnabled(true);
    }
    
    //
    // Here follows the factorial method (function) for BigIntegers. 
    // It is a class method, so must be static.
    //

    public static BigInteger factorial(BigInteger a) {
	if (a.compareTo(new BigInteger("0"))<0) 
	    return new BigInteger("0"); 
	if (a.compareTo(new BigInteger("0"))==0 ||
	    a.compareTo(new BigInteger("1"))==0) {
	    return new BigInteger("1");
	}
	else {
	    return a.multiply(factorial(a.subtract(new BigInteger("1"))));
	}
    }    // end of factorial method

    // 
    // A method to simplify sqrt(I) where I is a BigInteger, by checking
    // I for factors which are perfect squares.
    //
    
    public static BigInteger squarefactor(BigInteger a) {
	BigInteger i = new BigInteger("2");
	BigInteger result = new BigInteger("1");
	while(i.multiply(i).compareTo(a.min(new BigInteger("10000000")))<=0) {
	    if ( a.mod(i.multiply(i)).compareTo(new BigInteger("0"))==0) {
		return (i.multiply(squarefactor(a.divide(i.multiply(i))))); }
	    i = i.add(new BigInteger("1"));
	}
	return result;
    }

    // 
    // A method to give the oft-occuring "Delta" quantity, squared
    //
    public static BigRational delta(BigRational j1, BigRational j2,
				    BigRational j) {
	final BigRational ONE_R = new BigRational(1);
	BigRational d = new 
	    BigRational(factorial( (j1.add(j2)).subtract(j).toBigInteger() ),
			factorial( (((j1.add(j2)).add(j)).add(ONE_R)
				    ).toBigInteger() ) );
	d=d.multiply(factorial(j1.add(j).subtract(j2).toBigInteger()));
	d=d.multiply(factorial(j2.add(j).subtract(j1).toBigInteger()));
	return d;
    }
}

