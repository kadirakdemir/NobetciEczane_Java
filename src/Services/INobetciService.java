package Services;

import java.util.List;

import Models.Nobetci;

public interface INobetciService {
	int getSize();
	List<Nobetci> getNobetciAll();
	List<Nobetci> getNobetciAyarIlceAll();
	List<Nobetci> getNobetciAyarIlceTarihAll();
	String getDate();
}
