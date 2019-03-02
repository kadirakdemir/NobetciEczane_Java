package Services;

import Models.Ilce;

public interface IIlceService extends IService<Ilce> {
	String getIlceAd();
	String getIlceAd(int id);
	int getByIlceId();
}
