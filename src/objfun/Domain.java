package objfun;

public class Domain {
 public final Interval[] d;
 
 public Domain(Interval[] d) {
 	this.d = d;
 }
 
 public boolean in(double[] p) {
 	int i;
 	for(i=0;i<d.length;i++)
 		if(!d[i].in(p[i])) return false;
 	return true;
 }
 
 static Domain fromInterval(Interval pd,int nd) {
 	Interval[] dtmp = new Interval[nd];
 	int i;
 	for(i=0;i<nd;i++) {
 		dtmp[i] = pd; //no need to clone them since they're immutable
 	}
 	return new Domain(dtmp);
 }
}
