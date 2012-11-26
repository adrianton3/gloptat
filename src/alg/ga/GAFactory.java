package alg.ga;

import objfun.ObjectiveFunction;
import alg.ConfString;
import alg.OA;
import alg.OAParams;
import def.OAFactory;

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
