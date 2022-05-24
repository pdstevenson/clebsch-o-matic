import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;

public class TJ extends Applet implements ActionListener, Runnable {
    
    Thread playing;
    
    // declare our A.M values out here
    
    BigRational j1,j2,j,m1,m2,m;

    // Let's allow the user to input the numbers as reals. 
    // a double will be just fine here.
    double hj1,hj2,hj,hm1,hm2,hm;

    
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
    Label jlabel = new Label(" j=",Label.RIGHT);
    TextField jfield = new TextField(4);

    // row three has the m quantum numbers
    Panel row3 = new Panel();
    Label m1label = new Label("m1=",Label.RIGHT);
    TextField m1field = new TextField(4);
    Label m2label = new Label("m2=",Label.RIGHT);
    TextField m2field = new TextField(4);
    Label mlabel = new Label("m=",Label.RIGHT);
    TextField mfield = new TextField(4);

    // row four has the buttons
    Panel row4 = new Panel();
    Button go = new Button("Go!");
    Button clear = new Button("Clear");

    // row five has the answer
    Panel row5 = new Panel();
    TextField outfield = new TextField(70);

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
	row2.add(jlabel);
	row2.add(jfield);
	add(row2);
	

	FlowLayout layout3 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row3.setLayout(layout3);
	row3.add(m1label);
	row3.add(m1field);
	row3.add(m2label);
	row3.add(m2field);
	row3.add(mlabel);
	row3.add(mfield);
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
	m1field.setText(null);
	j2field.setText(null);
	m2field.setText(null);
	jfield.setText(null);
	mfield.setText(null);
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
	    hm1 = (new java.lang.Double(m1field.getText())).doubleValue();
	    hj2 = (new java.lang.Double(j2field.getText())).doubleValue();
	    hm2 = (new java.lang.Double(m2field.getText())).doubleValue();
	    hj = (new java.lang.Double(jfield.getText())).doubleValue();
	    hm = (new java.lang.Double(mfield.getText())).doubleValue();
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
	m1 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hm1))),
			     new BigInteger("2"));
	j2 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj2))),
			     new BigInteger("2"));
	m2 = new BigRational(new BigInteger(Long.toString(Math.round(2.*hm2))),
			     new BigInteger("2"));
	j = new BigRational(new BigInteger(Long.toString(Math.round(2.*hj))),
			     new BigInteger("2"));
	m = new BigRational(new BigInteger(Long.toString(Math.round(2.*hm))),
			     new BigInteger("2"));

	// we are re-using the cleb code, so for a 3-j symbol, we change 
	// the sign of m1 and m2;
	//m = m.multiply(new BigInteger("-1"));
	m1 = m1.multiply(new BigInteger("-1"));
	m2 = m2.multiply(new BigInteger("-1"));
	// There is a delta function on the m-values - so we catch this one
	// early on:
	
	if ( ! ((m1.add(m2)).equals(m)) ) {
	    outfield.setText("0 (m1+m2+m not zero)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);
	    return;
	}

	// Another problem with the input would be if any of the m's have a
	// bigger magnitude than their j.
	
	if ( Math.abs(hm1) > hj1) {
	    outfield.setText("0 (|m1| greater than j1)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if ( Math.abs(hm2) > hj2 ) {
	    outfield.setText("0 (|m2| greater than j2)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if ( Math.abs(hm) > hj ) {
	    outfield.setText("0 (|m| greater than j)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	
	// also, each of the values 2m must have the same parity as their
	// corresponding 2j, for physical reasons.
	
	if (Math.abs(Math.abs(hm1-hj1)-Math.round(Math.abs(hm1-hj1)))>0.0001 ){
	    outfield.setText("0 (m1="+m1field.getText()+
			     " is not consistent with j1="+
			     j1field.getText()+")");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if (Math.abs(Math.abs(hm2-hj2)-Math.round(Math.abs(hm2-hj2)))>0.0001){
	    outfield.setText("0 (m2="+m2field.getText()+
			     " is not consistent with j2="+
			     j2field.getText()+")");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if (Math.abs(Math.abs(hm-hj)-Math.round(Math.abs(hm-hj)))>0.0001 ) {
	    outfield.setText("0 (m="+mfield.getText()+
			     " is not consistent with j="+
			     jfield.getText()+")");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	
	// and finally... the j's must satisfy the triangle relations..
	
	if(Math.abs(hj1-hj2) > hj || Math.abs(hj1+hj2) < hj) {
	    outfield.setText("0 (j1, j2 and j do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	
	//
	//  We get this far if we pass all the tests for suitability of
	// input parameters. Now nothing can go wrong :-)
	//
	
	
	// now we must calculate the fraction delta(j1,j2,j)^2
	BigRational delta = new 
	    BigRational(factorial( (j1.add(j2)).subtract(j).toBigInteger() ),
			factorial( (((j1.add(j2)).add(j)).add(ONE_R)
				    ).toBigInteger() ) );
	delta=delta.multiply(factorial(j1.add(j).subtract(j2).toBigInteger()));
	delta=delta.multiply(factorial(j2.add(j).subtract(j1).toBigInteger()));
	
	// There are also a bunch of factors sitting outside the 
	// sum which must be calculated.
	BigInteger outfactors = 
	    factorial(j1.add(m1).toBigInteger())
	    .multiply( factorial(j1.subtract(m1).toBigInteger()) )
	    .multiply( factorial(j2.add(m2).toBigInteger()))
	    .multiply( factorial(j2.subtract(m2).toBigInteger()) )
	    .multiply( factorial(j.add(m).toBigInteger()) )
	    .multiply( factorial(j.subtract(m).toBigInteger()))
	    .multiply( (j.add(j)).add(ONE_R) .toBigInteger() );
	// Now we can do the sum. Happy Happy Joy Joy.
	BigInteger t1 = j1.subtract(m1).toBigInteger();
	BigInteger t2 = j.add(m1).subtract(j2).toBigInteger();
	BigInteger t3 = j2.add(m2).toBigInteger();
	BigInteger t4 = (j.subtract(j1)).subtract(m2).toBigInteger();
	BigInteger t5 = j1.add(j2).subtract(j).toBigInteger();

	BigInteger nulo = new BigInteger("0");
	if(nulo.compareTo(t2.negate()) < 0) nulo = t2.negate();
	if(nulo.compareTo(t4.negate()) < 0) nulo = t4.negate();
	BigInteger nuhi = t1;
	if(nuhi.compareTo(t3) > 0) nuhi = t3;
	if(nuhi.compareTo(t5) > 0) nuhi = t5;

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
		    add(new BigRational(new 
					BigInteger("1"),
					factorial(t1.subtract(nu)
						  ).multiply(factorial(t2.
								       add(nu))
							     ).
					multiply(factorial(t3.subtract(nu))
						 ).
					multiply(factorial(t4.add(nu))
						 ).
					multiply(factorial(t5.subtract(nu))
						 ).multiply(factorial(nu))));
	    }
	    else
		sumvar=sumvar.
		    subtract( new BigRational(new BigInteger("1"),
					      factorial(t1.subtract(nu)
							).
					      multiply(factorial(t2.add(nu))
						       ).
					      multiply(factorial(t3.
								 subtract(nu))
						       ).
					      multiply(factorial(t4.add(nu))
						       ).
					      multiply(factorial(t5.
								 subtract(nu))
						       ).
					      multiply(factorial(nu))));
	}	
	
	// Now we have everything and we just need to do a little 
	// manipulation for output.

	boolean isneg=false;
	
	// multiply cleb result by appropriate phase for 3j;
	if( (((j.add(m)).divide(new BigInteger("2")).add(j1))
	     .denominator).equals(new BigInteger("2"))) {
	    sumvar = sumvar.multiply(new BigInteger("-1"));
	}
	if(sumvar.numerator.compareTo(new BigInteger("0"))<0) isneg=true;

	// include extra multiplicative factor for 3j as opposed to cleb
	// Let's stick it on delta since it's already a rational object
	// which is implicitly square-rooted
	delta = delta.divide((j.add(j)).add(ONE_R));

	BigRational inside = delta.multiply(outfactors);
	outfield.setText("Simplifying Expression");
	BigInteger denomfactors = squarefactor(inside.denominator);
	BigInteger numerfactors = squarefactor(inside.numerator);

	inside = (inside.divide(numerfactors.multiply(numerfactors))).
	    multiply(denomfactors.multiply(denomfactors));

	BigRational outside = sumvar;
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
		if(inside.equals(ONE_R) == true) {
		    outfield.setText(outfield.getText()+
				     outside.abs().toString());}
		else {
		    outfield.setText(outfield.getText()+"("+
				     outside.abs().toString()+")*"); }}
	    if (inside.equals(ONE_R)==false) {
		outfield.setText(outfield.getText()+"sqrt("+
				 inside.toString()+")");}
  	    else if(outside.equals(ONE_R) == true &&
		    inside.equals(ONE_R) == true)
  		{outfield.setText(outfield.getText()+"1");}}
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
}

