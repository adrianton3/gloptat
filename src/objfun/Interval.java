package objfun;

public class Interval {
 public final double l;
 public final double r;
 
 public Interval(double l, double r) {
 	this.l = l;
 	this.r = r;
 }
 
 boolean in(double p) {
 	return p >= l && p <= r;
 }
}
