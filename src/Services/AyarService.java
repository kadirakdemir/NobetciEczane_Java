package Services;

import java.sql.ResultSet;

import DataConnection.NobetciDBConnection;
import Models.Ayar;

public class AyarService extends BaseService<Ayar> implements IAyarService {
	
	  private IlceService _ilceService;
	  
	    public AyarService(NobetciDBConnection connection) {
	        super(connection);
	        _ilceService=new IlceService(connection);
	    }

	    public String getAyarIlceAd(){
	        try {
	          return  _ilceService.getIlceAd(getByAyarIlceId());
	        }catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }
	        return null;
	    }

	    public int getByAyarIlceId(){
	        try {
	            ResultSet rs= super.result("Select * from Ayar");
	            while (rs.next()){
	                return rs.getInt("ayarIlceId");
	            }
	        }catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }
	        return 0;
	    }
}
