package Services;

import java.sql.ResultSet;

import DataConnection.NobetciDBConnection;
import Models.Ilce;

public class IlceService extends BaseService<Ilce> implements IIlceService {
	
    public IlceService(NobetciDBConnection connection) {
        super(connection);
    }

    public String getIlceAd(){
        try {
            ResultSet rs= super.result("Select * from Ilce");
            while (rs.next()){
              return rs.getString("ilceAd");
            }
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       return null;
    }

    public String getIlceAd(int id){
        try {
            ResultSet rs= super.result("Select * from Ilce where ilceId="+id);
            while (rs.next()){
                return rs.getString("ilceAd");
            }
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public int getByIlceId(){
        try {
            ResultSet rs= super.result("Select ilceId from Ilce");
            while (rs.next()){
                return rs.getInt("ilceId");
            }
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return 0;
    }
}
