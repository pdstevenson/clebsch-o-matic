import java.math.*;

// This class defines an object which is a rational number made up of 
// BigIntegers (which are part of the java 1.1 API) and also defines
// a number of common mathematical operations on them

public class BigRational {
    public BigInteger numerator, denominator;
    
    // Constructor methods.
    // As a pair of BigIntegers:
    public BigRational( BigInteger a, BigInteger b ) {
	this.numerator = a.divide(a.gcd(b));
    this.denominator = b.divide(a.gcd(b));
    }
    // As a single int
    public BigRational( int a ) {
	this.numerator = new BigInteger(java.lang.Integer.toString(a));
	this.denominator = new BigInteger("1");
    }
					
    // defaults to zero
    public BigRational() {
	this.numerator = new BigInteger("0");
	this.denominator = new BigInteger("1");
    }
    
    // Private class methods
    
    private BigRational simplify() {
	BigInteger a = (this.numerator).gcd(this.denominator);
	return new BigRational( (this.numerator).divide(a), 
				(this.denominator).divide(a) );
    }
    
    
    // Now our public class methods
    
    // addition of two BigRationals
    public BigRational add(BigRational val) {
	return (new BigRational( ((this.numerator).multiply(val.denominator)).add((this.denominator).multiply(val.numerator)),(this.denominator).multiply(val.denominator))).simplify();
    }
    
    // subtration of two BigRationals
    public BigRational subtract(BigRational val) {
	BigRational newval = new BigRational( val.numerator.negate(),
					      val.denominator); 
	return(this.add(newval));
    }
    
    
    // Now some multiply methods. We should be able to multiply a BigRational
    // by an int, an Integer, a BigInteger, and a BigRational.
    public BigRational multiply(BigRational val) {
	return (
		new BigRational(
				numerator.multiply(val.numerator),
				denominator.multiply(val.denominator)
				)
		).simplify();
    }
    public BigRational multiply(BigInteger val) {
	return (
		new BigRational(
				(numerator).multiply(val),denominator)
		).simplify();
    }
    
    // Division:
    public BigRational divide(BigRational val) {
	return (
		new BigRational(
				this.numerator.multiply(val.denominator),
				this.denominator.multiply(val.numerator)
				)
		).simplify();
    }
    public BigRational divide(BigInteger val) {
	return (
		new BigRational(
				this.numerator,
				this.denominator.multiply(val)
				)
		).simplify();
    }
    
    
    // Conversion of a Big Rational to a string:
    public String toString() {
	if ((this.denominator).compareTo(new BigInteger("1"))==0)
	    return (this.numerator).toString();
	else
	    return (this.numerator).toString()+"/"+
		(this.denominator).toString();
    }
    
    // Comparison of two BigRationals
    public boolean equals(BigRational val) {
	if(this.numerator.equals(val.numerator) & 
	   this.denominator.equals(val.denominator) )
	    return true;
	else
	    return false;
    }
    
    // absolute value
    public BigRational abs() {
	if(this.numerator.compareTo( new BigInteger("0") ) < 0) {
	    return ( new BigRational() ).subtract(this);
	} else { return this; }
    }
    
    // Return as a big integer; assumes that the caller knows the 
    // denominator is unity. (I suppose we could return an error otherwise)
    public BigInteger toBigInteger() {return(numerator);}
    
}


