package adrianton.gloptat.alg.ran;

import adrianton.gloptat.alg.ConfString;
import adrianton.gloptat.alg.OA;
import adrianton.gloptat.alg.OAFactory;
import adrianton.gloptat.alg.OAParams;
import adrianton.gloptat.objfun.ObjectiveFunction;

public class RandomSearcherFactory extends OAFactory {
	public String getName() {
		return "RandomSearcher";
	}

	public OA getOA(ObjectiveFunction of, OAParams oaParams) {
		OA oa = new RandomSearcher(of, (RandomSearcherParams)oaParams);

		return oa;
	}

	public OAParams getOAParams(ConfString cs) {
		return RandomSearcherParams.fromMap(cs.toMap());
	}
}
