package adrianton.gloptat.alg.pso;

import adrianton.gloptat.alg.ConfString;
import adrianton.gloptat.alg.OA;
import adrianton.gloptat.alg.OAParams;
import adrianton.gloptat.app.OAFactory;
import adrianton.gloptat.objfun.ObjectiveFunction;

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
