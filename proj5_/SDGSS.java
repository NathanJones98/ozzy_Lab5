
public class SDGSS extends SteepestDescent {
	private final double _PHI_ = (1. + Math . sqrt (5) ) /2.;
	private double maxStep ; // Armijo max step size
	private double minStep ; // Armijo beta parameter
	private double delta ; // Armijo delta parameter
	
	// constructors
	public SDGSS () {
		
	}
	public SDGSS ( double maxStep , double minStep , double delta ) {
		
	}
	
	 // getters
	public double getMaxStep () {
		return -1;
	}
	public double getMinStep () {
		return -1;
	}
	public double getDelta () {
		return -1;
	}
	
	 // setters
	public void setMaxStep ( double a ) {
		
	}
	public void setMinStep ( double a ) {
		
	}
	public void setDelta ( double a ) {
		
	}
	
	 // other methods
	public double lineSearch ( Polynomial P , double [] x ) // step size from GSS
	{
		return -1;
	}
	//public boolean getParamsUser () // get algorithm parameters from user
	//{
		
	//}
	//public void print () // print parameters
	private double GSS ( double a , double b , double c , double [] x , double [] dir ,Polynomial P ) {
		return -1;
	}
}