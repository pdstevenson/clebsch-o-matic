import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;

public class SJ extends Applet implements ActionListener, Runnable {
    
    Thread playing;
    
    // declare our A.M values out here
    
    BigRational j1,j2,j3,j4,j5,j6;

    // Let's allow the user to input the numbers as reals. 
    // a double will be just fine here.
    double hj1,hj2,hj3,hj4,hj5,hj6;

    
    // the approximate answer as a 'double'

    double approxanswer;

    
    // panels for the rows of GUI things:
    
    // row one has some text:
    Panel row1 = new Panel();
    Label instructions = new Label
	("Enter the values of a.m. in the boxes below..",Label.CENTER);

    // row two has the j quantum numbers
    Panel row2 = new Panel();
    Label j1label = new Label("j1=",Label.RIGHT);
    TextField j1field = new TextField(4);
    Label j2label = new Label("j2=",Label.RIGHT);
    TextField j2field = new TextField(4);
    Label j3label = new Label("j3=",Label.RIGHT);
    TextField j3field = new TextField(4);

    // row three has the m quantum numbers
    Panel row3 = new Panel();
    Label j4label = new Label("j4=",Label.RIGHT);
    TextField j4field = new TextField(4);
    Label j5label = new Label("j5=",Label.RIGHT);
    TextField j5field = new TextField(4);
    Label j6label = new Label("j6=",Label.RIGHT);
    TextField j6field = new TextField(4);

    // row four has the buttons
    Panel row4 = new Panel();
    Button go = new Button("Go!");
    Button clear = new Button("Clear");

    // row five has the answer
    Panel row5 = new Panel();
    TextField outfield = new TextField(80);

    // row six has the approximate answer
    Panel row6 = new Panel();
    Label approxlabel = new Label("approx:",Label.RIGHT);
    TextField approxfield = new TextField(40);
    
    
    public void init() {
	
	Font myfont = new Font("SansSerif", Font.PLAIN, 12);
	setFont(myfont);
	
	// set up the layout of the GUI
	
	GridLayout mylayout = new GridLayout(5,1,1,1);
	setLayout(mylayout);
	
	FlowLayout layout1 = new FlowLayout(FlowLayout.CENTER, 1,1);
	row1.setLayout(layout1);
	row1.add(instructions);
	//add(row2);
	
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
	row4.add(go);
	row4.add(clear);
	add(row4);

	FlowLayout layout5 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row5.setLayout(layout5);
	outfield.setEditable(false);
	row5.add(outfield);
	add(row5);
	
	FlowLayout layout6 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row6.setLayout(layout6);
	approxfield.setEditable(false);
	row6.add(approxlabel);
	row6.add(approxfield);
	add(row6);
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


	
	// Conditions: the j's must satisfy some triangle relations..
	
	if(Math.abs(hj1-hj2) > hj3 || Math.abs(hj1+hj2) < hj3) {
	    outfield.setText("0 (j1, j2 and j3 do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if(Math.abs(hj1-hj5) > hj6 || Math.abs(hj1+hj5) < hj6) {
	    outfield.setText("0 (j1, j5 and j6 do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if(Math.abs(hj4-hj2) > hj6 || Math.abs(hj4+hj2) < hj6) {
	    outfield.setText("0 (j4, j2 and j6 do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if(Math.abs(hj4-hj5) > hj3 || Math.abs(hj4+hj5) < hj3) {
	    outfield.setText("0 (j4, j5 and j3 do not satisfy the triangle"+
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
	if( (new Double(hj1+hj6+hj5)).intValue()*2 != 
	    (new Double(2*(hj1+hj6+hj5))).intValue() ) {
	    outfield.setText("0 (j1 and j5 cannot couple to j6)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if( (new Double(hj4+hj2+hj6)).intValue()*2 != 
	    (new Double(2*(hj4+hj2+hj6))).intValue() ) {
	    outfield.setText("0 (j4 and j2 cannot couple to j6)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if( (new Double(hj4+hj5+hj3)).intValue()*2 != 
	    (new Double(2*(hj4+hj5+hj3))).intValue() ) {
	    outfield.setText("0 (j4 and j5 cannot couple to j3)");
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
	BigRational deltas = new BigRational();
	delta1 = delta(j1,j2,j3);
	delta2 = delta(j2,j4,j6);
	delta3 = delta(j1,j5,j6);
	delta4 = delta(j3,j4,j5);
	deltas = (delta1.multiply(delta2)).divide(delta3.multiply(delta4));
						  
	// Now we can do the sum. Happy Happy Joy Joy.
	// the 't' quantities are used to find the limits of the sum;
	BigInteger t1 = j1.subtract(j2).add(j4).add(j5).toBigInteger();
	BigInteger t2 = j3.subtract(j2).add(j5).add(j6).toBigInteger();
	BigInteger t3 = j1.add(j3).add(j4).add(j6).add(ONE_R).toBigInteger();
	BigInteger t4 = j1.subtract(j2).add(j3).toBigInteger();
	BigInteger t5 = j4.subtract(j2).add(j6).toBigInteger();
	BigInteger t6 = j1.add(j5).add(j6).add(ONE_R).toBigInteger();
	BigInteger t7 = j3.add(j4).add(j5).add(ONE_R).toBigInteger();

	BigInteger nulo = new BigInteger("0");
	BigInteger nuhi = t1;
	if(nuhi.compareTo(t2) > 0) nuhi = t2;
	if(nuhi.compareTo(t3) > 0) nuhi = t3;
	if(nuhi.compareTo(t4) > 0) nuhi = t4;
	if(nuhi.compareTo(t5) > 0) nuhi = t5;
	if(nuhi.compareTo(t6) > 0) nuhi = t6;
	if(nuhi.compareTo(t7) > 0) nuhi = t7;

	// now we are ready to perform the sum. Ugh.
	
	BigRational sumvar = new BigRational(0);
	float pcdone = 0.0f;
	int range = (nuhi.subtract(nulo)).intValue();
	float pcpernu = 100.0f/(float) range;
	
	
	for (BigInteger nu = nulo; nu.compareTo(nuhi)<=0;
	     nu=nu.add(new BigInteger("1"))) {
	    
	    if(range >=10) {
		outfield.setText("Thinking.."+
				 java.lang.Integer.toString((int)pcdone)+
				 "% done.");
		pcdone += pcpernu;
	    }
	    
	    if(nu.mod(new BigInteger("2")).compareTo(new BigInteger("0")
						     ) == 0) {
		sumvar=sumvar.
		    add(new BigRational(factorial(t1.subtract(nu))
					.multiply(factorial(t2.subtract(nu)))
					.multiply(factorial(t3.subtract(nu))),
					factorial(t4.subtract(nu))
					.multiply(factorial(t5.subtract(nu)))
					.multiply(factorial(t6.subtract(nu)))
					.multiply(factorial(t7.subtract(nu)))
					.multiply(factorial(nu))));
	    }
	    else
		sumvar=sumvar.
		    subtract(new BigRational(factorial(t1.subtract(nu))
					.multiply(factorial(t2.subtract(nu)))
					.multiply(factorial(t3.subtract(nu))),
					factorial(t4.subtract(nu))
					.multiply(factorial(t5.subtract(nu)))
					.multiply(factorial(t6.subtract(nu)))
					.multiply(factorial(t7.subtract(nu)))
					.multiply(factorial(nu))));
	}	
	
	// Now we have everything and we just need to do a little 
	// manipulation for output.

	boolean isneg=false;
	
	// multiply result by appropriate phase for 6j;
	if( ((j1.add(j3).add(j4).add(j6)).divide(new BigInteger("2"))
	     .denominator).equals(new BigInteger("2"))) {
	    sumvar = sumvar.multiply(new BigInteger("-1"));
	}
	if(sumvar.numerator.compareTo(new BigInteger("0"))<0) isneg=true;

	BigRational inside = deltas;
	outfield.setText("Simplifying Expression");
	BigInteger denomfactors = squarefactor(inside.denominator);
	BigInteger numerfactors = squarefactor(inside.numerator);

	inside = (inside.divide(numerfactors.multiply(numerfactors))).
	    multiply(denomfactors.multiply(denomfactors));

	BigRational outside = sumvar;
	outside = (outside.multiply(numerfactors)).divide(denomfactors);

	
	// get an approximate (decimal) expression for an answer:

	BigDecimal bigapprox = (new BigDecimal(inside.numerator)).
	    setScale(500);
	bigapprox = bigapprox.divide((new BigDecimal(inside.denominator)).
				     setScale(500),BigDecimal.ROUND_HALF_DOWN);
	approxanswer = java.lang.Math.sqrt(bigapprox.doubleValue());
		   
	bigapprox = (new BigDecimal(outside.numerator)).setScale(500);
	bigapprox = bigapprox.divide((new BigDecimal(outside.denominator)).
				     setScale(500),BigDecimal.ROUND_HALF_DOWN);

	approxanswer = approxanswer * bigapprox.doubleValue();

	approxfield.setText("");
	approxfield.setText(approxfield.getText()+
			    java.lang.Double.toString(approxanswer));


	// print out the analytic answer

	outfield.setText("");
	
	if(inside.numerator.compareTo(new BigInteger("0"))==0) {
	    outfield.setText("0");} 
	else if(outside.numerator.compareTo(new BigInteger("0"))==0) {
	    outfield.setText("0");} 
	else { 
	    if(outside.equals(ONE_R) == false) {
		if (inside.equals(ONE_R)==false) {
		    outfield.setText(outfield.getText()+"("+
				     outside.abs().toString()+")*"); }
		else
		    outfield.setText(outfield.getText()+
				     outside.abs().toString()); }
	    if (inside.equals(ONE_R)==false) {
		outfield.setText(outfield.getText()+"sqrt("+
				 inside.toString()+")");}
	}
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
    // I for factors which are exact squares.
    //
    
    public static BigInteger squarefactor(BigInteger a) {
	BigInteger i = new BigInteger("2");
	BigInteger result = new BigInteger("1");
	// we stop at a predetermined value rather than completely factorize
	// the number:
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

