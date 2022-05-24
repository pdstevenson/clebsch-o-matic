import java.math.*;
import java.lang.*;

public class Clebsch {
  public static void main(String argv[]) {
    
    final BigRational ONE_R = new BigRational(new BigInteger("1"),new BigInteger("1"));
    final BigRational TWO_R = new BigRational(new BigInteger("2"),new BigInteger("1"));
    final BigRational HALF_R = new BigRational(new BigInteger("1"),new BigInteger("2"));

    // for testing purposes, let us define some BigRationals to calculate Clebsches of..

    BigRational j1 = new BigRational(new BigInteger("20"),new BigInteger("2"));
    BigRational m1 = new BigRational(new BigInteger("12"),new BigInteger("2"));
    BigRational j2 = new BigRational(new BigInteger("12"),new BigInteger("2"));
    BigRational m2 = new BigRational(new BigInteger("-6"),new BigInteger("2"));
    BigRational j = new BigRational(new BigInteger("18"),new BigInteger("2"));
    BigRational m = new BigRational(new BigInteger("6"),new BigInteger("2"));


    // There is a delta function on the m-values - so we catch this one
    // early on:

    if ( ! ((m1.add(m2)).equals(m)) ) {
      System.out.print("m1+m2=m not satisfied\n");
      return;
    }

    // now we must calculate the fraction delta(j1,j2,j)^2
    BigRational delta = new BigRational(
       factorial( (j1.add(j2)).subtract(j).toBigInteger() ),
       factorial( (((j1.add(j2)).add(j)).add(ONE_R)).toBigInteger() ) );
    delta=delta.multiply( factorial( j1.add(j).subtract(j2).toBigInteger() ));
    delta=delta.multiply( factorial( j2.add(j).subtract(j1).toBigInteger() ));

    // There are also a bunch of factors sitting outside the sum which must be 
    // calculated (if we want the right answer, at least).
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

    BigRational sumvar = new BigRational(new BigInteger("0"),new BigInteger("1"));

    for (BigInteger nu = nulo; nu.compareTo(nuhi)<=0;nu=nu.add(new BigInteger("1"))) {
      System.out.print("nu="+nu.toString()+"\n");
      if(nu.mod(new BigInteger("2")).compareTo(new BigInteger("0")) == 0) {
	sumvar=sumvar.add( new BigRational(new BigInteger("1"),
	      factorial(t1.subtract(nu)).multiply(factorial(t2.add(nu))).multiply(
              factorial(t3.subtract(nu))).multiply(factorial(t4.add(nu))).multiply(
              factorial(t5.subtract(nu))).multiply(factorial(nu))));
      }
      else
	sumvar=sumvar.subtract( new BigRational(new BigInteger("1"),
	      factorial(t1.subtract(nu)).multiply(factorial(t2.add(nu))).multiply(
              factorial(t3.subtract(nu))).multiply(factorial(t4.add(nu))).multiply(
              factorial(t5.subtract(nu))).multiply(factorial(nu))));
    }	
				  
    // Now we have everything and we just need to do a little manipulation for
    // output.
    boolean isneg=false;
    if(sumvar.numerator.compareTo(new BigInteger("0"))<0) isneg=true;
    sumvar=sumvar.multiply(sumvar);

    BigRational answer = (sumvar.multiply(delta)).multiply(outfactors);

    if(isneg==true) System.out.print("-");
    System.out.print("sqrt("+answer.toString()+")\n");
  }
  
  
  //
  // Here follows the factorial method (function) for BigIntegers. It is a class
  // method, so must be static.
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



}



