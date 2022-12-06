
public class SDArmijo extends SteepestDescent {
	private double maxStep ; // Armijo max step size
	private double beta ; // Armijo beta parameter
	private double tau ; // Armijo tau parameter
	private int K ; // Armijo max no. of iterations
	
	 // constructors
	public SDArmijo () {
		
	}
	public SDArmijo ( double maxStep , double beta , double tau , int K ) {
		
	}
	
	 // getters
	 public double getMaxStep () {
		 return -1;
	 }
	 public double getBeta () {
		 return -1;
	 }
	 public double getTau () {
		 return -1;
	 }
	 public int getK () {
		 return -1;
	 }
	
	 // setters
	public void setMaxStep ( double a ) {
		
	}
	public void setBeta ( double a ) {
		
	}
	public void setTau ( double a ) {
		
	}
	public void setK ( int a ) {
		
	}
	
	 // other methods
	//public double lineSearch ( Polynomial P , double [] x ) // Armijo line search
	//{
		
	//}
	//public boolean getParamsUser () // get algorithm parameters from user
	//{
		
	//}
	public void print () {
		
	}
}
