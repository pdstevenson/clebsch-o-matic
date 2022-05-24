import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;

public class Cleblet extends Applet implements ActionListener, Runnable {
    
    Thread playing;
    
    // declare our A.M values out here
    
    BigRational j1,j2,j,m1,m2,m;

    // Let's allow the user to input the numbers as reals. 
    // a double will be just fine here.
    double hj1,hj2,hj,hm1,hm2,hm;

    
    // the approximate answer as a 'double'

    double approxanswer;

    
    // panels for the rows of GUI items:
    
    Panel row1 = new Panel();
    Label j1label = new Label("<j1=",Label.RIGHT);
    TextField j1field = new TextField(4);
    Label m1label = new Label("m1=",Label.RIGHT);
    TextField m1field = new TextField(4);
    Label j2label = new Label("j2=",Label.RIGHT);
    TextField j2field = new TextField(4);
    Label m2label = new Label("m2=",Label.RIGHT);
    TextField m2field = new TextField(4);
    Label jlabel = new Label("| j=",Label.RIGHT);
    TextField jfield = new TextField(4);
    Label mlabel = new Label("m=",Label.RIGHT);
    TextField mfield = new TextField(4);
    Label endket = new Label(">",Label.LEFT);
    Button go = new Button("Go!");
    Button clear = new Button("Clear");
    Panel row3 = new Panel();
    Panel row4 = new Panel();
    TextField outfield = new TextField(70);
    Label approxlabel = new Label("approx:",Label.RIGHT);
    TextField approxfield = new TextField(40);
    
    
    // initialization routine: set up the GUI
    public void init() {
	
	Font myfont = new Font("SansSerif", Font.PLAIN, 12);
	setFont(myfont);
	
	// set up the layout of the GUI
	
	GridLayout mylayout = new GridLayout(3,1,1,1);
	setLayout(mylayout);
		
	FlowLayout layout2 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row1.setLayout(layout2);
	row1.add(j1label);
	row1.add(j1field);
	row1.add(m1label);
	row1.add(m1field);
	row1.add(j2label);
	row1.add(j2field);
	row1.add(m2label);
	row1.add(m2field);
	row1.add(jlabel);
	row1.add(jfield);
	row1.add(mlabel);
	row1.add(mfield);
	row1.add(endket);
	row1.add(go);
	row1.add(clear);
	add(row1);
	
	FlowLayout layout3 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row3.setLayout(layout3);
	outfield.setEditable(false);
	row3.add(outfield);
	add(row3);
	
	FlowLayout layout4 = new FlowLayout(FlowLayout.CENTER, 1, 1);
	row4.setLayout(layout4);
	approxfield.setEditable(false);
	row4.add(approxlabel);
	row4.add(approxfield);
	add(row4);
	// done setting up the layout. Now add some listeners (to the buttons)

	go.addActionListener(this);
	clear.addActionListener(this);
    }
    
    
    // Set up button listener
    public void actionPerformed(ActionEvent event) {
	String command = event.getActionCommand();
	if(command == "Go!") { 
	    playing = new Thread(this);
	    playing.start();
	    go.setEnabled(false);
	    clear.setEnabled(false);
	    outfield.setText(null);
	    approxfield.setText(null);
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
    
    // This is where the thread starts when the user hits 'go'.
    public void run() {
	
	final BigRational ONE_R = new BigRational(1);
	
	
	// We get the input from the input boxes and convert to the 
	// BigInteger type.
	// This can throw an exception if bad input is given, so let's catch
	// it if we can.
	
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
	
	// There is a kronecker delta on the m-values - so we catch this one
	// early on:
	
	if ( ! ((m1.add(m2)).equals(m)) ) {
	    outfield.setText("0 (m1+m2 not equal to m)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);
	    return;
	}
	
	// Another problem with the input would be if any of the m's have a
	// bigger magnitude than their j.
	
	if ( Math.abs(hm1) > hj1) {
	    outfield.setText("0  (|m1| is greater than j1)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if ( Math.abs(hm2) > hj2 ) {
	    outfield.setText("0  (|m2| is greater than j2)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	if ( Math.abs(hm) > hj ) {
	    outfield.setText("0  (|m| is greater than j)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	
	// also, each of the values 2m must have the same parity as their
	// corresponding 2j.
	
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
			     " is not consistent with j="
			     +jfield.getText()+")");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	
	// and finally, the j's must satisfy the triangle relations..
	
	if(Math.abs(hj1-hj2) > hj || Math.abs(hj1+hj2) < hj) {
	    outfield.setText("0 (j1, j2 and j do not satisfy the triangle"+
			     " relation)");
	    approxfield.setText("0.0");
	    go.setEnabled(true);
	    clear.setEnabled(true);      
	    return;
	}
	
	//
	// Now all trivial zeros have been accounted for, so we proceed
	// with calculation
	//
	
	
	// First we calculate the fraction delta(j1,j2,j)^2
	BigRational delta = new 
	    BigRational(factorial( (j1.add(j2)).subtract(j).toBigInteger() ),
			factorial( (((j1.add(j2)).add(j)).add(ONE_R)
				    ).toBigInteger() ) );
	delta=delta.multiply(factorial(j1.add(j).subtract(j2).toBigInteger()));
	delta=delta.multiply(factorial(j2.add(j).subtract(j1).toBigInteger()));
	
	// There are also some factors sitting outside the 
	// sum which must be calculated.
	BigInteger outfactors = 
	    factorial(j1.add(m1).toBigInteger())
	    .multiply( factorial(j1.subtract(m1).toBigInteger()) )
	    .multiply( factorial(j2.add(m2).toBigInteger()))
	    .multiply( factorial(j2.subtract(m2).toBigInteger()) )
	    .multiply( factorial(j.add(m).toBigInteger()) )
	    .multiply( factorial(j.subtract(m).toBigInteger()))
	    .multiply( (j.add(j)).add(ONE_R) .toBigInteger() );

	// Now we can do the sum.  Pre-calculate the constant terms inside the
	// factorials:
	BigInteger t1 = j1.subtract(m1).toBigInteger();
	BigInteger t2 = j.add(m1).subtract(j2).toBigInteger();
	BigInteger t3 = j2.add(m2).toBigInteger();
	BigInteger t4 = (j.subtract(j1)).subtract(m2).toBigInteger();
	BigInteger t5 = j1.add(j2).subtract(j).toBigInteger();

	// Work out the limits of the sum:
	BigInteger nulo = new BigInteger("0");
	if(nulo.compareTo(t2.negate()) < 0) nulo = t2.negate();
	if(nulo.compareTo(t4.negate()) < 0) nulo = t4.negate();
	BigInteger nuhi = t1;
	if(nuhi.compareTo(t3) > 0) nuhi = t3;
	if(nuhi.compareTo(t5) > 0) nuhi = t5;

	// now we are ready to perform the sum.	
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
	if(sumvar.numerator.compareTo(new BigInteger("0"))<0) isneg=true;

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
	    setScale(200);
	bigapprox = bigapprox.divide((new BigDecimal(inside.denominator)).
				     setScale(200),BigDecimal.ROUND_HALF_DOWN);
	approxanswer = java.lang.Math.sqrt(bigapprox.doubleValue());
		   
	bigapprox = (new BigDecimal(outside.numerator)).setScale(200);
	bigapprox = bigapprox.divide((new BigDecimal(outside.denominator)).
				     setScale(200),BigDecimal.ROUND_HALF_DOWN);

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
				 outside.abs().toString()+")*"); }
	    if (inside.equals(ONE_R)==false) {
		outfield.setText(outfield.getText()+"sqrt("+
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
}

