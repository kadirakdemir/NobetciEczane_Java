package Services;

import Models.Ayar;

public interface IAyarService extends IService<Ayar> {
	String getAyarIlceAd();
    int getByAyarIlceId();
}
