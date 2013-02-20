package adrianton.gloptat.alg.ga;

import adrianton.gloptat.alg.ConfString;
import adrianton.gloptat.alg.OA;
import adrianton.gloptat.alg.OAParams;
import adrianton.gloptat.app.OAFactory;
import adrianton.gloptat.objfun.ObjectiveFunction;

public class GAFactory extends OAFactory {
	public String getName() {
		return "GA";
	}

	public OA getOA(ObjectiveFunction of, OAParams oaParams) {
		OA oa = new GA(of,(GAParams)oaParams); //decomposition of oa params should be done in factory
		//can throw exception
		
		return oa;
	}

	public OAParams getOAParams(ConfString cs) {
		return GAParams.fromMap(cs.toMap());
	}
}
