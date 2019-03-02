package Services;

import java.sql.ResultSet;

public interface IService<TEntity> {

	ResultSet result(String query);
	boolean Connect(); 
	TEntity findId(int id);
}
