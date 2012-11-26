package alg.pso;

import objfun.ObjectiveFunction;
import alg.ConfString;
import alg.OA;
import alg.OAParams;
import def.OAFactory;

public class PSOFactory extends OAFactory {
	public String getName() {
		return "PSO";
	}

	public OA getOA(ObjectiveFunction of, OAParams oaParams) {
		OA oa = new PSO(of, (PSOParams)oaParams);

		return oa;
	}

	public OAParams getOAParams(ConfString cs) {
		return PSOParams.fromMap(cs.toMap());
	}
}
