package Services;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import DataConnection.NobetciDBConnection;
import Models.Nobetci;

public class NobetciService extends BaseService<Nobetci> implements INobetciService {
	
    private final AyarService _ayarService;
    String _tarih;

    public NobetciService(NobetciDBConnection connection, AyarService ayarService) {
        super(connection);
        _ayarService = ayarService;
    }

    public int getSize(){
        try{
            int i=0;
            _resultSet =super.result("Select * from Nobetci");
            while (_resultSet.next()){
                i++;
            }
            return i;
        }catch (Exception e){

        }
        return 0;
    }

    public int getSelectedSize(){
        try{
            int i=0;
            _resultSet =super.result("Select * from Nobetci where nobetciIlceId='"+_ayarService.getByAyarIlceId()+"' and nobetciDurum=1");
            while (_resultSet.next()){
                i++;
            }
            return i;
        }catch (Exception e){

        }
        return 0;
    }

    public List<Nobetci> getNobetciAll(){
        try {
            List<Nobetci> nobetciList =new ArrayList<Nobetci>();

            ResultSet rs=super.result("Select * from Nobetci");
            while (rs.next()){
                Nobetci nobetci=new Nobetci();
                nobetci.nobetciId= rs.getInt("nobetciId");
                nobetci.nobetciAd=rs.getString("nobetciAd");
                nobetci.nobetciAdres=rs.getString("nobetciAdres");
                nobetci.nobetciTarif=rs.getString("nobetciTarif");
                nobetci.nobetciTel=rs.getString("nobetciTel");
                nobetci.nobetciTarih=rs.getTime("nobetciTarih");
                nobetciList.add(nobetci);
            }
            return nobetciList;
        }catch (Exception e){

        }
        return null;
    }

    public List<Nobetci> getNobetciAyarIlceAll(){
        try {
            List<Nobetci> nobetciList =new ArrayList<Nobetci>();

            ResultSet rs=super.result("Select * from Nobetci where nobetciIlceId="+_ayarService.getByAyarIlceId());
            while (rs.next()){
                Nobetci nobetci=new Nobetci();
                nobetci.nobetciId= rs.getInt("nobetciId");
                nobetci.nobetciAd=rs.getString("nobetciAd");
                nobetci.nobetciAdres=rs.getString("nobetciAdres");
                nobetci.nobetciTarif=rs.getString("nobetciTarif");
                nobetci.nobetciTel=rs.getString("nobetciTel");
                nobetci.nobetciHaritaAdres=rs.getString("nobetciHaritaAdres");
                nobetci.nobetciTarih=rs.getTime("nobetciTarih");
                nobetciList.add(nobetci);
            }
            return nobetciList;
        }catch (Exception e){

        }
        return null;
    }
    public List<Nobetci> getNobetciAyarIlceTarihAll(){
        try {
            List<Nobetci> nobetciList =new ArrayList<Nobetci>();

            Calendar calendar=Calendar.getInstance();
            int year= calendar.get(Calendar.YEAR);
            int mounth=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            String systemTarih = year+"-"+(mounth+1)+"-"+day;

            ResultSet rs=super.result("Select * from Nobetci where nobetciIlceId="+_ayarService.getByAyarIlceId());
            while (rs.next()){

                String nobetciControlTarih=rs.getDate("nobetciTarih").toString();
                nobetciControlTarih.substring(0,9);

                if (nobetciControlTarih.equals(systemTarih)){
                    Nobetci nobetci=new Nobetci();
                    nobetci.nobetciId= rs.getInt("nobetciId");
                    nobetci.nobetciAd=rs.getString("nobetciAd");
                    nobetci.nobetciAdres=rs.getString("nobetciAdres");
                    nobetci.nobetciTarif=rs.getString("nobetciTarif");
                    nobetci.nobetciTel=rs.getString("nobetciTel");
                    nobetci.nobetciTarih=rs.getDate("nobetciTarih");
                    nobetciList.add(nobetci);
                }
            }
            return nobetciList;
        }catch (Exception e){

        }
        return null;
    }
    public String getDate(){
        try {

            ResultSet resultSet=super.result("Select * from Nobetci");
            while (resultSet.next()){
                _tarih=resultSet.getDate("nobetciTarih").toString();
            }
            return _tarih;
        }catch (Exception e){

        }
        return  null;
    }
}
